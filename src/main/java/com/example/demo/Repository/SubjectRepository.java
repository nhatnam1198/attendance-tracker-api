package com.example.demo.Repository;

import com.example.demo.Model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    public boolean existsByName(String name);
    public Optional<Subject> findById(Integer id);
}
