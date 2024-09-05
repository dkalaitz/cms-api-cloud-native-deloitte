package com.example.cms_api.image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByImagePath(String imagePath);
    boolean existsByImagePath(String imagePath);
}
