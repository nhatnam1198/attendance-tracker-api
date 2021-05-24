package com.example.demo.Controller;

import Util.Const;
import com.example.demo.DTO.ImageDTO;
import com.example.demo.DTO.StudentDTO;
import com.example.demo.Model.EmbeddedImage;
import com.example.demo.Model.Student;

import com.example.demo.Model.Temp;
import com.example.demo.Repository.EmbeddedImageRepository;
import com.example.demo.Repository.StudentRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class EmbeddedImageController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private EmbeddedImageRepository embeddedImageRepository;
//    @PostMapping("api/image/addImage")
//    public @ResponseBody ResponseEntity addImage(@RequestBody Temp person){
//        Student student = new Student();
//        Optional<Student> optionalStudent = studentRepository.findById(1);
//        if(optionalStudent.isPresent()){
//            student = optionalStudent.get();
//        }else{
//            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        EmbeddedImage embeddedImage = new EmbeddedImage();
//        embeddedImage.setStudent(student);
//        embeddedImage.setEmbeddingVector(person.getPredicted_vector().toString());
//
//        student.getEmbeddedImages().add(embeddedImage);
//
//        studentRepository.save(student);
//
//        //Optional<EmbeddedImage> imageOptional = embeddedImageRepository.findByStudentId(1);
//        return new ResponseEntity(HttpStatus.OK);
//    }
    @PostMapping("api/recognize/video")
    public @ResponseBody ResponseEntity recognizeFaceByVideo(@RequestParam("file") MultipartFile file) throws JsonProcessingException {
        List<EmbeddedImage> embeddedImageList = embeddedImageRepository.findAll();
        List<ImageDTO> imageList = new ArrayList<>();
        for (EmbeddedImage image :
                embeddedImageList) {
            ImageDTO imageTemp = new ImageDTO(image.getId(), image.getEmbeddingVector());
            imageList.add(imageTemp);
        }
        String json = new ObjectMapper().writeValueAsString(imageList);
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = Const.DOMAIN_NAME + "v1/resources/person/recog/video";
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
//        map.add("image", file.getResource());
        map.add("video", file.getResource());
        map.add("embedded_image_list", json);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(
                map, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(fooResourceUrl, requestEntity, String.class);
        String imageEmbeddingSet = result.getBody();

//        Student student2 = new Student();
//        StudentDTO studentDTO = new StudentDTO();
//        if(imageEmbeddingSet.compareTo("None") == 0){
//            return new ResponseEntity("Your face is denied", HttpStatus.OK);
//        }else{
//            student2 = studentRepository.findByEmbeddedImageId(Integer.parseInt(imageEmbeddingSet));
//            studentDTO.setStudentId(student2.getId());
//            studentDTO.setName(student2.getName());
//            studentDTO.setEmail(student2.getEmail());
//        }
        return new ResponseEntity(HttpStatus.OK);
    }


}
