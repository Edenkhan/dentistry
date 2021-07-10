<template>
  <span v-else :class="{'text-muted': !content}">{{ content || tip || '无' }}</span>
</template>

<script>
  export default {
    name: "EmptyTip",
    props: ['content', 'tip']
  }
</script>
