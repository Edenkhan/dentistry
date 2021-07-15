<template>
<div>
<div class="me" v-if='islogin'>
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar title="个人中心"/>
  </div>

  <div class="grbg">
    <div class="grmb">
      <img :src="img"><span @click='tiaologin'>{{iSlogin?content2:content}}</span><span class='dhdw' :style="iSlogin?'display:block':'display:none'">{{phoneNumber}}</span><span><van-icon name="arrow" /></span>
    </div>
  </div>

  <div class="wddd">
    <table></table>
    <ul class="wd_list">
      <li @click='go_ding'><img src="../assets/img/dd.png"> 我的订单<span><van-icon name="arrow" /></span></li>
      <li @click='go_bao'><img src="../assets/img/baogao.png"> 我的报告<span><van-icon name="arrow" /></span></li>
    </ul>
  </div>

  <div class="wddd2">
    <table></table>
    <ul class="wd_list">
      <li @click='go_dui'><img src="../assets/img/ddh.png"> 兑换码<span><van-icon name="arrow" /></span></li>
      <li @click='lxkf'><img src="../assets/img/kf.png"> 联系客服<span><van-icon name="arrow" /></span></li>
    </ul>
  </div>
  
</div>

<div class="relme" v-else>
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar title="个人中心"/>
  </div>

  <div class="grbg" @click='tiaologin'>
    <div class="grtx">
      <img :src="avatar">
    </div>  
    <div class="gexx">
      <p>{{uname }} <span><img src="../assets/img/nan.png" v-if='gender==1'><img src="../assets/img/nv.png" v-else></span></p>
      <p>{{phoneNumber}}</p>
    </div>
    <span class='grjt'><van-icon name="arrow" /></span>
  </div>

  <div class="wddd3">
    <table></table>
    <ul class="wd_list">
      <li @click='go_ding2'><img src="../assets/img/dd3.png"> 我的订单<span><van-icon name="arrow" /></span></li>
      <li @click='go_bao2'><img src="../assets/img/dd2.png"> 我的报告<span><van-icon name="arrow" /></span></li>
      <li @click='go_dui2'><img src="../assets/img/ddh2.png"> 兑换码<span><van-icon name="arrow" /></span></li>
      <li @click='lxkf'><img src="../assets/img/kf2.png"> 联系客服<span><van-icon name="arrow" /></span></li>
    </ul>
  </div>

</div>

<!-- 底部标签开始 -->
<van-tabbar v-model="active">
  <van-tabbar-item name="home" icon="wap-home-o" @click='home'>
    首页
  </van-tabbar-item>
  <van-tabbar-item name="me" icon="manager-o">
    我的
  </van-tabbar-item>
</van-tabbar>
<!-- 底部标签结束 -->

</div>
</template>

<style scoped>
.me,.relme{
  background: #e8e8e8;
  height:177.7vw;
}
.me .van-nav-bar,.relme .van-nav-bar{
  background: #000;
}
.me .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
  color:#fff;
}
.relme .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
  color:#fff;
}
.grbg{
  height:30vw;
  background: url(../assets/img/grbg.png) no-repeat;
  background-size: cover;
  position: relative;
}
.grmb{
  position: absolute;
  width:90%;
  height:24vw;
  background: #fff;
  bottom:-15%;
  left:5%;
  border-radius: 0.8rem;
  box-shadow:0px 5px 18px 0px rgba(35,35,35,0.18);
}
.grmb img{
  margin-top: 5vw;
  margin-left: 5vw;
  width:15%;
  vertical-align: middle;
}
.grmb span{
  width:40%;
  height:10vw;
  display: inline-block;
  padding-top: 5vw;
  position: absolute;
  top:17%;left: 27%;
  text-align: center;
  font-weight: bold;
  font-size: 1.1rem;
}
.grmb span:last-child{
  position: absolute;
  top:20%;left: 65%;
}
.relme .grbg{
  width:90%;
  margin:5% auto;
}

.wddd{
  height:33vw;
  background: #fff;
}
.wddd2{
  height:33vw;
  margin-top: 2vw;
  background: #fff;
}
.wddd3{
  height:60vw;
  margin-top: 2vw;
  background: #fff;
}
.wd_list{
  width:90%;
  margin:0 auto;
  margin-top: 10vw;
}
.wd_list li{
  height:6vw;
  padding-left:2vw ;
  margin-bottom: 5vw;
  position: relative;
}
.wd_list li span{
  position: absolute;
  right:2%;top:8%;
}
.wd_list li img{
  vertical-align: middle;
}
.van-tabbar-item--active{
  color:#03C590;
}
.grmb span.dhdw{
  margin:0;padding:0;
  width:20%;
  height:5vw;
  top:62%;
  left:29%;
  font-size: 0.5rem;
}

.grtx{
  height:20vw;
  width:20vw;
  overflow: hidden;
  position: absolute;
  top:17%;left:15%;
}
.grtx img{
  width:100%;
}
.gexx{
  position: absolute;
  top:12%;left:45%;
  color:#fff;
}
.gexx img{
  vertical-align: middle;
}
.gexx p:last-child{
  margin-top: 5vw;
  font-size: 0.8rem;
}
.grjt{
  position:absolute;
  top:45%;
  right:10%;
  color:#fff;
}


</style>

<script>
import {mapState} from 'vuex';
export default {
  computed:{
    ...mapState([
        'iSlogin','realName','phoneNumber','avatar','gender'
    ])
  },
  data(){
    return {
      active: 'me',
      isLogin:false,
      islogin:true,
      content:'请登录',
      content2:'请完善个人信息',
      uname:'',
    }
  },
  created() {
    this.fetch()
  },
  methods:{
    fetch() {
      this.axios.get('/api/registeredUser/get')
          .then(({data}) => {
            // alert(JSON.stringify(data))
            sessionStorage.setItem('id',data.id)
            sessionStorage.setItem('iSlogin',true)
            sessionStorage.setItem('avatar',data.avatar)
            sessionStorage.setItem('realName',data.realName)
            sessionStorage.setItem('gender',data.gender)
            sessionStorage.setItem('phoneNumber',data.phoneNumber)
          })
    },
    home(){
      this.$router.push('/');
      location.reload();
    },
    lxkf(){
      this.$dialog.confirm({
        title:'拨打客服电话？',
        message: '400-820-888',
        confirmButtonText:'拨打电话',
        confirmButtonColor:"#05C58F"
      })
      .then(() => {
      })
      .catch(() => {
      });
    },
    go_dui2(){
      this.$router.push('/dui');
    },
    go_dui(){
      if(!this.isLogin){
        this.$dialog.confirm({
          title:'您还未登陆',
          message: '是否立即登录',
          confirmButtonText:'是',
          confirmButtonColor:"#05C58F"
        })
        .then(() => {
          this.$router.push('/login');
        }).catch(()=>{})
      }else{
        this.$router.push('/dui');
      }
    },
    go_bao2(){
      this.$router.push('/mr');
    },
    go_bao(){
      if(!this.isLogin){
        this.$dialog.confirm({
          title:'您还未登陆',
          message: '是否立即登录',
          confirmButtonText:'是',
          confirmButtonColor:"#05C58F"
        })
        .then(() => {
          this.$router.push('/login');
        }).catch(()=>{})
      }else{
        this.$router.push('/mr');
      }
    },
    go_ding2(){
      this.$router.push('/md');
    },
    go_ding(){
      if(!this.isLogin){
        this.$dialog.confirm({
          title:'您还未登陆',
          message: '是否立即登录',
          confirmButtonText:'是',
          confirmButtonColor:"#05C58F"
        })
        .then(() => {
          this.$router.push('/login');
        }).catch(()=>{})
      }else{
        this.$router.push('/md');
      }
    },
    tiaologin(e){
      if(e.target.innerText=='请登录'){
        this.$router.push('/login');
      }else{
        this.$router.push('/zs');
        location.reload();
      }
    }
  },
  mounted(){
    // alert(this.gender)
    this.isLogin=this.iSlogin;

    if(this.realName!=''){
      this.islogin=false;
      this.uname=this.realName;
    }
  }
}
</script>

