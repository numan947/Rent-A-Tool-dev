package com.numan947.toolrent.note;

import com.numan947.toolrent.common.BaseEntity;
import com.numan947.toolrent.tool.Tool;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "_reviews")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ToolReview extends BaseEntity {
    private String title;
    private String content;
    private Double rating; // 1-10 scale

    @ManyToOne
    @JoinColumn(name = "tool_id")
    private Tool tool;
}
