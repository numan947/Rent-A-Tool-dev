package com.numan947.toolrent.tool;

import com.numan947.toolrent.common.BaseEntity;
import com.numan947.toolrent.history.ToolTransactionHistory;
import com.numan947.toolrent.note.ToolReview;
import com.numan947.toolrent.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

// Tool entity class
@Entity
@Table(name = "_tools")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class Tool extends BaseEntity {
    private String name;
    private String description;
    private String manufacturer;
    private String photo;
    private boolean shareable;
    private boolean archived;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "tool")
    List<ToolReview>reviews;

    @OneToMany(mappedBy = "tool")
    List<ToolTransactionHistory>histories;

    @Transient // this field is not stored in the database but calculated on the fly
    public double getAverageRating(){
        return reviews.stream()
                .mapToDouble(ToolReview::getRating)
                .average()
                .orElse(0);
    }
}
