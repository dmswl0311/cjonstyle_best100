package cjonstyle.best100.domain.dto.bestItem;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BestChRes{
    private List<BestCh> change;
    private boolean flag;
}
