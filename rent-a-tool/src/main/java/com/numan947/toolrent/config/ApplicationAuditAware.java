package com.numan947.toolrent.config;

import com.numan947.toolrent.user.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<String>{
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            return Optional.empty();
        }

        try{
            return Optional.ofNullable(authentication.getName());

        }catch (ClassCastException e){
            return Optional.empty();
        }
    }
}
