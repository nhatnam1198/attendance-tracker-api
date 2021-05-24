package com.example.demo.Service;

import com.example.demo.DTO.EventDTO;
import com.example.demo.Model.Event;
import com.example.demo.Model.Shift;
import com.example.demo.Model.SubjectClass;
import com.example.demo.Model.Teacher;
import com.example.demo.Repository.EventRepository;
import com.example.demo.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;

    @Autowired
    SubjectClassService subjectClassService;
    @Autowired
    ShiftService shiftService;
    public Event addEvent(EventDTO eventDto){
        SubjectClass subjectClass = subjectClassService.getById(eventDto.getSubjectClassId());
        Shift shift = shiftService.getById(eventDto.getShiftId());
        Event event = new Event();
        event.setSubjectClass(subjectClass);
        event.setShift(shift);
        event.setDateTime(eventDto.getDateTime());
        event.setName(eventDto.getName());
        event.setStatus(eventDto.getStatus());

        Event result = eventRepository.save(event);
        return result;
    }

    public boolean isExistsByDateTimeAndShiftId(Date date, Integer shiftId){
        if(eventRepository.existsByDateTimeAndShiftId(date, shiftId)){
            return true;
        }else{
            return false;
        }
    }
    public Optional<Event> findById(Integer eventId){
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        return  optionalEvent;
    }
    public Event updateStatus(EventDTO eventDto){
        Event eventToUpdate = eventRepository.getOne(eventDto.getId());
        eventToUpdate.setStatus(eventDto.getStatus());
        return eventRepository.save(eventToUpdate);
    }
    public ArrayList<Event> getEventByDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = new Date();
        try {
            newDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  eventRepository.getByDateTime(newDate);
    }
}

