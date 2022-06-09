package cjonstyle.best100.domain;

import cjonstyle.best100.domain.dto.BestDto;
import cjonstyle.best100.domain.dto.OpinionRes;
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
    private LocalDate date; // 날짜

    @Column(name = "best_price")
    private Long price; // 가격

    @Column(name = "best_rank")
    private int rank;  // 순위

    //    dto->entity
    public static Best of(BestDto best) {
        return builder()
                .id(best.getId())
                .itemId(best.getItemId())
                .date(LocalDate.now())
                .price(best.getPrice())
                .rank(best.getRank())
                .build();
    }
}
