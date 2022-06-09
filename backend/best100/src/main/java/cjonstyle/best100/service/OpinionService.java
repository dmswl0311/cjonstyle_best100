package cjonstyle.best100.service;

import cjonstyle.best100.domain.dto.OpinionReq;
import cjonstyle.best100.domain.dto.OpinionRes;

import java.util.List;

public interface OpinionService {
    List<OpinionRes> getAllOpinion(String itemId);
    OpinionRes saveOpinion(String itemId, OpinionReq req);
    OpinionRes updateOpinion(Long opinionId, OpinionReq req);
    boolean deleteOpinion(Long opinionId, OpinionReq req);
}
