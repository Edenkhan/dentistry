<template>
  <span class="gender">{{ value | displayName }}</span>
</template>


<script>
  export default {
    name: "Gender",
    props: ['value'],
    filters: {
      displayName(value) {
        return ['男', '女'][value];
      }
    }
  }
</script>

<style scoped>

</style>
