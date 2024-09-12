package com.numan947.toolrent.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ToolTransactionHistoryRepository extends JpaRepository<ToolTransactionHistory, Long> {



    @Query("""
            SELECT t FROM ToolTransactionHistory t
            WHERE t.user.id = :userId
            """)
    Page<ToolTransactionHistory> findAllBorrowedTools(Long userId, Pageable pageable);

    @Query("""
            SELECT t FROM ToolTransactionHistory t
            WHERE t.tool.owner.id = :userId
            """)
    Page<ToolTransactionHistory> findAllReturnedTools(Long userId, Pageable pageable);

    @Query("""
            SELECT
            (COUNT(*)>0) AS isAlreadyBorrowed
            FROM ToolTransactionHistory t
            WHERE t.tool.id = :toolId
            AND t.user.id = :userId
            AND t.returnApproved = false
            """)
    boolean isAlreadyBorrowedByUser(Long toolId, Long userId);

    @Query("""
            SELECT
            t
            FROM ToolTransactionHistory t
            WHERE t.user.id = :userId
            AND t.tool.id = :toolId
            AND t.returned = false
            AND t.returnApproved = false
            """)
    Optional<ToolTransactionHistory> findByToolIdAndUserId(Long toolId, Long userId);
}
