package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class EventDTO {
    private Integer id;
    private String name;
    private Integer shiftId;
    private Integer subjectClassId;
//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM-dd-yyyy")
    private Date dateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    public Integer getSubjectClassId() {
        return subjectClassId;
    }

    public void setSubjectClassId(Integer subjectClassId) {
        this.subjectClassId = subjectClassId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;
}
