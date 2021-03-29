package com.service.PropertyService.Controller;
import com.querydsl.core.Tuple;
import com.service.PropertyService.Service.PropertyInfoService;
import com.service.PropertyService.domain.PropertyInfo;
import com.service.PropertyService.domain.QPropertyInfo;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.service.PropertyService.domain.QPropertyInfo.*;
import static com.service.PropertyService.domain.QPost.*;


@Controller
@RequiredArgsConstructor
public class MapDataController {

    private final PropertyInfoService propertyInfoService;

    @GetMapping("/geoinfo")
    @ResponseBody
    public String GetGeoInfo() {
        List<Tuple> propertyInfoList = propertyInfoService.findInfos();
        String results = collectionToJsonConverter(propertyInfoList);
        return results;
    }

    //마커의 postId를 클릭하면 ajax로 받아온 id값을 활용해서, 집계함수를 호출한다.
    @GetMapping("/maps/map/detail/{postId}")
    @ResponseBody
    public JSONObject GetMarkerAggrCategoryOrGrade(@PathVariable Long postId) {
        List<Tuple> aggrCategory = propertyInfoService.findOneMarkerAggrCategory(postId);
        List<Double> aggrGrade = propertyInfoService.findOneMarkerAggrGrade(postId);
        JSONObject results = collectionToJsonConverter2(aggrCategory, aggrGrade);
        return results;
    }

    public JSONObject collectionToJsonConverter2(List<Tuple> aggrCategory, List<Double> aggrGrade) {
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < aggrCategory.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("category_type", aggrCategory.get(i).get(post.category));
            jsonObject.put("cnt", aggrCategory.get(i).get(post.category.count()));
            jsonArray.add(jsonObject);
        }

        JSONObject data = new JSONObject();
        try {
            data.put("aggr_category", jsonArray);
            data.put("aggr_grade", aggrGrade.get(0));
            data.put("message", "success!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    //컨트롤러 내부 로직함수.1 -> 부동산 정보 데이터를 json포맷으로 변환
    public String collectionToJsonConverter(List<Tuple> propertyInfoList) {
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < propertyInfoList.size(); i++) {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("lat", propertyInfoList.get(i).get(propertyInfo.latitude));
            jsonObject.put("lng", propertyInfoList.get(i).get(propertyInfo.longitude));
            jsonObject.put("postId", propertyInfoList.get(i).get(post.id));
            jsonObject.put("avg_grade", propertyInfoService.findOneMarkerAggrGrade(
                    propertyInfoList.get(i).get(post.id)
            ).get(0));
            jsonObject.put("roadAddr", propertyInfoList.get(i).get(post.address.roadAddr));
            jsonObject.put("subAddr", propertyInfoList.get(i).get(post.address.subAddress));
            jsonArray.add(jsonObject);
        }

        JSONObject data = new JSONObject();
        try {
            data.put("data", jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data.toJSONString();
    }
}
