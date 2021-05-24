package com.example.demo.Controller;

import com.example.demo.Model.Shift;
import com.example.demo.Model.Student;
import com.example.demo.Model.Subject;
import com.example.demo.Repository.ShiftRepository;
import com.example.demo.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class ShiftController {
    @Autowired
    private ShiftRepository shiftRepository;
    @GetMapping("api/shift/list")
    public @ResponseBody ResponseEntity getShiftList(){
        Student student = new Student();
        List<Shift> shiftList = shiftRepository.findAll();
        return new ResponseEntity(shiftList, HttpStatus.OK);
    }

    @PostMapping("api/shift")
    public @ResponseBody ResponseEntity createSubject(@RequestBody Shift shift){
        if(shift.getName().trim().compareTo("") == 0){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        boolean isExists = shiftRepository.existsByName(shift.getName());
        if(isExists){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        shiftRepository.save(shift);
        return new ResponseEntity(HttpStatus.OK);
    }
}
