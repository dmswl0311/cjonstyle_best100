<template>
  <div class="container">
      <!-- navbar -->
      <div class="main-nav-bar">
        <b-img src="http://cloud-image.cjonstyle.net/public/confirm/contents/design/module/dm0029/201801/best_ctg_img01.png" fluid alt="Fluid image"></b-img>
        <div class="main-nav-bar-right">
            <b-form-checkbox value="true" unchecked-value="false" v-model="tomorrowArriveStatus">내일 도착</b-form-checkbox>
            <!-- <b-form-checkbox value="true" unchecked-value="false" v-model="soldOutStatus">품절 포함</b-form-checkbox> -->
            <b-form-select v-model="optionSelected" :options="options" size="sm"></b-form-select>
        </div>
      </div>
      <b-overlay :show="show" rounded="sm">
        <!-- 카드 리스트 -->
        <div v-for="line in bestItem" :key="line[0].id" class="card-list">
            <div class="best-item-line">
                <!-- 카드 하나 -->
                <div v-for="item in line" :key="item.itemId" class="best-item-card">
                    <b-img fluid :src="item.itemImage" alt="Image 1"></b-img>
                    <div class="card-text">
                        <div class="card-box">
                            <div @click="onclickItem(item.itemId)">
                                <div class="card-title">
                                    <h5>{{item.rank}}위 {{item.itemName}}</h5>
                                </div>
                                <div class="card-price">
                                    {{item.price}}원
                                </div>
                            </div>
                            <div class="card-btn">
                                <div v-if="item.tmarvlYn=='T'">
                                    <b-button pill variant="outline-primary" size="sm" @click="clickTomorrowArriveStatus">내일도착</b-button>
                                </div>
                                <div v-if="item.slCls=='S'">
                                    <b-button pill variant="outline-danger" size="sm">품절</b-button>
                                </div>
                            </div>
                        </div>
                        <div class="opinion-box">
                            <div v-if="item.rank">
                                <div class="opinion"><b-icon icon="hand-thumbs-up" aria-hidden="true" variant="primary"></b-icon> 뭐를 넣는게</div>
                                <div class="opinion"><b-icon icon="hand-thumbs-down" aria-hidden="true" variant="danger"></b-icon> 좋을까요?????????????????????</div>
                            </div>
                            <div v-else>
                                <p>당신의 후기를 기다릴게요!</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
      </b-overlay>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'App',
  components: {
  },
  data(){
      return {
        show:true,
        tomorrowArriveStatus : "false",
        soldOutStatus:"false",
        optionSelected: "rank",
        options: [
            { value: 'rank', text: '랭킹순' },
            { value: 'priceAsc', text: '낮은 가격순' },
            { value: 'priceDesc', text: '높은 가격순' },
            ],
        bestItem:null,
        oriItem:null,
      }
  },
  created() {
    axios.get("http://localhost:8088/rest/api/best-item?state="+this.optionSelected)
      .then((data)=>{
          const bestItem=[]
            let items=data.data;
            this.oriItem=items;
            // 4개씩 잘라서 저장
            while(items.length > 0){
                if(items.length > 3) bestItem.push(items.splice(0,4))
                else bestItem.push(items.splice(0,items.length))
            }
            this.bestItem = bestItem
        }).finally(()=>{
            this.show=false
        })
  },

  methods:{
    onclickItem(itemId){
     this.$emit("item:clicked",itemId)
    },
    
    clickTomorrowArriveStatus(){
        if(this.tomorrowArriveStatus=="false") this.tomorrowArriveStatus="true"
        else this.tomorrowArriveStatus="false"
    }
  },
  watch: {
    optionSelected(){
        axios.get("http://localhost:8088/rest/api/best-item?state="+this.optionSelected)
        .then((data)=>{
            const bestItem=[]
                let items=data.data
                this.oriItem=items
                // 4개씩 잘라서 저장
                while(items.length > 0){
                    if(items.length > 3) bestItem.push(items.splice(0,4))
                    else bestItem.push(items.splice(0,items.length))
                }
                this.bestItem = bestItem
            })
    },
    tomorrowArriveStatus(){
        if(this.tomorrowArriveStatus=='true'){
            axios.get("http://localhost:8088/rest/api/best-item/tmarvlYn?state="+this.optionSelected)
            .then((data)=>{
                const bestItem=[]
                    let items=data.data
                    this.oriItem=items
                    // 4개씩 잘라서 저장
                    while(items.length > 0){
                        if(items.length > 3) bestItem.push(items.splice(0,4))
                        else bestItem.push(items.splice(0,items.length))
                    }
                    this.bestItem = bestItem
                })
        }else{
            axios.get("http://localhost:8088/rest/api/best-item?state="+this.optionSelected)
            .then((data)=>{
                const bestItem=[]
                    let items=data.data;
                    this.oriItem=items;
                    // 4개씩 잘라서 저장
                    while(items.length > 0){
                        if(items.length > 3) bestItem.push(items.splice(0,4))
                        else bestItem.push(items.splice(0,items.length))
                    }
                    this.bestItem = bestItem
                })
        }
        
    },
    soldOutStatus(){
    },
  },
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

.main-nav-bar{
    align-items: center;
    display: flex;
    justify-content: space-between;
    
}
.main-nav-bar-right{
    margin-right: 10px;
    display: flex;
}
.card-list{
    margin-top:3rem;
}
.best-item{
    width:100px;
    height:300px;
}
.best-item-line{
    display: flex;
    justify-content: center;
    margin-bottom: 100px;   
}
.best-item-card{
    padding:5px;
    height:30rem;
    width:17rem;
}
.card-text{
    text-align: left;
    padding: 15px;
}
.card-title{
    height:5rem;
}
.card-price{
    height:1rem;
    text-align: right;
    margin-bottom: 2rem;
    font-size: large;
}
.opinion-box{
    padding:10px;
    background-color: rgba(217, 217, 217, 0.32);
}
.opinion{
    overflow:hidden;
    text-overflow:ellipsis;
    white-space:nowrap;
}
.card-btn{
    text-align: right;
}
.card-box{
    height:13rem;
}
</style>
