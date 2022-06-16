package cjonstyle.best100.domain.dto.bestItem;

import cjonstyle.best100.domain.entity.bestItem.Best;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BestReq {
    private Long id; // pk
    private String itemId; //상품 코드
    private LocalDate date; // 날짜
    private Long price; // 가격
    private int rank;  // 순위
    private String tmarvlYn; // 내일 배송 여부
    private String slCls; // 상품 상태 정보 (A : 정상, S : 매진, I : 판매중단, D : 영구중단)
    private String itemName;
    private String itemImage;
}
