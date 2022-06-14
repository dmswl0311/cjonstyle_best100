package cjonstyle.best100.service.opinion;

import cjonstyle.best100.domain.dto.Opinion.OpinionReq;
import cjonstyle.best100.domain.dto.Opinion.OpinionRes;

import java.util.List;

public interface OpinionService {
    List<OpinionRes> getAllOpinion(String itemId, String state);
    OpinionRes saveOpinion(String itemId, OpinionReq req);
    OpinionRes updateOpinion(Long opinionId, OpinionReq req);
    boolean deleteOpinion(Long opinionId, OpinionReq req);
    OpinionRes updateExprOpinion(Long opinionId, String expr);

}
