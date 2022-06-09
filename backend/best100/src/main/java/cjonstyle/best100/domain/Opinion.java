package cjonstyle.best100.domain;

import cjonstyle.best100.domain.dto.OpinionReq;
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

    @Column(name="opinion_pwd")
    private String pwd; // 의견 비밀번호

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
                .pwd(res.getPwd())
                .contents(res.getContents())
                .date(res.getDate())
                .like(res.getLike())
                .hate(res.getHate())
                .build();
    }
    public static Opinion of(String itemId, OpinionReq req){
        return builder()
                .itemId(itemId)
                .pwd(req.getPwd())
                .contents(req.getContents())
                .date(LocalDate.now())
                .build();
    }

    public static Opinion updateOf(OpinionRes res, String contents){
        return builder()
                .id(res.getId())
                .itemId(res.getItemId())
                .pwd(res.getPwd())
                .contents(contents)
                .date(res.getDate())
                .like(res.getLike())
                .hate(res.getHate())
                .build();
    }

}
