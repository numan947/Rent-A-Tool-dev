package com.numan947.toolrent.auth;

import com.numan947.toolrent.auth.dto.AuthenticationRequestDTO;
import com.numan947.toolrent.auth.dto.AuthenticationResponseDTO;
import com.numan947.toolrent.auth.dto.RegistrationRequestDTO;
import com.numan947.toolrent.email.EmailService;
import com.numan947.toolrent.email.EmailTemplateName;
import com.numan947.toolrent.role.RoleRepository;
import com.numan947.toolrent.security.JwtService;
import com.numan947.toolrent.user.Token;
import com.numan947.toolrent.user.TokenRepository;
import com.numan947.toolrent.user.User;
import com.numan947.toolrent.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RoleRepository roleRepository;
    private final AuthMapper authMapper;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;




    @Value("${application.security.mailing.frontend.activation-url:http://localhost:8080/auth/activate}")
    private String activationUrl;
    @Value("${application.security.mailing.frontend.activation-code-length:6}")
    private Integer activationCodeLength;
    @Value("${application.security.mailing.frontend.activation-code-expiration:15}")
    private Integer activationCodeExpirationTime = 15;
    @Value("${application.security.mailing.frontend.activation-code-characters:0123456789}")
    private String activationCodeCharacters;
    @Value("${application.security.mailing.frontend.activation-code-subject:Activate your account}")
    private String activationCodeSubject;





    public void register(RegistrationRequestDTO registrationRequestDTO) throws MessagingException {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("ROLE USER was not initialized"));

        var user = authMapper.toUser(registrationRequestDTO, userRole);
        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        String activationToken = generateAndSaveActivationToken(user);
        emailService.sendConfirmationEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                activationToken,
                activationCodeSubject
        );
    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode(activationCodeLength);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(activationCodeExpirationTime))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(Integer codeLength) {
        StringBuilder code = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < codeLength; i++) {
            code.append(activationCodeCharacters.charAt(random.nextInt(activationCodeCharacters.length())));
        }
        return code.toString();
    }

    public AuthenticationResponseDTO authenticate(@Valid AuthenticationRequestDTO requestDTO) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDTO.email(), requestDTO.password())
        );
        var claims = new HashMap<String, Object>();
        var user = (User) auth.getPrincipal();
        claims.put("fullName", user.getFullName());
        var jwtToken = jwtService.generateToken(claims, user);
        return new AuthenticationResponseDTO(jwtToken);
    }


    @Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid Token"));
        if (savedToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Token expired, new token sent to email.");
        }
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }
}
