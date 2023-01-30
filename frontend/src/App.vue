<template>
  <the-header></the-header>
  <router-view></router-view>
</template>
<script>
import TheHeader from "./components/layouts/TheHeader.vue";

export default {
  components: { TheHeader },
};
</script>

<style lang="scss">
#app {
  // font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  font-family: "LINESeedKR-Bd";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_11-01@1.0/LINESeedKR-Bd.woff2")
    format("woff2");
  font-weight: 700;
  font-style: normal;
}

nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: black;
      // color: #42b983;
    }
  }
}
</style>
