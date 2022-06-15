import Vue from "vue";
import VueRouter from "vue-router";
import Main from "../Main.vue";
import Detail from "../Detail.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: Main,
  },
  {
    path: "/detail/:id",
    component: Detail,
  },
];

const router = new VueRouter({
  mode: "history",
  scrollBehavior() {
    return { x: 0, y: 0 };
  },
  base: process.env.BASE_URL,
  routes,
});

export default router;
