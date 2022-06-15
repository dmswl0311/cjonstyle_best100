package cjonstyle.best100.service.item;


import cjonstyle.best100.domain.entity.bestItem.Best;
import cjonstyle.best100.domain.dto.bestItem.BestCh;
import cjonstyle.best100.domain.dto.bestItem.BestChRes;
import cjonstyle.best100.domain.dto.bestItem.BestRes;
import cjonstyle.best100.domain.dto.item.ItemInfo;
import cjonstyle.best100.repository.item.ItemRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepo repo;
    private final String bestItemUri = "https://display.cjonstyle.com/c/rest/category/getTop100ItemList?type=P"; // BEST 아이템 OPEN API
    private final String itemInfoUriBase1 = "https://display.cjonstyle.com/c/rest/item/"; // 아이템 기본정보1 REST API
    private final String itemInfoUriBase2 = "/itemInfo.json?channelCode=50001002&isEmployee=false"; // 아이템 기본정보2 REST API
    private final String reviewInfoUriBase1 = "https://item.cjonstyle.com/celebshop/review/rest/itemReviewInfo?itemCode="; // 아이템 리뷰정보1 REST API
    private final String reviewInfoUriBase2 = "&channelCode=50001002"; // 아이템 리뷰정보2 REST API
    private final String deliveryInfoUriBase2 = "/explainAreaInfo?channelCode=50001002&isMyzone=false&isEmployee=false"; // 아이템 택배정보2 REST API

    @Override
    public boolean saveAllBestItem() {
        /**
         * 하루에 한번만 실행되어야 하므로 한번만 실행되었는지 확인
         * 오늘 날짜로 검색, 하나 이상이 있다면 DB에 추가하지 않고 return
         */
        List<Best> flag = repo.findTopByDate(LocalDate.now());
        if (flag.size() >= 1) return false;

        try {
            JSONObject api = apiTemplate(bestItemUri);
            JSONArray ItemInfo = (JSONArray) api.get("cateTop100ItemTupleList");
            BestRes best;
            for (int i = 0; i < ItemInfo.size(); i++) {
                best = new BestRes();
                JSONObject item = (JSONObject) ItemInfo.get(i);
                String itemImage = "https:" + item.get("itemImgUrl").toString(); // 상품이미지
                String itemId = (String) item.get("itemCd"); // 상품아이디
                JSONObject info = (JSONObject) item.get("rmItempriceInfo");
                Long price = (Long) info.get("salePrice"); // 가격
                boolean tmarvlYn = (boolean) info.get("tmarvlYn"); // 내일 배송 여부
                String itemName = info.get("displayItemName").toString(); // 상품명

                // 상품 상태 불러오기
                String itemInfoUri = itemInfoUriBase1 + itemId + itemInfoUriBase2;
                api = apiTemplate(itemInfoUri);

                info = (JSONObject) api.get("detailInfo");
                String slCls = (String) info.get("slCls"); // 상품 상태

                // 아이템 저장
                best.setItemId(itemId);
                best.setPrice(price);
                best.setRank(i + 1);
                if (tmarvlYn) best.setTmarvlYn("T");
                else best.setTmarvlYn("F");
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
        LocalDate today = LocalDate.now();
        LocalDate inputDate = LocalDate.parse(date);

        //   입력 date가 오늘~3일전에 포함되는지 확인
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DATE, -2);
        String beforeDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(day.getTime());

        if (inputDate.isBefore(LocalDate.parse(beforeDate)) || !inputDate.isBefore(today.plusDays(1L))) {
            throw new NullPointerException();
        }

        List<Best> flag = repo.findTopByDate(inputDate);
        if (flag.size() >= 1) saveAllBestItem(); // 오늘 날짜에 저장된 DB가 없다면 저장

        List<Best> bestList;
        if ("priceAsc".equals(state)) bestList = repo.findAllByDateOrderByPriceAsc(inputDate);
        else if ("priceDesc".equals(state)) bestList = repo.findAllByDateOrderByPriceDesc(inputDate);
        else bestList = repo.findAllByDateOrderByRank(inputDate);

        return bestList.stream().map(BestRes::of).collect(Collectors.toList());
    }

    @Override
    public BestChRes getChangeBestItem(String itemId) {
        LocalDate today = LocalDate.now();
        LocalDate before = LocalDate.now().minusDays(2);
        List<Best> best = repo.findAllByItemIdAndDateBetween(itemId, before, today);
        boolean flag = false;
        List<BestCh> result = best.stream().map(BestCh::of).collect(Collectors.toList());
        Long value = Long.valueOf(result.stream()
                .mapToInt(x -> Math.toIntExact(x.getPrice()))
                .min()
                .orElseThrow(NoSuchElementException::new)); // 최소 가격 구하기
        BestCh todayBestCh = result.get(result.size() - 1); // 오늘 날짜의 가격과 최소가격 비교
        if (todayBestCh.getPrice().equals(value)) flag = true;
        return new BestChRes(result, flag);
    }

    @Override
    public ItemInfo getItemInfo(String itemId) {
        String uri = itemInfoUriBase1 + itemId + itemInfoUriBase2;
        ItemInfo item = null;
        try {
            JSONObject api = apiTemplate(uri);

            // 이미지
            JSONObject ImagesInfo = (JSONObject) api.get("imagesInfo");
            JSONArray imagesArray = (JSONArray) ImagesInfo.get("itemImages");
            List<String> images = new ArrayList<>();
            for (Object image : imagesArray) {
                images.add("https:" + (String) image);
            }
            String ReviewScore = api.get("itemReviewAvgScore").toString(); // 리뷰 점수
            JSONObject detailInfo = (JSONObject) api.get("detailInfo");

//            JSONObject PriceInfo=(JSONObject) detailInfo.get("itemPrice");
//            Long oriPrice=(Long) PriceInfo.get("salePrice");
//            Long price=(Long) PriceInfo.get("discountPrice");

            Long oriPrice = (Long) detailInfo.get("slPrc"); // 가격 채널 코드 변경하지 않아 가격이 다른점 감안
            Long price = (Long) detailInfo.get("slPrc"); //고맞가

            String slCls = detailInfo.get("slCls").toString(); // 상품 상태 정보
            String itemName = detailInfo.get("dispItemName").toString(); // 상품명
            String vendCode = detailInfo.get("mainVenCd").toString();
            String mdCode = detailInfo.get("mdCd").toString();
            String brandCode = detailInfo.get("brandCd").toString();
            String typeCode = detailInfo.get("itemTypeCd").toString();

            JSONObject orderInfo = (JSONObject) detailInfo.get("orderCntInfo");
            int order = Integer.valueOf(orderInfo.get("orderCnt").toString()); // 구매 갯수
            String orderIsShow = orderInfo.get("isShowOrderCnt").toString(); // 구매 갯수 공개 여부

            boolean flag = getItemTmarvlYn(itemId); // 내일 배송 여부
            String tmarvlYn = "F";
            if (flag) tmarvlYn = "T";

            int[] grade = getItemGrade(itemId); // 품질, 디자인, 한달 사용 점수

            List<String> cards = getItemCardInfo(itemId, price, vendCode, mdCode, brandCode, typeCode);
            Long cardPrice = Long.valueOf(cards.get(cards.size() - 1)); // 최대 카드 혜택가
            cards = cards.subList(0, cards.size() - 1);
            item = new ItemInfo(itemId, itemName, oriPrice, price, order, orderIsShow, cardPrice, images, ReviewScore, grade, tmarvlYn, slCls, cards);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<BestRes> getAllBestItemTmarvlYn(String date, String state) {
        List<BestRes> res = getAllBestItem(date, state);
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
        String uri = reviewInfoUriBase1 + itemId + reviewInfoUriBase2;
        try {
            JSONObject api = apiTemplate(uri);
            JSONObject ReviewInfo = (JSONObject) api.get("reviewSummary");
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

    private JSONObject apiTemplate(String uri) throws IOException, ParseException {
        URL url = new URL(uri);
        BufferedReader bf;
        bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        String result = bf.readLine();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(result);

        return (JSONObject) jsonObject.get("result");
    }

    // 내일 배송 여부
    public boolean getItemTmarvlYn(String itemId) {
        boolean tmarvlYn = false;
        String uri = itemInfoUriBase1 + itemId + deliveryInfoUriBase2;
        try {
            JSONObject api = apiTemplate(uri);
            JSONObject DeliveryInfo = (JSONObject) api.get("itemDeliveryInfoTuple");
            tmarvlYn = (Boolean) DeliveryInfo.get("tmarvlYn");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tmarvlYn;
    }

    // 카드 혜택 정보 OPEN API
    public List<String> getItemCardInfo(String itemId, Long price, String vendCode, String mdCode, String brandCode, String typeCode) {
        String uri = itemInfoUriBase1 + itemId + "/cardPromotionInfo.json?channelCode=50001002" +
                "&salePrice=" + price +
                "&mainVenCode=" + vendCode +
                "&mdCode=" + mdCode +
                "&brandCode=" + brandCode +
                "&itemTypeCode=" + typeCode +
                "&customerPrice=" + price +
                "&inflowGroupCode=G0001&dispAreaCode=M";
        List<String> list = new ArrayList<>();
        DecimalFormat decFormat = new DecimalFormat("###,###");
        try {
            JSONObject api = apiTemplate(uri);
            JSONArray CardInfo = (JSONArray) api.get("cardPromotions");
            Long maxPrice = price;
            for (int i = 0; i < CardInfo.size(); i++) {
                JSONObject card = (JSONObject) CardInfo.get(i);
                String cardName = card.get("cardName").toString();
                String promText = card.get("promText").toString();
                String dcVal = card.get("dcVal").toString();
                String discountUnit = card.get("discountUnit").toString();
                String discountPrice = card.get("discountPrice").toString();
                String salePrice = card.get("salePrice").toString();
                maxPrice = maxPrice > Long.parseLong(salePrice) ? Long.parseLong(salePrice) : maxPrice;
                discountPrice = decFormat.format(Integer.parseInt(discountPrice));
                salePrice = decFormat.format(Integer.parseInt(salePrice));
                String str = cardName + " " + promText + " " + dcVal + discountUnit + " " + salePrice + "원 (" + discountPrice + "원 할인)";
                list.add(str);
            }
            list.add(maxPrice.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
