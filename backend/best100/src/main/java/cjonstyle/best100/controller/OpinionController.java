package cjonstyle.best100.controller;

import cjonstyle.best100.domain.dto.OpinionReq;
import cjonstyle.best100.domain.dto.OpinionRes;
import cjonstyle.best100.service.OpinionServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/opinion")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class OpinionController {
    private final OpinionServiceImpl service;

    // 상품에 대한 모든 한줄 의견
    @GetMapping("/{item_id}")
    public ResponseEntity<List<OpinionRes>> getAllOpinion(@PathVariable("item_id") String itemId) {
        List<OpinionRes> res = service.getAllOpinion(itemId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // 상품에 대한 한줄 의견 등록
    @PostMapping("/{item_id}")
    public ResponseEntity<OpinionRes> saveOpinion(@PathVariable("item_id") String itemId, @RequestBody OpinionReq req) {
        OpinionRes res = service.saveOpinion(itemId,req);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // 상품에 대한 한줄 의견 수정
    @PatchMapping("/{opinion_id}")
    public ResponseEntity<OpinionRes> updateOpinion(@PathVariable("opinion_id") Long opinionId, @RequestBody OpinionReq req) {
        OpinionRes res = service.updateOpinion(opinionId, req);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // 상품에 대한 한줄 의견 삭제
    @DeleteMapping("/{opinion_id}")
    public ResponseEntity<Boolean> deleteOpinion(@PathVariable("opinion_id") Long opinionId, @RequestBody OpinionReq req) {
        boolean res = service.deleteOpinion(opinionId, req);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
