package com.example.demo.Repository;

import com.example.demo.Model.Attendance;
import com.example.demo.Model.AttendanceDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface AttendanceDetailRepository extends JpaRepository<AttendanceDetails, Integer> {

    ArrayList<AttendanceDetails> findByEventIdAndStatusNotIn(Integer integer, ArrayList<Integer> status);

    ArrayList<AttendanceDetails> findByEventId(Integer eventId);
    ArrayList<AttendanceDetails> findByEventIdAndStatusIn(Integer eventId, List<Integer> status);
    AttendanceDetails findByAttendanceId(Integer attendanceId);

    @Override
    ArrayList<AttendanceDetails> findAllById(Iterable<Integer> iterable);

    AttendanceDetails findAttendanceDetailsByAttendanceAndEventId(Attendance attendance, int eventId);
}
