package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
@JsonIgnoreProperties (value = { "hibernateLazyInitializer", "handler"})
public class Student {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "student_code")
    private String studentCode;

//    @JsonManagedReference("student-attendance")
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Attendance> attendanceList = new ArrayList<>();

//    @JsonIgnore
//    @JsonManagedReference("embeddedimage-student")
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<EmbeddedImage> embeddedImages = new ArrayList<>();
    @Transient
    private byte[] profileImage;

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }
    public List<EmbeddedImage> getEmbeddedImages() {
        return embeddedImages;
    }

    public void setEmbeddedImages(List<EmbeddedImage> embeddedImages) {
        this.embeddedImages = embeddedImages;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public void setName(String name) {
        this.name = name;
    }
}
