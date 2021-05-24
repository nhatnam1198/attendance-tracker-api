package com.example.demo.Controller;

import com.example.demo.Model.Attendance;
import com.example.demo.Model.AttendanceDetails;
import com.example.demo.Model.Event;
import com.example.demo.Model.Student;
import com.example.demo.Service.AttendanceDetailService;
import com.example.demo.Service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        //boolean isExists = AttendanceDetailService.isExist(AttendanceDetail);
//        if(isExists){
//            return new ResponseEntity(HttpStatus.CONFLICT);
//        }
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
        Event event = new ObjectMapper().convertValue(map.get("event"), Event.class);

        if(attendanceDetailService.isExistsByEventId(event.getId())){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        attendanceDetailService.addAttendanceDetails(attendedStudentList, event);

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
}
