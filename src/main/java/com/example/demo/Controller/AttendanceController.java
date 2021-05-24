package com.example.demo.Controller;

import com.example.demo.Model.Attendance;
import com.example.demo.Model.Event;
import com.example.demo.Service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController

public class AttendanceController {
    @Autowired
    AttendanceService attendanceService;

    @PostMapping("api/attendance")
    public @ResponseBody
    ResponseEntity addAttendance(@RequestBody Attendance attendance) {
        boolean isExists = attendanceService.isExist(attendance);
        if(isExists){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        attendanceService.addAttendance(attendance);
        return new ResponseEntity("Added", HttpStatus.OK);

    }
    @GetMapping("api/attendance/list")
    public @ResponseBody
    ResponseEntity getEventByDate(@RequestParam("subjectClassId")Integer subjectClassId){
        ArrayList<Attendance> attendanceArrayList = attendanceService.getAttendanceListBySubjectClassId(subjectClassId);
        return new ResponseEntity(attendanceArrayList, HttpStatus.OK);
    }


}
