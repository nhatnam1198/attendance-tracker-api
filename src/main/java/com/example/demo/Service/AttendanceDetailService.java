package com.example.demo.Service;

import Util.Const;
import com.example.demo.DTO.EventDTO;
import com.example.demo.Model.Attendance;
import com.example.demo.Model.AttendanceDetails;
import com.example.demo.Model.Event;
import com.example.demo.Model.Student;
import com.example.demo.Repository.AttendanceDetailRepository;
import com.example.demo.Repository.AttendanceRepository;
import com.example.demo.Repository.EventRepository;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

@Service
public class AttendanceDetailService {
    @Autowired
    AttendanceDetailRepository attendanceDetailRepository;
    private static final int EVENT_CHECKED = 1;
    @Autowired
    AttendanceService attendanceService;
    @Autowired
    EventService eventService;
    public void addAttendanceDetails(ArrayList<Student> attendedStudentArrayList, Event event){
        ArrayList<AttendanceDetails> leaveOfAbsence = getLeaveOfAbsentRequestList(event.getId());
        HashSet<Integer> leaveOfAbsenceAttendanceId = new HashSet<>();
        for(int i = 0; i< leaveOfAbsence.size(); i++){
            leaveOfAbsenceAttendanceId.add(leaveOfAbsence.get(i).getAttendance().getId());
        }

        HashSet<Integer> attendedStudentIdSet = new HashSet<>();
        for(int i = 0; i< attendedStudentArrayList.size(); i++){
            Integer studentId = attendedStudentArrayList.get(i).getId();
            if(!attendedStudentIdSet.contains(studentId)){
                attendedStudentIdSet.add(studentId);
            }
        }

        // remove students who have leave of absence request
        ArrayList<Attendance> attendanceArrayList = attendanceService.getAttendanceListBySubjectClassId(event.getSubjectClass().getId());
        if(leaveOfAbsence!= null && leaveOfAbsence.size() != 0){
            for(int i = 0; i< attendanceArrayList.size(); i++){
                if(leaveOfAbsenceAttendanceId.contains(attendanceArrayList.get(i).getId())){
                    attendanceArrayList.remove(i);
                }
            }
        }

        Optional<Event> optionalEvent = eventService.findById(event.getId());
        if(optionalEvent.isPresent()){
            Event dbEvent = optionalEvent.get();
            for(int i = 0; i< attendanceArrayList.size(); i++){
                AttendanceDetails attendanceDetail = new AttendanceDetails();
                Attendance attendance = attendanceArrayList.get(i);
                Integer status = Const.ABSENT;
                if(attendedStudentIdSet.contains(attendance.getStudent().getId())){
                    status = Const.ATTENDED;
                }
                attendanceDetail.setEvent(dbEvent);
                attendanceDetail.setAttendance(attendance);
                attendanceDetail.setStatus(status);
                attendanceDetailRepository.saveAndFlush(attendanceDetail);
            }
            EventDTO eventDTO = new EventDTO();
            eventDTO.setId(event.getId());
            eventDTO.setStatus(EVENT_CHECKED);
            eventService.updateStatus(eventDTO);
        }
    }

    public boolean isExistsByEventId(Integer eventId){
        ArrayList<Integer> status = new ArrayList<>();
        status.add(Const.LEAVE_OF_ABSENCE_REQUEST);
        status.add(Const.ALLOWED);
        if(attendanceDetailRepository.findByEventIdAndStatusNotIn(eventId, status) == null || attendanceDetailRepository.findByEventIdAndStatusNotIn(eventId, status).size() == 0){
            return false;
        }
        return true;
    }

    public ArrayList<AttendanceDetails> getAttendedResultList(Integer eventId) {
        ArrayList<AttendanceDetails> attendanceArrayList = attendanceDetailRepository.findByEventId(eventId);
        for(int i = 0; i< attendanceArrayList.size(); i++){
//            if(attendanceArrayList.get(i).getAttendance().getStudent().getEmbeddedImages() != null && attendanceArrayList.get(i).getAttendance().getStudent().getEmbeddedImages().size() != 0){
//                profileImagePath = attendance.getStudent().getEmbeddedImages().get(0).getFilePath();
//            }
//            if(profileImagePath != null && profileImagePath.compareTo("") != 0){
//                try {
//                    FileInputStream inputStream = new FileInputStream(Const.IMAGE_STORAGE_PATH + profileImagePath);
//                    byte[] bytes = IOUtils.toByteArray(inputStream);
//                    attendance.getStudent().setProfileImage(bytes);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                    continue;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    continue;
//                }
//            }
        }

        return attendanceArrayList;
    }

    public ArrayList<AttendanceDetails> getLeaveOfAbsentRequestList(Integer eventId) {
        List<Integer> status = new ArrayList<>();
        status.add(Const.LEAVE_OF_ABSENCE_REQUEST);
        status.add(Const.ALLOWED);
        ArrayList<AttendanceDetails> attendanceArrayList = attendanceDetailRepository.findByEventIdAndStatusIn(eventId, status);
        return attendanceArrayList;
    }

    public void approveLeaveOfAbsenceRequest(Integer attendanceId) {
        AttendanceDetails attendanceDetailToUpdate = attendanceDetailRepository.findByAttendanceId(attendanceId);
        attendanceDetailToUpdate.setStatus(Const.ALLOWED);
        attendanceDetailRepository.save(attendanceDetailToUpdate);
    }

    public void updateAttendanceDetail(ArrayList<AttendanceDetails> attendanceDetailsArrayList) {
        ArrayList<Integer> attendanceDetailIds = new ArrayList<>();
        HashMap<Integer, Integer> statusMap = new HashMap<>();
        for (AttendanceDetails attendanceDetails:
             attendanceDetailsArrayList) {
            attendanceDetailIds.add(attendanceDetails.getId());
            statusMap.putIfAbsent(attendanceDetails.getId(), attendanceDetails.getStatus());
        }
        ArrayList<AttendanceDetails> attendanceDetailsToUpdate = attendanceDetailRepository.findAllById(attendanceDetailIds);

        for(int i = 0; i< attendanceDetailsToUpdate.size(); i++){
            AttendanceDetails attendanceDetail = attendanceDetailsToUpdate.get(i);
            if(statusMap.containsKey(attendanceDetail.getId())){
                attendanceDetail.setStatus(statusMap.get(attendanceDetail.getId()));
                attendanceDetailRepository.save(attendanceDetail);
            }
        }
    }
}
