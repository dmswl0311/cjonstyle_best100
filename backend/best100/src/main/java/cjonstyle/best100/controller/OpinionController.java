package cjonstyle.best100.controller;

import cjonstyle.best100.domain.dto.OpinionRes;
import cjonstyle.best100.service.OpinionServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/opinion")
@RequiredArgsConstructor
@Slf4j
public class OpinionController {
    private final OpinionServiceImpl service;

    // 상품에 대한 모든 한줄 의견
    @GetMapping("/{item_id}")
    public ResponseEntity<List<OpinionRes>> getAllOpinion(@PathVariable("item_id") String itemId){
        List<OpinionRes> res=service.getAllOpinion(itemId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
