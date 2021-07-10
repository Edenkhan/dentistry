<template>
<div class='duih'>
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar title="兑换码" left-text="返回" left-arrow  @click-left="Dclick"/>
  </div>

  <div class="emp_yy">
    <img src="../assets/img/duih.png">
    <p>兑换码</p>
  </div>

  <div class="dh_input">
    <van-field v-model="duiCode" placeholder="请输入兑换码"/>
    <p class='login_line'></p>
  </div>

  <div class="login_btn">
    <button @click='duih'>兑换</button>
  </div>

  <div class='choys'>
    <van-picker title="请选择您的医生" show-toolbar
    :columns="columns" @confirm="onConfirm"
    @cancel="ysshow=false" v-show='ysshow' />
  </div>
  

</div>
</template>

<style scoped>
.duih .tit .van-nav-bar{
    background: #000;
  }
.duih .tit .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
  color:#fff;
}
.duih .login_btn{
  margin:15vw auto;
  text-align:center;
}
.duih .login_btn button{
  border:0;
  outline: 0;
  width:75vw;
  height:13vw;
  border-radius: 30px;
  background:rgb(102 205 170);
  color:#fff;
}
.duih .emp_yy{
  margin:20vw auto;
  text-align: center;
}
.duih .login_line{
  width:76%;
  border:1px solid rgba(216,216,216,1);
  position: absolute;
  top:50%;
  left: 12%;
}
.duih .van-cell{
  padding:10px 50px;
}
.duih .van-cell::after{
  border:0;
}
.duih /deep/.van-field__control{
  letter-spacing: 0.5rem;
  font-size: 1.1rem;
}
.duih .choys{
  width:100%;
  position: fixed;
  bottom: 0;
}
</style>

<script>
import qs from 'qs'
import {mapState} from 'vuex';
export default {
  computed:{
    ...mapState([
        'username','iphone','id','sex'
    ])
  },
  data(){
    return {
      ysshow:false,
      duiCode:'',
      columns: [],
    }
  },
  methods:{
    duih(){
      if(this.duiCode.trim()==''){
        this.$toast('请输入正确的兑换码!');
      }else{
        // 发送请求
        this.axios.get('api/work/redeemCode/getProductType?'+`code=${this.duiCode}`).then(res=>{
          console.log(res.data);
          // 如果是远程弹起医生选择
          if(res.data.product.type==0){
            this.ysshow=true;
          }else{
            // 直接兑换成功
            this.axios.post('api/work/redeemCode/edit',qs.stringify({code:this.duiCode,userId:this.id})).then(res=>{
              console.log(res.data);
              this.$router.push({path:'/succ',query:{id:3}})
            })   
          }
        })

      }
    },
    Dclick(){
      this.$router.push('/me');
    },
    onConfirm(value, index) {
      // this.$toast(`当前值：${value}, 当前索引：${index}`);
      this.axios.post('api/work/redeemCode/edit',qs.stringify({code:this.duiCode,userId:this.id,doctor:index+14})).then(res=>{
        console.log(res.data);
      })
      // this.$router.push({path:'/succ',query:{id:3}})
    },
  },
  mounted(){
    // 获取医生信息
    this.axios.get('api/platform/orders/getDoctor').then(res=>{
      // console.log(res.data);
      for(let key of res.data){
        this.columns.push(key.title);
      }
      // console.log(this.columns);
    })
  }
}
</script>
