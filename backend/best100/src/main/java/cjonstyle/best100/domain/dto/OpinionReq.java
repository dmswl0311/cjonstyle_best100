package cjonstyle.best100.domain.dto;

import cjonstyle.best100.domain.Opinion;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OpinionReq {
    private String pwd; // 회원 비밀번호
    private String contents; // 한줄 의견 내용
}
