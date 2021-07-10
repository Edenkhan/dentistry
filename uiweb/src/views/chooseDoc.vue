<template>
<div class='cD'>
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar title="标题" left-text="返回" left-arrow/>
  </div>

  <div class="emp_yy">
    <img src="../assets/img/cd.png">
    <p>选择医生</p>
  </div>
  <div class="login_btn">
    <button>兑换</button>
  </div>

</div>
</template>

<style scoped>
.cD .tit .van-nav-bar{
    background: #000;
  }
.cD .tit .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
  color:#fff;
}
.login_btn{
  margin:20vw auto;
  text-align:center;
}
.cD .login_btn button{
  border:0;
  outline: 0;
  width:75vw;
  height:13vw;
  border-radius: 30px;
  background:rgb(102 205 170);
  color:#fff;
}
.cD .emp_yy{
  margin:20vw auto;
  text-align: center;
}
</style>