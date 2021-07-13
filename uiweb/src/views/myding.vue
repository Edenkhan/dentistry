<template>
<div class='md'>
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar title="我的订单" left-text="返回" left-arrow @click-left="mDclick"/>
  </div>

  <!-- 顶部导航栏 -->
  <van-tabs v-model="activeName">
    <van-tab title="全部" name="aa">
      <!-- 全部开始 -->
        <ul class="home_list" v-if='orderList.length>0'>

          <li @click='kankanxq(i.id,i.shopId,i.payStatus)' v-for="(i,index) of orderList" :key='index' >
            <table></table>
            <p class="ti">
              <span v-if="i.productType===0">【线上远程】</span>
            <span v-else-if="i.userType===0">【线下/个人】</span>
            <span v-else-if="i.userType===1">【线下/团体】</span> 
              <span>{{i.productName}}</span>
              <span :class="i.payStatus==0||i.totalNum-i.appointNum==0?'bu':''">
                {{i.payStatus==0||i.totalNum-i.appointNum==0?'不可用':'可用'}}
              </span>
            </p>
            <p class="cont">
              下单时间：{{i.createdDate | timeFormat}}
            </p>
            <p class='login_line'></p>
            <p class='yzf' v-if="i.payStatus==1">已支付：￥ {{i.price}} .00</p>
            <p class='yzf' v-if="i.payStatus==0">未支付：￥ {{i.price}} .00</p>
            <p class="las">
              <span>剩余可预约：{{i.totalNum - i.appointNum}}次</span>
              <span></span>
            </p>
          </li>

        </ul>
      <div class='nomore'>没有更多了</div>
      <!-- 全部结束 -->
    </van-tab>
    <van-tab title="可用" name="bb">
      <!-- 待预约开始 -->
      <div v-if='yesuse.length==0'>
        <div class="emp_yy">
          <img src="../assets/img/emp_yy.png">
          <p>暂无相关订单</p>
        </div>
        <div class="login_btn">
          <button><router-link to='/'>去购买</router-link></button>
        </div>
      </div>
      <ul class="home_list" v-else>
        <li @click='kankanxq(i.id,i.shopId,i.payStatus)' v-for="(i,index) of yesuse" :key='index'>
          <table></table>
          <p class="ti">
            <span v-if="i.productType===0">【线上远程】</span>
            <span v-else-if="i.userType===0">【线下/个人】</span>
            <span v-else-if="i.userType===1">【线下/团体】</span>
            <span>{{i.productName}}</span>
            <span>可用</span>
          </p>
          <p class="cont">
            下单时间：{{i.createdDate | timeFormat}}
          </p>
          <p class='login_line'></p>
          <p class='yzf' v-if="i.payStatus==1">已支付：￥ {{i.price}} .00</p>
          <p class='yzf' v-if="i.payStatus==0">未支付：￥ {{i.price}} .00</p>
          <p class="las">
            <span>剩余可预约：{{i.totalNum - i.appointNum}}次</span>
            <span></span>
          </p>
        </li>
      </ul>
      <!-- 待预约结束 -->
    </van-tab>
    <van-tab title="不可用" name="cc">
      <!-- 已预约开始 -->
      <div v-if='nouse.length==0'>
        <div class="emp_yy">
          <img src="../assets/img/emp_yy.png">
          <p>暂无相关订单</p>
        </div>
        <div class="login_btn">
          <button><router-link to='/'>去购买</router-link></button>
        </div>
      </div>

      <ul class="home_list" v-else>
        <li @click='kankanxq(i.id,i.shopId,i.payStatus)' v-for="(i,index) of nouse" :key='index' :data-num='i.id'>
          <table></table>
          <p class="ti">
            <span v-if="i.productType===0">【线上远程】</span>
            <span v-else-if="i.userType===0">【线下/个人】</span>
            <span v-else-if="i.userType===1">【线下/团体】</span> 
            <span>{{i.productName}}</span>
            <span class="bu">不可用</span>
          </p>
          <p class="cont">
            下单时间：{{i.createdDate | timeFormat}}
          </p>
          <p class='login_line'></p>
          <p class='yzf' v-if="i.payStatus==1">已支付：￥ {{i.price}} .00</p>
          <p class='yzf' v-if="i.payStatus==0">未支付：￥ {{i.price}} .00</p>
          <p class="las">
            <span>剩余可预约：{{i.totalNum - i.appointNum}}次</span>
            <span></span>
          </p>
        </li>
      </ul>
      <!-- 已预约结束 -->
    </van-tab>
  </van-tabs>


</div>
</template>

<style>
/* 头部样式 */
  .md{
    background: #e8e8e8;
    height: 550vw;
  }
  .md .tit .van-nav-bar{
      background: #000;
    }
  .md .tit .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
    color:#fff;
  }
/* 头部样式结束 */

/* 导航栏样式 */
  .md .van-tab--active,.md .van-tabs__line {
    color: #03C590;
  }
  .md .van-tabs__line {
    background-color:#03C590;
  }
  .md .van-tabs__nav{
    background: #e8e8e8 !important;
  }
/* 导航栏样式 */

/* 表格样式 */
  .md .home_list{
    width:90%;
    margin:5vw auto;
    list-style: none;
    position: relative;
  }
  .md .home_list li{
    width:98%;
    height:40vw;
    margin-bottom: 5vw;
    position: relative;
    background: #fff;
    border-radius: 0.6rem;
    padding:2vw 1vw;
    overflow: hidden;
  }
/* 表格样式 */

/* 预约详情 */
  .md .ti{
    margin-bottom: 0;
    margin-top: 2vw;
  }
  .md .ti span:last-child{
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
  .md .ti span.bu{
    background: #939393;
  }
  .md .cont{
    margin-top: 2vw;
    font-size: 0.8rem;
    margin-left: 2vw;
    color:#232323;
  }
  .md .yzf{
    margin-bottom: 2vw;
    margin-left: 2vw;
    font-size: 0.8rem;
    margin-top: 12vw;
  }
  .md .las{
    margin-bottom: 2vw;
    margin-left: 2vw;
    font-size: 0.8rem;
    margin-top: 0vw;
  }
  .md .las span:last-child{
    display: inline-block;
    width: 18vw;
    height: 5vw;
    text-align: center;
    line-height: 5vw;
    border-radius: 0.5rem;
    margin-left: 42vw;
    background: #fff;
    color: #fff;
  }
  .md .login_line{
    width:93%;
    border:1px solid rgba(216,216,216,1);
    position: absolute;
    top:37%;
    left: 3%;
  }
  
/* 预约详情 */

/* 空预约 */
.md .login_btn{
  margin:20vw auto;
  text-align:center;
}
.md .login_btn button{
  border:0;
  outline: 0;
  width:75vw;
  height:13vw;
  border-radius: 30px;
  background:rgb(102 205 170);
  color:#fff;
}
.md .emp_yy{
  margin:10vw auto;
  text-align: center;
}

.md .nomore{
  width:100%;
  height:10vw;
  line-height: 10vw;
  text-align: center;
  color:#939393;
}
</style>

<script>
import {mapState} from 'vuex';
export default {
  computed:{
    ...mapState([
        'iSlogin','username','iphone','id'
    ])
  },
  data() {
    return {
      activeName: 'aa',
      orderList:[],
      yesuse:[],
      nouse:[],
      uid:0
    };
  },
  methods:{
    nowyy(){
      // this.$router.push('/yy_succ');
    },
    mDclick(){
      this.$router.push('/');
    },
    kankanxq(orderId,shopId,payStatus){
      if(payStatus === 0) {
        return
      }
      if(typeof(orderId)==='number'){
        // console.log(num);
        this.$router.push({path:'/dd',query:{orderId:orderId,shopId:shopId}});
      }
    }
  },
  mounted(){
    // alert(1)
    this.uid=this.id;
    // 我的订单列表
    this.axios.get('api/frontdesk/orders/list?'+`userId=${this.uid}`).then(({data})=>{
      console.log(data)
      this.orderList=data.data;
      for(var key of this.orderList){
        if(key.payStatus===1 && (key.totalNum-key.appointNum)>0){
          this.yesuse.push(key);
        }else{
          this.nouse.push(key);
        }
      }
      // alert(JSON.stringify(this.yesuse))
      // alert(JSON.stringify(this.nosuse))
    })



  }
}
</script>

