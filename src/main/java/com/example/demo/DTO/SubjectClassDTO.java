package com.example.demo.DTO;

import com.example.demo.Model.Attendance;
import com.example.demo.Model.Subject;
import com.example.demo.Model.Teacher;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubjectClassDTO {
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
    private LocalDate startDateTime;

//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM-dd-yyyy")
    private LocalDate endDateTime;

    private int status;
    private Integer subjectId;

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    private Integer teacherId;
}
