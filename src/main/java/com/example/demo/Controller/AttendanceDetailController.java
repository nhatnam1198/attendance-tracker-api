package com.example.demo.Controller;

import com.example.demo.Model.Attendance;
import com.example.demo.Model.AttendanceDetails;
import com.example.demo.Model.Event;
import com.example.demo.Model.Student;
import com.example.demo.Service.AttendanceDetailService;
import com.example.demo.Service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jdk.internal.org.objectweb.asm.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

@RestController
public class AttendanceDetailController {
    @Autowired
    AttendanceDetailService attendanceDetailService;
    @Autowired
    EventService eventService;
    @PostMapping("api/attendanceDetail")
    public @ResponseBody
    ResponseEntity addAttendanceDetail(@RequestBody HashMap<String, Object> map) {
        if(!map.containsKey("attendedStudentList") || !map.containsKey("event")){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        ArrayList<Student> studentList = (ArrayList<Student>)map.get("attendedStudentList");
        ArrayList<Student> attendedStudentList = new ArrayList<>();
        for (Object object :
                studentList) {
            Student newStudent = new Student();
            newStudent = new ObjectMapper().convertValue(object, Student.class);
            attendedStudentList.add(newStudent);
        }

        Event event = new ObjectMapper().registerModule(new JavaTimeModule()).convertValue(map.get("event"), Event.class);

        if(attendanceDetailService.isExistsByEventId(event.getId())){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        attendanceDetailService.addAttendanceDetails(attendedStudentList, event);

        return new ResponseEntity(HttpStatus.OK);

    }
    @PostMapping("api/attendanceDetails/init")
    public @ResponseBody
    ResponseEntity initAttendanceDetails(@RequestBody HashMap<String, Object> map) {
        if(!map.containsKey("attendanceArrayList") || !map.containsKey("event")){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        ArrayList<Attendance> attendanceArrayList = (ArrayList<Attendance>)map.get("attendanceArrayList");
        ArrayList<Attendance> attendanceArrList = new ArrayList<>();
        for (Object object :
                attendanceArrayList) {
            Attendance newAttendance = new Attendance();
            newAttendance = new ObjectMapper().convertValue(object, Attendance.class);
            attendanceArrList.add(newAttendance);
        }

        Event event = new ObjectMapper().registerModule(new JavaTimeModule()).convertValue(map.get("event"), Event.class);

        if(attendanceDetailService.isExistsByEventId(event.getId())){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        attendanceDetailService.initAttendanceDetails(attendanceArrList, event);

        return new ResponseEntity(HttpStatus.OK);

    }
    @PutMapping("api/attendanceDetail/single")
    public @ResponseBody
    ResponseEntity updateAttendanceDetailSingle(@RequestParam("eventId") Integer eventId, @RequestParam("email") String studentEmail) {
//        if(attendanceDetailService.isExistsByEventId(eventId)){
//            return new ResponseEntity(HttpStatus.CONFLICT);
//        }
        attendanceDetailService.updateAttendanceDetailSingle(eventId, studentEmail);
        return new ResponseEntity(HttpStatus.OK);

    }


    @GetMapping("api/attendanceDetail/attendedResult/list")
    public @ResponseBody ResponseEntity getAttendedResultList(@RequestParam("eventId") Integer eventId){
        ArrayList<AttendanceDetails> attendedStudentList = attendanceDetailService.getAttendedResultList(eventId);
        return new ResponseEntity(attendedStudentList, HttpStatus.OK);
    }

    @GetMapping("api/attendanceDetail/leaveOfAbsence/list")
    public @ResponseBody ResponseEntity getLeaveOfAbsentRequestList(@RequestParam("eventId") Integer eventId) {
        ArrayList<AttendanceDetails> leaveOfAbsenceRequestList = attendanceDetailService.getLeaveOfAbsentRequestList(eventId);
        return new ResponseEntity(leaveOfAbsenceRequestList, HttpStatus.OK);
    }

    @PutMapping("api/attendanceDetail/absence")
    public @ResponseBody ResponseEntity approveLeaveOfAbsenceRequest(@RequestBody Integer attendanceId){
        attendanceDetailService.approveLeaveOfAbsenceRequest(attendanceId);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PutMapping("api/attendanceDetail")
    public @ResponseBody ResponseEntity updateAttendanceDetail(@RequestBody ArrayList<AttendanceDetails> attendanceDetailsArrayList){
        attendanceDetailService.updateAttendanceDetail(attendanceDetailsArrayList);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("api/attendanceDetail/status")
    public @ResponseBody ResponseEntity getAttendanceDetailStatusByEventIdAndUserEmail(@RequestParam("eventId") Integer eventId, @RequestParam("userEmail") String userEmail){
        AttendanceDetails attendanceDetails = attendanceDetailService.getAttendanceDetailStatusByEventIdAndUserEmail(eventId, userEmail);
        if(attendanceDetails == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(attendanceDetails, HttpStatus.OK);
    }
}
