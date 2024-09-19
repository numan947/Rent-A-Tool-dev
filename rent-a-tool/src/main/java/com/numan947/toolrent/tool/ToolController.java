package com.numan947.toolrent.tool;

import com.numan947.toolrent.common.dto.PageResponseDTO;
import com.numan947.toolrent.tool.dto.ToolTransactionDTO;
import com.numan947.toolrent.tool.dto.ToolRequestDTO;
import com.numan947.toolrent.tool.dto.ToolResponseDTO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "Tool", description = "Tool API")
@RequiredArgsConstructor
@RequestMapping("/tools")
public class ToolController {
    private final ToolService toolService;

    @PostMapping
    public ResponseEntity<Long> createTool(@Valid @RequestBody ToolRequestDTO toolRequest, Authentication connectedUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toolService.createTool(toolRequest, connectedUser));
    }

    @GetMapping("/{tool-id}")
    public ResponseEntity<ToolResponseDTO> getTool(@PathVariable("tool-id") Long toolId) {
        return ResponseEntity.ok(toolService.findToolById(toolId));
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<ToolResponseDTO>> findAllTools(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        System.out.println("Connected User: " + connectedUser);
        return ResponseEntity.ok(toolService.findAllTools(page, size, connectedUser));
    }

    @GetMapping("/owner")
    public ResponseEntity<PageResponseDTO<ToolResponseDTO>> findToolsByOwner(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser)
    {
        return ResponseEntity.ok(toolService.findAllToolsByOwner(page, size, connectedUser));
    }

    @GetMapping("/borrowed")
    public ResponseEntity<PageResponseDTO<ToolTransactionDTO>> findAllBorrowedTools(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser)
    {
        return ResponseEntity.ok(toolService.findAllBorrowedToolsOfOwner(page, size, connectedUser));
    }

    @GetMapping("/returned")
    public ResponseEntity<PageResponseDTO<ToolTransactionDTO>> findAllReturnedTools(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser)
    {
        return ResponseEntity.ok(toolService.findAllReturnedToolsOfOwner(page, size, connectedUser));
    }


    @PatchMapping("/shareable/{tool-id}")
    public ResponseEntity<Long> updateToolShareableStatus(
            @PathVariable("tool-id") Long toolId,
            Authentication connectedUser
    )
    {
        return ResponseEntity.ok(toolService.updateToolShareableStatus(toolId, connectedUser));
    }

    @PatchMapping("/archived/{tool-id}")
    public ResponseEntity<Long> archiveTool(
            @PathVariable("tool-id") Long toolId,
            Authentication connectedUser
    )
    {
        return ResponseEntity.ok(toolService.updateArchiveStatus(toolId, connectedUser));
    }

    @PostMapping("/borrow/{tool-id}")
    public ResponseEntity<Long> borrowTool(
            @PathVariable("tool-id") Long toolId,
            Authentication connectedUser
    )
    {
        return ResponseEntity.ok(toolService.borrowTool(toolId, connectedUser));
    }

    @PatchMapping("/borrow/return/{tool-id}")
    public ResponseEntity<Long> returnTool(
            @PathVariable("tool-id") Long toolId,
            Authentication connectedUser
    )
    {
        return ResponseEntity.ok(toolService.returnTool(toolId, connectedUser));
    }

    @PatchMapping("/borrow/return/approve/{tool-id}")
    public ResponseEntity<Long> approveReturnTool(
            @PathVariable("tool-id") Long toolId,
            Authentication connectedUser
    )
    {
        return ResponseEntity.ok(toolService.approveReturnBorrowedTool(toolId, connectedUser));
    }

    @PostMapping(value = "/photo/{tool-id}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadToolPhoto(
            @PathVariable("tool-id") Long toolId,
            @Parameter()
            @RequestPart("file") MultipartFile file,
            Authentication connectedUser
    )
    {
        toolService.uploadPhoto(toolId, file, connectedUser);
        return ResponseEntity.accepted().build();
    }





}
