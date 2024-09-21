package com.numan947.toolrent.tool;

import com.numan947.toolrent.common.FileUtils;
import com.numan947.toolrent.history.ToolTransactionHistory;
import com.numan947.toolrent.tool.dto.ToolTransactionDTO;
import com.numan947.toolrent.tool.dto.ToolRequestDTO;
import com.numan947.toolrent.tool.dto.ToolResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ToolMapper {
    public Tool toTool(ToolRequestDTO toolRequest) {
        return Tool.builder()
                .name(toolRequest.name())
                .description(toolRequest.description())
                .manufacturer(toolRequest.manufacturer())
                .shareable(toolRequest.shareable())
                .id(toolRequest.id())
                .archived(false)
                .build();
    }

    public ToolResponseDTO toToolResponseDTO(Tool tool) {
        return new ToolResponseDTO(
                tool.getId(),
                tool.getName(),
                tool.getDescription(),
                tool.getManufacturer(),
                tool.isShareable(),
                tool.isArchived(),
                FileUtils.readFileFromPath(tool.getPhoto()),
                tool.getAverageRating(),
                tool.getCreatedBy()
        );
    }

    public ToolTransactionDTO toBorrowedToolResponseDTO(ToolTransactionHistory toolTransactionHistory) {
        return new ToolTransactionDTO(
                toolTransactionHistory.getTool().getId(),
                toolTransactionHistory.getTool().getName(),
                toolTransactionHistory.getTool().getDescription(),
                toolTransactionHistory.getTool().getManufacturer(),
                toolTransactionHistory.getTool().getAverageRating(),
                toolTransactionHistory.isReturned(),
                toolTransactionHistory.isReturnApproved()
        );
    }
}
