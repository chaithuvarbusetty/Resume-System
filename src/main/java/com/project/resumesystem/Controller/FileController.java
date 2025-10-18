package com.project.resumesystem.Controller;

import com.project.resumesystem.Model.FileEntity;
import com.project.resumesystem.Model.User;
import com.project.resumesystem.Repository.FileEntityRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor

public class FileController {
    private final FileEntityRepository fileEntityRepository;


   // default value

    @Value("${app.files.base-dir:./uploads}")
    private String baseDir;




    @PostMapping("/upload")
    public ResponseEntity<?> upload(Authentication auth, @RequestParam("file") MultipartFile multipart) throws IOException {
        if (auth == null) return ResponseEntity.status(401).build();
        User user = (User) auth.getPrincipal();

        String filename = StringUtils.cleanPath(multipart.getOriginalFilename());
        String uuid = UUID.randomUUID().toString();
        Path userDir = Paths.get(baseDir, user.getId().toString());
        Files.createDirectories(userDir);
        Path dest = userDir.resolve(uuid + "-" + filename);
        try (InputStream in = multipart.getInputStream()) {
            Files.copy(in, dest, StandardCopyOption.REPLACE_EXISTING);
        }

        FileEntity fe = FileEntity.builder()
                .user(user)
                .filename(filename)
                .storagePath(dest.toString())
                .contentType(multipart.getContentType())
                .createdAt(OffsetDateTime.now())
                .build();
        fileEntityRepository.save(fe);

        return ResponseEntity.ok(Map.of("fileId", fe.getId(), "path", fe.getStoragePath()));
    }
}