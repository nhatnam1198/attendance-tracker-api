package com.example.demo.Model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "subject_class")

public class SubjectClass {
    @Id
    @Column(name = "subject_class_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }

    @JsonManagedReference("attendance-subject")
    @OneToMany(mappedBy = "subjectClass", cascade = CascadeType.ALL)
    private List<Attendance> attendanceList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDate startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDate getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDate endDateTime) {
        this.endDateTime = endDateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM-dd-yyyy")
    @Column(name = "start_date_time")
    private LocalDate startDateTime;

//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM-dd-yyyy")
    @Column(name = "end_date_time")
    private LocalDate endDateTime;

    @Column(name = "status")
    private int status;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    private Subject subject;

//    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
}
