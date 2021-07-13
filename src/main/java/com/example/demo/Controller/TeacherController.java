package com.example.demo.Controller;

import com.example.demo.Model.Subject;
import com.example.demo.Model.Teacher;
import com.example.demo.Repository.SubjectRepository;
import com.example.demo.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class TeacherController {
    @Autowired
    private TeacherRepository teacherRepository;
    @GetMapping("api/teacher/list")
    public @ResponseBody
    ResponseEntity getTeacherList(){
        List<Teacher> teacherList = teacherRepository.findAll();
        return new ResponseEntity(teacherList, HttpStatus.OK);
    }

    @PostMapping("api/teacher")
    public @ResponseBody ResponseEntity createTeacher(@RequestBody Teacher teacher){
        if(teacher.getName().trim().compareTo("") == 0){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        boolean isExists = teacherRepository.existsByName(teacher.getName());
        if(isExists){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        teacherRepository.save(teacher);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("api/teacher")
    public @ResponseBody ResponseEntity getTeacher(@RequestParam("email") String teacherEmail){
        Teacher teacherForResponse = teacherRepository.getByEmail(teacherEmail);
        if(teacherForResponse == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(teacherForResponse, HttpStatus.OK);
    }
}
