package com.example.demo.Controller;

import com.example.demo.DTO.EventDTO;
import com.example.demo.Model.Event;
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
        }else{
            this.eventService.addEvent(event);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @GetMapping("api/event/{date}")
    public @ResponseBody
    ResponseEntity getEventByDate(@PathVariable String date){
        ArrayList<Event>eventArrayList  = eventService.getEventByDate(date);
        return new ResponseEntity(eventArrayList, HttpStatus.OK);
    }
}
