package cjonstyle.best100.domain.entity.bestItem;

import cjonstyle.best100.domain.dto.bestItem.BestReq;
import cjonstyle.best100.domain.dto.bestItem.BestRes;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Best {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "best_id")
    private Long id; // pk

    @Column(name = "best_item_id")
    private String itemId; //상품 코드

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Column(name = "best_date")
    private LocalDate date; // 날짜

    @Column(name = "best_price")
    private Long price; // 가격

    @Column(name = "best_rank")
    private int rank;  // 순위

    @Column(name = "best_tmarvlYn")
    private String tmarvlYn; // 내일 배송 여부

    @Column(name="best_slCls")
    private String slCls; // 상품 상태 정보 (A : 정상, S : 매진, I : 판매중단, D : 영구중단)

    @Column(name="best_itemName")
    private String itemName;

    @Column(name="best_itemImage")
    private String itemImage;

    public static Best of(BestRes best) {
        return builder()
                .id(best.getId())
                .itemId(best.getItemId())
                .date(LocalDate.now())
                .price(best.getPrice())
                .rank(best.getRank())
                .tmarvlYn(best.getTmarvlYn())
                .slCls(best.getSlCls())
                .itemImage(best.getItemImage())
                .itemName(best.getItemName())
                .build();
    }
    public static Best of(BestReq best) {
        return builder()
                .id(best.getId())
                .itemId(best.getItemId())
                .date(LocalDate.now())
                .price(best.getPrice())
                .rank(best.getRank())
                .tmarvlYn(best.getTmarvlYn())
                .slCls(best.getSlCls())
                .itemImage(best.getItemImage())
                .itemName(best.getItemName())
                .build();
    }
}
