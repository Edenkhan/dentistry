<template>
<div class="pw">
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar title="完善资料"/>
  </div>

  <div class="pw_c">
    <table></table>
    <p class='login_text'>姓名</p>
    <div class="pwuname">
      <van-field
        v-model="pwuname"
        error-message='姓名不能为空'
        required
        placeholder="请输入您的姓名"
      />
      <p class='login_line'></p>
    </div>

    <p class='login_text'>性别</p>
    <div class="pwsex">
      <van-radio-group v-model="radio">
        <van-cell-group>
          <van-cell title="男" clickable @click="radio = '1'">
            <template #right-icon>
              <van-radio name="1" />
            </template>
          </van-cell>
          <van-cell title="女" clickable @click="radio = '0'">
            <template #right-icon>
              <van-radio name="0" />
            </template>
          </van-cell>
        </van-cell-group>
      </van-radio-group>
    </div>

    <div class="login_btn">
      <button @click='tjwszl'>确认</button>
    </div>

    <div></div>
  </div>

</div>
</template>

<style scoped> 
.pw{
  background: #e8e8e8;
  height:177.7vw;
}
.pw .van-nav-bar{
  background: #000;
}
.pw .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
  color:#fff;
}
.pw .login_text{
  width:40vw;
  height:10vw;
  font-weight: bold;
  padding-left: 5vw;
  margin-top: 10vw;
}
.pw_c{
  background: #fff;
}
.pwuname{
  padding-left: 0.2rem;
  margin-top: -2vw;
  position: relative;
}
.pwuname>>>.van-field__control{
  font-size: 1rem;
}
.pwuname .login_line{
  width:90%;
  border:1px solid rgba(216,216,216,1);
  position: absolute;
  top:25%;
  left: 5%;
}
.pw_c .van-cell::after{
  border-bottom: 0;
}
.pw_c .pwsex .van-cell{
  font-size: 1rem;
  padding-left:5vw;
}
.pw_c .login_btn{
  margin:0vw auto;
  text-align:center;
  margin-top: 25vw;
}
.pw_c .login_btn button{
  border:0;
  outline: 0;
  width:75vw;
  height:13vw;
  border-radius: 30px;
  background:rgb(102 205 170);
  color:#fff;
}
.pw_c div:last-child{
  height:10vw;
}
</style>

<script>
import {mapMutations} from 'vuex';
import {mapState} from 'vuex';
export default {
  computed:{
    ...mapState([
        'username','iphone','id','sex'
    ])
  },
  data() {
    return {
      radio: '1',
      pwuname:''
    };
  },
  methods:{
    ...mapMutations([
			'logined'
		]),
    tjwszl(){
      if(this.pwuname==''){
        this.$toast('姓名不能为空')
      }else{
        // 更改性别
        this.logined({sex:this.radio});
        sessionStorage.setItem('sex',this.radio);
        // 更改姓名
        this.logined({username:this.pwuname});
        sessionStorage.setItem('username',this.pwuname);

        this.$router.push('./wszl');
      }
    }
  },
  mounted(){
    
  }
}
</script>