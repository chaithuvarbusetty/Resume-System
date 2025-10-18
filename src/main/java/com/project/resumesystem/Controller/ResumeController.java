package com.project.resumesystem.Controller;

import com.project.resumesystem.Model.Resume;
import com.project.resumesystem.Model.User;
import com.project.resumesystem.Service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @PostMapping("/generate")
    public ResponseEntity<?> generate(Authentication auth, @RequestBody Map<String, String> body) {
        if (auth == null) return ResponseEntity.status(401).build();
        User user = (User) auth.getPrincipal();
        String title = body.getOrDefault("title", "Resume");
        Resume r = resumeService.generateResume(user.getId(), title);
        return ResponseEntity.ok(Map.of("resumeId", r.getId(), "sections", r.getSections()));
    }
}
