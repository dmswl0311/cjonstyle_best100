package cjonstyle.best100.service.item;

import cjonstyle.best100.domain.dto.bestItem.BestCh;
import cjonstyle.best100.domain.dto.bestItem.BestRes;

import java.util.List;

public interface ItemService {
    boolean saveAllBestItem();
    List<BestRes> getAllBestItem(String date, String state);
    List<BestCh> getChangeBestItem(String itemId);
    Object getItemInfo(String itemId);
    List<BestRes> getAllBestItemTmarvlYn(String date,String state);
}