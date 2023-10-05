package com.mjc.school.web.security.controller;

import com.mjc.school.service.security.AuthenticateService;
import com.mjc.school.service.security.request.AuthenticateRequest;
import com.mjc.school.service.security.request.RegisterRequest;
import com.mjc.school.service.security.response.AuthenticateResponse;
import com.mjc.school.web.controller.PathConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = PathConstants.AUTH_PATH)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticateService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticateResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticateResponse> authenticate(@RequestBody AuthenticateRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
