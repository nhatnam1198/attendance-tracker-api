package com.example.demo.Service;

import Util.Const;
import com.example.demo.Model.Attendance;
import com.example.demo.Repository.AttendanceRepository;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService {
    @Autowired
    AttendanceRepository attendanceRepository;

    public ArrayList<Attendance> getAttendanceListBySubjectClassId(Integer subjectClassId){
        ArrayList<Attendance> attendanceArrayList = attendanceRepository.findStudentBySubjectClassId(subjectClassId);
        for(int i = 0; i < attendanceArrayList.size(); i++){
            Attendance attendance = attendanceArrayList.get(i);
            String profileImagePath = "";
            if(attendance.getStudent().getEmbeddedImages() != null && attendance.getStudent().getEmbeddedImages().size() != 0){
                profileImagePath = attendance.getStudent().getEmbeddedImages().get(0).getFilePath();
            }
            if(profileImagePath != null && profileImagePath.compareTo("") != 0){
                try {
                    FileInputStream inputStream = new FileInputStream(Const.IMAGE_STORAGE_PATH + profileImagePath);
                    byte[] bytes = IOUtils.toByteArray(inputStream);
                    attendance.getStudent().setProfileImage(bytes);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    continue;
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
        return attendanceArrayList;
    }

    public Attendance addAttendance(Attendance attendance){
        return attendanceRepository.save(attendance);
    }

    public boolean isExist(Attendance attendance){
        return attendanceRepository.existsBySubjectClassAndStudent(attendance.getSubjectClass(),  attendance.getStudent());
    }
}
