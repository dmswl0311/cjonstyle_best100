package cjonstyle.best100.service;


import cjonstyle.best100.domain.Best;
import cjonstyle.best100.domain.Opinion;
import cjonstyle.best100.domain.dto.BestItem.BestCh;
import cjonstyle.best100.domain.dto.BestItem.BestRes;
import cjonstyle.best100.domain.dto.Item.ItemInfo;
import cjonstyle.best100.repository.ItemRepo;
import cjonstyle.best100.repository.OpinionRepo;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@CrossOrigin("*")
public class ItemServiceImpl implements ItemService {
    private final ItemRepo repo;
    private final String uri1 = "https://display.cjonstyle.com/c/rest/category/getTop100ItemList?type=P"; // BEST 아이템 OPEN API

    @Override
    public boolean saveAllBestItem() {
        /**
         * 하루에 한번만 실행되어야 하므로 한번만 실행되었는지 확인
         * 오늘 날짜로 검색, 하나 이상이 있다면 DB에 추가하지 않고 return
         */
        List<Best> flag = repo.findTopByDate(LocalDate.now());
        if (flag.size() >= 1) return false;

        try {
            String result = "";
            URL url = new URL(uri1);
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            result = bf.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);

            JSONObject ItemInfoResult = (JSONObject) jsonObject.get("result");
            JSONArray ItemInfo = (JSONArray) ItemInfoResult.get("cateTop100ItemTupleList");

            BestRes best;
            for (int i = 0; i < ItemInfo.size(); i++) {
                best = new BestRes();
                JSONObject item = (JSONObject) ItemInfo.get(i);
                String itemImage = "https:" + item.get("itemImgUrl").toString();
                // 상품코드
                String itemId = (String) item.get("itemCd");
                // 가격
                JSONObject info = (JSONObject) item.get("rmItempriceInfo");
                Long price = (Long) info.get("customerPrice");
                // 내일 배송 여부
                boolean tmarvlYn = (boolean) info.get("tmarvlYn");
                if (tmarvlYn) best.setTmarvlYn("T");
                else best.setTmarvlYn("F");
                // 상품명
                String itemName = info.get("displayItemName").toString();

                // 상품 상태 불러오기
                String uri2 = "https://display.cjonstyle.com/c/rest/item/" + itemId + "/itemInfo.json?channelCode=50001002&isEmployee=false";
                URL url2 = new URL(uri2);
                bf = new BufferedReader(new InputStreamReader(url2.openStream(), "UTF-8"));
                result = bf.readLine();
                jsonObject = (JSONObject) jsonParser.parse(result);
                ItemInfoResult = (JSONObject) jsonObject.get("result");
                info = (JSONObject) ItemInfoResult.get("detailInfo");
                String slCls = (String) info.get("slCls");

                // 아이템 저장
                best.setItemId(itemId);
                best.setPrice(price);
                best.setRank(i + 1);
                best.setSlCls(slCls);
                best.setItemName(itemName);
                best.setItemImage(itemImage);
                repo.save(Best.of(best));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<BestRes> getAllBestItem(String date, String state) {
//        LocalDate today = LocalDate.now();
        LocalDate day=LocalDate.parse(date);
        List<Best> flag = repo.findTopByDate(day);
        if (flag.size() >= 1) saveAllBestItem(); // 오늘 날짜에 저장된 DB가 없다면 api 불러와서 저장
        List<Best> bestList;
        if ("priceAsc".equals(state)) {
            // 낮은 가격순
            bestList = repo.findAllByDateOrderByPriceAsc(day);
        } else if ("priceDesc".equals(state)) {
            // 높은 가격순
            bestList = repo.findAllByDateOrderByPriceDesc(day);
        } else {
            // 없으면 랭킹순
            bestList = repo.findAllByDateOrderByRank(day);
        }

        return bestList.stream().map(BestRes::of).collect(Collectors.toList());
    }

    @Override
    public List<BestCh> getChangeBestItem(String itemId) {
        LocalDate today = LocalDate.now();
        LocalDate preview = LocalDate.now().minusDays(2);
        List<Best> best = repo.findAllByItemIdAndDateBetween(itemId, preview, today);
        return best.stream().map(BestCh::of).collect(Collectors.toList());
    }

    @Override
    public ItemInfo getItemInfo(String itemId) {
        String result = "";
        String uri = "https://display.cjonstyle.com/c/rest/item/" + itemId + "/itemInfo.json?channelCode=50001002&isEmployee=false";
        JSONObject jsonObject = null;
        ItemInfo item = null;
        try {
            URL url = new URL(uri);
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            result = bf.readLine();

            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(result);
            JSONObject ItemInfoResult = (JSONObject) jsonObject.get("result");

            // 이미지
            JSONObject ImagesInfo = (JSONObject) ItemInfoResult.get("imagesInfo");
            JSONArray imagesArray = (JSONArray) ImagesInfo.get("itemImages");
            List<String> images = new ArrayList<>();
            for (Object image : imagesArray) {
                images.add("https:" + (String) image);
            }

            JSONObject detailInfo = (JSONObject) ItemInfoResult.get("detailInfo");
            Long oriPrice = (Long) detailInfo.get("slPrc"); // 가격
            Long price = (Long) detailInfo.get("clpSlPrc");
            String slCls = detailInfo.get("slCls").toString(); // 상품 상태 정보
            String itemName = detailInfo.get("dispItemName").toString(); // 상품명
            String vendCode = detailInfo.get("mainVenCd").toString();
            String mdCode = detailInfo.get("mdCd").toString();
            String brandCode = detailInfo.get("brandCd").toString();
            String typeCode = detailInfo.get("itemTypeCd").toString();

            JSONObject orderInfo = (JSONObject) detailInfo.get("orderCntInfo");
            int order = Integer.valueOf(orderInfo.get("orderCnt").toString()); // 구매 갯수
            String orderIsShow = orderInfo.get("isShowOrderCnt").toString(); // 구매 갯수 공개 여부
            String ReviewScore = ItemInfoResult.get("itemReviewAvgScore").toString(); // 리뷰 점수

            boolean flag = getItemTmarvlYn(itemId); // 내일 배송 여부
            String tmarvlYn;
            if (flag) tmarvlYn = "T";
            else tmarvlYn = "F";

            int[] grade = getItemGrade(itemId); // 품질, 디자인, 한달 사용 점수

            List<String> cards = getItemCardInfo(itemId, price, vendCode, mdCode, brandCode, typeCode);
            Long cardPrice = Long.valueOf(cards.get(cards.size() - 1));
            cards = cards.subList(0, cards.size() - 1);

            item = new ItemInfo(itemId, itemName, oriPrice, price, order, orderIsShow, cardPrice, images, ReviewScore, grade, tmarvlYn, slCls, cards);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<BestRes> getAllBestItemTmarvlYn(String date, String state) {
        List<BestRes> res = getAllBestItem(date,state);
        List<BestRes> result = new ArrayList<>();
        for (BestRes best : res) {
            if ("T".equals(best.getTmarvlYn())) {
                result.add(best);
            }
        }
        return result;
    }

    //    상품의 품질 점수, 디자인 점수, 한달 사용 점수 -> 기존 리뷰 정보를 가져옴
    public int[] getItemGrade(String itemId) {
        String result = "";
        int[] res = new int[3];
        String uri = "https://item.cjonstyle.com/celebshop/review/rest/itemReviewInfo?itemCode=" + itemId + "&channelCode=50001002";
        try {
            URL url = new URL(uri);
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            result = bf.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);

            JSONObject ReviewInfoResult = (JSONObject) jsonObject.get("result");
            JSONObject ReviewInfo = (JSONObject) ReviewInfoResult.get("reviewSummary");
            for (int i = 0; i < 3; i++) {
                if (ReviewInfo.get("grade" + (i + 1)) == null) {
                    continue;
                }
                res[i] = Integer.valueOf(ReviewInfo.get("grade" + (i + 1)).toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean getItemTmarvlYn(String itemId) {
        String result = "";
        boolean tmarvlYn = false;
        String uri = "https://display.cjonstyle.com/c/rest/item/" + itemId + "/explainAreaInfo?channelCode=50001002&isMyzone=false&isEmployee=false";
        try {
            URL url = new URL(uri);
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            result = bf.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);

            JSONObject DeliveryInfoResult = (JSONObject) jsonObject.get("result");
            JSONObject DeliveryInfo = (JSONObject) DeliveryInfoResult.get("itemDeliveryInfoTuple");
            tmarvlYn = (Boolean) DeliveryInfo.get("tmarvlYn");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tmarvlYn;
    }

    // 카드 혜택 정보 OPEN API
    public List<String> getItemCardInfo(String itemId, Long price, String vendCode, String mdCode, String brandCode, String typeCode) {
        String uri = "https://display.cjonstyle.com/c/rest/item/" + itemId + "/cardPromotionInfo.json?channelCode=50001002" +
                "&salePrice=" + price +
                "&mainVenCode=" + vendCode +
                "&mdCode=" + mdCode +
                "&brandCode=" + brandCode +
                "&itemTypeCode=" + typeCode +
                "&customerPrice=" + price +
                "&inflowGroupCode=G0001&dispAreaCode=M";
        String result = "";
        List<String> list = new ArrayList<>();
        try {
            URL url = new URL(uri);
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            result = bf.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);

            JSONObject CardInfoResult = (JSONObject) jsonObject.get("result");
            JSONArray CardInfo = (JSONArray) CardInfoResult.get("cardPromotions");
            Long maxPrice = price;
            for (int i = 0; i < CardInfo.size(); i++) {
                JSONObject card = (JSONObject) CardInfo.get(i);
                String cardName = card.get("cardName").toString();
                String promText = card.get("promText").toString();
                String dcVal = card.get("dcVal").toString();
                String discountUnit = card.get("discountUnit").toString();
                String discountPrice = card.get("discountPrice").toString();
                String salePrice = card.get("salePrice").toString();
                String str = cardName + " " + promText + " " + dcVal + discountUnit + " " + salePrice + "원 (" + discountPrice + "원 할인)";
                maxPrice = maxPrice > Long.parseLong(salePrice) ? Long.parseLong(salePrice) : maxPrice;
                list.add(str);
            }
            list.add(maxPrice.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
