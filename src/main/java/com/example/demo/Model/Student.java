package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
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

//    @JsonIgnore
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
