package com.example.demo.Controller;

import com.example.demo.Service.EventService;
import com.example.demo.Service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {
    @Autowired
    StatisticsService statisticsService;
    @Autowired
    EventService eventService;
    @GetMapping("api/statistics/reportFile")
    public @ResponseBody
    ResponseEntity getStatisticsSheet(@RequestParam("subjectClassId") Integer subjectClassId) {
        byte[] fileBytes = statisticsService.getStatisticsSheet(subjectClassId);
        return new ResponseEntity(fileBytes, HttpStatus.OK);
    }


}