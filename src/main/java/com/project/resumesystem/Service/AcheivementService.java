package com.project.resumesystem.Service;

import com.project.resumesystem.Model.Acheivement;
import com.project.resumesystem.Model.User;
import com.project.resumesystem.Repository.AchievementRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AcheivementService {


    private final AchievementRepository achievementRepository;

    private final UserService userService;

    public AcheivementService(AchievementRepository achievementRepository, UserService userService) {
        this.achievementRepository = achievementRepository;
        this.userService = userService;
    }

    public Acheivement createAchievement(UUID userId, Acheivement ach) {
        User user = userService.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        ach.setUser(user);
        return achievementRepository.save(ach);
    }

    public List<Acheivement> listByUser(UUID userId) {
        return achievementRepository.findByUserId(userId);
    }

    public List<Acheivement> listVerifiedByUser(UUID userId) {
        return achievementRepository.findByUserIdAndStatus(userId, "verified");
    }

    public Acheivement markVerified(UUID achievementId) {
        Acheivement a = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new IllegalArgumentException("Achievement not found"));
        a.setStatus("verified");
        return achievementRepository.save(a);
    }

    public Acheivement setStatus(UUID achievementId, String status) {
        Acheivement a = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new IllegalArgumentException("Achievement not found"));
        a.setStatus(status);
        return achievementRepository.save(a);
    }
}
