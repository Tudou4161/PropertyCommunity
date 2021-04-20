package com.service.PropertyService.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.service.PropertyService.domain.Post;
import com.service.PropertyService.domain.User;

import static com.service.PropertyService.domain.QLike.*;
import static com.service.PropertyService.domain.QPost.*;
import org.springframework.stereotype.Repository;
import static com.service.PropertyService.domain.QUser.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    public UserRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void save(User user) {
        if (user.getId() != null) {
            em.merge(user);
        } else {
            em.persist(user);
        }
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return queryFactory
                .select(user)
                .from(user)
                .fetch();
    }

    public Optional<User> findByEmail(String email) {
        Optional<User> optionalUser = queryFactory
                .select(user)
                .from(user)
                .where(user.email.eq(email))
                .fetch()
                .stream().findFirst();

        return optionalUser;
    }

    public User findByUserEmail(String email) {
        return queryFactory
                .selectFrom(user)
                .where(user.email.eq(email))
                .fetchOne();
    }

    public User findByUsername(String name) {
        return queryFactory
                .select(user)
                .from(user)
                .where(user.name.eq(name))
                .fetchOne();
    }

    public User findByUsernameAndPhoneNumber(String username, String phoneNumber) {
        return queryFactory
                .selectFrom(user)
                .where(user.name.eq(username)
                        .and(user.phoneNumber.eq(phoneNumber)))
                .fetchOne();
    }

    public void delete(Long id) {
        User user = em.find(User.class, id);
        String username = user.getUsername();
        em.remove(user);

        List<Post> userPosts = queryFactory
                .select(post)
                .from(post)
                .where(post.author.eq(username))
                .fetch();


        for (Post userPost : userPosts) {
            if (userPost.getAuthor() != null) {
                em.remove(userPost);
                System.out.println(userPost.getAuthor() + "의 게시글이 정상적으로 삭제되었습니다.");
            }
        }
    }
}
