package com.project.resumesystem.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Data
@Setter
@Builder
public class Organisation {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String domain;
    private String type; // company/university/event
}
