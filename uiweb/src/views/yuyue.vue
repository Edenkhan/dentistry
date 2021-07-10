<template>
<div class='yuyue'>
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar title="我要预约" left-text="返回" left-arrow @click-left="yyclick"/>
  </div>

  <!-- 顶部导航栏 -->
  <van-tabs v-model="activeName">
    <van-tab title="全部" name="a">
      <div v-if="meprev.length==0">
        <div class="emp_yy">
          <img src="../assets/img/emp_yy.png">
          <p>暂无相关信息</p>
        </div>
        <div class="login_btn">
          <button>去购买</button>
        </div>
      </div>
      <!-- 全部开始 -->
        <ul class="home_list">
          <li v-for='(i,index) of meprev' :key='index'>
            <table></table>
            <p class="ti">
              <span v-if="i.productType===0">【线上远程】</span>
              <span v-else-if="i.userType===0">【线下/个人】</span>
              <span v-else-if="i.userType===1">【线下/团体】</span>
              <span>{{i.productName}} {{i.totalNum}}次</span>
              <span v-if="i.appointStatus === 0">待预约</span>
              <span v-if="i.appointStatus === 1">已预约</span>
            </p>
            <p class="cont">
              下单时间：{{i.createdDate | timeFormat}}
            </p>
            <p class="las">
              <span>剩余可预约：{{i.totalNum - i.appointNum}}次</span>
              <span v-if="i.appointStatus === 0" @click='nowyy(i.id,i.productId)'>立即预约</span>
              <span v-if="i.appointStatus === 1" @click='nowyy(i.id,i.productId,1)'>修改预约</span>
            </p>
          </li>
        </ul>
      <!-- 全部结束 -->
    </van-tab>
    <van-tab title="待预约" name="b">
      <!-- 待预约开始 -->
        <div v-if="mest1.length==0">
          <div class="emp_yy">
            <img src="../assets/img/emp_yy.png">
            <p>暂无相关信息</p>
          </div>
          <div class="login_btn">
            <button>去购买</button>
          </div>
        </div>
      <!-- 待预约结束 -->
      <ul class="home_list" v-else>
        <li v-for='(i,index) of mest1' :key='index'>
          <table></table>
            <p class="ti">
              <span v-if="i.productType===0">【线上远程】</span>
            <span v-else-if="i.userType===0">【线下/个人】</span>
            <span v-else-if="i.userType===1">【线下/团体】</span> 
              <span>{{i.productName}} {{i.totalNum}}次</span>
              <span>待预约</span>
            </p>
            <p class="cont">
              下单时间：{{i.createdDate | timeFormat}}
            </p>
            <p class="las">
              <span>剩余可预约：{{i.totalNum - i.appointNum}}次</span>
              <span @click='nowyy(i.id,i.productId)'>立即预约</span>
            </p>         
        </li>
      </ul>
    </van-tab>
    <van-tab title="已预约" name="c">
      <!-- 已预约开始 -->
        <div v-if="mest2.length==0">
          <div class="emp_yy">
            <img src="../assets/img/emp_yy.png">
            <p>暂无相关信息</p>
          </div>
          <div class="login_btn">
            <button>去购买</button>
          </div>
        </div>
      <!-- 已预约结束 -->
      <ul class="home_list" v-else>
        <li v-for='(i,index) of mest2' :key='index'>
          <table></table>
            <p class="ti">
              <span v-if="i.productType===0">【线上远程】</span>
            <span v-else-if="i.userType===0">【线下/个人】</span>
            <span v-else-if="i.userType===1">【线下/团体】</span> 
              <span>{{i.productName}} {{i.totalNum}}次</span>
              <span>已预约</span>
            </p>
            <p class="cont">
              下单时间：{{i.createdDate | timeFormat}}
            </p>
            <p class="las">
              <span>剩余可预约：{{i.totalNum - i.appointNum}}次</span>
              <span @click='nowyy(i.id,i.productId,1)'>修改预约</span>
            </p>         
        </li>
      </ul>
    </van-tab>
  </van-tabs>

</div>
</template>

<style>
/* 头部样式 */
  .yuyue{
    background: #e8e8e8;
    height:220vw;
  }
  .yuyue .tit .van-nav-bar{
      background: #000;
    }
  .yuyue .tit .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
    color:#fff;
  }
/* 头部样式结束 */

/* 导航栏样式 */
  .yuyue .van-tab--active{
    color: #03C590 !important;
  }
  .yuyue .van-tabs__line {
    background-color: #03C590
  }
  .yuyue .van-tabs__nav{
    background: #e8e8e8 !important;
  }
/* 导航栏样式 */

/* 表格样式 */
  .yuyue .home_list{
    width:90%;
    margin:5vw auto;
    list-style: none;
  }
  .yuyue .home_list li{
    width:100%;
    height:30vw;
    margin-bottom: 5vw;
    position: relative;
    background: #fff;
    border-radius: 0.6rem;
  }
/* 预约详情 */
  .ti{
    margin-bottom: 0;
    margin-top: 2vw;
  }
  .ti span:last-child{
    display: inline-block;
    font-size: 0.5rem;
    margin-left: 24vw;
  }
  .cont{
    margin-top: 2vw;
    font-size: 0.8rem;
    margin-left: 2vw;
    color:#232323;
  }
  .las{
    margin-bottom: 2vw;
    margin-left: 2vw;
    font-size: 0.8rem;
    margin-top: 9vw;
  }
  .las span:last-child{
    display: inline-block;
    width:18vw;
    height:5vw;
    text-align: center;
    line-height: 5vw;
    border-radius: 0.5rem;
    margin-left: 42vw;
    background: #03C590;
    color:#fff;
  }
/* 空预约 */
.login_btn{
  margin:20vw auto;
  text-align:center;
}
.yuyue .login_btn button{
  border:0;
  outline: 0;
  width:75vw;
  height:13vw;
  border-radius: 30px;
  background:rgb(102 205 170);
  color:#fff;
}
.emp_yy{
  margin:10vw auto;
  text-align: center;
}
</style>

<script>
// import qs from 'qs'
import {mapState} from 'vuex';
export default {
  computed:{
    ...mapState([
        'username','iphone','id','sex'
    ])
  },
  data() {
    return {
      activeName: 'a',
      meprev:[],
      mest1:[],
      mest2:[],
    };
  },
  methods:{
    nowyy(orderId,productId,flag){
      // alert(JSON.stringify(orderId))
      // alert(JSON.stringify(productId))
      this.$router.push({path:'/yym',query:{orderId:orderId,productId:productId,flag:flag}});
    },
    yyclick(){
      this.$router.push('/');
    }
  },
  mounted(){
    // 我要预约
    this.axios.get('api/frontdesk/orders/getByUser').then(({data})=>{
      // alert(JSON.stringify(data))
      this.meprev=data.data;
      for(let key of data.data){
        if(key.appointStatus==0){
          this.mest1.push(key)
        }else if(key.appointStatus==1){
          this.mest2.push(key)
        }
      }
      // console.log(this.mest1);
      // console.log(this.mest2);
    })

    
  }
}
</script>

