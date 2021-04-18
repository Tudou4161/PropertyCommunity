package com.service.PropertyService.Repository;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.service.PropertyService.domain.PropertyInfo;
import static com.service.PropertyService.domain.QPropertyInfo.*;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import static com.service.PropertyService.domain.QPost.*;

@Repository
public class PropertyInfoRepository {

    @PersistenceContext
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    public PropertyInfoRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void save(PropertyInfo propertyInfo) {
        if (propertyInfo.getId() == null) {
            em.persist(propertyInfo);
        } else {
            em.merge(propertyInfo);
        }
    }

    public PropertyInfo findById(Long id) {

        return em.find(PropertyInfo.class, id);
    }


    public PropertyInfo findByPostId(Long postId) {
        return queryFactory
                .select(propertyInfo)
                .from(propertyInfo)
                .where(propertyInfo.post.id.eq(postId))
                .fetchOne();
    }

    public List<Tuple> findAll() {
        return queryFactory
                .select(
                        post.address.roadAddr,
                        post.address.subAddress,
                        post.id,
                        propertyInfo.latitude,
                        propertyInfo.longitude)
                .from(propertyInfo)
                .leftJoin(post).on(propertyInfo.post.id.eq(post.id))
                .groupBy(propertyInfo.latitude, propertyInfo.longitude)
                .fetch();
    }

    public List<Tuple> findByOneMarkerByAggrCategory(Long postId) {
        return queryFactory
                .select(post.category,
                        post.category.count())
                .from(propertyInfo)
                .leftJoin(post).on(propertyInfo.post.id.eq(post.id))
                .where(propertyInfo.latitude.in(
                        JPAExpressions
                        .select(propertyInfo.latitude)
                        .from(propertyInfo)
                        .where(propertyInfo.post.id.eq(postId))
                )
                .and(propertyInfo.longitude.in(
                        JPAExpressions
                        .select(propertyInfo.longitude)
                        .from(propertyInfo)
                        .where(propertyInfo.post.id.eq(postId))
                )))
                .groupBy(post.category)
                .fetch();
    }

    public List<Double> findByOneMarkerAggrGrade(Long postId) {
        return queryFactory
                .select(post.grade.avg())
                .from(propertyInfo)
                .leftJoin(post).on(propertyInfo.post.id.eq(post.id))
                .where(propertyInfo.latitude.in(
                        JPAExpressions
                                .select(propertyInfo.latitude)
                                .from(propertyInfo)
                                .where(propertyInfo.post.id.eq(postId))
                )
                        .and(propertyInfo.longitude.in(
                                JPAExpressions
                                        .select(propertyInfo.longitude)
                                        .from(propertyInfo)
                                        .where(propertyInfo.post.id.eq(postId))
                        )))
                .fetch();
    }

    public List<Tuple> findJoinTable() {
        List<Tuple> tuple = queryFactory
                .select(post.address,
                        propertyInfo.latitude,
                        propertyInfo.longitude)
                .from(propertyInfo)
                .leftJoin(post).on(propertyInfo.post.id.eq(post.id))
                .fetch();

        return tuple;
    }
}

