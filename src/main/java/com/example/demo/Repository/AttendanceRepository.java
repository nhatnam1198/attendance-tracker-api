package com.example.demo.Repository;

import com.example.demo.Model.Attendance;
import com.example.demo.Model.Student;
import com.example.demo.Model.SubjectClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    ArrayList<Attendance> findBySubjectClassId(Integer subjectClassId);
    boolean existsBySubjectClassAndStudent(SubjectClass subjectClass, Student student);
    ArrayList<Attendance> findStudentBySubjectClassId(Integer subjectClassId);

//    @Query("select s from Attendance s where s.student.id in : studentIds")
    ArrayList<Attendance> findAllByStudentIdIn(Iterable<Integer> studentIds);
}
