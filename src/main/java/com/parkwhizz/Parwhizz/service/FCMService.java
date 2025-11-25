package com.parkwhizz.Parwhizz.service;

public interface FCMService {
    void sendMessage(String token, String title, String body) throws Exception;
}

