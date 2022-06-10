package cjonstyle.best100.service;

import cjonstyle.best100.domain.dto.BestCh;
import cjonstyle.best100.domain.dto.BestRes;

import java.util.List;

public interface ApiService {
    boolean saveAllBestItem();
    List<BestRes> getAllBestItem(String state);
    List<BestCh> getChangeBestItem(String itemId);
}
