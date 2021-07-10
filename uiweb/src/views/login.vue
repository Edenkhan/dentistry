<template>
<div>
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar title="法赫光学" left-text="返回" left-arrow @click-left="lGclick"/>
  </div>
  <!-- 登录注册 -->
  <p class='login_text'>登录/注册</p>
  <!-- 手机号输入 -->
  <div class='line_f'>
    <div class="login_yz">
      <van-form validate-first>
      <van-field
        v-model="phoneNum"
        name="validator"
        placeholder="请输入手机号"
        :rules="[{ validator, message: '请正确填写手机号' }]" maxlength="11"
      />
      </van-form>
    </div>
    <p class='login_line'></p>
  </div>
  <!-- 发送按钮 -->
  <div class="login_btn">
    <button :disabled='isDis' @click='fs' :class='isDis?"jy":"jf"'>发送手机验证码</button>
    <p>点击代表您已阅读并同意以下协议</p>
    <p class='p_ys'>
      <span>《xxx服务使用协议》</span>
      <span>《隐私政策》</span>
    </p>
  </div>

  <!-- 验证码开始 -->
  <van-overlay :show="show" @click="show = false">

    <div class="yzm_wrapper" @click.stop>
      <div class="yzm_block">
        <p>请输入图形验证码</p>
        <van-form validate-first>
          <van-field
          v-model="yzm"
          name="pattern"
          :rules="[{pattern, message: '验证码错误,请重新输入' }]" maxlength="4"
          />
          <div class="imgc">
            <img :src="verifySrc" @click="changeCode" alt="">
          </div>
          <p class='login_line'></p>

        </van-form>
      </div>
    </div>
  </van-overlay>
  <!-- 验证码结束 -->

</div>
</template>

<style>
/* 基本样式开始 */
  .login_text{
    width:40vw;
    height:10vw;
    font-weight: bold;
    padding-left: 10vw;
    margin-top: 10vw;
  }
  .login_yz{
    height:20vw;
    padding-left: 6vw;
    font-weight: 600;
    margin-top: 25vw;
  }
  .line_f{
    position: relative;
  }
  .login_line{
    width:80%;
    border:1px solid rgba(216,216,216,1);
    position: absolute;
    top:25%;
    left: 10%;
  }
  .line_f .van-field__control{
    margin-left: 3vw;
    margin-right: 5vw;
    font-size: 1.4rem;
    letter-spacing:0.5rem;
    text-indent:0.2rem;
  }
  .login_yz .van-field__error-message{
    font-size: 16px;
    margin-top: 3vw;
    margin-left: 3vw;
    letter-spacing: 0.5rem;
  }
  .login_btn{
    margin:20vw auto;
    text-align:center;
  }
  .login_btn button{
    border:0;
    outline: 0;
    width:75vw;
    height:13vw;
    border-radius: 30px;
    background:rgb(118 238 198);
    color:#fff;
  }
  .login_btn p{
    font-size: 0.5rem;
  }
  .p_ys{
    color:rgb(102 205 170);
  }
  .jy{
    opacity: 0.5;
  }
  .jf{
    opacity: 1;

  }
  .tit .van-nav-bar{
    background: #000;
  }
  .tit .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
    color:#fff;
  }
/* 基本样式结束 */

/* 验证码开始 */
  .yzm_wrapper {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
  }
  .yzm_block {
    width: 70%;
    height: 35vw;
    background-color: #fff;
    border-radius:0.8rem;
    position: relative;
  }
  .yzm_block p{
    margin-top: 5vw;
    text-align: center;
  } 
  .yzm_block .imgc{
    position: absolute;
    top:45%;
    right:6%;
    overflow: hidden;
    width:20vw;
  }
  .imgc img{
    width:100%;
  }
  .yzm_block .login_line{
    width:90%;
    top:52%;
    left:5%;
  }
  .yzm_block .van-field__error-message{
    letter-spacing: 0.1rem;
    text-align: center;
    margin-top: 2vw;
  }
  .yzm_block .van-field__control{
    font-size: 1.4rem;
    letter-spacing: 0.1rem;
  }
/* 验证码结束 */


</style>

<script>
import qs from 'qs'
export default {
  data(){
    return {
      phoneNum:'',
      yzm:'',
      isDis:true,
      show:false,
      pattern: /\d{4}/,
      verifySrc: 'api/registeredUser/getVerify',
    }
  },
  created() {
    this.changeCode()
  },
  methods:{
    // 换验证码图片
    changeCode() {
      this.verifySrc = 'api/registeredUser/getVerify?' + new Date().getTime()
    },
    // 检测是否输入正确手机号
      validator(val) {
        if(/^1[3456789]\d{9}$/.test(val)){
          this.isDis=false;
        }else{
          return false;
        }
      },
    // 发送验证码
    fs(){
      if(/^1[3456789]\d{9}$/.test(this.phoneNum)){
        this.show=true;
      }else{
        this.$toast('手机号错误');
      }
    },
    lGclick(){
      this.$router.push('/me');
    },
    
  },
  watch:{
    phoneNum(){
      if(this.phoneNum.length==11){
        this.isDis=false;
      }
    },
    yzm(){
      // 图片验证码检验
      this.axios.post('api/registeredUser/checkVerify',qs.stringify({verifyCode:this.yzm}))
          .then(({data})=>{
        console.log(data);
        if(data.passed){
          this.$router.push({path:'/sjyz',query:{
            phone:this.phoneNum
          }})
        }
      })

    }
  },
  mounted(){
    // 发送验证码
    // this.axios.post('/registeredUser/sendVerifyCode',`phoneNumber=${this.phoneNum}`).then(res=>{
    //   console.log(res.data);
    // })

    //注册
    // this.axios.post('/registeredUser/register',`phoneNumber=${电话号码}&verifyCode=${验证码}`).then(res=>{
    //   console.log(res.data);
    // })
  }
}
</script>


