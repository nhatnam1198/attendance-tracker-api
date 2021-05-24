package com.example.demo.Repository;

import com.example.demo.Model.Shift;
import com.example.demo.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepository extends JpaRepository<Shift, Integer> {
    boolean existsByName(String name);
    Shift getById(Integer id);
}
