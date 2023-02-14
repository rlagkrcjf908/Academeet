<template>
  <ul class="city-buttons">
    <li
      @click="selectCity(city)"
      :class="{ isSelected: citySelected === true }"
      v-for="city in cities"
      :key="city.code"
    >
      {{ city.lable }}
    </li>
  </ul>
</template>
js

<script>
export default {
  name: "city-selector",
  data() {
    cities: [
      { code: "Seoul", label: "서울", selected: false },
      { code: "London", label: "런던", selected: false },
      { code: "Chicago", label: "시카고", selected: false },
    ];
  },
  methods: {
    selectCity(item) {
      item.selected = !item.selected;
    },
  },
};
</script>
css

<style>
.city-buttons {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
}
.city-buttons li {
  cursor: pointer;
}
.isSelected {
  background: #333;
  color: #fff;
}
</style>