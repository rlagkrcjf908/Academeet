<template>
  <header>
    <router-link to="/">
      <img
        src="@/assets/logo.png"
        class="the-header-title"
        type="button"
        @click="$router.push('/')"
      />
      <!-- <img src="@/assets/images/logo.png" />
      <h1 class="the-header-title">
        <span style="color: #94d82d">A</span>cade<span style="color: #94d82d"
          >M</span
        >eet
      </h1> -->
    </router-link>

    <nav class="the-header-nav">
      <ul class="the-header-ul">
        <li style="color: #94d82d">{{ userInfo.name }} 님</li>
        <li><router-link to="/profile">My Page</router-link></li>
        <li><router-link to="/group">Group</router-link></li>
        <li class="the-header-logout" @click="logout()">Logout</li>
      </ul>
    </nav>
  </header>
  <hr class="the-header-hr" />
</template>

<script>
export default {
  data() {
    return {
      // getItem()을 사용하여 조회한다.
      // 해당 키에 값이 없는 경우 ""로 할당한다.
      userInfo: JSON.parse(localStorage.getItem("userInfo")),
    };
  },
  methods: {
    logout() {
      this.$store.dispatch("accountStore/logout");
    },
  },
};
</script>

<style>
header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  /* width: 100%; */
  height: 64px;
  padding: 10px;
  position: relative;
}

.the-header-title {
  padding: 0;
  margin: 0;
  position: absolute;
  left: 25px;
  top: 8px;
}

.the-header-nav {
  display: inline-block;
  vertical-align: middle;
  padding: 8px;
}

.the-header-ul {
  list-style: none;
  margin: 10px;
  padding: 10px;
  display: flex;
  font-size: 20px;
}

.the-header-hr {
  background: lightgray;
  height: 1.6px;
  border: 0;
  width: 100%;
  margin: 0px;
}
.the-header-logout {
  margin-left: 20px;
}
.the-header-logout:hover {
  margin-left: 20px;
  color: #fdce7e;
}

a:-webkit-any-link {
  text-decoration: none;
  margin-left: 20px;
}
a:-webkit-any-link:hover {
  text-decoration: none;
  color: #fdce7e;
}
</style>