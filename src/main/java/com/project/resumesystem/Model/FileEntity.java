package com.project.resumesystem.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Data
@Setter
@Builder
public class FileEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String storagePath;
    private String filename;
    private String contentType;
    private OffsetDateTime createdAt = OffsetDateTime.now();
}
