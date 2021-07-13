<template>
<div class="home">
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar title="法赫光学"/>
  </div>

  <!-- 第一部分 -->
  <div class='fir'>
    <table></table>
    <p class='login_text'>牙齿健康就找XXX</p>
    <!-- 三小只 -->
    <ul class="sxz">
      <li @click='wyyy'>
        <img src="../assets/img/yy.png" alt="">
        <p>我要预约</p>
      </li>
      <li @click='bggg'>
        <img src="../assets/img/bg.png" alt="">
        <p>报告查询</p>
      </li>
      <li @click='mDd'>
        <img src="../assets/img/wd.png" alt="">
        <p>我的订单</p>
      </li>
    </ul>
    <p class='login_line'></p>
    <div class="fir_la">
      <span><img src="../assets/img/dw.png"></span>
      <span>成都市天府三街国际节能大厦B座1805号</span>
      <span><img src="../assets/img/dh.png"></span>
    </div>
  </div>

  <div class="home_content">
    <table></table>
    <p class='login_text'>热销推荐</p>
    <ul class="home_list">
      <li v-for='(i,index) of rxlist' :key='index' @click='jumpdetail(index)'>
        <div class="cc">
          <div class="cc_img">
            <img :src="i.icon" alt="">
          </div>
          <div class="cc_c">
            <div class='an'>
              <span>{{i.ti}}</span>
              <span>{{i.title}}</span>
            </div>

            <p class='yc' v-html="i.content"></p>

            <div class='xgpri'>
              <div class='xgprice'>
                <span> ￥ {{i.price}}.00</span>
                <span>/3次</span>
              </div>
              <div class='xgnum'>已售{{i.num}}</div>
            </div>

          </div>
        </div>
        <p class='login_line'></p>
      </li>
    </ul>
  </div>

  <!-- 底部标签开始 -->
  <van-tabbar v-model="active">
    <van-tabbar-item name="home" icon="wap-home-o">
      首页
    </van-tabbar-item>
    <van-tabbar-item name="me" icon="manager-o" @click='me'>
      我的
    </van-tabbar-item>
  </van-tabbar>
  <!-- 底部标签结束 -->

</div>
</template>
<style scoped>
/* 头部样式 */
  .home{
    width:100%;
    background: #e8e8e8;
  }
  .home .tit .van-nav-bar{
      background: #000;
    }
  .home .tit .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
    color:#fff;
  }
  .home .fir .login_text{
    width:40vw;
    height:10vw;
    font-weight: bold;
    margin-left: -5vw;
    margin-top: 5vw;
  }
  .fir{
    padding:0 5vw;
    position: relative;
    background: #fff;
  }
  .fir .sxz{
    list-style: none;
    display: flex;
    justify-content: space-around;
    width:100%;
    margin:0 auto;
    text-align: center;
  }
  .fir .sxz li{
    width:20%;
  }
  .fir .sxz li img{
    width:80%;
  }
  .fir .login_line{
    width:90%;
    border:1px solid rgba(216,216,216,1);
    position: absolute;
    top:74%;
    left: 5%;
  }
  .fir_la{
    margin-top: 5vw;
  }
  .fir_la span:first-child,.fir_la span:last-child{
    display: inline-block;
    width:10%;
    vertical-align: middle;
    text-align: center;
  }
  .fir_la span:nth-child(2){
    display: inline-block;
    width: 74%;
    font-size: 0.8rem;
    padding-left: 4.4vw;
  }
  .fir_la span img{
    width: 70%;
  }
/* 第一部分样式结束 */
.home .xgpri{
  display: flex;
  justify-content: space-between;
  margin-top:7vw;
  color:#f00;
  font-size: 1.3rem;
  font-weight: bold;
}
.home .xgpri .xgnum{
  font-weight:normal;
  font-size: 0.5rem;
  color:#000;
  line-height: 8vw;
}
.home .xgpri .xgprice span:last-child{
  font-size: 0.5rem;
}

/* 第二部分样式开始 */
  .home_content{
    width: 100%;
    margin-top: 5vw;
    margin-bottom: 14vw;
    background: #fff;
  }
  .home_content .login_text{
    height:5vw;
    font-weight: bold;
    margin-top: 5vw;
  }
  .home_list{
    width:90%;
    margin:0 auto;
    list-style: none;
  }
  .home_list li{
    width:100%;
    height:30vw;
    margin-bottom: 5vw;
    position: relative;
  }

  .home_list li .login_line{
    width: 100%;
    top:94%;
    left: 0%;
  }
  .cc{
    display: flex;
    justify-content: space-around;
  }
  .cc_img{
    width:35vw;
    height:30vw;
    overflow: hidden;
  }
  .cc_img img{
    width:100%;
  }
  .cc_c{
    width:55vw;
    padding:0 3vw;
    height:30vw;
    overflow: hidden;
    position: relative;
  }
  .an{
    margin:0;
    padding:0;
    overflow: hidden;
  }
  .an span:first-child{
    display: block;
    width:10vw;
    height:7vw;
    text-align: center;
    line-height: 7vw;
    border-radius: 0.3rem;
    color:#fff;
    background: #03C590;
    margin-right: 2vw;
    margin-bottom: 2vw;
    float: left;
  }
  .an span:last-child{
    height:5vw;

    display: block;
    width:39vw;
    font-weight: 600;   
    white-space: no wrap;
    text-overflow: ellipsis;
    overflow: hidden;
    margin-top: 1vw;
  }
/* 第二部分样式结束 */
.yc{
  font-size: 0.8rem;
  margin:0;padding:0;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
}
.c_price{
  color:#f00;
  position: absolute;
  font-size: 1.3rem;
  font-weight: bold;
  bottom:0%;
  right:0%;
}
.c_price span:nth-child(2){
  font-size: 0.5rem;
}
.c_price b:first-child{
  display: inline-block;
  width:25vw;
}
.c_price span:nth-child(3){
  display: inline-block;
  width:9vw;
}
.c_price span:last-child{
  width:10vw;
  font-weight:normal;
  font-size: 0.5rem;
  color:#000;
  display: inline-block;
  margin-right: 5vw;
}
.van-tabbar-item--active{
  color:#03C590;
}
</style>

<script>
import {mapState} from 'vuex';
export default {
  computed:{
    ...mapState([
        'iSlogin'
    ])
  },
  data(){
    return {
      active: 'home',
      rxlist:[
        // {
        //   ti:'线下',
        //   title:'关爱牙齿基础套餐3次',num:79,
        //   content:'包含拍片、洁牙、美白、售后服务...'
        // },
        // {
        //   ti:'线下',
        //   title:'关爱牙齿基础套餐3次',num:22,
        //   content:'高级专家为您服务'
        // },
        // {
        //   ti:'远程',
        //   title:'高级专家远程服务',num:8,
        //   content:'私人专家为您的牙齿保驾护航'
        // }
      ]
    }
  },
  methods:{
    wyyy(){
      if(this.iSlogin){
        this.$router.push('/yuyue');
      }else{
        this.$dialog.confirm({
          title:'您还未登陆',
          message: '是否立即登录',
          confirmButtonText:'是',
          confirmButtonColor:"#05C58F"
        }).then(() => {
          this.$router.push('/login');
        }).catch(()=>{})
      }
    },
    bggg(){
      if(this.iSlogin){
        this.$router.push('/wd');
      }else{
        this.$dialog.confirm({
          title:'您还未登陆',
          message: '是否立即登录',
          confirmButtonText:'是',
          confirmButtonColor:"#05C58F"
        })
        .then(() => {
          this.$router.push('/login');
        }).catch(()=>{})
      }
    },
    mDd(){
      if(this.iSlogin){
        this.$router.push('/md');
      }else{
        this.$dialog.confirm({
          title:'您还未登陆',
          message: '是否立即登录',
          confirmButtonText:'是',
          confirmButtonColor:"#05C58F"
        })
        .then(() => {
          this.$router.push('/login');
        }).catch(()=>{})
      }
    },
    me(){
      this.$router.push('/me');
      location.reload();
    },
    jumpdetail(index){
      this.$router.push({path:'/detail',query:{count:this.rxlist[index].id}});
    }
  },
  mounted(){
    console.log(this.iSlogin);

    // 显示内容加载中
    this.$toast.loading({
      message: '加载中...',
      duration:4000
    });

    this.axios.get('api/frontdesk/product/list').then(({data})=>{
      // 关闭加载中
      this.$toast.clear();

      // console.log(res.data.data);
      for(var key of data.data){
        var ti='';
        if(key.type==0){
          ti='线上';
        }else if(key.type==1){
          ti='线下';
        }
        this.rxlist.push(
            {
              ti:ti,
              title:key.intro,
              content:key.description,
              num:key.sales,
              icon:key.iconPath,
              id:key.id,
              price:key.price
            }
        )
      }
    })


  },
}
</script>








