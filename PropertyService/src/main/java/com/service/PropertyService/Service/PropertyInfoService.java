package com.service.PropertyService.Service;
import com.querydsl.core.Tuple;
import com.service.PropertyService.Repository.PropertyInfoRepository;
import com.service.PropertyService.domain.Post;
import com.service.PropertyService.domain.PropertyInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PropertyInfoService {

    private final PropertyInfoRepository propertyInfoRepository;

    @Transactional
    public void saveInfo(PropertyInfo propertyInfo) {
        propertyInfoRepository.save(propertyInfo);
    }

    public PropertyInfo findInfoOne(Long id) {
        return propertyInfoRepository.findById(id);
    }

    public List<Tuple> findInfos() {
        return propertyInfoRepository.findAll();
    }

    public PropertyInfo findByPost(Long postId) {
        return propertyInfoRepository.findByPostId(postId);
    }

    public List<Tuple> findOneMarkerAggrCategory(Long postId) {
        return propertyInfoRepository.findByOneMarkerByAggrCategory(postId);
    }

    public List<Double> findOneMarkerAggrGrade(Long postId) {
        return propertyInfoRepository.findByOneMarkerAggrGrade(postId);
    }
}
