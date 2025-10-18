package com.project.resumesystem.Controller;

import com.project.resumesystem.Model.Acheivement;
import com.project.resumesystem.Model.User;
import com.project.resumesystem.Service.AcheivementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/achievements")
@RequiredArgsConstructor
public class AchievementController {
    private final AcheivementService achievementService;

    @PostMapping
    public ResponseEntity<?> createAchievement(Authentication auth, @RequestBody Acheivement payload) {
        if (auth == null) return ResponseEntity.status(401).build();
        User user = (User) auth.getPrincipal();
        var created = achievementService.createAchievement(user.getId(), payload);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<?> listMyAchievements(Authentication auth) {
        if (auth == null) return ResponseEntity.status(401).build();
        User user = (User) auth.getPrincipal();
        var list = achievementService.listByUser(user.getId());
        return ResponseEntity.ok(list);
    }

    @PostMapping("/{id}/verify")
    public ResponseEntity<?> verifyAchievement(@PathVariable("id") UUID id, Authentication auth) {
        // For MVP: allow user to self-verify or admin to verify. Here we allow same user.
        var updated = achievementService.setStatus(id, "verified");
        return ResponseEntity.ok(updated);
    }
}
