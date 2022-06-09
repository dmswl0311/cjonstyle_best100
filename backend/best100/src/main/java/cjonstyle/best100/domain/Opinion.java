package cjonstyle.best100.domain;

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
public class Opinion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="opinion_id")
    private Long id; // pk

    @Column(name="opinion_item_id")
    private String itemId; //상품 코드

    @Column(name="opinion_user_id")
    private Long userId; // 회원 아이디

    @Column(name="opinion_contents")
    private String contents; // 한줄 의견 내용

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date; // 작성 날짜

    @Column(name="opinion_like")
    private int like; // 좋아요

    @Column(name="opinion_hate")
    private int hate;  // 싫어요

//    dto->entity
    public static Opinion of(OpinionRes res){
        return builder()
                .id(res.getId())
                .itemId(res.getItemId())
                .userId(res.getUserId())
                .contents(res.getContents())
                .date(res.getDate())
                .like(res.getLike())
                .hate(res.getHate())
                .build();
    }
}
