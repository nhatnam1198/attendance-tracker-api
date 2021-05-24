package com.example.demo.Repository;

import com.example.demo.Model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Date;

public interface EventRepository extends JpaRepository<Event, Integer> {
    boolean existsByDateTimeAndShiftId(Date dateTime, Integer shiftId);

    ArrayList<Event> getByDateTime(Date date);
}
