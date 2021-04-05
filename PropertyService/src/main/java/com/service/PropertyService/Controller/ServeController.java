package com.service.PropertyService.Controller;

import com.service.PropertyService.Repository.LikeRepository;
import com.service.PropertyService.Service.PostService;
import com.service.PropertyService.Service.UserService;
import com.service.PropertyService.domain.Like;
import com.service.PropertyService.domain.Post;
import com.service.PropertyService.domain.User;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Random;

/*
 * post의 서브기능을 처리하는 컨트롤러
 * @author: 지영석
 * @Object: 좋아요 로직 생성
 */

@Controller
@RequiredArgsConstructor
public class ServeController {

    private final PostService postService;
    private final UserService userService;
    private final LikeRepository likeRepository;

    @ResponseBody
    @GetMapping("/like/getlike/{postId}")
    public JSONObject getUserLike(@PathVariable Long postId,
                                  Principal principal) {

        JSONObject jsonObject = new JSONObject();
        User user = userService.findOneByUserEmail(principal.getName());

        if (likeRepository.findByUserIdAndPostId(user.getId(), postId) != null) {
            jsonObject.put("Message", "already clicked");
            return jsonObject;
        } else {
            jsonObject.put("Message", "non clicked");
            return jsonObject;
        }
    }

    @ResponseBody
    @GetMapping("/like/{postId}")
    public JSONObject setUpLike(@PathVariable Long postId,
                            Principal principal) {

        User user = userService.findOneByUserEmail(principal.getName());
        postService.createLike(user.getId(), postId);

        JSONObject jsonObject = new JSONObject();
        try {
            Long likeCnt = postService.LikeCount(postId);
            jsonObject.put("like_cnt", likeCnt);
            jsonObject.put("Message", "set like complete!");
        } catch (IllegalStateException e) {
            e.printStackTrace();
            String eMessage = e.getMessage();
            jsonObject.put("Message", eMessage);
        }

        return jsonObject;
    }

    @ResponseBody
    @GetMapping("/likeDelete/{postId}")
    public JSONObject deleteLike(@PathVariable Long postId,
                             Principal principal) {

        User user = userService.findOneByUserEmail(principal.getName());
        postService.deleteLike(user.getId(), postId);

        JSONObject jsonObject = new JSONObject();
        try {
            Long likeCnt = postService.LikeCount(postId);
            jsonObject.put("like_cnt", likeCnt);
            jsonObject.put("Message", "delete like complete!");
        } catch (IllegalStateException e) {
            e.printStackTrace();
            String eMessage = e.getMessage();
            jsonObject.put("Message", eMessage);
        }

        return jsonObject;
    }
}
