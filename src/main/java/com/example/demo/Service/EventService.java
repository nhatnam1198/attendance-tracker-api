package com.example.demo.Service;

import com.example.demo.DTO.EventDTO;
import com.example.demo.Model.Event;
import com.example.demo.Model.Shift;
import com.example.demo.Model.SubjectClass;
import com.example.demo.Model.Teacher;
import com.example.demo.Repository.EventRepository;
import com.example.demo.Repository.TeacherRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        Event result = new Event();
        SubjectClass subjectClass = subjectClassService.getById(eventDto.getSubjectClassId());
        Shift shift = shiftService.getById(eventDto.getShiftId());
        Event event = new Event();
        event.setSubjectClass(subjectClass);
        event.setShift(shift);
        event.setDateTime(eventDto.getDateTime());
        event.setName(eventDto.getName());
        event.setStatus(eventDto.getStatus());

        result = eventRepository.save(event);
        return result;
    }
    public Event updateEvent(EventDTO eventDto){
        Event result = new Event();
        SubjectClass subjectClass = subjectClassService.getById(eventDto.getSubjectClassId());
        Shift shift = shiftService.getById(eventDto.getShiftId());
        Optional<Event> eventOptional = eventRepository.findById(eventDto.getId());
        if(eventOptional.isPresent()){
            Event event = eventOptional.get();
            event.setSubjectClass(subjectClass);
            event.setShift(shift);
            event.setDateTime(eventDto.getDateTime());
            event.setName(eventDto.getName());
            event.setStatus(eventDto.getStatus());

            result = eventRepository.save(event);
        }
        return result;
    }

    public boolean isExistsByDateTimeAndShiftId(LocalDate date, Integer shiftId){
        if(eventRepository.existsByDateTimeAndShiftId(date, shiftId)){
            return true;
        }else{
            return false;
        }
    }

    public boolean isDateValid(EventDTO eventDTO){
        SubjectClass subjectClass = subjectClassService.getById(eventDTO.getSubjectClassId());
        LocalDate subjectClassStartDate = subjectClass.getStartDateTime();
        LocalDate subjectClassEndDate = subjectClass.getEndDateTime();
        LocalDate eventDate = eventDTO.getDateTime();
        if(eventDate.isBefore(subjectClassStartDate) || eventDate.isAfter(subjectClassEndDate)){
            return false;
        }
        return true;
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
    public ArrayList<Event> getEventByDateAndUserName(String date, String userName) {
        List<SubjectClass> subjectClassList = subjectClassService.getByTeacherUserName(userName);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);
        for(int i = 0; i< subjectClassList.size(); i++){
            ArrayList<Event> eventArrayList = eventRepository.getByDateTimeAndSubjectClass(localDate, subjectClassList.get(i));
            if(eventArrayList != null){
                return eventArrayList;
            }
        }
        return  new ArrayList<Event>();
    }

    public ArrayList<Event> getEventListBySubjectClassId(Integer subjectClassId) {
        ArrayList<Event> eventArrayList = eventRepository.getBySubjectClassId(subjectClassId);
        return eventArrayList;
    }

    public void deleteEvent(Integer eventId) {
        eventRepository.deleteById(eventId);
    }
}

