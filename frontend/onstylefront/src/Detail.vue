<template>
  <div class="container">
    <b-overlay :show="show" rounded="sm">
      <div v-if="item">
        <!-- {{ item.itemId }} -->
        <b-row>
          <b-col
            ><b-img :src="item.images[0]" fluid alt="Responsive image"></b-img
          ></b-col>
          <b-col>
            <div>
              <div v-if="item.orderCnt">
                <h5 class="item-price">{{ item.order }}개 주문</h5>
              </div>
              <h2 v-if="item.itemName">
                {{ item.itemName }}
              </h2>
              <h2 v-else>제목없음</h2>
              <h3 class="item-price">
                <font class="item-price-deco">{{ item.oriPrice }}원</font>
                {{ item.price }}원
              </h3>
              <hr />
              <div v-for="card in item.cards" :key="card">
                <h5>{{ card }}</h5>
              </div>
              <div class="card-price-box">
                <h4>카드 최대 혜택가 {{ item.cardPrice }}원</h4>
              </div>
              <div class="item-tmarvlYn-box">
                <div v-if="item.tmarvlYn == 'T'">
                  <b-button pill variant="outline-primary">#내일도착</b-button>
                </div>
                <div v-else>
                  <b-button pill variant="outline-secondary"
                    >#내일도착 불가</b-button
                  >
                </div>
              </div>
            </div>
            <div class="btn-box">
              <img
                src="@/assets/images/copylogo.png"
                id="clip-btn"
                @click="clipboardShare"
              />

              <img
                class="kakao_btn"
                src="@/assets/images/kakaobtnlogo.png"
                @click="kakaoLink"
              />
            </div>
          </b-col>
        </b-row>
        <!-- 차트 -->
        <div class="rank-price-chart-box">
          <div>
            <LineChartGenerator
              :chart-options="chartOptions"
              :chart-data="chartData"
              :chart-id="chartId"
              :dataset-id-key="datasetIdKey"
              :plugins="plugins"
              :width="width"
              :height="height"
            />
          </div>
          <!-- 가격 차트 -->
          <div>
            <div v-if="minPriceFlag">
              <h4>최근 3일 중 최저가입니다!</h4>
              <b-icon icon="arrow-down" aria-hidden="true"></b-icon>
            </div>
            <div v-else>
              <div v-if="minPriceFlag">
                <h4>최저가가 아닙니다.</h4>
                <b-icon icon="arrow-up" aria-hidden="true"></b-icon>
              </div>
            </div>
            <Bar
              :chart-options="chartOptions"
              :chart-data="chartData2"
              :chart-id="chartId"
              :dataset-id-key="datasetIdKey"
              :plugins="plugins"
              :css-classes="cssClasses"
              :styles="styles"
              :width="width"
              :height="height"
            />
          </div>
        </div>
        <!-- 평점 -->
        <div class="review-box">
          <b-row>
            <b-col> 전체 평점 {{ item.itemReviewAvgScore }} </b-col>
            <b-col>
              <div>
                품질 점수
                <p v-if="item.grade[0] == 0">없음</p>
                <p v-else>{{ item.grade[0] }}</p>
              </div>
              <div>
                디자인 점수
                <p v-if="item.grade[1] == 0">없음</p>
                <p v-else>{{ item.grade[1] }}</p>
              </div>
              <div>
                한달 사용 점수
                <p v-if="item.grade[2] == 0">없음</p>
                <p v-else>{{ item.grade[2] }}</p>
              </div>
            </b-col>
          </b-row>
        </div>
        <div>
          <br />
          <h3>상품 한줄 의견</h3>
          <!-- 상품 한줄 의견 CRUD -->
          <div class="opinion-box">
            <!-- 버튼 두개 -->
            <div class="opinion-sort-btn">
              <b-button @click="clickSortDate()">최신순</b-button>
              <b-button @click="clickSortLike()">인기순</b-button>
            </div>
            <div>
              <b-form-input
                type="password"
                placeholder="비밀번호"
                style="width: 150px"
                v-model="pwd"
              ></b-form-input>
              <b-form-input
                type="text"
                placeholder="한줄 평을 입력해주세요.."
                v-model="contents"
              ></b-form-input>
              <div class="opinion-create-btn">
                <b-button @click="onCreate" variant="outline-success"
                  >등록</b-button
                >
              </div>
            </div>
            <!-- 상품 한줄 의견 리스트-->
            <div
              v-for="opinion in opinions"
              :key="opinion.id"
              class="opinion-one"
            >
              <div class="opinion-top-btn">
                <div class="opinion-date">
                  <b-icon
                    icon="calendar2-date"
                    aria-hidden="true"
                    id="opinion-date"
                  ></b-icon>
                  {{ opinion.date }}
                </div>
                <div class="opinion-modify-delete">
                  <b-icon
                    icon="pencil-fill"
                    aria-hidden="true"
                    @click="onModifyOpinion(opinion)"
                  >
                  </b-icon>
                  <b-icon
                    icon="trash-fill"
                    aria-hidden="true"
                    @click="onClickDeleteOpinion(opinion)"
                  >
                  </b-icon>
                </div>
              </div>
              <b-row>
                <b-col cols="8">
                  <b-form-input
                    type="password"
                    placeholder="비밀번호"
                    style="width: 150px"
                    v-model="opinion.inputPwd"
                  ></b-form-input>
                  <b-form-input
                    type="text"
                    placeholder="한줄 평을 입력해주세요.."
                    :value="opinion.contents"
                    v-model="opinion.contents"
                    :disabled="opinion.readOnly"
                  ></b-form-input>
                  <div class="modify-btn">
                    <b-button
                      v-if="!opinion.readOnly"
                      @click="onClickUpdateOpinion(opinion)"
                      variant="outline-primary"
                      >수정</b-button
                    >
                  </div>
                </b-col>
                <b-col cols="4" class="opinion-like-hate-box">
                  <b-row>
                    <b-col
                      ><p class="h5 mb-2">
                        <b-icon
                          icon="hand-thumbs-up"
                          aria-hidden="true"
                          variant="primary"
                          @click="clickLike(opinion)"
                        ></b-icon>
                        {{ opinion.like }}
                      </p></b-col
                    >
                    <div class="w-100"></div>
                    <b-col
                      ><p class="h5 mb-2">
                        <b-icon
                          icon="hand-thumbs-down"
                          aria-hidden="true"
                          variant="danger"
                          @click="clickHate(opinion)"
                        ></b-icon>
                        {{ opinion.hate }}
                      </p></b-col
                    >
                  </b-row>
                </b-col>
              </b-row>
            </div>
          </div>
        </div>
      </div>
      <!-- <div v-if="item == null">
        <b-jumbotron header="품절된 상품입니다!">
          <p>죄송합니다. 상품 정보를 불러올 수 없습니다.</p>
          <b-button variant="primary" href="/">홈으로 가기</b-button>
        </b-jumbotron>
      </div> -->
    </b-overlay>
  </div>
</template>
<script>
import axios from "axios";
import { Bar } from "vue-chartjs/legacy";
import { Line as LineChartGenerator } from "vue-chartjs/legacy";

import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  LineElement,
  LinearScale,
  CategoryScale,
  PointElement,
  BarElement,
} from "chart.js";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  LineElement,
  LinearScale,
  CategoryScale,
  BarElement,
  PointElement
);

export default {
  name: "App",
  components: {
    LineChartGenerator,
    Bar,
  },
  props: {
    chartId: {
      type: String,
      default: "line-chart",
    },
    datasetIdKey: {
      type: String,
      default: "label",
    },
    width: {
      type: Number,
      default: 600,
    },
    height: {
      type: Number,
      default: 400,
    },
    cssClasses: {
      default: "",
      type: String,
    },
    styles: {
      type: Object,
      default: () => {},
    },
    plugins: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    const today = new Date();
    const todayFormat = getDateFormat.call(this, today);
    const yesterday = new Date(today.setDate(today.getDate() - 1));
    const yesterdayFormat = getDateFormat.call(this, yesterday);
    const beforeYesterday = new Date(today.setDate(today.getDate() - 1));
    const beforeYesterdayFormat = getDateFormat.call(this, beforeYesterday);
    return {
      maxPrice: -1,
      minPriceFlag: false,
      routerId: null,
      min: 101,
      max: -1,
      show: true,
      sort: "date",
      item: null,
      opinions: null,
      pwd: null,
      contents: null,
      chartData: {
        labels: [beforeYesterdayFormat, yesterdayFormat, todayFormat],
        datasets: [
          {
            type: "line",
            label: "순위 변화",
            backgroundColor: "rgb(255,99,132)",
            borderColor: "rgb(255,99,132)",
            data: [],
          },
        ],
      },
      chartData2: {
        labels: [beforeYesterdayFormat, yesterdayFormat, todayFormat],
        datasets: [
          {
            type: "bar",
            label: "가격 변화",
            backgroundColor: [
              "rgba(255,99,132,0.2)",
              "rgba(54,162,235,0.2)",
              "rgba(255,206,86,0.2)",
            ],
            borderColor: [
              "rgba(255,99,132,0.2)",
              "rgba(54,162,235,0.2)",
              "rgba(255,206,86,0.2)",
            ],
            borderWidth: 1,
            data: [],
          },
        ],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
      },
    };
  },
  created() {
    this.routerId = this.$route.params.id;
    console.log("routerId " + this.routerId);
    axios
      .get("http://localhost:8088/rest/api/item-info/" + this.routerId)
      .then((data) => {
        this.item = data.data;
      })
      .then(() => {
        axios
          .get("http://localhost:8088/rest/api/best-item/" + this.routerId)
          .then((data) => {
            const itemData = data.data.change;
            this.minPriceFlag = data.data.flag;
            // 그래프에 rank 기입
            for (let i = 0; i < 3; i++) {
              let flag = false;
              for (let j = 0; j < itemData.length; j++) {
                if (itemData[j].date == this.chartData.labels[i]) {
                  this.chartData.datasets[0].data.push(itemData[j].rank);
                  this.chartData2.datasets[0].data.push(itemData[j].price);

                  if (this.min > itemData[j].rank) this.min = itemData[j].rank;
                  if (this.max < itemData[j].rank) this.max = itemData[j].rank;

                  if (this.maxPrice < itemData[j].price)
                    this.maxPrice = itemData[j].price;
                  flag = true;
                  break;
                }
              }
              if (!flag) {
                this.chartData.datasets[0].data.push(null);
                this.chartData2.datasets[0].data.push(null);
              }
            }
            this.chartData.datasets[0].data.push(this.min, this.max);
            this.chartData2.datasets[0].data.push(0, this.maxPrice + 10000);
          })
          .then(() => {
            axios
              .get(
                "http://localhost:8088/rest/opinion/" +
                  this.routerId +
                  "?state=" +
                  this.sort
              )
              .then((data) => {
                this.opinions = data.data.map((d) => {
                  d.readOnly = true;
                  d.inputPwd = null;
                  d.precontents = d.contents;
                  return d;
                });
              })
              .finally(() => {
                this.show = false;
              });
          });
      });
  },
  methods: {
    clipboardShare() {
      // 1. 새로운 element 생성
      var tmpTextarea = document.createElement("textarea");

      // 2. 해당 element에 복사하고자 하는 value 저장
      tmpTextarea.value = document.location.href;

      // 3. 해당 element를 화면에 안보이는 곳에 위치
      tmpTextarea.setAttribute("readonly", "");
      tmpTextarea.style.position = "absolute";
      tmpTextarea.style.left = "-9999px";
      document.body.appendChild(tmpTextarea);

      // 4. 해당 element의 value를 시스템 함수를 호출하여 복사
      tmpTextarea.select();
      tmpTextarea.setSelectionRange(0, 9999); // 셀렉트 범위 설정
      var successChk = document.execCommand("copy");

      // 5. 해당 element 삭제
      document.body.removeChild(tmpTextarea);

      // 클립보드 성공여부 확인
      if (!successChk) {
        alert("클립보드 복사에 실패하였습니다.");
      } else {
        alert("클립보드에 복사가 완료되었습니다.");
      }
    },
    kakaoLink() {
      window.Kakao.Link.sendDefault({
        objectType: "feed",
        content: {
          title: this.item.itemName,
          description:
            this.item.price +
            "원, 현재 랭킹 " +
            this.chartData.datasets[0].data[
              this.chartData.datasets[0].data.length - 2
            ] +
            "위",
          imageUrl: this.item.images[0],
          link: {
            mobileWebUrl: document.location.href,
            webUrl: document.location.href,
          },
        },
        buttons: [
          {
            title: "웹으로 보기",
            link: {
              mobileWebUrl: document.location.href,
              webUrl: document.location.href,
            },
          },
        ],
        // 카카오톡 미설치 시 카카오톡 설치 경로이동
        installTalk: true,
      });
    },
    onClickDeleteOpinion(o) {
      if (o.inputPwd != o.pwd) {
        alert("비밀번호가 일치하지 않습니다!");
        return;
      }
      axios
        .delete("http://localhost:8088/rest/opinion/" + o.id, {
          data: {
            pwd: o.inputPwd,
            contents: null,
          },
        })
        .then((data) => {
          if (data.data) {
            alert("삭제 되었습니다.");
            axios
              .get(
                "http://localhost:8088/rest/opinion/" +
                  this.routerId +
                  "?state=" +
                  this.sort
              )
              .then((data) => {
                this.opinions = data.data.map((d) => {
                  d.readOnly = true;
                  return d;
                });
              });
          }
        });
    },
    onModifyOpinion(o) {
      o.readOnly = false;
    },
    onClickUpdateOpinion(o) {
      o.readOnly = true;
      // console.log("수정할 내용" + o.contents);
      // console.log("입력한 비밀번호" + o.inputPwd);
      // console.log("클릭한 아이디" + o.id);

      if (o.inputPwd != o.pwd) {
        alert("비밀번호가 일치하지 않습니다!");
        // 내용을 원래내용으로 바꿔줘야하는데 어케함;
        o.contents = o.precontents;
        o.inputPwd = null;
        return;
      }
      axios
        .patch("http://localhost:8088/rest/opinion/" + o.id, {
          pwd: o.inputPwd,
          contents: o.contents,
        })
        .then(() => {
          alert("수정 되었습니다.");
          o.inputPwd = null;
        });
    },
    onCreate() {
      if (this.pwd == null) {
        alert("비밀번호를 입력해주세요!");
        return;
      }
      axios
        .post("http://localhost:8088/rest/opinion/" + this.routerId, {
          pwd: this.pwd,
          contents: this.contents,
        })
        .then(() => {
          alert("등록되었습니다.");
          axios
            .get(
              "http://localhost:8088/rest/opinion/" +
                this.routerId +
                "?state=" +
                this.sort
            )
            .then((data) => {
              this.opinions = data.data.map((d) => {
                d.readOnly = true;
                return d;
              });
              this.pwd = null;
              this.contents = null;
            });
        });
    },
    clickLike(o) {
      o.like += 1;
      axios
        .patch("http://localhost:8088/rest/opinion/expr/" + o.id + "?expr=like")
        .then(() => {});
    },
    clickHate(o) {
      o.hate += 1;
      axios
        .patch("http://localhost:8088/rest/opinion/expr/" + o.id + "?expr=hate")
        .then(() => {});
    },
    clickSortDate() {
      this.sort = "date";
      axios
        .get(
          "http://localhost:8088/rest/opinion/" +
            this.routerId +
            "?state=" +
            this.sort
        )
        .then((data) => {
          this.opinions = data.data.map((d) => {
            d.readOnly = true;
            d.inputPwd = null;
            return d;
          });
        });
    },
    clickSortLike() {
      this.sort = "like";
      axios
        .get(
          "http://localhost:8088/rest/opinion/" +
            this.routerId +
            "?state=" +
            this.sort
        )
        .then((data) => {
          this.opinions = data.data.map((d) => {
            d.readOnly = true;
            d.inputPwd = null;
            return d;
          });
        });
    },
  },
  watch: {},
};
function getDateFormat(_day) {
  const year = _day.getFullYear();
  const month = ("0" + (_day.getMonth() + 1)).slice(-2);
  const day = ("0" + _day.getDate()).slice(-2);
  return year + "-" + month + "-" + day;
}
</script>

<style>
.container {
  margin-bottom: 3rem;
}
.item-price {
  text-align: right;
}
.item-price-deco {
  text-decoration: line-through;
  font-size: large;
  color: rgba(128, 128, 128, 0.518);
}
.opinion-one {
  padding: 10px;
  margin-top: 2rem;
  border: 1px solid rgba(0, 0, 0, 0.194);
  background-color: white;
}
.review-box {
  margin-top: 2rem;
  background-color: #fafafa;
}
.opinion-box {
  background-color: #fafafa;
}
.opinion-sort-btn {
  text-align: right;
}
.opinion-create-btn {
  text-align: right;
}
.opinion-top-btn {
  display: flex;
  margin-bottom: 1rem;
}
.opinion-date {
  text-align: left;
}
.opinion-modify-delete {
  margin-left: auto;
}
.opinion-like-hate-box {
  text-align: center;
  display: flex;
}
.modify-btn {
  text-align: right;
}
.item-tmarvlYn-box {
  text-align: right;
}
.card-price-box {
  margin-top: 1.5rem;
}
.rank-price-chart-box {
  margin-top: 2rem;
  display: flex;
  justify-content: space-evenly;
}
.kakao_btn {
  width: 3rem;
  height: 3rem;
}
#clip-btn {
  margin-right: 0.5rem;
  width: 3rem;
  height: 3rem;
}
.btn-box {
  margin-top: 1rem;
  display: flex;
  float: right;
}
</style>
