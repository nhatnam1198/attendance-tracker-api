package com.example.demo.Repository;

import com.example.demo.Model.Subject;
import com.example.demo.Model.SubjectClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface SubjectClassRepository extends JpaRepository<SubjectClass, Integer> {
    boolean existsByName(String subjectClassName);
    ArrayList<SubjectClass> getBySubjectIdAndTeacherId(Integer subjectId, Integer teacherId);
    SubjectClass getById(Integer id);
}
