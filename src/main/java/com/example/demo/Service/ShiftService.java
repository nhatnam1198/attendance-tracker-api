package com.example.demo.Service;

import com.example.demo.Model.Shift;
import com.example.demo.Model.SubjectClass;
import com.example.demo.Repository.ShiftRepository;
import com.example.demo.Repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShiftService {
    @Autowired
    ShiftRepository shiftRepository;
    public Shift getById(Integer id){
        return shiftRepository.getById(id);
    }

}
