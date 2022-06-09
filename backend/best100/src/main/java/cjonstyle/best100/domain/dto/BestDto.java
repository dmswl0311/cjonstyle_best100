package cjonstyle.best100.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BestDto {
    private Long id; // pk
    private String itemId; //상품 코드
    private LocalDate date; // 날짜
    private Long price; // 가격
    private int rank;  // 순위
}
