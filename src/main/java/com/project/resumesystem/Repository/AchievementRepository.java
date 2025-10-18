package com.project.resumesystem.Repository;

import com.project.resumesystem.Model.Acheivement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AchievementRepository extends JpaRepository<Acheivement, UUID> {
    List<Acheivement> findByUserId(UUID userId);
    List<Acheivement> findByUserIdAndStatus(UUID userId, String status);
}
