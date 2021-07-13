package com.example.demo.Model;

import Helper.CustomDateDeserializer;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "event")

public class Event {
    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public Integer getId() {
        return id;
    }


    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<AttendanceDetails> attendanceDetailsList = new ArrayList<>();

    @Column(name = "name", nullable = false)
    private String name;


    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_id")
    private Shift shift;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_class_id")
    private SubjectClass subjectClass;

//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM-dd-yyyy", timezone = "UTC+07:00")

//    @JsonDeserialize(using = CustomDateDeserializer.class)
//    @JsonFormat(pattern="MM/dd/yyyy", timezone = "UTC+7")
//    @Temporal(TemporalType.DATE)
    @Column(name = "date_time", nullable = false)
    private LocalDate dateTime;

    @Column(name = "status", nullable = false)
    private int status;

    public Event() {
    }


    public List<AttendanceDetails> getAttendanceDetailsList() {
        return attendanceDetailsList;
    }

    public void setAttendanceDetailsList(List<AttendanceDetails> attendanceDetailsList) {
        this.attendanceDetailsList = attendanceDetailsList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public SubjectClass getSubjectClass() {
        return subjectClass;
    }

    public void setSubjectClass(SubjectClass subjectClass) {
        this.subjectClass = subjectClass;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
