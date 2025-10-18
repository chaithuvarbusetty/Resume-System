package com.project.resumesystem.Controller;

import com.project.resumesystem.Model.User;
import com.project.resumesystem.Security.JwtUtil;
import com.project.resumesystem.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "email and password required"));
        }
        User user = userService.createUser(email, password);
        return ResponseEntity.ok(Map.of("userId", user.getId().toString()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        var opt = userService.findByEmail(email);
        if (opt.isEmpty()) return ResponseEntity.status(401).body(Map.of("error", "invalid creds"));

        User user = opt.get();
        boolean ok = userService.verifyPassword(user, password);
        if (!ok) return ResponseEntity.status(401).body(Map.of("error", "invalid creds"));

        String token = jwtUtil.generateToken(user.getId().toString(), user.getEmail());
        return ResponseEntity.ok(Map.of("token", token));
    }
    }
