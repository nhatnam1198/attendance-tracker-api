package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "embedded_image")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "student"})
public class EmbeddedImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "embedded_id")
    private int id;

    public int getId() {
        return id;
    }

    @Column(name = "embedding_vector", columnDefinition = "TEXT")
    private String embeddingVector;

    @Column(name = "file_path")
    private String filePath;

//    @JsonBackReference("embeddedimage-student")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    public String getEmbeddingVector() {
        return embeddingVector;
    }

    public void setEmbeddingVector(String embeddingVector) {
        this.embeddingVector = embeddingVector;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
