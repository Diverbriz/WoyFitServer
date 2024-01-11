package com.example.woyfitserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping("")
    public ResponseEntity<?> base(){
        Map<String, String> map = new HashMap<>();
        map.put("drugs","{\"id\":44346,\"name\":\"Виши Нормадерм [BHA] корректирующий уход двойного действия несовершенства +постакне 50мл\",\"receipt\":0,\"image\":\"https://f.neopharm.ru/n/drugs/small/44/44346.jpg\",\"price\":{\"min_price\":1785,\"with_discount\":1517.25,\"bad_price\":1684}");
        map.put("filter", "apply");
        return ResponseEntity.ok(map);
    }
}
