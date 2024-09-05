package com.example.cmsapi.repository;

import com.example.cmsapi.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByImagePath(String imagePath);
    boolean existsByImagePath(String imagePath);
}
