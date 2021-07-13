package com.example.demo.Repository;

import com.example.demo.Model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    public boolean existsByName(String name);
    Teacher getByEmail(String email);
}
