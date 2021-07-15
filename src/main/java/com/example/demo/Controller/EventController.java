package com.example.demo.Controller;

import com.example.demo.DTO.EventDTO;
import com.example.demo.Model.Event;
import com.example.demo.Model.Teacher;
import com.example.demo.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@RestController
public class EventController {
    @Autowired EventService eventService;
    @PostMapping("api/event")
    public @ResponseBody
    ResponseEntity addEvent(@RequestBody EventDTO event){
        if(eventService.isExistsByDateTimeAndShiftId(event.getDateTime(), event.getShiftId())){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }else if (!eventService.isDateValid(event)){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }else{
            this.eventService.addEvent(event);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @PutMapping("api/event")
    public @ResponseBody
    ResponseEntity updateEvent(@RequestBody EventDTO eventDto){
        if (!eventService.isDateValid(eventDto)){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }else{
            Event event = this.eventService.updateEvent(eventDto);
            if(event != null){
                return new ResponseEntity(HttpStatus.OK);
            }else {
                return  new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

    @GetMapping("api/event/{date}")
    public @ResponseBody
    ResponseEntity getEventByDateAndUserName(@PathVariable String date, @RequestParam("userName") String userName){
        ArrayList<Event> eventArrayList  = eventService.getEventByDateAndUserName(date, userName);
        return new ResponseEntity(eventArrayList, HttpStatus.OK);
    }

    @GetMapping("api/event/")
    public @ResponseBody ResponseEntity getEventListBySubjectClassId(@RequestParam("subjectClassId") Integer subjectClassId, @RequestParam("email") String email){
        ArrayList<Event>eventArrayList  = eventService.getEventListBySubjectClassId(subjectClassId, email);
        return new ResponseEntity(eventArrayList, HttpStatus.OK);
    }

    @DeleteMapping("api/event")
    public @ResponseBody ResponseEntity deleteEvent(@RequestParam("eventId") Integer eventId){
        eventService.deleteEvent(eventId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
