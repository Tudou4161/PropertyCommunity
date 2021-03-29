package com.service.PropertyService.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.service.PropertyService.domain.Like;
import com.service.PropertyService.domain.Post;
import org.springframework.stereotype.Repository;
import static com.service.PropertyService.domain.QLike.*;
import static com.service.PropertyService.domain.QPost.post;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class LikeRepository {

    @PersistenceContext
    private EntityManager em;
    private JPAQueryFactory queryFactory;

    public LikeRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void save(Like like) {
        em.persist(like);
    }

    public void delete(Like like) {
        em.remove(like);
    }


    public Like findById(Long id) {
        return em.find(Like.class, id);
    }


    public Like findByUserIdAndPostId(Long userId, Long postId) {
        return queryFactory
                .selectFrom(like)
                .where(like.user.id.eq(userId).and(
                        like.post.id.eq(postId)
                ))
                .fetchOne();
    }

    public List<Like> findAll() {
        return queryFactory
                .selectFrom(like)
                .fetch();
    }

    //좋아요 집계 기능
    public Long getCountLike (Post post_) {
        return queryFactory
                .select(like)
                .from(like)
                .where(like.post.id.eq(post_.getId()))
                .fetchCount();
    }
}
