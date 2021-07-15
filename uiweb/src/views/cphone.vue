<template>
<div class="cp">
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar title="修改手机" left-text="返回" left-arrow  @click-left="cpclick"/>
  </div>

  <p class='login_text text1'>请验证</p>
  <p class='login_text text2'>原绑定手机</p>

  <p class='login_text text3'>
    验证码将发送至您手机</p>
  <p class='login_text text3'>{{iphone}}</p>

  <div class="qsr">
    <van-cell-group>
      <van-field v-model="qvalue" placeholder="验证码" maxlength="6"/>
    </van-cell-group>
    <span @click='aggetyz'>重新获取（<van-count-down :time="time" format="ss 秒" @finish='jswc'/>）</span>
  </div>

  <div class="qsr">
    <van-cell-group>
      <van-field v-model="qvalue2" placeholder="请输入图片验证码" maxlength="4"/>
    </van-cell-group>
    <span class="yzz">
      <div class="imgc">
        <img :src="verifySrc" @click="changeCode" alt="">
      </div>
    </span>
  </div>


  <div class="login_btn">
    <button @click='judge'>下一步</button>
  </div>
</div>
</template>

<style scoped>
.cp .tit .van-nav-bar{
  background: #000;
}
.cp .tit .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
  color:#fff;
}
.cp .login_text{
  padding:0;margin:0;
  width:40vw;
  height:5vw;
  font-weight: bold;
  margin-left:3vw;
}
.cp .login_text.text1{
  margin-top:5vw;
}
.cp .login_text.text2{
  margin-bottom:10vw;
}
.cp .login_text.text3{
  color:#939393;
  font-size: 0.8rem;
}

.qsr{
  position: relative;
  margin-top: 5vw;
}
.qsr span{
  font-size: 0.75rem;
  color:#939393;
  position: absolute;
  right:5%;top:30%;
}
.qsr span{
  font-size: 0.75rem;
  color:#939393;
  position: absolute;
  right:5%;top:30%;
}
.qsr span.yzz{
  width:19vw;height:7vw;
  position: absolute;
  right:8%;top:15%;
  overflow: hidden;
}
.qsr span .van-count-down{
  display: inline-block;
  font-size: 0.8rem;
  color:#939393;
}




.cp .login_btn{
  margin:20vw auto;
  text-align:center;
}
.cp .login_btn button{
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
export default {
  computed:{
    ...mapState([
        'phoneNumber'
    ])
  },
  data() {
    return {
      qvalue: '',
      qvalue2: '',
      time:1*60* 1000,
      myphone:'',
      verifySrc: '',
      currPhoneNumber: null,
    };
  },
  created() {
    this.currPhoneNumber = sessionStorage.getItem('phoneNumber')
  },
  methods:{
    // 换验证码图片
    changeCode() {
      this.verifySrc = 'api/registeredUser/getVerify?' + new Date().getTime()
    },
    jswc(){
      this.$toast('请重新获取验证码');
    },
    judge(){
      if(this.qvalue.trim()==''){
        this.$toast('验证码为空');
      }
      if(this.qvalue2.trim()==''){
        this.$toast('图形验证码为空');
      }

      this.axios.post('api/registeredUser/checkOldPhone',qs.stringify({verifyCode:this.qvalue})).then(() => {
        // 图片验证码检验
        this.axios.post('api/registeredUser/checkVerify',qs.stringify({verifyCode:this.qvalue2})).then(({data})=>{
          // alert(data.passed)
          if(data.passed){
            this.$router.push('/phonesucc')
          }else{
            this.$toast('验证码错误')
          }
        })
      }).catch(({message}) => {
        this.$toast(message)
      })



    },
    cpclick(){
      this.$router.push('/zs');
    },
    aggetyz(){
      this.time=1*60*1000;
      this.pcheck();
    },
    pcheck(){
      // 发送验证码
      this.axios.post('api/registeredUser/sendVerifyCode',qs.stringify({phoneNumber:this.currPhoneNumber})).then(res=>{
          console.log(res.data);
      })
    }
  },
  mounted(){
    this.pcheck();
    this.changeCode()
  }
}
</script>
