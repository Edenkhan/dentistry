<template>
<div class='yysucc'>
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar title="预约成功"/>
  </div>

  <div class="emp_yy">
    <img src="../assets/img/succ.png">
    <p>预约成功</p>
  </div>
  <div class="login_btn">
    <button><router-link to='/yuyue'>完成</router-link></button>
  </div>

</div>
</template>

<style scoped>
.yysucc .tit .van-nav-bar{
    background: #000;
  }
.yysucc .tit .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
  color:#fff;
}
.login_btn{
  margin:20vw auto;
  text-align:center;
}
.yysucc .login_btn button{
  border:0;
  outline: 0;
  width:75vw;
  height:13vw;
  border-radius: 30px;
  background:rgb(102 205 170);
  color:#fff;
}
.yysucc .emp_yy{
  margin:20vw auto;
  text-align: center;
}
</style>

<script>
export default {
  
}
</script>
