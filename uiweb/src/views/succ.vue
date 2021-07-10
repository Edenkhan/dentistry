<template>
<div class='succ'>
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar :title="succ[num].title"  left-text="首页" left-arrow  @click-left="succclick"/>
  </div>

  <div class="emp_yy">
    <img src="../assets/img/succ.png">
    <p>{{succ[num].content}}</p>
    <p>{{succ[num].xq==''?iphone:succ[num].xq}}</p>
  </div>
  <div class="login_btn">
    <button @click='jumprou'>{{succ[num].btn}}</button>
  </div>

</div>
</template>

<style scoped>
.succ .tit .van-nav-bar{
    background: #000;
  }
.succ .tit .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
  color:#fff;
}
.login_btn{
  margin:20vw auto;
  text-align:center;
}
.succ .login_btn button{
  border:0;
  outline: 0;
  width:75vw;
  height:13vw;
  border-radius: 30px;
  background:rgb(102 205 170);
  color:#fff;
}
.succ .emp_yy{
  margin:20vw auto;
  text-align: center;
}
.succ .emp_yy p:last-child{
  margin-top: -3vw;
  color:#939393;
  font-size: 0.8rem;
  letter-spacing: 0.1rem;
}
</style>

<script>
import {mapState} from 'vuex';
export default {
  computed:{
    ...mapState([
        'iphone'
    ])
  },
  data(){
    return {
      num:0,
      succ:[
        {
          title:'支付完成',content:'购买成功',
          xq:'我们的工作人员将尽快与您电话联系，请耐心等待',btn:'立即预约'
        },
        {
          title:'支付完成',content:'购买成功',
          xq:'我们的工作人员将尽快与您电话联系，请耐心等待',btn:'完成'
        },
        {
          title:'绑定手机号',content:'您已成功绑定手机',
          xq:'',btn:'去修改'
        },
        {
          title:'修改手机',content:'恭喜，您已成功绑定手机',
          xq:'',btn:'完成'
        },
        {
          title:'兑换成功',content:'兑换成功',
          xq:'兑换成功',btn:'立即预约'
        },
        {
          title:'预约成功',content:'预约成功',
          xq:'',btn:'完成'
        }
      ],
      dingid:0,
      proid:0,
    }
  },
  mounted(){
    this.num=this.$route.query.id;

    this.dingid=this.$route.query.dingid;
    this.proid=this.$route.query.proid;

    
    if(this.$route.query.notfar){
      this.num=1;
    }
  },
  methods:{
    jumprou(e){
      if(e.target.innerText=='完成'){
        this.$router.push('/');
      }else if(e.target.innerText=='去修改'){
        this.$router.push('/xp');
      }else if(e.target.innerText=='立即预约'){
        this.$router.push({path:'/yym',query:{dingid:this.dingid,proid:this.proid}});
      }
    },
    succclick(){
      this.$router.push('/');
    }
  }
}
</script>
