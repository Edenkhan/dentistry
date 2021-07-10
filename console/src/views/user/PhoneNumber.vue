<template>
  <span v-if="value" class="phone-number" :class="{ mask: isMask }" @click="show" :title="isMask ? '点击显示全部' : ''" >{{ value | mask(isMask) }}</span>
  <span v-else class="text-muted">无</span>
</template>


<script>
  import {maskPhoneNumber} from "../../utils/mask";

  export default {
    name: "PhoneNumber",
    props: ['value'],
    data() {
      return {
        isMask: true
      }
    },
    methods: {
      show() {
        this.isMask = false;
      }
    },
    filters: {
      mask(value, isMask) {
        return isMask ? maskPhoneNumber(value) : value;
      }
    }
  }
</script>

<style scoped lang="scss">
  .phone-number {
    &.mask {
      border-bottom: 1px dotted;
      cursor: pointer;
    }
  }
</style>

