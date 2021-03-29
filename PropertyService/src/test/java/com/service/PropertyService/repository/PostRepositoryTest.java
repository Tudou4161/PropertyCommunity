package com.service.PropertyService.repository;

import com.service.PropertyService.Repository.PostRepository;
import com.service.PropertyService.dto.PostSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    public void 검색조건필터링테스트() {
        PostSearchCondition condition = new PostSearchCondition();
        condition.setFirstAddr("서울");
    }
}