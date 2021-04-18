package com.service.PropertyService.EmailTool;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SenderTest {

    @Autowired
    private Sender sender;

    @Test
    public void 이메일발송테스트() throws Exception {

        List<String> to = new ArrayList<>();
        to.add("dudtjr5787@gmail.com");
        String subject = "테스트";
        String content = "테스트입니다!";

        sender.send(subject, content, to);

    }
}