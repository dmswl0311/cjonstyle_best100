package cjonstyle.best100.domain.dto.opinion;
import lombok.*;
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
