package com.project.resumesystem.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Data
@Setter
@Builder
@Table(name="users_profiles")
public class UserProfile {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String fullName;
    private String headline;

    @Column(columnDefinition = "text")
    private String about;

    private String location;

    // store skills as simple comma-separated string for MVP; later use array or separate table
    private String skills;


}
