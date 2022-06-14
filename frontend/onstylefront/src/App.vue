<template>
  <div id="app" class="container">
    <b-container class="main-cjonstyle">
      <b-row>
        <b-col>
          <router-link to="/">
            <img
              src="@/assets/images/onstylelogo.jpg"
              width="300px"
            /> </router-link
        ></b-col>
        <b-col></b-col>
        <b-col></b-col>
      </b-row>
    </b-container>
    <router-view />
  </div>
</template>
<script>
export default {
  name: "app",
  components: {},
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
.main-cjonstyle {
  margin-top: 3rem;
}
nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
