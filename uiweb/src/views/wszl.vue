<template>
<div class='wszl'>
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar title="完善成功"/>
  </div>

  <div class="emp_yy">
    <img src="../assets/img/succ.png">
    <p>您已完善个人资料</p>
  </div>
  <div class="login_btn">
    <button><router-link to='/yuyue'>立即预约</router-link> </button>
  </div>

</div>
</template>

<style scoped>
.wszl .tit .van-nav-bar{
    background: #000;
  }
.wszl .tit .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
  color:#fff;
}
.login_btn{
  margin:20vw auto;
  text-align:center;
}
.wszl .login_btn button{
  border:0;
  outline: 0;
  width:75vw;
  height:13vw;
  border-radius: 30px;
  background:rgb(102 205 170);
  color:#fff;
}
.wszl .emp_yy{
  margin:20vw auto;
  text-align: center;
}
</style>