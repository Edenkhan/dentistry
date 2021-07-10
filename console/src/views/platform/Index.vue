<template>
  <div>
    <a-alert
      style="margin-top: 16px;"
      message="登录成功！"
      type="success"
      showIcon>
      <template slot="description">
        <p>欢迎您使用直播课后台管理系统！</p>
        <p>当前时间：{{ currentTime }}</p>
      </template>
    </a-alert>
  </div>
</template>
<script>
  import moment from "moment";

  export default {
    data() {
      return {
        currentTime: moment().format('YYYY年MM月DD日 HH:mm:ss')
      }
    },
    created() {
      setInterval(() => {
        this.currentTime = moment().format('YYYY年MM月DD日 HH:mm:ss');
      }, 1000);
    }
  }
</script>
