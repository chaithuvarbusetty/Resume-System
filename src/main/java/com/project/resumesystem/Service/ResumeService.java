package com.project.resumesystem.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.resumesystem.Model.Acheivement;
import com.project.resumesystem.Model.Resume;
import com.project.resumesystem.Model.User;
import com.project.resumesystem.Repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final AcheivementService achievementService;
    private final ResumeRepository resumeRepository;
    private final UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Generate a JSON resume for user from verified achievements.
     * Stores a Resume snapshot in DB and returns the saved Resume entity.
     */
    public Resume generateResume(UUID userId, String title) {
        User user = userService.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Acheivement> verified = achievementService.listVerifiedByUser(userId);

        Map<String, Object> resumeJson = new LinkedHashMap<>();
        resumeJson.put("fullName", user.getEmail()); // replace with profile.fullName later
        resumeJson.put("title", title == null ? "Resume" : title);

        List<Map<String, Object>> items = new ArrayList<>();
        for (Acheivement a : verified) {
            Map<String, Object> it = new HashMap<>();
            it.put("kind", a.getKind());
            it.put("title", a.getTitle());
            it.put("description", a.getDescription());
            it.put("startDate", a.getStartDate());
            it.put("endDate", a.getEndDate());
            it.put("externalUrl", a.getExternalUrl());
            items.add(it);
        }
        resumeJson.put("items", items);
        try {
            String sections = objectMapper.writeValueAsString(resumeJson);
            Resume r = Resume.builder()
                    .user(user)
                    .title((String) resumeJson.get("title"))
                    .sections(sections)
                    .updatedAt(OffsetDateTime.now().toLocalDateTime())
                    .build();
            return resumeRepository.save(r);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to generate resume JSON", ex);
        }
    }


}
