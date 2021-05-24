package com.example.demo.Repository;

import com.example.demo.Model.EmbeddedImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmbeddedImageRepository extends JpaRepository<EmbeddedImage, Integer> {
    Optional<EmbeddedImage> findByStudentId(Integer studentId);

}
