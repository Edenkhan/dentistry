<template>
  <div style="font-size: 14px;margin: 8px 0;">
    <p style="margin-right: 12px; display: inline-block; color: rgba(0,0,0,.5)">
      <template v-if="title">{{ title }}</template>
      <slot v-else name="title">标题</slot>
    </p>
    <template v-if="content != null">{{ content }}</template>
    <slot v-else name="content">
      <span class="text-muted">无</span>
    </slot>
  </div>
</template>
<style scoped>
  p {
    margin-bottom: 0;
  }
</style>
<script>
  export default {
    props: {
      title: String,
      content: String
    }
  }
</script>
