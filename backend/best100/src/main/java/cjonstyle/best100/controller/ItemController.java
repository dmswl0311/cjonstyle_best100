package cjonstyle.best100.controller;

import cjonstyle.best100.domain.dto.BestItem.BestCh;
import cjonstyle.best100.domain.dto.BestItem.BestRes;
import cjonstyle.best100.service.ItemServiceImpl;
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
public class ItemController {
    private final ItemServiceImpl service;

    //  오늘자 BEST 아이템을 DB에 저장
    //  하루에 한번만 실행
    @PostMapping("/best-item")
    public ResponseEntity<Boolean> saveAllBestItem() {
        boolean res = service.saveAllBestItem();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * state:
     * priceDesc 가격 내림차순 (높은가격순)
     * priceAsc 가격 오름차순 (낮은가격순)
     * rank 랭킹순
     */
    @GetMapping("/best-item")
    public ResponseEntity<List<BestRes>> getAllBestItem(@RequestParam("state") String state) {
        List<BestRes> res = service.getAllBestItem(state);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // BEST100 순위 및 가격 변동 추이 API
    @GetMapping("/best-item/{item_id}")
    public ResponseEntity<List<BestCh>> getChangeBestItem(@PathVariable("item_id") String itemId) {
        List<BestCh> res = service.getChangeBestItem(itemId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // 상품 정보 API
    @GetMapping("/item-info/{item_id}")
    public ResponseEntity<Object> getItemInfo(@PathVariable("item_id") String itemId) {
        Object res = service.getItemInfo(itemId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // 내일 배송 true
    @GetMapping("/best-item/tmarvlYn")
    public ResponseEntity<List<BestRes>> getAllBestItemTmarvlYn(@RequestParam("state") String state) {
        List<BestRes> res = service.getAllBestItemTmarvlYn(state);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
