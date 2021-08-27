package com.example.demo.Controller;

import Util.Const;
import com.example.demo.DTO.EventDTO;
import com.example.demo.DTO.ImageDTO;
import com.example.demo.DTO.StudentDTO;
import com.example.demo.Model.Attendance;
import com.example.demo.Model.EmbeddedImage;
import com.example.demo.Model.Event;
import com.example.demo.Model.Student;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.Service.AttendanceDetailService;
import com.example.demo.Service.EmbeddedImageService;
import com.example.demo.Service.EventService;
import com.example.demo.Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@RestController
public class RecognitionController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    AttendanceDetailService attendanceDetailService;
    @Autowired
    EmbeddedImageService embeddedImageService;
    @Autowired
    MessageService messageService;

    @Autowired
    EventService eventService;
    @PostMapping("api/recognition/student/video")
    public @ResponseBody
    ResponseEntity recognizeFaceByVideo(@RequestParam MultipartFile file) throws JsonProcessingException {
        List<EmbeddedImage> embeddedImageList = embeddedImageService.findAll();

        List<ImageDTO> imageList = new ArrayList<>();
        for (EmbeddedImage image :
                embeddedImageList) {
            ImageDTO imageTemp = new ImageDTO(image.getId(), image.getEmbeddingVector());
            imageList.add(imageTemp);
        }

        String json = new ObjectMapper().writeValueAsString(imageList);
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl
                = Const.DOMAIN_NAME + "v1/resources/person/recog/video";
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("video", file.getResource());
        map.add("embedded_image_list", json);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(
                map, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(resourceUrl, requestEntity, String.class);

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

        Student student = new Student();
        ArrayList<StudentDTO> studentList = new ArrayList<>();
        Set<Integer> studentIdSet = new HashSet<>();
        for(int i = 0; i< imageEmbeddingList.size(); i++){
            StudentDTO studentDTO = new StudentDTO();
            String profileImagePath = "";
            student = studentRepository.findByEmbeddedImageId(imageEmbeddingList.get(i));
            Integer studentId = student.getId();
            if(!studentIdSet.contains(studentId)){
                studentIdSet.add(studentId);
                studentDTO.setId(studentId);
                studentDTO.setName(student.getName());
                studentDTO.setEmail(student.getEmail());
                if(student.getEmbeddedImages() != null && student.getEmbeddedImages().size() != 0){
                    profileImagePath = student.getEmbeddedImages().get(0).getFilePath();
                }
                if(profileImagePath != null && profileImagePath.compareTo("") != 0){
                    try {
                        FileInputStream inputStream = new FileInputStream(Const.IMAGE_STORAGE_PATH + profileImagePath);
                        byte[] bytes = IOUtils.toByteArray(inputStream);
                        studentDTO.setProfileImage(bytes);
                        studentList.add(studentDTO);
                    } catch (IOException e) {
                        e.printStackTrace();
                        studentList.add(studentDTO);
                    }
                }
            }
        }
        return new ResponseEntity(studentList, HttpStatus.OK);
    }

    @PostMapping("api/recognition/student/image")
    public @ResponseBody
    ResponseEntity recognizeFaceByImage(@RequestParam("file") MultipartFile file) throws FileNotFoundException {
        List<EmbeddedImage> embeddedImageList = embeddedImageService.findAll();
        List<ImageDTO> imageList = new ArrayList<>();
        for (EmbeddedImage image :
                embeddedImageList) {
            ImageDTO imageTemp = new ImageDTO(image.getId(), image.getEmbeddingVector());
            imageList.add(imageTemp);
        }
        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(imageList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl
                = Const.DOMAIN_NAME + "v1/resources/person/recog/image";
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("image", file.getResource());
        map.add("embedded_image_list", json);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(
                map, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(resourceUrl, requestEntity, String.class);

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

        Student student = new Student();
        ArrayList<StudentDTO> studentList = new ArrayList<>();
        Set<Integer> studentIdSet = new HashSet<>();
        for(int i = 0; i< imageEmbeddingList.size(); i++){
            StudentDTO studentDTO = new StudentDTO();
            String profileImagePath = "";
            student = studentRepository.findByEmbeddedImageId(imageEmbeddingList.get(i));
            Integer studentId = student.getId();
            if(!studentIdSet.contains(studentId)){
                studentIdSet.add(studentId);
                studentDTO.setId(studentId);
                studentDTO.setName(student.getName());
                studentDTO.setEmail(student.getEmail());
                if(student.getEmbeddedImages() != null && student.getEmbeddedImages().size() != 0){
                    profileImagePath = student.getEmbeddedImages().get(0).getFilePath();
                }
                if(profileImagePath != null && profileImagePath.compareTo("") != 0){
                    try {
                        FileInputStream inputStream = new FileInputStream(Const.IMAGE_STORAGE_PATH + profileImagePath);
                        byte[] bytes = IOUtils.toByteArray(inputStream);
                        studentDTO.setProfileImage(bytes);
                        studentList.add(studentDTO);
                    } catch (IOException e) {
                        e.printStackTrace();
                        studentList.add(studentDTO);
                    }
                }
            }
        }
        return new ResponseEntity(studentList, HttpStatus.OK);
    }

    @PostMapping("api/recognition/student/wifi")
    public @ResponseBody
    ResponseEntity recognizeStudentByWifi(@RequestBody HashMap<String, Object> map){
        if(!map.containsKey("attendanceArrayList") || !map.containsKey("event")){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        ArrayList<Attendance> attendanceArrayList = (ArrayList<Attendance>)map.get("attendanceArrayList");
        ArrayList<Attendance> attendanceArrList = new ArrayList<>();
        for (Object object :
                attendanceArrayList) {
            Attendance newAttendance = new Attendance();
            newAttendance = new ObjectMapper().convertValue(object, Attendance.class);
            attendanceArrList.add(newAttendance);
        }

        Event event = new ObjectMapper().registerModule(new JavaTimeModule()).convertValue(map.get("event"), Event.class);

        if(attendanceDetailService.isExistsByEventId(event.getId())){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        attendanceDetailService.initAttendanceDetails(attendanceArrList, event);
        messageService.sendAttendanceTrackerMessage(event);
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setStatus(1);
        eventService.updateStatus(eventDTO);
        return  new ResponseEntity(HttpStatus.OK);
    }

}
