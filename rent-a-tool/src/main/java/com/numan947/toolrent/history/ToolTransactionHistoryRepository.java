package com.numan947.toolrent.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ToolTransactionHistoryRepository extends JpaRepository<ToolTransactionHistory, Long> {



    @Query("""
            SELECT t FROM ToolTransactionHistory t
            WHERE t.userId = :userId
            """)
    Page<ToolTransactionHistory> findAllBorrowedTools(String userId, Pageable pageable);

    @Query("""
            SELECT t FROM ToolTransactionHistory t
            WHERE t.tool.createdBy = :userId
            """)
    Page<ToolTransactionHistory> findAllReturnedTools(String userId, Pageable pageable);

    @Query("""
            SELECT
            (COUNT(*)>0) AS isAlreadyBorrowed
            FROM ToolTransactionHistory t
            WHERE t.tool.id = :toolId
            AND t.userId = :userId
            AND t.returnApproved = false
            """)
    boolean isAlreadyBorrowedByUser(Long toolId, String userId);

    @Query("""
            SELECT
            t
            FROM ToolTransactionHistory t
            WHERE t.userId = :userId
            AND t.tool.id = :toolId
            AND t.returned = false
            AND t.returnApproved = false
            """)
    Optional<ToolTransactionHistory> findByToolIdAndUserId(Long toolId, String userId);


    @Query("""
            SELECT
            t
            FROM ToolTransactionHistory t
            WHERE t.tool.createdBy = :ownerId
            AND t.tool.id = :toolId
            AND t.returned = true
            AND t.returnApproved = false
            """)
    Optional<ToolTransactionHistory> findByToolIdAndOwnerId(Long toolId, String ownerId);
}
