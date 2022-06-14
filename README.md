# cjonstyle_best100

# 기술 스택

| |기술 스택|
|------|---|
|Back-End|JAVA, Spring Boot, MySQL|
|Front-End|Vue.js, HTML, CSS|

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

### 1. BEST 100 상품을 DB에 저장 (하루에 한번)
```
POST /rest/api/best-item
```
#### Response
```javascript
{
  true
}
```

### 2. BEST 100 상품 전체 조회 
```
GET /rest/api/best-item
```
| Parameter | Type   | Description                                                             |
|:----------|:-------|:------------------------------------------------------------------------|
| date      | String | "yyyy-MM-DD" 형식으로 조회하고싶은 날짜 입력                                          |
| state     | String | 정렬을 위한 state / rank(랭킹순), priceDesc(높은가격순), priceAsc(낮은가격순) / 기본값은 rank |

#### Response
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

### 3. 상품 BEST 100 순위 및 가격 변동 추이 (3일간)
```
GET /rest/api/best-item/{item_id}
```
#### Response
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

### 4. 상품 상세 정보
```
GET /rest/api/item-info/{item_id}
```
#### Response
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

### 5. 내일 배송상품 조회 

```
GET /rest/api/best-item/tmarvlYn
```

| Parameter | Type   | Description                                                             |
|:----------|:-------|:------------------------------------------------------------------------|
| date      | String | "yyyy-MM-DD" 형식으로 조회하고싶은 날짜 입력                                          |
| state     | String | 정렬을 위한 state / rank(랭킹순), priceDesc(높은가격순), priceAsc(낮은가격순) / 기본값은 rank |

#### Response
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
-----

## 한줄 평 REST API 
### 1. 상품에 대한 모든 한줄 평 조회

```
GET /rest/opinion/{item_id}
```

| Parameter | Type   | Description                                              |
|:----------|:-------|:---------------------------------------------------------|
| state     | String | 정렬을 위한 state / like(좋아요가 높은 순), date(최근 날짜순) / 기본값은 date |

#### Response
```javascript
{
  [
    {
        "id": 526,
        "itemId": "2002503869",
        "pwd": "1111",
        "contents": "한줄평 내용",
        "date": "2022-06-11",
        "like": 13,
        "hate": 5
    },
        ...
   ]
}
```

### 2. 한줄 평 등록

```
POST /rest/opinion/{item_id}
```
#### Request
```javascript
{
    "pwd":"1234",
    "contents":"작성할 내용"
}
```
#### Response
```javascript
{
    "id": 937,
    "itemId": "1111",
    "pwd": "1234",
    "contents": "작성할 내용",
    "date": "2022-06-14",
    "like": 0,
    "hate": 0
}
```

### 3. 한줄 평 수정

```
PATCH /rest/opinion/{opinion_id}
```
#### Request
```javascript
{
    "pwd":"1234",
    "contents":"수정할 내용"
}
```
#### Response
```javascript
{
    "id": 937,
    "itemId": "1111",
    "pwd": "1234",
    "contents": "수정할 내용",
    "date": "2022-06-14",
    "like": 0,
    "hate": 0
}
```

### 4. 한줄 평 삭제

```
DELETE /rest/opinion/{opinion_id}
```
#### Request
```javascript
{
    "pwd":"1234",
    "contents":null
}
```
#### Response
```javascript
{
    true
}
```

### 5. 한줄 평 좋아요 or 싫어요

```
PATCH /rest/opinion/expr/{opinion_id}
```
| Parameter | Type   | Description                |
|:----------|:-------|:---------------------------|
| expr      | String | 좋아요 클릭시 like, 싫어요 클릭시 hate |
```
#### Response
```javascript
{
    "id": 937,
    "itemId": "1111",
    "pwd": "1234",
    "contents": "수정할 내용",
    "date": "2022-06-14",
    "like": 1,
    "hate": 0
}
```
