package com.service.PropertyService.Controller.UserAdmin;

import com.service.PropertyService.EmailTool.Sender;
import com.service.PropertyService.dto.MailFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Slf4j
@Controller
@RequiredArgsConstructor
public class EmailController {

    private final Sender sender;

    @ResponseBody
    @GetMapping("/email/checkEmail/{emailAddr}")
    public JSONObject confirmEmailCodeV2(@PathVariable String emailAddr) {

        boolean result = true;

        Random random = new Random();  //난수 생성을 위한 랜덤 클래스
        String key="";  //인증번호

        //스크립트에서 보낸 메일을 받을 사용자 이메일 주소
        List<String> to = new ArrayList<>();
        to.add(emailAddr);

        //입력 키를 위한 코드
        for(int i =0; i<3;i++) {
            int index=random.nextInt(25)+65; //A~Z까지 랜덤 알파벳 생성
            key+=(char)index;
        }
        int numIndex=random.nextInt(9999)+1000; //4자리 랜덤 정수를 생성
        key+=numIndex;

        String title = emailAddr + " 님의 회원가입 인증번호입니다.";
        String content = key;

        log.info(title + ":" + content + ":" + to);
        sender.send(title, content, to);

        JSONObject obj = new JSONObject();
        obj.put("result", result);
        obj.put("email", emailAddr);
        obj.put("content", content);

        return obj;
    }
}
