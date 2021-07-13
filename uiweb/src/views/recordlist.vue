<template>
<div class="rl">
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar title="报告列表"/>
  </div>

  <p class='login_text'>共{{reportList.length}}份</p>

  <van-cell v-for="(item, index) in reportList" :key="index" :title="item.appointDate.split('T')[0]" is-link :to='`/mr?id=${item.id}`'/>

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
  data() {
    return {
      appointId: this.$route.query.id,
      reportList: [],
    }
  },
  methods:{
    
  },
  mounted(){
    this.axios.get('api/frontdesk/report/list?appointId='+this.appointId).then(({data})=>{
      console.log(data);
      this.reportList = data.data
    })
  }
}
</script>

