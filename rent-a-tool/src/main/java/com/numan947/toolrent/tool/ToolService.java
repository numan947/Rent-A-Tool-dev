package com.numan947.toolrent.tool;

import com.numan947.toolrent.common.FileStorageService;
import com.numan947.toolrent.common.dto.PageResponseDTO;
import com.numan947.toolrent.exception.OperationNotPermittedException;
import com.numan947.toolrent.history.ToolTransactionHistory;
import com.numan947.toolrent.history.ToolTransactionHistoryRepository;
import com.numan947.toolrent.tool.dto.ToolTransactionDTO;
import com.numan947.toolrent.tool.dto.ToolRequestDTO;
import com.numan947.toolrent.tool.dto.ToolResponseDTO;
import com.numan947.toolrent.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ToolService {
    private final ToolRepository toolRepository;
    private final ToolMapper toolMapper;
    private final ToolTransactionHistoryRepository toolTransactionHistoryRepository;
    private final FileStorageService fileStorageService;


    public Long createTool(ToolRequestDTO toolRequest, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Tool tool = toolMapper.toTool(toolRequest);
        tool.setOwner(user);
        return toolRepository.save(tool).getId();
    }

    public ToolResponseDTO findToolById(Long toolId) {
        return toolRepository.findById(toolId)
                .map(toolMapper::toToolResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("No tool found with id: " + toolId));
    }

    public PageResponseDTO<ToolResponseDTO> findAllTools(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        //Paging implementation for the tools
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Tool>tools = toolRepository.findAllValidDisplayableTools(pageable, user.getId());
        List<ToolResponseDTO> content = tools.map(toolMapper::toToolResponseDTO).toList();
        return PageResponseDTO.<ToolResponseDTO>builder()
                .content(content)
                .page(tools.getNumber())
                .size(tools.getSize())
                .totalElements(tools.getTotalElements())
                .totalPages(tools.getTotalPages())
                .first(tools.isFirst())
                .last(tools.isLast())
                .build();
    }

    public PageResponseDTO<ToolResponseDTO> findAllToolsByOwner(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        //Paging implementation for the tools
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Tool>tools = toolRepository.findAll(ToolSpec.withOwnerId(user.getId()), pageable); // example of using JpaSpecificationExecutor to filter tools by owner
        List<ToolResponseDTO> content = tools.map(toolMapper::toToolResponseDTO).toList();
        return PageResponseDTO.<ToolResponseDTO>builder()
                .content(content)
                .page(tools.getNumber())
                .size(tools.getSize())
                .totalElements(tools.getTotalElements())
                .totalPages(tools.getTotalPages())
                .first(tools.isFirst())
                .last(tools.isLast())
                .build();
    }

    public PageResponseDTO<ToolTransactionDTO> findAllBorrowedToolsOfOwner(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        //Paging implementation for the tools
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page<ToolTransactionHistory>allBorrowedTools = toolTransactionHistoryRepository.findAllBorrowedTools(user.getId(), pageable);
        List<ToolTransactionDTO> content = allBorrowedTools.map(toolMapper::toBorrowedToolResponseDTO).toList();
        return PageResponseDTO.<ToolTransactionDTO>builder()
                .content(content)
                .page(allBorrowedTools.getNumber())
                .size(allBorrowedTools.getSize())
                .totalElements(allBorrowedTools.getTotalElements())
                .totalPages(allBorrowedTools.getTotalPages())
                .first(allBorrowedTools.isFirst())
                .last(allBorrowedTools.isLast())
                .build();
    }

    public PageResponseDTO<ToolTransactionDTO> findAllReturnedToolsOfOwner(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        //Paging implementation for the tools
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page<ToolTransactionHistory>allBorrowedTools = toolTransactionHistoryRepository.findAllReturnedTools(user.getId(), pageable);
        List<ToolTransactionDTO> content = allBorrowedTools.map(toolMapper::toBorrowedToolResponseDTO).toList();
        return PageResponseDTO.<ToolTransactionDTO>builder()
                .content(content)
                .page(allBorrowedTools.getNumber())
                .size(allBorrowedTools.getSize())
                .totalElements(allBorrowedTools.getTotalElements())
                .totalPages(allBorrowedTools.getTotalPages())
                .first(allBorrowedTools.isFirst())
                .last(allBorrowedTools.isLast())
                .build();
    }


    public Long updateToolShareableStatus(Long toolId, Authentication connectedUser) {
        Tool tool = toolRepository.findById(toolId)
                .orElseThrow(() -> new EntityNotFoundException("No tool found with id: " + toolId));
        User user = (User) connectedUser.getPrincipal();
        if (Objects.equals(tool.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are not the owner of this tool");
        }
        tool.setShareable(!tool.isShareable());
        toolRepository.save(tool);
        return tool.getId();
    }

    public Long updateArchiveStatus(Long toolId, Authentication connectedUser) {
        Tool tool = toolRepository.findById(toolId)
                .orElseThrow(() -> new EntityNotFoundException("No tool found with id: " + toolId));
        User user = (User) connectedUser.getPrincipal();
        if (!Objects.equals(tool.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are not the owner of this tool");
        }
        tool.setArchived(!tool.isArchived());
        toolRepository.save(tool);
        return tool.getId();
    }

    public Long borrowTool(Long toolId, Authentication connectedUser) {
        Tool tool = toolRepository.findById(toolId)
                .orElseThrow(() -> new EntityNotFoundException("No tool found with id: " + toolId));
        if (!tool.isShareable() || tool.isArchived()) {
            throw new OperationNotPermittedException("This tool is not shareable");
        }
        User user = (User) connectedUser.getPrincipal();
        if (Objects.equals(tool.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are the owner of this tool");
        }
        final boolean isAlreadyBorrowed = toolTransactionHistoryRepository.isAlreadyBorrowedByUser(toolId, user.getId());
        if (isAlreadyBorrowed) {
            throw new OperationNotPermittedException("You have already borrowed this tool");
        }

        ToolTransactionHistory toolTransactionHistory = ToolTransactionHistory.builder()
                .tool(tool)
                .user(user)
                .returnApproved(false)
                .returned(false)
                .build();
        return toolTransactionHistoryRepository.save(toolTransactionHistory).getId();
    }

    public Long returnTool(Long toolId, Authentication connectedUser) {
        Tool tool = toolRepository.findById(toolId)
                .orElseThrow(() -> new EntityNotFoundException("No tool found with id: " + toolId));
        if (!tool.isShareable() || tool.isArchived()) {
            throw new OperationNotPermittedException("This tool is not shareable");
        }
        User user = (User) connectedUser.getPrincipal();
        if (Objects.equals(tool.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are the owner of this tool");
        }
        ToolTransactionHistory history = toolTransactionHistoryRepository.findByToolIdAndUserId(toolId, user.getId()).orElseThrow(
                () -> new OperationNotPermittedException("You have not borrowed this tool")
        );
        history.setReturned(true);
        return toolTransactionHistoryRepository.save(history).getId();
    }

    public Long approveReturnBorrowedTool(Long toolId, Authentication connectedUser) {
        Tool tool = toolRepository.findById(toolId)
                .orElseThrow(() -> new EntityNotFoundException("No tool found with id: " + toolId));
        User user = (User) connectedUser.getPrincipal();
        if (!Objects.equals(tool.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are not the owner of this tool");
        }
        ToolTransactionHistory history = toolTransactionHistoryRepository.findByToolIdAndOwnerId(toolId, user.getId()).orElseThrow(
                () -> new OperationNotPermittedException("No borrowed tool marked returned found with id: " + toolId)
        );
        history.setReturnApproved(true);
        return toolTransactionHistoryRepository.save(history).getId();
    }

    public void uploadPhoto(Long toolId, MultipartFile file, Authentication connectedUser) {
        Tool tool = toolRepository.findById(toolId)
                .orElseThrow(() -> new EntityNotFoundException("No tool found with id: " + toolId));
        User user = (User) connectedUser.getPrincipal();
        if (!Objects.equals(tool.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are not the owner of this tool");
        }
        var toolPhotoPath = fileStorageService.saveFile(file, tool.getId(), user.getId());
        tool.setPhoto(toolPhotoPath);
        toolRepository.save(tool);
    }
}
