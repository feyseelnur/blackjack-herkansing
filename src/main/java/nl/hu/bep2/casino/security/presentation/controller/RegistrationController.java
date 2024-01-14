package nl.hu.bep2.casino.security.presentation.controller;

import nl.hu.bep2.casino.security.application.UserService;
import nl.hu.bep2.casino.security.domain.exception.UsernameAlreadyExistsException;
import nl.hu.bep2.casino.security.presentation.dto.Registration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/register")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> register(@Validated @RequestBody Registration registration) {
        Map<String, String> response = new HashMap<>();
        try {
            this.userService.register(
                    registration.username,
                    registration.password,
                    registration.firstName,
                    registration.lastName
            );
            response.put("message", "User has successfully registered.");
            return ResponseEntity.ok().body(response);
        }  catch (Exception e) {
            response.put("message", "An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
