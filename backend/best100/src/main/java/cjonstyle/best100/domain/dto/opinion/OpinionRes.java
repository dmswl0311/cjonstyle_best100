package cjonstyle.best100.domain.dto.opinion;

import cjonstyle.best100.domain.Opinion;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OpinionRes {
    private Long id;
    private String itemId; //상품 코드
    private String pwd; // 회원 아이디
    private String contents; // 한줄 의견 내용
    private LocalDate date; // 작성 날짜
    private int like; // 좋아요
    private int hate;  // 싫어요

    public static OpinionRes of(Opinion opinion) {
        return builder()
                .id(opinion.getId())
                .itemId(opinion.getItemId())
                .pwd(opinion.getPwd())
                .contents(opinion.getContents())
                .date(opinion.getDate())
                .like(opinion.getLike())
                .hate(opinion.getHate())
                .build();
    }
}
