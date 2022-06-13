<template>
  <div id="App" class="container">
    <b-container class="main-cjonstyle">
      <b-row>
        <b-col><img src="@/picture/onstylelogo.jpg" width="300px" @click="onLogoClick"/></b-col>
        <b-col></b-col>
        <b-col></b-col>
      </b-row>
    </b-container>
    <div>
      <component :is="com" @item:clicked="onItemClicked" :itemId="itemId"></component>
    </div>
  </div>
</template>

<script>
import Main from '@/Main.vue';
import Detail from "@/Detail.vue"

export default {
  name: 'App',
  components: {
    Main,
    Detail,
  },
  data(){
    return{
      com : "Main",
      itemId : null,
    }
  },
  methods:{
    onItemClicked(e){
      this.com="Detail"
      this.itemId = e
    },
    onLogoClick(){
      this.com="Main"
    }
  }
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
.main-cjonstyle{
  margin-top:3rem;
}
</style>
