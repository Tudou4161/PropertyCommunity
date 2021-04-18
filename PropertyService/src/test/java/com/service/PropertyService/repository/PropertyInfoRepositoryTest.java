package com.service.PropertyService.repository;

import com.querydsl.core.Tuple;
import com.service.PropertyService.Controller.Map.MapDataController;
import com.service.PropertyService.Repository.PropertyInfoRepository;
import com.service.PropertyService.Service.PostService;
import com.service.PropertyService.Service.PropertyInfoService;
import com.service.PropertyService.Service.UserService;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.service.PropertyService.domain.QPost.*;
import static com.service.PropertyService.domain.QPropertyInfo.*;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
class PropertyInfoRepositoryTest {

    @Autowired
    PropertyInfoRepository propertyInfoRepository;
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;
    @Autowired
    MapDataController mapDataController;
    @Autowired
    PropertyInfoService propertyInfoService;

    @Test
    public void 마커별행출력로직테스트() throws NoSuchFieldException {
        List<Tuple> tuple = propertyInfoRepository.findAll();

        for (int i = 0; i < tuple.size(); i++ ) {
            System.out.println("tuple.get(i) = " + tuple.get(i).get(propertyInfo.latitude));
            System.out.println("tuple.get(i) = " + tuple.get(i).get(propertyInfo.longitude));
            System.out.println("tuple = " + tuple.get(i).get(post.id));
            System.out.println("tuple = " + tuple.get(i).get(post.address.roadAddr));
            System.out.println("tuple = " + tuple.get(i).get(post.address.subAddress));
        }
    }

    @Test
    public void 마커별카테고리집계함수테스트() {
        List<Tuple> tuple = propertyInfoRepository.findByOneMarkerByAggrCategory(3l);
        //List<Tuple> tuple = pr+opertyInfoRepository.findJoinTable();

        for (int i = 0; i < tuple.size(); i++ ) {
            System.out.println("tuple.get(i) = " + tuple.get(i).get(post.category));
            System.out.println("tuple = " + tuple.get(i).get(post.category.count()));
        }
    }

    @Test
    public void 마커별평점집계함수테스트() {
        List<Double> tuple = propertyInfoRepository.findByOneMarkerAggrGrade(3l);

        for (Double aDouble : tuple) {
            System.out.println("aDouble = " + aDouble);
        }
    }
    
    @Test
    public void JSON포맷일치여부확인테스트() {
        List<Tuple> aggrCategory = propertyInfoService.findOneMarkerAggrCategory(3l);
        List<Double> aggrGrade = propertyInfoService.findOneMarkerAggrGrade(3l);
        JSONObject results = mapDataController.collectionToJsonConverter2(aggrCategory, aggrGrade);

        System.out.println("results = " + results);
    }

}