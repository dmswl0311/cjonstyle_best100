package cjonstyle.best100.controller;

import cjonstyle.best100.domain.dto.OpinionRes;
import cjonstyle.best100.service.ApiServiceImpl;
import cjonstyle.best100.service.OpinionServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ApiController {
    private final ApiServiceImpl service;

    //    오늘자 BEST 아이템을 DB에 저장
    @GetMapping("/best-item")
    public ResponseEntity<Boolean> saveAllBestItem() {
        boolean res = service.saveAllBestItem();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
