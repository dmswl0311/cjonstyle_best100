package cjonstyle.best100.controller;

import cjonstyle.best100.domain.dto.BestRes;
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
@CrossOrigin("*")
@Slf4j
public class ApiController {
    private final ApiServiceImpl service;

    //  오늘자 BEST 아이템을 DB에 저장
    //  하루에 한번만 실행
    @PostMapping("/best-item")
    public ResponseEntity<Boolean> saveAllBestItem() {
        boolean res = service.saveAllBestItem();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * state:
     * priceDesc 높은 가격순
     * priceAsc 낮은 가격순
     * rank 랭킹순
     */
    @GetMapping("/best-item")
    public ResponseEntity<List<BestRes>> getAllBestItem(@RequestParam("state") String state) {
        List<BestRes> res = service.getAllBestItem(state);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
