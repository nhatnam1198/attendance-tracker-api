package com.example.demo.Service;

import com.example.demo.Model.EmbeddedImage;
import com.example.demo.Model.SubjectClass;
import com.example.demo.Repository.EmbeddedImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmbeddedImageService {
    @Autowired
    EmbeddedImageRepository embeddedImageRepository;
    public List<EmbeddedImage> findAll(){
        return embeddedImageRepository.findAll();
    }
}
