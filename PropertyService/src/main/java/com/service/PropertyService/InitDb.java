package com.service.PropertyService;

import com.service.PropertyService.Repository.PropertyInfoRepository;
import com.service.PropertyService.Service.PostService;
import com.service.PropertyService.Service.UserService;
import com.service.PropertyService.domain.*;
import com.service.PropertyService.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.initUserPostLikeInfo();
    }


    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService {

        private final UserService userService;
        private final PostService postService;
        private final PropertyInfoRepository propertyInfoRepository;

        public void initUserPostLikeInfo() {
            //기본 유저 데이터 4개 저장
            UserDto user = UserDto.builder()
                    .email("cef5787@naver.com")
                    .password("asd123456")
                    .auth("ROLE_USER")
                    .name("www")
                    .phoneNumber("01041614096")
                    .build();

            UserDto user2 = UserDto.builder()
                    .email("xcvef5787@naver.com")
                    .password("asd123456")
                    .auth("ROLE_USER")
                    .phoneNumber("01041614096")
                    .build();

            UserDto user3 = UserDto.builder()
                    .email("xcvef6687@naver.com")
                    .password("asd123456")
                    .auth("ROLE_USER")
                    .phoneNumber("0101221122")
                    .build();

            UserDto user4 = UserDto.builder()
                    .email("xxcvjxo87@naver.com")
                    .password("asd123456")
                    .auth("ROLE_USER")
                    .phoneNumber("11111111111")
                    .build();

            userService.join(user);
            userService.join(user2);
            userService.join(user3);
            userService.join(user4);

            //게시글 데이터 3개 저장
            Post post = Post.builder()
                    .title("개같네요")
                    .author(user.getEmail())
                    .content("진짜 여기서 거래하지 마세요")
                    .grade(1)
                    .address(new Address("서울특별시", "서울", "신림동",
                            "11-7", "신림뉴씨엘"))
                    .transactionType(TransactionType.공인중개사)
                    .category(Category.계약조항위반)
                    .findCnt(0l)
                    .build();

            Post post2 = Post.builder()
                    .title("개같네요2")
                    .author(user2.getEmail())
                    .content("진짜 여기서 거래하지 마세요2")
                    .grade(3)
                    .address(new Address("서울특별시", "서울", "도봉구",
                            "11-7", "도봉해성부동산"))
                    .transactionType(TransactionType.공인중개사)
                    .category(Category.전세사기)
                    .findCnt(0l)
                    .build();

            Post post3 = Post.builder()
                    .title("개같네요3")
                    .author("asdasdsad")
                    .content("진짜 여기서 거래하지 마세요3")
                    .grade(2)
                    .address(new Address("원주시", "강원", "원주시",
                            "11-7", "원주영석부동산"))
                    .transactionType(TransactionType.공인중개사)
                    .category(Category.전세사기)
                    .findCnt(0l)
                    .build();

            Post post4 = Post.builder()
                    .title("개같네요3")
                    .author("asd")
                    .content("진짜 여기서 거래하지 마세요3")
                    .grade(4)
                    .address(new Address("원주시", "강원", "원주시",
                            "11-7", "원주영석부동산"))
                    .transactionType(TransactionType.공인중개사)
                    .category(Category.전세사기)
                    .findCnt(0l)
                    .build();

            Post post5 = Post.builder()
                    .title("개같네요3")
                    .author("asd")
                    .content("진짜 여기서 거래하지 마세요3")
                    .grade(4)
                    .address(new Address("원주시", "강원", "원주시",
                            "11-7", "원주영석부동산"))
                    .transactionType(TransactionType.공인중개사)
                    .category(Category.계약조항위반)
                    .findCnt(0l)
                    .build();


            postService.savePost(post);
            postService.savePost(post2);
            postService.savePost(post3);
            postService.savePost(post4);
            postService.savePost(post5);

            postService.createPropertyInfo(post.getId(), 37.3980854357918d, 127.027871939898d);
            postService.createPropertyInfo(post2.getId(), 37.4980854357918d, 127.127871939898d);
            postService.createPropertyInfo(post3.getId(), 37.4980854357918d, 127.227871939898d);
            postService.createPropertyInfo(post4.getId(), 37.4980854357918d, 127.227871939898d);
            postService.createPropertyInfo(post5.getId(), 37.4980854357918d, 127.227871939898d);

//            //한 게시글에 여러 명이 좋아요를 누를 수 있다.
//            postService.createLike(userService.findOne(1l).getId(), post.getId());
//            postService.createLike(userService.findOne(2l).getId(), post.getId());
//            postService.createLike(userService.findOne(3l).getId(), post.getId());
//
//            //한 유저가 여러 게시글에 좋아요를 누를 수 있다.
//            postService.createLike(userService.findOne(4l).getId(), post.getId());
//            postService.createLike(userService.findOne(4l).getId(), post2.getId());
//            postService.createLike(userService.findOne(4l).getId(), post3.getId());

        }
   }
}
