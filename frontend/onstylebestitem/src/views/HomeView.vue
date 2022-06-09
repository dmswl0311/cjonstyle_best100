<template>
  <div class="home">
    <img alt="Vue logo" src="../../public/cjonstylelogo.jpg" />
    <b-alert show variant="secondary">
      <HelloWorld msg="CJ ONSTYLE 베스트 100" />
    </b-alert>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from "@/components/HelloWorld.vue";

export default {
  name: "HomeView",
  components: {
    HelloWorld,
  },
};
</script>
