# cjonstyle_best100

# Git Commit Message Convention
```
feat : 새로운 기능에 대한 커밋
fix : 버그 수정에 대한 커밋
build : 빌드 관련 파일 수정에 대한 커밋
chore : 그 외 자잘한 수정에 대한 커밋
ci : CI 관련 설정 수정에 대한 커밋
docs : 문서 수정에 대한 커밋
style : 코드 스타일 혹은 포맷 등에 관한 커밋
refactor : 코드 리팩토링에 대한 커밋
test : 테스트 코드 수정에 대한 커밋
```

# API 명세서

## 상품 REST API 
1. BEST 100 상품을 DB에 저장 (하루에 한번)
```
POST /rest/api/best-item
```

```javascript
{
  true
}
```

2. BEST 100 상품 전체 조회 
```
GET /rest/api/best-item
```
| Parameter | Type   | Description                                                             |
|:----------|:-------|:------------------------------------------------------------------------|
| date      | String | "yyyy-MM-DD" 형식으로 조회하고싶은 날짜 입력                                          |
| state     | String | 정렬을 위한 state / rank(랭킹순), priceDesc(높은가격순), priceAsc(낮은가격순) / 기본값은 rank |

```javascript
{
  [
    {
        "id": 837,
        "itemId": "2000265560",
        "date": "2022-06-14",
        "price": 89000,
        "rank": 1,
        "tmarvlYn": "T",
        "slCls": "A",
        "itemName": "[완전 HOT썸머/3년 째 매진] 최신상 슬로기 ZERO FEEL COOL 매쉬",
        "itemImage": "https://itemimage.cjonstyle.net/goods_images/20/560/2000265560L.jpg"
    },
          ...
   ]
}
```

3. 상품 BEST 100 순위 및 가격 변동 추이 (3일간)
```
GET /rest/api/best-item/{item_id}
```

```javascript
{
  [
    {
        "id": 635,
        "itemId": "2000265560",
        "date": "2022-06-12",
        "price": 89000,
        "rank": 2
    },
    {
        "id": 734,
        "itemId": "2000265560",
        "date": "2022-06-13",
        "price": 89000,
        "rank": 1
    },
    {
        "id": 837,
        "itemId": "2000265560",
        "date": "2022-06-14",
        "price": 89000,
        "rank": 1
    }
]
}
```

4. 상품 상세 정보
```
GET /rest/api/item-info/{item_id}
```

```javascript
{
    "itemId": "2002237584",
    "itemName": "홈쇼핑 인기 비스코스100 썸머 블라우스 3종 택 1",
    "oriPrice": 12900,
    "price": 10320,
    "order": 0,
    "orderIsShow": "false",
    "cardPrice": 9600,
    "images": [
        "https://itemimage.cjonstyle.net/goods_images/20/584/2002237584L.jpg?timestamp=20220513094425",
        "https://itemimage.cjonstyle.net/goods_images/20/584/2002237584L1.jpg?timestamp=20220515110227",
        "https://itemimage.cjonstyle.net/goods_images/20/584/2002237584L2.jpg?timestamp=20220515110242"
    ],
    "itemReviewAvgScore": "4.1",
    "grade": [
        80,
        84,
        0
    ],
    "tmarvlYn": "F",
    "slCls": "A",
    "cards": [
        "카카오페이(KB국민카드) 즉시할인 7.0% 9600원 (720원 할인)",
        "카카오페이(삼성카드) 즉시할인 7.0% 9600원 (720원 할인)",
        "CJ카드 청구할인 5.0% 9810원 (510원 할인)"
    ]
}
```

5. 내일 배송상품 조회 

```
GET /rest/api/best-item/tmarvlYn
```

| Parameter | Type   | Description                                                             |
|:----------|:-------|:------------------------------------------------------------------------|
| date      | String | "yyyy-MM-DD" 형식으로 조회하고싶은 날짜 입력                                          |
| state     | String | 정렬을 위한 state / rank(랭킹순), priceDesc(높은가격순), priceAsc(낮은가격순) / 기본값은 rank |


```javascript
{
  [
    {
        "id": 837,
        "itemId": "2000265560",
        "date": "2022-06-14",
        "price": 89000,
        "rank": 1,
        "tmarvlYn": "T",
        "slCls": "A",
        "itemName": "[완전 HOT썸머/3년 째 매진] 최신상 슬로기 ZERO FEEL COOL 매쉬",
        "itemImage": "https://itemimage.cjonstyle.net/goods_images/20/560/2000265560L.jpg"
    },
          ...
  ]
}
```
