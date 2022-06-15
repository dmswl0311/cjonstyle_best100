package cjonstyle.best100.controller.opinion;

import cjonstyle.best100.domain.dto.opinion.OpinionReq;
import cjonstyle.best100.domain.dto.opinion.OpinionRes;
import cjonstyle.best100.service.opinion.OpinionServiceImpl;
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

    /**
     * 상품에 대한 모든 한줄 평 조회
     *
     * @param itemId 상품 아이디
     * @param state  정렬을 위한 state / like(좋아요가 높은 순), date(최근 날짜순) / 기본값은 date
     * @return
     */
    @GetMapping("/{item_id}")
    public ResponseEntity<List<OpinionRes>> getAllOpinion(@PathVariable("item_id") String itemId, @RequestParam("state") String state) {
        return new ResponseEntity<>(service.getAllOpinion(itemId, state), HttpStatus.OK);
    }

    /**
     * 한줄 평 등록
     *
     * @param itemId 상품 아이디
     * @param req    {
     *               "pwd":"1234",
     *               "contents":"작성할 내용"
     *               }
     * @return
     */
    @PostMapping("/{item_id}")
    public ResponseEntity<OpinionRes> saveOpinion(@PathVariable("item_id") String itemId, @RequestBody OpinionReq req) {
        return new ResponseEntity<>(service.saveOpinion(itemId, req), HttpStatus.OK);
    }

    /**
     * 한줄 평 수정
     *
     * @param opinionId 한줄 평 아이디
     * @param req       {
     *                  "pwd":"1234",
     *                  "contents":"수정할 내용"
     *                  }
     * @return
     */
    @PatchMapping("/{opinion_id}")
    public ResponseEntity<OpinionRes> updateOpinion(@PathVariable("opinion_id") Long opinionId, @RequestBody OpinionReq req) {
        return new ResponseEntity<>(service.updateOpinion(opinionId, req), HttpStatus.OK);
    }

    /**
     * 한줄 평 삭제
     *
     * @param opinionId 한줄 평 아이디
     * @param req       {
     *                  "pwd":"1234",
     *                  "contents":null
     *                  }
     * @return
     */
    @DeleteMapping("/{opinion_id}")
    public ResponseEntity<Boolean> deleteOpinion(@PathVariable("opinion_id") Long opinionId, @RequestBody OpinionReq req) {
        return new ResponseEntity<>(service.deleteOpinion(opinionId, req), HttpStatus.OK);
    }

    /**
     * 한줄 평 좋아요 or 싫어요
     *
     * @param opinionId 한줄 평 아이디
     * @param expr      좋아요 like, 싫어요 hate
     * @return
     */
    @PatchMapping("/expr/{opinion_id}")
    public ResponseEntity<OpinionRes> updateExprOpinion(@PathVariable("opinion_id") Long opinionId, @RequestParam("expr") String expr) {
        return new ResponseEntity<>(service.updateExprOpinion(opinionId, expr), HttpStatus.OK);
    }
}
