<template>
  <a-carousel
    indicator-type="slider"
    show-arrow="hover"
    auto-play
    style="width: 100%; height: 150px; border-radius: 4px; overflow: hidden"
  >
    <a-carousel-item v-for="(image, idx) in images" :key="idx">
      <div>
        <a-link
          :href="image.src"
          target="_blank"
          rel="noopener"
        >
          <img :src="image.src" style="width: 100%" alt="" />
        </a-link>
      </div>
    </a-carousel-item>
  </a-carousel>
</template>

<script setup lang="ts">
const images = [
  {
    src: new URL('@/assets/images/slideshow1.jpg', import.meta.url).href,
  },
  {
    src: new URL('@/assets/images/slideshow2.jpg', import.meta.url).href,
  },
]
</script>
