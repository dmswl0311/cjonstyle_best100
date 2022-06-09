package cjonstyle.best100.service;

import cjonstyle.best100.domain.dto.OpinionRes;

import java.util.List;

public interface OpinionService {
    List<OpinionRes> getAllOpinion(String itemId);
}
