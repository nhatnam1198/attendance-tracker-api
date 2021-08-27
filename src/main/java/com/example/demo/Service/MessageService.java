package com.example.demo.Service;

import com.example.demo.Model.Event;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
@Service
public class MessageService {
    public String sendAttendanceTrackerMessage(Event event){
        Integer eventId = event.getId();
        String subjectClassName = event.getSubjectClass().getName();
//        String teacherName = event.getSubjectClass().getTeacher().getEmail();
        String shiftName = event.getShift().getName();
        String dateTime = event.getDateTime().toString();

        FileInputStream serviceAccount =
                null;
        try {
            serviceAccount = new FileInputStream(System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            FirebaseApp.getInstance();
        }catch (IllegalStateException e){
            FirebaseApp.initializeApp(options);
        }



        // This registration token comes from the client FCM SDKs.
        String registrationToken = "f-YbpOOkQDq4Dk2ucwTwTy:APA91bHGLgHo4_dnnKInFEvXAM8tls86SNP9sMr3bZIxebRuKF7XdPLCqKpBTb3OZVI7EH8FHKQaZB8e0nelSXOyiSk_FVj3yP4zp2RHtEV8O2x0Zi-hrQ92K0LdLdS-I2BWraG_bt2j";

        // See documentation on defining a message payload.
        Message message = Message.builder().setNotification(Notification.builder()
                .setTitle("Điểm danh lớp")
                .setBody("Giáo viên đang yêu cầu điểm danh. Mời sinh viên truy cập ứng dụng để diểm danh")
                .build())
                .setAndroidConfig(AndroidConfig.builder()
                        .setTtl(3600 * 1000)
                        .setNotification(AndroidNotification.builder()
                                .setIcon("stock_ticker_update")
                                .setColor("#f45342")
                                .build())
                        .build())
                .setApnsConfig(ApnsConfig.builder()
                        .setAps(Aps.builder()
                                .setBadge(42)
                                .build())
                        .build())
//                .putData("eventIdString", "494")
//                .putData("subjectClassName", "JAVA")
                .putData("teacherEmail", "chris@phanthora.onmicrosoft.com")
//                .putData("shift", "Ca 2")
//                .putData("timeRage", "30000")
//                .putData("dateTime", "23/09/2021")
                .putData("eventIdString", String.valueOf(eventId))
                .putData("subjectClassName", subjectClassName)
//                .putData("teacherEmail", teacherName)
                .putData("shift", shiftName)
                .putData("timeRage", "180000")
                .putData("dateTime", dateTime)
                .setToken(registrationToken)
                .build();

        // Send a message to the device corresponding to the provided
        // registration token.
        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
        // Response is a message ID string.
        System.out.println("Successfully sent message: " + response);
        return  response;
    }
}
