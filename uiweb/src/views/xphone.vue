<template>
<div class="xp">
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar title="修改手机" left-text="返回" left-arrow  @click-left="xpclick"/>
  </div>

  <p class='login_text text1'>请绑定</p>
  <p class='login_text text2'>新手机号码</p>

  <div class="qsr">
    <van-cell-group>
      <van-field v-model="xvalue2" placeholder="请输入新手机号" maxlength="11"/>
    </van-cell-group>
  </div>

  <div class="qsr">
    <van-cell-group>
      <van-field v-model="xvalue" placeholder="验证码" maxlength="6"/>
    </van-cell-group>
    <span @click='aggetyz'>获取验证码（<van-count-down :time="time" format="ss 秒" @finish='jswc'/>）</span>
  </div>

  <div class="login_btn">
    <button @click='judgex'>确定</button>
  </div>
</div>
</template>

<style scoped>
.xp .tit .van-nav-bar{
  background: #000;
}
.xp .tit .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
  color:#fff;
}
.xp .login_text{
  padding:0;margin:0;
  width:40vw;
  height:5vw;
  font-weight: bold;
  margin-left:3vw;
}
.xp .login_text.text1{
  margin-top:5vw;
}
.xp .login_text.text2{
  margin-bottom:10vw;
}
.xp .login_text.text3{
  color:#939393;
  font-size: 0.8rem;
}

.xp .qsr{
  position: relative;
  margin-top: 5vw;
}
.xp .qsr span{
  font-size: 0.75rem;
  color:#939393;
  position: absolute;
  right:5%;top:30%;
}
.xp .qsr span{
  font-size: 0.75rem;
  color:#939393;
  position: absolute;
  right:5%;top:30%;
}
.xp .qsr span.yzz{
  width:19vw;height:7vw;
  position: absolute;
  right:8%;top:15%;
  background: url(../assets/img/yzm.png) no-repeat;
  ;
  background-size: contain;
}
.xp .qsr span .van-count-down{
  display: inline-block;
  font-size: 0.8rem;
  color:#939393;
}




.xp .login_btn{
  margin:20vw auto;
  text-align:center;
}
.xp .login_btn button{
  border:0;
  outline: 0;
  width:75vw;
  height:13vw;
  border-radius: 30px;
  background:rgb(102 205 170);
  color:#fff;
}
</style>

<script>
import qs from 'qs'
import {mapState} from 'vuex';
import {mapMutations} from 'vuex';
export default {
  computed:{
    ...mapState([
        'id'
    ])
  },
  data() {
    return {
      xvalue: '',
      xvalue2: '',
      time:0,
      uid:0
    };
  },
  methods:{
    ...mapMutations([
			'logined'
		]),
    jswc(){
      this.$toast('请重新获取验证码');
    },
    judgex(){
      if(this.xvalue2.trim()==''){
        this.$toast('手机号不能为空');
      }else if(!(/^1[3-9]\d{9}$/.test(this.xvalue2))){
        this.$toast('手机号格式错误');
      }else if(this.xvalue.trim()==''){
        this.$toast('验证码为空');
      }else{
        // 更改手机号
        let newpt=this.xvalue2.slice(0,3);
        let newpw=this.xvalue2.substring(this.xvalue2.length-4);
        let newph=`${newpt}****${newpw}`;
        this.logined({iphone:newph});
        sessionStorage.setItem('iphone',newph);

        // 新手机验证
        this.axios.post('api/registeredUser/changePhone',qs.stringify({id:this.uid,phoneNumber:this.xvalue2,verifyCode:this.xvalue})).then(res=>{
          console.log(res.data);
        })

        this.$router.push({path:'/succ',query:{id:2}})

      }
    },
    xpclick(){
      this.$router.push('/cp');
    },
    aggetyz(){
      if(this.xvalue2==''){
        this.$toast('手机号不能为空');
      }else{
        this.time=1*60*1000;
        this.pcheck();
      }
    },
    pcheck(){
      // 发送验证码
      this.axios.post('api/registeredUser/sendVerifyCode',qs.stringify({phoneNumber:this.xvalue2})).then(res=>{
          console.log(res.data);
      })
    }
  },
  mounted(){
    this.uid=this.id;
    console.log(this.uid);
  }
  
}
</script>
