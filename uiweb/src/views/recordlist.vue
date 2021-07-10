<template>
<div class="rl">
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar title="报告列表"/>
  </div>

  <p class='login_text'>共10份</p>

  <van-cell title="王二麻子" is-link to='/mr'/>
  <van-cell title="铁憨憨" is-link to='/mr'/>
  <van-cell title="铁憨憨" is-link to='/mr'/>
  <van-cell title="铁憨憨" is-link to='/mr'/>
  <van-cell title="铁憨憨" is-link to='/mr'/>

</div>
</template>

<style scoped>
.rl{
  width:100%;
  height:177.7vw;
  background: #e8e8e8;
}
.rl .tit .van-nav-bar{
    background: #000;
  }
.rl .tit .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
  color:#fff;
}

.rl .login_text{
  font-size: 1.3rem;
  width:40vw;
  height:10vw;
  font-weight: bold;
  padding-left:4vw;
  margin-top: 5vw;
}



</style>

<script>
import {mapState} from 'vuex';
export default {
  computed:{
    ...mapState([
        'username','iphone','id','sex'
    ])
  },
  methods:{
    
  },
  mounted(){
    this.axios.get('api/work/report/getByUserId?'+`userId=${this.id}`).then(res=>{
      console.log(res.data);
    })
  }
}
</script>

