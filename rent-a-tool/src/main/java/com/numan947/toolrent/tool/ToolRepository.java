package com.numan947.toolrent.tool;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ToolRepository extends JpaRepository<Tool, Long>, JpaSpecificationExecutor<Tool> {

    @Query("""
        SELECT tool
        FROM Tool tool
        WHERE tool.archived = false
        AND
        tool.shareable = true
        AND
        tool.owner.id != :userId
    """)
    Page<Tool> findAllValidDisplayableTools(Pageable pageable, Long userId);
}
