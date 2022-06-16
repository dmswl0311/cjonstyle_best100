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
public class BestCh {
    private Long id; // pk
    private String itemId; //상품 코드
    private LocalDate date; // 날짜
    private Long price; // 가격
    private int rank;  // 순위
    public static BestCh of(Best best) {
        return builder()
                .id(best.getId())
                .itemId(best.getItemId())
                .date(best.getDate())
                .price(best.getPrice())
                .rank(best.getRank())
                .build();
    }
}
