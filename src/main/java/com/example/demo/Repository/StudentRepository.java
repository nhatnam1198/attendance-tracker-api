package com.example.demo.Repository;

import com.example.demo.DTO.StudentDTO;
import com.example.demo.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository  extends JpaRepository<Student, Integer> {
    public final static String FIND_BY_EMBEDDED_IMAGE_ID = "SELECT s.id as id, s.name as name, s.email as email, s.student_code as studentCode" +
                                                            " FROM `student` s" +
                                                            " INNER JOIN `embedded_image` e" +
                                                            " ON s.id = e.student_id"+
                                                            " WHERE e.embedded_id = ?1";

//    @Query(value = FIND_BY_EMBEDDED_IMAGE_ID, nativeQuery = true)
    @Query(value = "SELECT DISTINCT s from Student s inner join s.embeddedImages e where e.id = ?1")
    public Student findByEmbeddedImageId(Integer embeddedId);

    public boolean existsByEmail(String email);
    public Student findByEmail(String email);
}
