package com.parkwhizz.Parwhizz.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.parkwhizz.Parwhizz.service.FCMService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FCMServiceImple implements FCMService {
    Logger logger =  LoggerFactory.getLogger(FCMServiceImple.class);
    @Override
    public void sendMessage(String token, String title, String body) throws FirebaseMessagingException {
        try{
            Message message = Message.builder()
                    .setNotification(Notification.builder().setTitle(title)
                            .setBody(body).setImage("https://t3.ftcdn.net/jpg/02/25/50/42/360_F_225504297_87gJSI1zso872WwS4ExbWcCRSA6W6yf5.jpg").build())
//                .putData("title", title)
//                .putData("body", body)
                    .setToken(token)
                    .build();
            System.out.println(message);
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}

