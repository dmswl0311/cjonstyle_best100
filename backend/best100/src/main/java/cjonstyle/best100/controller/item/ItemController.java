package cjonstyle.best100.controller.item;

import cjonstyle.best100.domain.dto.bestItem.BestChRes;
import cjonstyle.best100.domain.dto.bestItem.BestRes;
import cjonstyle.best100.domain.dto.item.ItemInfo;
import cjonstyle.best100.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ItemController {
    private final ItemService service;

    /**
     * BEST 100 상품을 DB에 저장 (하루에 한번)
     *
     * @return
     */
    @PostMapping("/best-item")
    public ResponseEntity<Boolean> saveAllBestItem() {
        return new ResponseEntity<>(service.saveAllBestItem(), HttpStatus.OK);
    }

    /**
     * BEST 100 상품 전체 조회
     *
     * @param date  "yyyy-MM-DD" 형식으로 조회하고싶은 날짜 입력
     * @param state 정렬을 위한 state / rank(랭킹순), priceDesc(높은가격순), priceAsc(낮은가격순) / 기본값은 rank
     * @return
     */
    @GetMapping("/best-item")
    public ResponseEntity<List<BestRes>> getAllBestItem(@RequestParam("date") String date, @RequestParam("state") String state) {
        return new ResponseEntity<>(service.getAllBestItem(date, state), HttpStatus.OK);
    }

    /**
     * 상품 BEST 100 순위 및 가격 변동 추이 (3일간)와 현재 가격 최저가 여부
     *
     * @param itemId 상품 아이디
     * @return
     */
    @GetMapping("/best-item/{item_id}")
    public ResponseEntity<BestChRes> getChangeBestItem(@PathVariable("item_id") String itemId) {
        return new ResponseEntity<>(service.getChangeBestItem(itemId), HttpStatus.OK);
    }

    /**
     * 상품 상세 정보
     *
     * @param itemId 상품 아이디
     * @return
     */
    @GetMapping("/item-info/{item_id}")
    public ResponseEntity<ItemInfo> getItemInfo(@PathVariable("item_id") String itemId) {
        return new ResponseEntity<>(service.getItemInfo(itemId), HttpStatus.OK);
    }

    /**
     * 내일 배송상품 조회
     *
     * @param date  "yyyy-MM-DD" 형식으로 조회하고싶은 날짜 입력
     * @param state 정렬을 위한 state / rank(랭킹순), priceDesc(높은가격순), priceAsc(낮은가격순) / 기본값은 rank
     * @return
     */
    @GetMapping("/best-item/tmarvlYn")
    public ResponseEntity<List<BestRes>> getAllBestItemTmarvlYn(@RequestParam("date") String date, @RequestParam("state") String state) {
        return new ResponseEntity<>(service.getAllBestItemTmarvlYn(date, state), HttpStatus.OK);
    }


}
