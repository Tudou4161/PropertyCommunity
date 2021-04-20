package com.service.PropertyService.Controller.UserAdmin;

import com.service.PropertyService.EmailTool.Sender;
import com.service.PropertyService.Service.UserService;
import com.service.PropertyService.domain.User;
import com.service.PropertyService.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class FindUserAuthController {

    private final UserService userService;
    private final Sender sender;

    @GetMapping("/findUser/findEmail")
    public String findEmailTemplate() {
        return "findID";
    }


    @ResponseBody
    @GetMapping("/findUser/findEmail/{name}/{phoneNum}")
    public JSONObject findUserEmail(@PathVariable String name,
                                    @PathVariable String phoneNum) {

        User user = userService.findOneByUsernameAndPhoneNumber(name, phoneNum);

        char[] charArr = user.getEmail().toCharArray();

        for (int i=0; i<charArr.length; i+=2) {
            charArr[i] = '*';
        }

        String email = String.valueOf(charArr);

        JSONObject obj = new JSONObject();
        obj.put("data", email);

        return obj;
    }

    @GetMapping("/findUser/findpw")
    public String sendPasswordCodeTemplate() {
        return "findPW";
    }


    @ResponseBody
    @GetMapping("/findUser/findpw/{userEmail}")
    public JSONObject sendAlternatedPasswordCode(@PathVariable String userEmail) {

        List<String> to = new ArrayList<>();
        to.add(userEmail);


        String title = userEmail + " 님의 회원가입 인증번호입니다.";
        String content = userService.getAuthCode();

        log.info(title + ":" + content + ":" + to);
        sender.send(title, content, to);

        JSONObject obj = new JSONObject();
        obj.put("alterCode", content);
        return obj;
    }

    @GetMapping("/findUser/change")
    public String changePasswordTemplate() {
        return "changePW";
    }

    @ResponseBody
    @PutMapping("/findUser/change/{email}")
    public JSONObject changePassword(@RequestBody @Valid PasswordRequestDto request,
                                     @PathVariable String email) {

        User user = userService.findByUserEmail(email);

        UserDto userDto = UserDto.builder()
                .auth(user.getAuth())
                .password(request.getPassword())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .build();

        userService.join(userDto);

        JSONObject obj = new JSONObject();
        obj.put("message", "비밀번호 변경에 성공하셨습니다.");
        obj.put("data", user.getPassword());

        return obj;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class PasswordRequestDto {

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "[a-zA-Z1-9]{6,12}", message = "비밀번호는 영어와 숫자로 포함해서 6-12자 이내로 입력해주세요.")
        private String password;
    }
}
