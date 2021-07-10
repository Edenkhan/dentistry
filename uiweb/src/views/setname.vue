<template>
<div class='sm'>
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar title="编辑姓名"  left-text="返回" left-arrow  @click-left="nameclick"/>
  </div>

  <p class='login_text'>姓名</p>

  <van-cell-group>
    <van-field v-model="realName" placeholder="请输入您的姓名" />
  </van-cell-group>
  
  <div class="login_btn">
    <button @click='qddown'>确定</button>
  </div>

</div>
</template>

<style scoped>
.sm .tit .van-nav-bar{
    background: #000;
  }
.sm .tit .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
  color:#fff;
}
.login_btn{
  margin:20vw auto;
  text-align:center;
}
.sm .login_btn button{
  border:0;
  outline: 0;
  width:75vw;
  height:13vw;
  border-radius: 30px;
  background:rgb(102 205 170);
  color:#fff;
}

.sm /deep/.van-field__control{
  text-align: center;
  font-size: 1.2rem;
}


</style>

<script>
import qs from 'qs'
import {mapState} from 'vuex'
import {mapMutations} from 'vuex'
export default {
  computed:{
    ...mapState([
        'iSlogin','realName','iphone','id'
    ])
  },
  data(){
    return {
      realName:''
    }
  },
  methods:{
    ...mapMutations([
			'logined'
		]),
    qddown(){
      if(this.realName.trim()==''){
        this.$toast('不能为空');
      }else{
        this.logined({realName:this.realName});
        sessionStorage.setItem('realName',this.realName);

        // 修改用户姓名
        this.axios.post('api/registeredUser/edit',qs.stringify({id:this.id,realName:this.realName}))

        this.$router.push('/me');
        location.reload();
      }
    },
    nameclick(){
      this.$router.push('/zs');
    }
  },
  mounted(){
    
  }
}
</script>