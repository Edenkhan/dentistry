<template>
<div class="mb">
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar title="我的报告"/>
  </div>
  <div>
    <div>
      <button type="button" @click="changePdfPage(0)">上一页</button>
      <button type="button" @click="changePdfPage(1)">下一页</button>
    </div>
    <p>{{ currentPage }} / {{ pageCount }}</p>
    <div>
      <button type="button" @click="scaleD()">放大</button>
      <button type="button" @click="scaleX()">缩小</button>
    </div>
    <div>
      <button type="button" @click="clock()">顺时针</button>
      <button type="button" @click="counterClock()">逆时针</button>
    </div>
    <pdf
        ref="pdf"
        :src="pathList[0]"
        :page="currentPage"
        :rotate="pageRotate"
        @num-pages="pageCount = $event"
        @page-loaded="currentPage = $event"
        @loaded="loadPdfHandler"
    ></pdf>

  </div>
</div>
</template>

<script>
import pdf from 'vue-pdf'

export default {
  components: {
    pdf
  },
  data() {
    return {
      reportId: this.$route.query.id,
      pathList: [],
      reportList: [],
      currentPage: 0, // pdf文件页码
      pageCount: 0, // pdf文件总页数
      scale: 100,
      pageRotate: 0
    }
  },
  methods:{
    loadPdfHandler() {
      this.currentPage = 1
    },
    // 改变PDF页码,val传过来区分上一页下一页的值,0上一页,1下一页
    changePdfPage(val) {
      if (val === 0 && this.currentPage > 1) {
        this.currentPage--;
      }
      if (val === 1 && this.currentPage < this.pageCount) {
        this.currentPage++;
      }
    },
    //放大
    scaleD() {
      this.scale += 5;
      this.$refs.pdf.$el.style.width = parseInt(this.scale) + "%";
    },
    //缩小
    scaleX() {
      if (this.scale === 100) {
        return;
      }
      this.scale += -5;
      this.$refs.pdf.$el.style.width = parseInt(this.scale) + "%";
    },
    // 顺时针
    clock() {
      this.pageRotate += 90;
    },
    // 逆时针
    counterClock() {
      this.pageRotate -= 90;
    }
  },
  mounted(){
    if(this.reportId) {
      this.axios.get('api/frontdesk/report/get?id='+this.reportId).then(({data})=>{
        // console.log(data);
        this.pathList = data.path.split(',')
      })
    }else{
      this.axios.get('api/frontdesk/report/getByUser?').then(({data})=>{
        console.log(data);
        this.reportList = data.data
        this.pathList = this.reportList[0].path.split(',')
      })
    }

  }
}
</script>

<style scoped>
.mb{
  width:100%;
  height:177.7vw
}
.mb_img{
  width:100%;
  background: url(../assets/img/mybg.png) no-repeat;
  height:166.7vw;
  background-size: cover;
  background-position: center;
}
.mb .tit .van-nav-bar{
    background: #000;
  }
.mb .tit .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
  color:#fff;
}
</style>
