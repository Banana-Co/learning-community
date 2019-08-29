package com.x3110.learningcommunity.service;

import com.x3110.learningcommunity.result.Result;

public interface MailService {
    Result sendPin(String toEmail);
}
