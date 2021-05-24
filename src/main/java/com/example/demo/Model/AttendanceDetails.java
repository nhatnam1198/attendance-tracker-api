package com.example.demo.Model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "attendance_detail")
//@JsonIgnoreProperties({"attend"})
public class AttendanceDetails {
    @Id
    @Column(name = "attendance_details_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendance_id")
    private Attendance attendance;


//    @JsonProperty(value="isAttend")
    @Column(name = "status")
    private Integer status;

    @Column(name = "message")
    private String message;
    @Column(name = "created_date_time")
    private Instant createdDateTime;

    @Column(name = "updated_date_time")
    private Instant updatedDateTime;

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

}
