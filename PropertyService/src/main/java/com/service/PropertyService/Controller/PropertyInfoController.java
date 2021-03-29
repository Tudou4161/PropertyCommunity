package com.service.PropertyService.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.Tuple;
import com.service.PropertyService.Service.PropertyInfoService;
import com.service.PropertyService.domain.PropertyInfo;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.HasAnnotation;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*")
@Controller
@RequiredArgsConstructor
public class PropertyInfoController {

    private final PropertyInfoService propertyInfoService;

    @GetMapping("/maps/map")
    public String ViewGeoMap() {
        return "maps/map";
    }
}
