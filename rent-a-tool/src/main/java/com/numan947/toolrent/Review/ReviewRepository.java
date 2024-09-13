package com.numan947.toolrent.Review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<ToolReview, Long> {
    @Query("""
            SELECT r
            FROM ToolReview r
            WHERE r.tool.id = :toolId
            """)
    Page<ToolReview> findAllByToolId(Long toolId, Pageable pageable);
}
