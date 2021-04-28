package com.service.PropertyService.Controller.UserAdmin;

import com.service.PropertyService.EmailTool.Sender;
import com.service.PropertyService.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class EmailController {

    private final Sender sender;
    private final UserService userService;

    @ResponseBody
    @GetMapping("/email/checkEmail/{emailAddr}")
    public JSONObject confirmEmailCode(@PathVariable String emailAddr) {

        boolean result = true;

        //스크립트에서 보낸 메일을 받을 사용자 이메일 주소
        List<String> to = new ArrayList<>();
        to.add(emailAddr);


        String title = emailAddr + " 님의 회원가입 인증번호입니다.";
        String content = userService.getAuthCode();

        log.info(title + ":" + content + ":" + to);
        sender.send(title, content, to);

        JSONObject obj = new JSONObject();
        obj.put("result", result);
        obj.put("email", emailAddr);
        obj.put("content", content);

        return obj;
    }
}
