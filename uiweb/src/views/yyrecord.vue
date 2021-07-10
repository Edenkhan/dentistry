<template>
<div class="yyr">
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar :title="iswen?'问诊记录':'预约记录'"/>
  </div>

  
  <!-- 购买详情开始 -->
  <div class="buyde" v-for="(i,index) of appointmentList" :key='index'>
    <p class='login_text'>{{i.appointDate | timeFormat}}</p>
    <p class='login_line'></p>
    <p class="xiangq" v-show='iswen'>问诊人<span>王二麻子</span></p>

    <p class="xiangq" v-show='iswen'>问诊医生<span>问诊医生1</span></p>
    <p class="xiangq" v-show='!iswen'>预约时间<span>{{i.appointDate | timeFormat}}</span></p>
    <p class="xiangq" v-show='!iswen'>预约时段<span>{{i.timePeriod==0?'上午':'下午'}}</span></p>

    <p class="xiangq">次数<span>第{{index}}/3次</span></p>
    <p class="xiangq">报告<span class='ck'><router-link to='/rl'>查看</router-link></span></p>
  </div>
  <!-- 购买详情结束 -->

</div>
</template>

<style scoped>
a{
  text-decoration: none;
  color:#fff;
}
/* 头部开始 */
  .yyr{
    background: #e8e8e8;
    height:182.7vw;
  }
  .yyr .tit .van-nav-bar{
      background: #000;
    }
  .yyr .tit .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
    color:#fff;
  }
/* 头部结束 */

/* 购买详情开始 */
  .yyr .buyde{
    width:90%;margin:5vw auto;
    background: #fff;
    border-radius: 0.8rem;
    overflow: hidden;
  }
  .yyr .buyde .login_text{
    font-size: 1rem;
    width:40vw;
    height:10vw;
    font-weight: bold;
    margin-left: 0vw;
    margin-top: 3vw;
    margin-bottom: 2vw;
  }
  .yyr .buyde .login_line{
    width:80%;
    border:1px solid rgba(216,216,216,1);
    position: absolute;
    top:13%;
    left: 10%;
  }
  .yyr .buyde p.xiangq{
    margin:0;padding:0;
    margin-left:5vw;
    margin-bottom: 3vw;
    font-size: 0.8rem;
    color:#939393;
    position: relative;
  }
  .yyr .buyde p.xiangq span{
    position: absolute;
    right:5%;
  }
  .ck{
    display: inline-block;
    width:10vw;
    height: 5vw;
    text-align:center;
    line-height: 5vw;
    border-radius: 0.5rem;
    color:#fff;
    background: #05C58F;
  }
/* 购买详情结束 */
</style>

<script>
export default {
  data(){
    return {
      orderId: this.$route.query.orderId,
      shopId: this.$route.query.shopId,
      iswen:false,
      appointmentList:[]
    }
  },
  methods:{
    
  },
  mounted(){

    // 预约记录
    this.axios.get(`api/frontdesk/appointment/getByUser?orderId=${this.orderId}`).then(({data})=>{
      // alert(JSON.stringify(data))
      this.appointmentList=data.data;
    })
  }
}
</script>
