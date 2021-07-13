package com.example.demo.Controller;

import Util.Const;
import com.example.demo.DTO.ImageDTO;
import com.example.demo.DTO.StudentDTO;
import com.example.demo.Model.EmbeddedImage;
import com.example.demo.Model.Student;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.Service.EmbeddedImageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@RestController
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    EmbeddedImageService embeddedImageService;
    @PostMapping("api/student")
    public @ResponseBody ResponseEntity addStudent(@RequestParam String name, @RequestParam String email){

        Student student = new Student();
        boolean isStudentExist = studentRepository.existsByEmail(email);
        if(isStudentExist){
            return new ResponseEntity("Email account already existed", HttpStatus.OK);
        }else{
            student.setName(name);
            student.setEmail(email);
            studentRepository.save(student);
            return new ResponseEntity("saved", HttpStatus.OK);
        }
    }
    @PostMapping("api/student/embeddedImage")
    public @ResponseBody ResponseEntity addEmbeddingVector(@RequestParam Integer studentId, @RequestParam("file") MultipartFile multipartFile){
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = Const.DOMAIN_NAME + "v1/resources/person/add";
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("image", multipartFile.getResource());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(
                map, headers);
        ResponseEntity<String> response
                = restTemplate.postForEntity(fooResourceUrl, requestEntity, String.class);
        String predicted_vector = response.getBody().toString();

        String path = Const.IMAGE_STORAGE_PATH + studentId+"/" + multipartFile.getOriginalFilename();
        // Create directory if not exists
        try {
            Files.createDirectories(Paths.get(Const.IMAGE_STORAGE_PATH + studentId));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Files.copy(multipartFile.getInputStream(), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Student student = new Student();
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if(optionalStudent.isPresent()){
            student = optionalStudent.get();
        }else{
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        EmbeddedImage embeddedImage = new EmbeddedImage();
        embeddedImage.setStudent(student);
        embeddedImage.setEmbeddingVector(predicted_vector);
        embeddedImage.setFilePath(studentId+"/"+multipartFile.getOriginalFilename());
        student.getEmbeddedImages().add(embeddedImage);
        studentRepository.save(student);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/student")
    public String getEmbeddingVector() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = Const.DOMAIN_NAME + "/v1/resources/image/embedding";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl, String.class);
        return response.getBody().toString();
    }

    @PostMapping("api/student/recognize/image")
    public @ResponseBody ResponseEntity recognizeFaceByVideo(@RequestParam("file") MultipartFile file) throws JsonProcessingException {
        List<EmbeddedImage> embeddedImageList = embeddedImageService.findAll();
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
        map.add("image", file.getResource());
        map.add("embedded_image_list", json);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(
                map, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(fooResourceUrl, requestEntity, String.class);
        String imageEmbeddingSet = result.getBody();

        Gson gson = new Gson();
        JsonObject jsonObject  = gson.fromJson(imageEmbeddingSet, JsonObject.class);
        JsonArray jsonArray = jsonObject.getAsJsonArray("results");
        ArrayList<Integer> imageEmbeddingList = new ArrayList<>();
        for(JsonElement jsonElement : jsonArray){
            imageEmbeddingList.add(jsonElement.getAsInt());
        }

        if(jsonObject.size() == 0){
            return new ResponseEntity("Empty", HttpStatus.OK);
        }

        Student studentDto = new Student();
        ArrayList<StudentDTO> studentDTOArrayList = new ArrayList<>();
        Set<Integer> studentIdSet = new HashSet<>();
        for(int i = 0; i< imageEmbeddingList.size(); i++){

            studentDto = (Student) studentRepository.findByEmbeddedImageId(imageEmbeddingList.get(i));
            StudentDTO studentDTO = new StudentDTO();
            Integer studentId = studentDto.getId();
            if(!studentIdSet.contains(studentId)){
                studentIdSet.add(studentDto.getId());
                studentDTO.setId(studentDto.getId());
                studentDTO.setName(studentDto.getName());
                studentDTO.setEmail(studentDto.getEmail());
                studentDTO.setProfileImage(studentDto.getProfileImage());
                studentDTOArrayList.add(studentDTO);
            }else{
                continue;
            }
        }
        return new ResponseEntity(studentDTOArrayList, HttpStatus.OK);
    }
}
