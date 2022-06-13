package cjonstyle.best100.domain.dto.Item;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ItemInfo {
    private String itemId; //상품 코드
    private String itemName; // 상품명
    private Long price; // 가격
    private int order; // 현재까지 구매 갯수
    private String orderIsShow; // 구매 갯수 공개여부
    private Long cardPrice; // 최대 카드 혜택가
    private List<String> images; // 이미지배열
    private String itemReviewAvgScore; // 리뷰점수
    private int[] grade; // 품질, 디자인, 한달 사용 점수
    private String tmarvlYn; // 내일 배송 여부
    private String slCls; // 상품 상태 정보 (A : 정상, S : 매진, I : 판매중단, D : 영구중단)
    private List<String> cards; // 카드 혜택 정보
}
