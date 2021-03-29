package com.service.PropertyService.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.service.PropertyService.Repository.PostRepository;
import com.service.PropertyService.Repository.PropertyInfoRepository;
import com.service.PropertyService.Repository.UserRepository;
import com.service.PropertyService.domain.PropertyInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import static com.service.PropertyService.domain.QPost.*;
import static com.service.PropertyService.domain.QPropertyInfo.*;
import static com.service.PropertyService.domain.QUser.*;
import static com.service.PropertyService.domain.QLike.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
@Commit
public class QueryTest {

//    @Autowired
//    private PropertyInfoRepository propertyInfoRepository;
//
//    @Autowired
//    private PostRepository postRepository;
//
//    @Autowired
//    private UserRepository userRepository;

    @PersistenceContext
    EntityManager em;

    JPAQueryFactory queryFactory;

    @Test
    public void 마커집계를위한쿼리구현() {
        List<PropertyInfo> result = queryFactory
                .select(propertyInfo)
                .from(propertyInfo)
                .fetch();
        }
    }
