package com.example.demo.Repository;

import com.example.demo.Model.Event;
import com.example.demo.Model.SubjectClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public interface EventRepository extends JpaRepository<Event, Integer> {
    boolean existsByDateTimeAndShiftId(LocalDate dateTime, Integer shiftId);

    ArrayList<Event> getByDateTime(LocalDate date);
    ArrayList<Event> getBySubjectClassId(Integer subjectClassId);

    void deleteById(Integer eventId);
}
