package com.numan947.toolrent.history;

import com.numan947.toolrent.common.BaseEntity;
import com.numan947.toolrent.tool.Tool;
import com.numan947.toolrent.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "_tool_transaction_history")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ToolTransactionHistory extends BaseEntity {
    boolean returned;
    boolean returnApproved;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    @Column(name = "user_id")
    private String userId;

    @ManyToOne
    @JoinColumn(name = "tool_id")
    private Tool tool;
}
