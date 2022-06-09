package cjonstyle.best100.service;


import cjonstyle.best100.domain.Best;
import cjonstyle.best100.domain.dto.BestDto;
import cjonstyle.best100.repository.ApiRepo;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@CrossOrigin("*")
public class ApiServiceImpl implements ApiService {
    private final ApiRepo repo;

    @Override
    public boolean saveAllBestItem() {
        /**
         * 하루에 한번만 실행되어야 하므로 한번만 실행되었는지 확인
         * 오늘 날짜로 검색, 하나 이상이 있다면 DB에 추가하지 않고 return
         */
        List<Best> flag=repo.findTopByDate(LocalDate.now());
        if(flag.size()>=1) return false;

        try{
            String result="";
            URL url = new URL("https://display.cjonstyle.com/c/rest/category/getTop100ItemList?type=P");
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            result = bf.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(result);

            JSONObject ItemInfoResult = (JSONObject)jsonObject.get("result");
            JSONArray ItemInfo = (JSONArray)ItemInfoResult.get("cateTop100ItemTupleList");

            BestDto best;
            for (int i=0; i<ItemInfo.size(); i++){
                best=new BestDto();
                JSONObject item = (JSONObject)ItemInfo.get(i);
                // 상품명
                String itemId= (String) item.get("itemCd");
                // 가격
                JSONObject info = (JSONObject)item.get("rmItempriceInfo");
                Long price= (Long) info.get("customerPrice");
                best.setItemId(itemId);
                best.setPrice(price);
                best.setRank(i+1);
                repo.save(Best.of(best));
            }
            return true;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
