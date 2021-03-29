package com.service.PropertyService.Service;
import com.service.PropertyService.Repository.LikeRepository;
import com.service.PropertyService.Repository.PostRepository;
import com.service.PropertyService.Repository.PropertyInfoRepository;
import com.service.PropertyService.Repository.UserRepository;
import com.service.PropertyService.domain.Like;
import com.service.PropertyService.domain.Post;
import com.service.PropertyService.domain.PropertyInfo;
import com.service.PropertyService.domain.User;
import com.service.PropertyService.dto.PostDto;
import com.service.PropertyService.dto.PostSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PropertyInfoRepository propertyInfoRepository;

    @Transactional
    public void savePost(Post post) {
        postRepository.save(post);
    }

    public List<Post> findPosts() {
        //return postRepository.searchByBuilder(condition);
        return postRepository.findAll();
    }

    public List<Post> findPostsBySearchCondition(PostSearchCondition condition) {
        return postRepository.findAllAndsearchByBuilder(condition);
    }

    public Post findPost(Long postId) {
        return postRepository.findById(postId);
    }

    public List<Post> findPostByEmail(String email) {
        return postRepository.findByEmail(email);
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.delete(postId);
    }


    @Transactional
    public void setPostFindCnt(Long postId) {
        Post findPost = postRepository.findById(postId);
        Long findFindCnt = findPost.getFindCnt();

        findPost.setFindCnt(++findFindCnt);

        postRepository.save(findPost);
    }

    @Transactional
    public void createLike(Long userId, Long postId) {

        User findUser = userRepository.findById(userId);
        Post findPost = postRepository.findById(postId);

        validateDuplicateLike(findUser.getId(), findPost.getId());

        Like createLike = Like.builder()
                .user(findUser)
                .post(findPost)
                .build();

        likeRepository.save(createLike);

    }

    //생성할 좋아요가 이미 있는지 없는지 검증.
    public void validateDuplicateLike(Long userId, Long postId) {
        if (likeRepository.findByUserIdAndPostId(userId, postId) != null) {
            throw new IllegalStateException("이미 좋아요를 누르셨습니다!");
        }
    }

    //삭제할 좋아요가 있는지 없는지 먼저 검증.
    public void validateCheckLikeIsNull(Long userId, Long postId) {
        if (likeRepository.findByUserIdAndPostId(userId, postId) == null) {
            throw new IllegalStateException("삭제할 좋아요가 없습니다.");
        }
    }

    @Transactional
    public void deleteLike(Long userId, Long postId) {
        validateCheckLikeIsNull(userId, postId);
        Like like = likeRepository.findByUserIdAndPostId(userId, postId);
        likeRepository.delete(like);
    }

    @Transactional
    public void createPropertyInfo(Long postId, double lat, double lon) {
        Post post = postRepository.findById(postId);

        PropertyInfo propertyInfo = PropertyInfo.builder()
                .post(post)
                .latitude(lat)
                .longitude(lon)
                .build();

        propertyInfoRepository.save(propertyInfo);
    }

    public Long LikeCount(Long postId) {
        Post post = postRepository.findById(postId);

        Long likeCnt = likeRepository.getCountLike(post);

        return likeCnt;
    }

}
