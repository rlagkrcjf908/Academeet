<template>
  <header>
    <!-- router-link 넣으니까 css 망가짐 -->
    <router-link :to="{name: 'main'}" >
      <img src="@/assets/images/logo.png" />
      <h1>
        <span style="color: #94d82d">A</span>cade<span style="color: #94d82d"
        >M</span
        >eet
      </h1>
    </router-link>
    
    <nav>
      <ul>
        <li style="color: #94d82d">(nickname)님</li>
        <!--<small v-if="userInfo" class="text-muted">{{ userInfo.uid }}'s </small>-->
        <li><router-link to="/">My Page</router-link></li>
        <li><router-link to="#">Group</router-link></li>
        <li><router-link to="#">Logout</router-link></li>
      </ul>
    </nav>
  </header>
  <hr />
</template>

<script>
export default {};
</script>

<style>
header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 99%;
  height: 64px;
  padding: 10px;
  position: relative;
}

h1 {
  display: inline-block;
  vertical-align: middle;
  color: black;
  /* color: #fdce7e; */
  /* margin: 0.1 rem; */
  padding: 0;
  position: absolute;
  left: 92px;
}

nav {
  display: inline-block;
  vertical-align: middle;
}

ul {
  list-style: none;
  margin: 0;
  padding: 0;
  display: flex;
}
nav a.router-link-exact-active {
  padding: 10px;
  text-decoration: none;
  color: black;
}

nav a.router-link-exact-active:hover {
  color: #fdce7e;
}

hr {
  background: lightgray;
  height: 1.6px;
  border: 0;
  width: 99%;
  margin: 0px;
}
/* user-id는 로그인 했을때 닉네임 보이게 하려고 나둔거 */
/* .user-id {
  font-weight: bold;
  font-size: 1.3rem;
  position: absolute;
  top: 11px;
  left: 40%;
} */
</style>
