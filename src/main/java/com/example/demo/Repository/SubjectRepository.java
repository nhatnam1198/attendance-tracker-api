package com.example.demo.Repository;

import com.example.demo.Model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    public boolean existsByName(String name);
}
