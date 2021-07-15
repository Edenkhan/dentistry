<template>
<div class="dD">
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar title="订单详情" left-text="返回" left-arrow @click-left="DDclick"/>
  </div>

  <!-- 预约套餐开始 -->
    <ul class="home_list">
      <li>
        <div class="cc">
          <div ><img :src="orders.iconPath" width="150" height="150"></div>
          <div class="cc_c">
            <p class='an'>
              <span v-if="orders.productType===0">【线上远程】</span>
              <span v-else-if="orders.userType===0">【线下/个人】</span>
              <span v-else-if="orders.userType===1">【线下/团体】</span>
              <span>{{orders.productName}}</span>
            </p>

<!--            <p class='yc'>{{order.description}}</p>-->
            <p v-html="orders.description"></p>
          </div>
        </div>
        <span class='rospan' :class='!isPay||validNum===0?"bu":""'>{{!isPay||validNum===0?'不可用':'可用'}}</span>
      </li>
    </ul>
  <!-- 预约套餐结束 -->

  <!-- 购买详情开始 -->
  <div class="buyde" v-if='orders.length!=0'>
    <p class='login_text'>购买详情</p>
    <p class='login_line'></p>
    <p class="xiangq">下单时间<span>{{orders.createdDate | timeFormat}}</span></p>
    <p class="xiangq" v-show="isTeam">套餐包含人数<span>{{orders.peopleNum}}</span></p>
    <p class="xiangq" v-show="isTeam">套餐包含次数<span>1{{orders.totalNum}}</span></p>
    <p class="xiangq">支付金额<span>￥{{orders.price}}.00</span></p>
    <p class="xiangq">支付时间<span>{{orders.boughtTime | timeFormat}}</span></p>
    <p class="xiangq">订单编号<span>{{orders.orderNo}}</span></p>
  </div>
  <!-- 购买详情结束 -->

  <!-- 预约详情开始 -->
  <div class="buyde" v-if="shopId!=null">
    <p class='login_text'>预约详情</p>
    <p class='login_line'></p>
    <p class="xiangq">剩余可预约次数<span>{{orders.totalNum - orders.appointNum}}</span></p>
    <p class="xiangq">预约记录<span class='ck' @click='nowkan'>查看</span></p>
  </div>
  <!-- 预约详情结束 -->
</div>
</template>

<style scoped>
/* 头部开始 */
  .dD{
    background: #e8e8e8;
    height:177.7vw;
  }
  .dD .tit .van-nav-bar{
      background: #000;
    }
  .dD .tit .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
    color:#fff;
  }
/* 头部结束 */

/* 预约套餐开始 */
  .dD .home_list{
    background: #fff;
    width:100%;
    margin:0 auto;
    list-style: none;
    height: 30vw;
    overflow: hidden;
  }
  .dD .home_list li{
    width:100%;
    height:30vw;
    margin-bottom: 5vw;
    position: relative;
  }
  .cc{
    display: flex;
    justify-content: space-around;
  }
  .cc_img{
    width:35vw;
    height:30vw;
    background: url('../assets/img/bbg.png') no-repeat;
    background-position: center;
    background-size:cover;
  }
  .dD .an{
    margin:0;
    padding:0;
    margin-top: 7vw;
  }
  .dD .an span:first-child{
    margin-left:-2vw;
  }
  .an span:last-child{
    font-weight: 600;
  }
  .dD .cc_c{
    width:55vw;
    padding:0 2vw;
    height:30vw;
    overflow: hidden;
    position: relative;
  }
  .dD .yc{
    font-size: 0.8rem;
    margin:0;padding:0;
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
    margin-top: 8vw;
  }
  .dD span.rospan{
    display: inline-block;
    font-size: 0.5rem;
    margin-left: 10vw;
    width:20vw;
    height:5vw;
    text-align: center;
    line-height: 5vw;
    transform: rotate(40deg);
    position:absolute;
    right:-5%;top:4%;
    background:#FF7800;
    color:#fff;
  }
  .dD span.rospan.bu{
    background: #939393;
  }
/* 预约套餐结束 */

/* 购买详情开始 */
  .dD .buyde{
    width:90%;margin:5vw auto;
    background: #fff;
    border-radius: 0.8rem;
    overflow: hidden;
  }
  .dD .buyde .login_text{
    font-size: 1rem;
    width:40vw;
    height:10vw;
    font-weight: bold;
    margin-left: 0vw;
    margin-top: 3vw;
    margin-bottom: 2vw;
  }
  .dD .buyde .login_line{
    width:80%;
    border:1px solid rgba(216,216,216,1);
    position: absolute;
    top:30%;
    left: 10%;
  }
  .dD .buyde p.xiangq{
    margin:0;padding:0;
    margin-left:5vw;
    margin-bottom: 3vw;
    font-size: 0.8rem;
    color:#939393;
    position: relative;
  }
  .dD .buyde p.xiangq span{
    position: absolute;
    right:5%;
  }
/* 购买详情结束 */

/* 预约详情开始 */
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
/* 预约详情结束 */
</style>

<script>
export default {
  data(){
    return {
      orderId: this.$route.query.orderId,
      shopId: this.$route.query.shopId,
      isTeam:false,
      isPay:false,
      orders:[],
      validNum: null,
    }
  },
  methods:{
    DDclick(){
      this.$router.push('/md');
    },
    nowkan(){
      this.$router.push({path:'/yr',query:{orderId:this.orderId,shopId:this.shopId}});
    }
  },
  mounted(){
    // 订单详情
    this.axios.get('api/frontdesk/orders/get?orderId='+this.orderId).then(({data})=>{
      // console.log(this.orders)
      this.orders=data;
      this.validNum = data.totalNum - data.appointNum
      if(this.orders.payStatus==1){
        this.isPay=true;
      }else{
        this.isPay=false;
      }
      if(typeof(this.orders.peopleNum)=='number'){
        this.isTeam=true;
      }else{
        this.isTeam=false;
      }
    })

  }
}
</script>
