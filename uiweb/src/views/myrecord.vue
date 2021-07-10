<template>
<div class="mb">
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar title="我的报告"/>
  </div>
  <div class="mb_img"></div>
</div>
</template>

<style scoped>
.mb{
  width:100%;
  height:177.7vw
}
.mb_img{
  width:100%;
  background: url(../assets/img/mybg.png) no-repeat;
  height:166.7vw;
  background-size: cover;
  background-position: center;
}
.mb .tit .van-nav-bar{
    background: #000;
  }
.mb .tit .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
  color:#fff;
}
</style>
