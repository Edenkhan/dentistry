<template>
<div>
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar title="法赫光学" left-text="返回" left-arrow @click-left="sJclick"/>
  </div>

  <p class='login_text'>请输入手机验证码</p>

  <div class="xhx">
    <van-form validate-first>
      <van-field
        v-model="phoneyz"
        name="validator"
        maxlength="6"
        :rules="[{ validator, message: '重新发送' }]"
      />
    </van-form>
    <ul class="fgx">
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
    </ul>
    <p class="tssj" :style='ischange?"display:block":"display:none"'>剩余时间:{{time}}s</p>
    <p class="cxfs" :style='ischange?"display:none":"display:block"' @click='cxfs'>重新发送</p>

    <div class="ts" :style='ischange?"    visibility: hidden":"visibility: visible"'>验证码错误</div>

    <!-- 确认按钮 -->
    <div class="login_btn">
      <button @click='qr' :class='ischange?"jf":"jy"'>确认</button>
    </div>
  </div>



</div>
</template>

<style>
/* 顶部标题 */
  .tit .van-nav-bar{
    background: #000
  }
  .tit .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
    color:#fff
  }
/* 手机验证码文字 */
  .login_text{
      width:40vw;
      height:10vw;
      font-weight: bold;
      padding-left: 5vw;
      margin-top: 10vw;
  }
/* 下划线 */
.xhx{
  width:90%;
  margin:0 auto;
  position: relative;
}
.fgx{
  list-style: none;
  width:95%;
  display: flex;
  justify-content: space-around;
  margin:0 auto
}
.fgx li{
  border:1px solid rgba(216,216,216,1);
  width:8%
}
.xhx .van-field__control{
    font-size: 1.4rem;
    letter-spacing:3rem;
    text-indent:1rem
}
.cxfs,.tssj{
  margin-left: 5vw;
  color:#000;
  opacity: 0.5
}
.cxfs{
  opacity: 1
}
.ts{
  margin:0 auto;
  font-size: 1.1rem;
  color:#f00;
  width:25%
}

.xhx .login_btn{
    margin:10vw auto;
    text-align:center
}

.jy{
    opacity: 0.5
}
.jf{
  opacity: 1

}

</style>

<script>
import {mapMutations} from 'vuex'
export default {
  data(){
    return {
      phoneyz:'',
      ischange:true,
      time:120,
      phone:''
    }
  },
  methods:{
    ...mapMutations([
			'logined'
		]),
    validator(val) {
      if(/\w{6}/.test(val)){
        this.ischange=true
      }else{
        this.ischange=false
      }
    },
    qr(){
      // 注册
      this.axios.post('api/registeredUser/register',`phoneNumber=${this.phone}&verifyCode=${this.phoneyz}`)
          .then(({data})=>{
          this.logined({id:data.id})
          sessionStorage.setItem('id',data.id)

          if(this.phoneyz.trim()==''){
            this.$toast.fail('验证码错误')
          }else{
            // 在vuex里面改变登录状态
            sessionStorage.setItem('iSlogin',true)
            // 保存手机号
            let newpt=this.phone.slice(0,3)
            let newpw=this.phone.substring(this.phone.length-4)
            let newph=`${newpt}****${newpw}`
            this.logined({iphone:newph})
            sessionStorage.setItem('iphone',newph)
            // 在vuex里面改变登录状态
            this.$toast.success('登陆成功')
            this.$router.push('/')
            // location.reload()
          }

      })


    },
    cxfs(){
      this.$notify({ type: 'success', message: 'ssssss' })
      this.pcheck()
      this.ischange=true
      this.time=120
      var timer=setInterval(()=>{
        if(this.time==0){
          clearInterval(timer)
          this.ischange=false
        }else{
          this.time--
        }
      },1000)
    },
    sJclick(){
      this.$router.push('/login')
    },
    // 发送手机验证码
    pcheck(){
      // 发送验证码
      this.axios.post('api/registeredUser/sendVerifyCode',`phoneNumber=${this.phone}`)
          .then(res=>{
          console.log(res.data)
      })
    }
  },
  mounted(){
    this.phone=this.$route.query.phone

    this.$notify({ type: 'success', message: '验证码已发送' })
    var timer=setInterval(()=>{
      if(this.time==0){
        clearInterval(timer)
        this.ischange=false
      }else{
        this.time--
      }
    },1000)


    // 发送手机验证码
    this.pcheck()

  }
}
</script>









