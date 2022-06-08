package cjonstyle.best100.controller;

import cjonstyle.best100.service.OpinionServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class OpinionController {
    private final OpinionServiceImpl service;

    @GetMapping("/")
    public ResponseEntity<String> test(){
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

}
