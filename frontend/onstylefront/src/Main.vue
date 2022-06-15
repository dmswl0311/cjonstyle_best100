<template>
  <div class="container">
    <!-- navbar -->
    <div class="main-nav-bar">
      <b-row>
        <b-col></b-col>
        <b-col>
          <div class="main-nav-bar-center">
            <b-row>
              <b-col
                ><div v-if="this.dateFormat > this.beforeToday">
                  <b-icon
                    icon="arrow-left-circle"
                    font-scale="3"
                    aria-hidden="true"
                    @click="onClickLeft"
                  ></b-icon>
                </div>
                <div v-else></div>
              </b-col>
              <b-col>
                <h4>{{ date }}</h4>
              </b-col>
              <b-col>
                <div v-if="this.today > this.dateFormat">
                  <b-icon
                    icon="arrow-right-circle"
                    font-scale="3"
                    aria-hidden="true"
                    @click="onClickRight"
                  ></b-icon>
                </div>
                <div v-else></div>
              </b-col>
            </b-row>
          </div>
        </b-col>
        <b-col>
          <div class="main-nav-bar-right">
            <b-button
              variant="outline-primary"
              style="margin-right: 1rem"
              @click="onClicktomorrowBtn"
            >
              <b>#내일 도착📦</b>
            </b-button>
            <!-- <b-form-checkbox value="true" unchecked-value="false" v-model="soldOutStatus">품절 포함</b-form-checkbox> -->
            <b-form-select
              v-model="optionSelected"
              :options="options"
              size="sm"
            ></b-form-select>
          </div>
        </b-col>
      </b-row>
    </div>
    <b-overlay :show="show" rounded="sm">
      <!-- 카드 리스트 -->
      <div v-for="line in bestItem" :key="line[0].id" class="card-list">
        <div class="best-item-line">
          <!-- 카드 하나 -->
          <div v-for="item in line" :key="item.itemId" class="best-item-card">
            <div class="mdl-card mdl-shadow--2dp demo-card-square">
              <div
                class="mdl-card__title mdl-card--expand"
                :style="`background-image:url(${item.itemImage})`"
                @click="onClickCard(item.itemId)"
              >
                <h2 class="mdl-card__title-text">{{ item.rank }}</h2>
              </div>
              <div class="card-title-box">
                <div
                  class="mdl-card__supporting-text"
                  @click="onClickCard(item.itemId)"
                >
                  <strong>{{ item.itemName }}</strong>
                  <div style="text-align: right">
                    <strong>{{ item.price | comma }}원</strong>
                  </div>
                </div>
                <div class="mdl-card__actions mdl-card--border">
                  <div v-if="item.tmarvlYn == 'T'">
                    <b-button
                      pill
                      variant="outline-primary"
                      size="sm"
                      @click="clickTomorrowArriveStatus"
                      class="tomorrowBtn"
                      >#내일도착</b-button
                    >
                  </div>
                </div>
              </div>
            </div>
            <!-- <div class="card-text">
              <div class="opinion-box">
                <div v-if="item.rank">
                  <div class="opinion">
                    <b-icon
                      icon="hand-thumbs-up"
                      aria-hidden="true"
                      variant="primary"
                    ></b-icon>
                    뭐를 넣는게
                  </div>
                  <div class="opinion">
                    <b-icon
                      icon="hand-thumbs-down"
                      aria-hidden="true"
                      variant="danger"
                    ></b-icon>
                    좋을까요?????????????????????
                  </div>
                </div>
                <div v-else>
                  <p>당신의 후기를 기다릴게요!</p>
                </div>
              </div>
            </div> -->
          </div>
        </div>
      </div>
    </b-overlay>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "App",
  components: {},
  data() {
    return {
      today: null,
      beforeToday: null,
      date: null,
      dateFormat: null,
      show: true,
      tomorrowArriveStatus: "false",
      soldOutStatus: "false",
      optionSelected: "rank",
      checked1: false,
      options: [
        { value: "rank", text: "랭킹순" },
        { value: "priceAsc", text: "낮은 가격순" },
        { value: "priceDesc", text: "높은 가격순" },
      ],
      bestItem: null,
      oriItem: null,
    };
  },
  created() {
    this.today = new Date();
    let tday = new Date();
    this.dateFormat = tday;
    this.date = getDateFormat(tday);
    this.beforeToday = new Date(new Date().setDate(new Date().getDate() - 2));
    axios
      .get(
        "http://localhost:8088/rest/api/best-item?state=" +
          this.optionSelected +
          "&date=" +
          this.date
      )
      .then((data) => {
        const bestItem = [];
        let items = data.data;
        this.oriItem = items;
        // 4개씩 잘라서 저장
        while (items.length > 0) {
          if (items.length > 3) bestItem.push(items.splice(0, 4));
          else bestItem.push(items.splice(0, items.length));
        }
        this.bestItem = bestItem;
      })
      .finally(() => {
        this.show = false;
      });
  },
  methods: {
    onclickItem(itemId) {
      this.$emit("item:clicked", itemId);
    },

    clickTomorrowArriveStatus() {
      if (this.tomorrowArriveStatus == "false")
        this.tomorrowArriveStatus = "true";
      else this.tomorrowArriveStatus = "false";
    },
    onClickCard(itemId) {
      console.log("반응이");
      this.$router.push("/detail/" + itemId);
    },
    onClicktomorrowBtn() {
      if (this.tomorrowArriveStatus == "false")
        this.tomorrowArriveStatus = "true";
      else this.tomorrowArriveStatus = "false";
    },
    onClickLeft() {
      const before = new Date(
        this.dateFormat.setDate(this.dateFormat.getDate() - 1)
      );
      this.dateFormat = before;
      this.date = getDateFormat(before);
      console.log(this.date);
    },
    onClickRight() {
      const before = new Date(
        this.dateFormat.setDate(this.dateFormat.getDate() + 1)
      );
      this.dateFormat = before;
      this.date = getDateFormat(before);
    },
  },
  watch: {
    date() {
      axios
        .get(
          "http://localhost:8088/rest/api/best-item?state=" +
            this.optionSelected +
            "&date=" +
            this.date
        )
        .then((data) => {
          const bestItem = [];
          let items = data.data;
          this.oriItem = items;
          // 4개씩 잘라서 저장
          while (items.length > 0) {
            if (items.length > 3) bestItem.push(items.splice(0, 4));
            else bestItem.push(items.splice(0, items.length));
          }
          this.bestItem = bestItem;
        });
    },
    optionSelected() {
      axios
        .get(
          "http://localhost:8088/rest/api/best-item?state=" +
            this.optionSelected +
            "&date=" +
            this.date
        )
        .then((data) => {
          const bestItem = [];
          let items = data.data;
          this.oriItem = items;
          // 4개씩 잘라서 저장
          while (items.length > 0) {
            if (items.length > 3) bestItem.push(items.splice(0, 4));
            else bestItem.push(items.splice(0, items.length));
          }
          this.bestItem = bestItem;
        });
    },
    tomorrowArriveStatus() {
      if (this.tomorrowArriveStatus == "true") {
        axios
          .get(
            "http://localhost:8088/rest/api/best-item/tmarvlYn?state=" +
              this.optionSelected +
              "&date=" +
              this.date
          )
          .then((data) => {
            const bestItem = [];
            let items = data.data;
            this.oriItem = items;
            // 4개씩 잘라서 저장
            while (items.length > 0) {
              if (items.length > 3) bestItem.push(items.splice(0, 4));
              else bestItem.push(items.splice(0, items.length));
            }
            this.bestItem = bestItem;
          });
      } else {
        axios
          .get(
            "http://localhost:8088/rest/api/best-item?state=" +
              this.optionSelected +
              "&date=" +
              this.date
          )
          .then((data) => {
            const bestItem = [];
            let items = data.data;
            this.oriItem = items;
            // 4개씩 잘라서 저장
            while (items.length > 0) {
              if (items.length > 3) bestItem.push(items.splice(0, 4));
              else bestItem.push(items.splice(0, items.length));
            }
            this.bestItem = bestItem;
          });
      }
    },
    soldOutStatus() {},
  },
};
function getDateFormat(_day) {
  const year = _day.getFullYear();
  const month = ("0" + (_day.getMonth() + 1)).slice(-2);
  const day = ("0" + _day.getDate()).slice(-2);
  return year + "-" + month + "-" + day;
}
</script>

<style scoped>
#app {
  content-visibility: auto;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

.main-nav-bar {
  align-items: center;
}
.main-nav-bar-right {
  float: right;
  display: flex;
}
.main-nav-bar-center {
  margin: 0 auto;
  text-align: center;
}
.card-list {
  margin-top: 3rem;
}
.best-item {
  width: 100px;
  height: 300px;
}
.best-item-line {
  display: flex;
  justify-content: center;
  margin-bottom: 100px;
}
.best-item-card {
  padding: 5px;
}
.card-text {
  text-align: left;
  padding: 15px;
}
.card-title {
  height: 5rem;
}
.card-price {
  height: 1rem;
  text-align: right;
  margin-bottom: 2rem;
  font-size: large;
}
.opinion-box {
  padding: 10px;
  background-color: rgba(217, 217, 217, 0.32);
}
.opinion {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.card-btn {
  text-align: right;
}
.card-box {
  height: 12rem;
}
/* .lb-text {
  padding: 10px 10px;
  border-radius: 30px 30px 0px 0px;
  background-color: #3633ff48;
  text-align: center;
} */

.demo-card-square.mdl-card {
  width: 20rem;
  height: 30rem;
  margin: 1rem;
}

.demo-card-square.mdl-card:hover {
  box-shadow: 0 8px 10px 1px rgba(0, 0, 0, 0.14),
    0 3px 14px 2px rgba(0, 0, 0, 0.12), 0 5px 5px -3px rgba(0, 0, 0, 0.2);
}

.demo-card-square > .mdl-card__title {
  background-size: 20rem 20rem;
  background-repeat: no-repeat;
  color: #fff;
  /* background: #03a9f4; */
  /* background-image: url(./picture/kakaobtnlogo.png); */
}

.demo-card-square > .mdl-card__accent {
  background: #ff9800;
}
.card-title-box {
  height: 10rem;
}
.mdl-card__supporting-text {
  font-size: 1.2rem;
  height: 6.5rem;
  justify-content: center;
}
body {
  padding: 20px;
  background: #fafafa;
}
.mdl-card__title-text {
  background-color: rgba(102, 18, 171, 0.582);
  border-radius: 100%;
  text-align: center;
  width: auto;
  height: auto;
  padding: 0px 10px 0px 10px;
}
.tomorrowBtn {
  display: flex;
  float: right;
}
</style>
