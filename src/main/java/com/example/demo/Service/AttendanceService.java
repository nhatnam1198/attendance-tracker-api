package com.example.demo.Service;

import com.example.demo.Model.Attendance;
import com.example.demo.Repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService {
    @Autowired
    AttendanceRepository attendanceRepository;

    public ArrayList<Attendance> getAttendanceListBySubjectClassId(Integer subjectClassId){
        ArrayList<Attendance> attendanceArrayList = attendanceRepository.findStudentBySubjectClassId(subjectClassId);
        return attendanceArrayList;
    }

    public Attendance addAttendance(Attendance attendance){
        return attendanceRepository.save(attendance);
    }

    public boolean isExist(Attendance attendance){
        return attendanceRepository.existsBySubjectClassAndStudent(attendance.getSubjectClass(),  attendance.getStudent());
    }
}
