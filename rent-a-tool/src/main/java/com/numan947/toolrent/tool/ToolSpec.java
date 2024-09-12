package com.numan947.toolrent.tool;

import org.springframework.data.jpa.domain.Specification;

public class ToolSpec {
    public static Specification<Tool> withOwnerId(Long ownerId){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("owner").get("id"), ownerId);
    }
}
