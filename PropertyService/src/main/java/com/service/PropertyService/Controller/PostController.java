package com.service.PropertyService.Controller;

import com.service.PropertyService.Repository.PropertyInfoRepository;
import com.service.PropertyService.Service.PostService;
import com.service.PropertyService.Service.PropertyInfoService;
import com.service.PropertyService.Service.UserService;
import com.service.PropertyService.domain.Address;
import com.service.PropertyService.domain.Post;
import com.service.PropertyService.domain.PropertyInfo;
import com.service.PropertyService.domain.TransactionType;
import com.service.PropertyService.dto.PostDto;
import com.service.PropertyService.dto.PostSearchCondition;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.dom4j.rule.Mode;
import org.json.simple.JSONObject;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Array;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final PropertyInfoService propertyInfoService;


    @GetMapping("/lists/listinfo")
    public String postRendering(@ModelAttribute PostSearchCondition condition,
                                Model model) {

        List<Post> posts = postService.findPostsBySearchCondition(condition);

        List<Long> likeCnt = new ArrayList<>();
        for (Post post: posts) {
            Long _tmp = postService.LikeCount(post.getId());
            likeCnt.add(_tmp);
        }

        List<PostDto> postDtos = new ArrayList<>();
        int i = 0;
        for (Post post : posts) {
            PostDto postDto = new PostDto();
            postDto.setId(post.getId());
            postDto.setAuthor(post.getAuthor());
            postDto.setTitle(post.getTitle());
            postDto.setContent(post.getContent());
            postDto.setLikeCnt(likeCnt.get(i));
            postDto.setTransactionType(post.getTransactionType());
            postDto.setRoadAddr(post.getAddress().getRoadAddr());
            postDto.setSubAddress(post.getAddress().getSubAddress());
            postDto.setCategory(post.getCategory());
            postDtos.add(postDto);

            ++i;
        }

        model.addAttribute("posts", postDtos);

        return "/lists/listinfo";
    }

    @GetMapping("/lists/createPost")
    public String createPostForm(Model model) {
        model.addAttribute("form", new PostDto());
        return "lists/createPost";
    }

    @PostMapping(value = "/lists/createPost")
    public String createPost(Principal principal,
                             @ModelAttribute("form") @Valid PostDto form,
                             BindingResult result) {

        if (result.hasErrors()) {
            return "lists/createPost";
        }

        // 주소를 DB에 넣기 위해서 split 진행.
        String[] splitRoadAddr = form.getRoadAddr().split(" ");
        // 평점 값 정수로 변환
        int getGrade = Integer.parseInt(String.valueOf(form.getGrade()));
        //post 데이터 dto에서 받아온 값으로 엔티티 생성.
        Post post = Post.builder()
                .author(principal.getName())
                .title(form.getTitle())
                .content(form.getContent())
                .address(new Address(form.getRoadAddr(), splitRoadAddr[0],
                        splitRoadAddr[1], splitRoadAddr[2] + ' ' + splitRoadAddr[3],
                        form.getSubAddress()))
                .transactionType(form.getTransactionType())
                .category(form.getCategory())
                .grade(getGrade)
                .findCnt(0l)
                .build();
        //포스트 정보 생성
        postService.savePost(post);
        // 부동산 정보 생성
        postService.createPropertyInfo(post.getId(), form.getLatitude(), form.getLongitude());

        return "redirect:/lists/listinfo";
    }


    @GetMapping("/lists/postId&={postId}/details")
    public String postDetails(@PathVariable("postId") Long postId, Model model) {
        Post post = postService.findPost(postId);
        Long likeCnt = postService.LikeCount(postId);
        //조회수 갱신
        postService.setPostFindCnt(post.getId());
        // 조회수 갱신 후 자동으로 commit이 완료되므로, 새로운 객체를 생성해서 조회할 필요가 없다!!
        // 그냥 기존에 호출한 엔티티를 getter로 조회하면 된다.

        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setAuthor(post.getAuthor());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setFindCnt(post.getFindCnt());
        postDto.setLikeCnt(likeCnt);
        postDto.setTransactionType(post.getTransactionType());
        postDto.setRoadAddr(post.getAddress().getRoadAddr());
        postDto.setCategory(post.getCategory());

        model.addAttribute("post", postDto);
        return "/lists/postDetails";
    }

    @GetMapping(value = "/lists/{postId}/edit")
    public String updatePostForm(@PathVariable("postId") Long postId, Model model) {

        Post post = postService.findPost(postId);

        PostDto form = new PostDto();

        form.setId(post.getId());
        form.setAuthor(post.getAuthor());
        form.setTitle(post.getTitle());
        form.setContent(post.getContent());
        form.setRoadAddr(post.getAddress().getRoadAddr());
        form.setLatitude(post.getPropertyInfo().getLatitude());
        form.setLongitude(post.getPropertyInfo().getLongitude());
        form.setCategory(post.getCategory());
        form.setTransactionType(post.getTransactionType());

        model.addAttribute("form", form);
        return "lists/updatePostForm";
    }


    @PostMapping(value = "/lists/{postId}/edit")
    public String updatePost(@PathVariable Long postId,
                             @ModelAttribute("form") PostDto form) {

        System.out.println("postId = " + postId);
        String[] splitRoadAddr = form.getRoadAddr().split(" ");

        Post post = Post.builder()
                .id(postId)
                .author(form.getAuthor())
                .title(form.getTitle())
                .content(form.getContent())
                .address(new Address(form.getRoadAddr(), splitRoadAddr[0], splitRoadAddr[1],
                        splitRoadAddr[2], splitRoadAddr[3]))
                .transactionType(form.getTransactionType())
                .category(form.getCategory())
                .build();

        postService.savePost(post);

        PropertyInfo propertyInfo = propertyInfoService.findByPost(post.getId());
        propertyInfo.setId(propertyInfo.getId());
        propertyInfo.setPost(post);
        propertyInfo.setLatitude(form.getLatitude());
        propertyInfo.setLongitude(form.getLongitude());

        propertyInfoService.saveInfo(propertyInfo);

        return "redirect:/lists/myPosts";
    }

    @GetMapping("/lists/{postId}/delete")
    public String postDelete(@PathVariable Long postId, Model model) {
        postService.deletePost(postId);

        return "redirect:/lists/listinfo";
    }

    @GetMapping("/lists/myPosts")
    public String myPosts(Principal principal, Model model) {
        List<Post> posts = postService.findPostByEmail(principal.getName());

        model.addAttribute("myposts", posts);

        return "/lists/myPosts";
    }
}
