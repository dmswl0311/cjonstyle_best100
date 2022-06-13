<template>
  <div class="container">
    <!-- navbar -->
    <div class="main-nav-bar">
      <div class="main-nav-bar-right">
        <b-form-checkbox
          value="true"
          unchecked-value="false"
          v-model="tomorrowArriveStatus"
          name="check-button"
          button
          button-variant="outline-primary"
          style="margin-right: 1rem"
        >
          <b>#내일 도착 📦</b>
        </b-form-checkbox>
        <!-- <b-form-checkbox value="true" unchecked-value="false" v-model="soldOutStatus">품절 포함</b-form-checkbox> -->
        <b-form-select
          v-model="optionSelected"
          :options="options"
          size="sm"
        ></b-form-select>
      </div>
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
                    <strong>{{ item.price }}원</strong>
                  </div>
                </div>
                <div class="mdl-card__actions mdl-card--border">
                  <div v-if="item.tmarvlYn == 'T'">
                    <b-button
                      pill
                      variant="outline-primary"
                      size="sm"
                      @click="clickTomorrowArriveStatus"
                      >#내일도착</b-button
                    >
                  </div>
                </div>
              </div>
            </div>

            <!-- <b-img fluid :src="item.itemImage" alt="Image 1"></b-img> -->
            <!-- <div class="card-text">
              <div class="card-box">
                <router-link :to="`/detail/${item.itemId}`">
                  <div>
                    <div class="card-title">
                      <h5>{{ item.rank }}위 {{ item.itemName }}</h5>
                    </div>
                    <div class="card-price">{{ item.price }}원</div>
                  </div>
                </router-link>
                <div class="card-btn">
                  <div v-if="item.tmarvlYn == 'T'">
                    <b-button
                      pill
                      variant="outline-primary"
                      size="sm"
                      @click="clickTomorrowArriveStatus"
                      >내일도착</b-button
                    >
                  </div>
                  <div v-if="item.slCls == 'S'">
                    <b-button pill variant="outline-danger" size="sm"
                      >품절</b-button
                    >
                  </div>
                </div>
              </div>
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
    axios
      .get(
        "http://localhost:8088/rest/api/best-item?state=" + this.optionSelected
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
  },
  watch: {
    optionSelected() {
      axios
        .get(
          "http://localhost:8088/rest/api/best-item?state=" +
            this.optionSelected
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
              this.optionSelected
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
              this.optionSelected
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
</script>

<style scoped>
#app {
  content-visibility: auto;
  contain-intrinsic-size: 1px 5000px;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

.main-nav-bar {
  align-items: center;
  display: flex;
  justify-content: flex-end;
}
.main-nav-bar-right {
  margin-right: 10px;
  display: flex;
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
</style>
