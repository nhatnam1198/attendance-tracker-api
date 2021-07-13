package com.example.demo.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @Column(name = "teacher_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @Column(name = "name")
    private String name;

    @Column(name ="email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<SubjectClass> subjectClassList = new ArrayList<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
