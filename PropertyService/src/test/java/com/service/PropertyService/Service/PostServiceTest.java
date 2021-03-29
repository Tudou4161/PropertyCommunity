package com.service.PropertyService.Service;

import com.service.PropertyService.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class PostServiceTest {

    @Autowired
    PostService postService;

    @Test
    public void 조회수생성로직처리() {
        Post post = postService.findPost(1l);
        postService.setPostFindCnt(post.getId());
        //em.flush() 완료 상태!

        Long findCnt = post.getFindCnt();

        Assertions.assertThat(findCnt).isEqualTo(1l);
    }

    @Test
    public void 좋아요생성로직처리() {
        postService.createLike(1l, 1l);
    }

    @Test
    public void 좋아요삭제로직처리() {
        postService.deleteLike(6l, 3l);
    }
}