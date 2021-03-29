package com.service.PropertyService.Repository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.service.PropertyService.Service.PostService;
import com.service.PropertyService.domain.Category;
import com.service.PropertyService.domain.Post;
import com.service.PropertyService.domain.QPost;
import com.service.PropertyService.domain.User;
import com.service.PropertyService.dto.PostDto;
import com.service.PropertyService.dto.PostSearchCondition;
import com.service.PropertyService.dto.QPostDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.util.StringUtils.*;

import static com.service.PropertyService.domain.QPost.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import static com.service.PropertyService.domain.QUser.user;

@Repository
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    //쿼리펙토리 주입
    public PostRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    // 저장 기능
    public void save(Post post) {
        if (post.getId() == null) {
            em.persist(post);
        } else {
            em.merge(post);
        }
    }

    //삭제 기능
    public void delete(Long id) {
        Post post = em.find(Post.class, id);
        em.remove(post);
    }

    //아이디 기반 엔티티조회
    public Post findById(Long id) {
        Post post = em.find(Post.class, id);
        return post;
    }

    //작성자 기반 엔티티 조회
    public List<Post> findByEmail(String email) {
        return queryFactory
                .selectFrom(post)
                .where(post.author.eq(email))
                .fetch();
    }

    //전체 데이터 조회기능 : findall v1
    public List<Post> findAll() {
        return queryFactory
                .select(post)
                .from(post)
                .fetch();
    }

    //post데이터 검색 필터 : findall v2
    public List<Post> findAllAndsearchByBuilder(PostSearchCondition condition) {

        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(condition.getFirstAddr())) {
            builder.and(post.address.firstAddr.eq(condition.getFirstAddr()));
        }
        if (hasText(condition.getSecondAddr())) {
            builder.and(post.address.secondAddr.contains(condition.getSecondAddr()));
        }
        if (hasText(condition.getSubAddress())) {
            builder.and(post.address.subAddress.contains(condition.getSubAddress()));
        }
        if (condition.getCategory() != null) {
            builder.and(post.category.eq(condition.getCategory()));
        }
        if (condition.getTransactionType() != null) {
            builder.and(post.transactionType.eq(condition.getTransactionType()));
        }

        //검색조건 where절에 넣고, 필터링된 데이터 가져오기!
        return queryFactory
                .select(post)
                .from(post)
                .where(builder)
                .orderBy(post.findCnt.desc())
                .fetch();
    }
}
