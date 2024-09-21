package com.numan947.toolrent.auth;

import com.numan947.toolrent.auth.dto.AuthenticationRequestDTO;
import com.numan947.toolrent.auth.dto.AuthenticationResponseDTO;
import com.numan947.toolrent.auth.dto.RegistrationRequestDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
//@RestController
//@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication related endpoints")
public class AuthenticationController {
//    private final AuthenticationService authenticationService;
//
//    @PostMapping(value = {"/register", "/signup"})
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequestDTO registrationRequestDTO) throws MessagingException {
////        authenticationService.register(registrationRequestDTO);
//        return ResponseEntity.accepted().build();
//    }
//
//    @PostMapping("/authenticate")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody @Valid AuthenticationRequestDTO requestDTO ) {
//        return ResponseEntity.ok(authenticationService.authenticate(requestDTO));
//    }
//
//    @GetMapping("/activate-account")
//    @ResponseStatus(HttpStatus.OK)
//    public void activate(@RequestParam String token) throws MessagingException {
////        authenticationService.activateAccount(token);
//    }
//


}
