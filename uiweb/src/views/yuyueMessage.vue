<template>
<div class="yyMess">
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar title="确认预约信息"/>
  </div>
  <!-- 预约套餐 -->
    <p class='login_text'>预约套餐</p>
    <ul class="home_list">
      <li>
        <div class="cc">
          <img :src="product.iconPath" height="125px">
          <div class="cc_c">
            <p class='an'>
              <span v-if="product.userType===0">【线下/个人】</span>
              <span v-else-if="product.userType===1">【线下/团体】</span>
              <span>{{product.name}}</span>
            </p>

            <p class='yc' v-html="product.description"></p>

          </div>
        </div>
      </li>
    </ul>
  <!-- 预约人信息 -->
  <p class='login_text'>预约人信息</p>
  <ul class="home_list">
    <li>
      <table></table>
      <div class="yyper">
        <p><span>姓名</span>{{realName}}</p>
        <p><span>性别</span>{{currGender==1?'男':'女'}}</p>
        <p><span>电话</span>{{phoneNumber}}</p>
      </div>
    </li>
  </ul>
  <!-- 预约时间 -->
  <p class='login_text'>预约时间</p>
  <ul class="home_list">
    <li>
      <div class="yytime" id='yttt'>
        <table></table>
        <p @click='yyshow=true' v-if='ischoose'>请选择→</p>
        <div class="xzwc" v-else  @click='yyshow=true'>
          <p>
            <span>{{appointDate}}</span>
            <span>{{timePeriod}}</span>
          </p>
          <p>{{isFull}}</p>
        </div>
        <van-overlay :show="yyshow" @click="yyshow = false">
          <div class="yywrapper" @click.stop>
            <!-- <van-area 
            title="请选择时间" 
            :area-list="yyList"
            confirm-button-text='×'
            cancel-button-text=' '
            @confirm="onConfirm"
            @change='onchange'
            ref='area' color='#f00'
            /> -->
            <van-picker show-toolbar title="请选择预约时间" :columns="yyList" confirm-button-text='×'
            cancel-button-text=' '
            @confirm="onConfirm"
            @change='onchange'
            ref='area' color='#f00'/>

            <div class="login_btn">
              <button @click='getyy' :class='isFull=="约满"?"bianh":""' :disabled='isFull=="约满"?true:false'>{{isFull=="约满"?"已约满":"确定"}}</button>
            </div>
          </div>
        </van-overlay>
      </div>
    </li>
  </ul>


  <div class="login_btn">
    <button @click='ljyy'>立即预约</button>
  </div>
</div>
</template>

<style scoped>
.yyMess .login_btn button.bianh{
  background: #f00;
}

.yyMess{
  background: #e8e8e8;
  height:177.7vw;
}
.yyMess .tit .van-nav-bar{
    background: #000;
  }
.yyMess .tit .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
  color:#fff;
}
.tdl{
  color:aquamarine !important;
}
.tdh{
  color:#f00 !important;
}
.yyMess .cc_img{
  overflow: hidden;
}
.yyMess .cc_img img{
  height:100%;
}
.yyMess .login_text{
  font-size: 1.3rem;
  width:40vw;
  height:10vw;
  font-weight: bold;
  margin-left: 0vw;
  margin-top: 3vw;
  margin-bottom: 2vw;
}
.yyMess .home_list{
  background: #fff;
  width:90%;
  margin:0 auto;
  list-style: none;
  height: 30vw;
}
.yyMess .home_list li{
  width:100%;
  height:30vw;
  margin-bottom: 5vw;
  position: relative;
}

/* 预约套餐 */
  .cc{
    display: flex;
    justify-content: space-around;
  }
  .cc_img{
    width:35vw;
    height:30vw;
    background: url('../assets/img/bbg.png') no-repeat;
    background-position: center;
    background-size:cover;
  }
  .yyMess .an{
    margin:0;
    padding:0;
    margin-top: 7vw;
  }
  .yyMess .an span:first-child{
    margin-left:-2vw;
  }
  .an span:last-child{
    font-weight: 600;
  }
  .yyMess .cc_c{
    width:55vw;
    padding:0 2vw;
    height:30vw;
    overflow: hidden;
    position: relative;
  }
  .yyMess .yc{
    font-size: 0.8rem;
    margin:0;padding:0;
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
    margin-top: 8vw;
  }
/* 预约套餐 */

/* 预约人信息 */
.yyper{
  padding:0vw 5vw;
  font-size: 1.1rem;
}
.yyper p{
  margin:3vw 0;
}
.yyper p span:first-child{
  display: inline-block;
  margin-right: 5vw;
}

.yywrapper{
  position: absolute;
  bottom:0;
  width:100%;
}
.yyMess .yywrapper .login_btn{
  background:#fff;
  margin:0 auto;
  padding:5vw 0;
}
/* 预约时间 */
.yytime{
  text-align: center;
}
.yytime p{
  margin:0;padding:0;
  width:100%;
  height:5vw;
  margin-top: 12vw;
}
.xzwc{
  width:95%;
  height:30vw;
  display: flex;
  align-items: center;
  padding: 0 4vw;
}

.xzwc p{
  margin:0;padding:0;height:10vw;
  line-height: 10vw;
  margin-left: 2vw;
}
.xzwc p:first-child span{
  display: inline-block;
  margin-left: 2vw;
}

.yyMess .login_btn{
  margin:8vw auto;
  text-align:center;
}
.yyMess .login_btn button{
  background:rgb(102 205 170);
}
</style>

<script>
import {mapState} from 'vuex';
import qs from 'qs'
export default {
  computed:{
    ...mapState([
        'id','realName','phoneNumber','gender'
    ])
  },
  data(){
    return {
      orderId: this.$route.query.orderId,
      productId: this.$route.query.productId,
      flag: this.$route.query.flag,
      yyshow:false,
      ischoose:true,
      tdata:{},
      appointDate:'',
      timePeriod:'',
      isFull:'',
      yyList:[
        {
          values: [],
          defaultIndex: 0,
        },
        {
          values: ['上午','下午'],
          defaultIndex: 0,
        },
        {
          values: ['可约','约满'],
          defaultIndex: 0,
        },
      ],
      product: {},
      currGender: null,
    }
  },
  methods:{
    onConfirm(val){
      this.yyshow=false;
      // console.log(index);
      // console.log(val);
      this.isFull=val[2];
      this.timePeriod=val[1];
      this.appointDate=val[0];
    },
    getyy(){
      this.yyshow=false;
      this.$refs.area.$el.children[0].children[2].click();
      this.ischoose=false;
    },
    onchange(){
      // this.$refs.area.$el.children[1].children[2].children[0].children[0]

      this.$refs.area.$el.children[0].children[2].click();
      this.yyshow=true;
    },
    ljyy(){
      if(this.realName==''){
        this.$dialog.confirm({
          message: '请完善个人信息后再预约',
          confirmButtonText:'去完成'
        })
        .then(() => {
          this.$router.push('/pw');
        })
        .catch(() => {
        });
      }else{
        // console.log(this.timePeriod);
        let timePeriod
        if(this.timePeriod==''){
          this.yyshow=true;
        }
        if(this.timePeriod=='上午'){
          timePeriod = 0
        }
        if(this.timePeriod=='下午'){
          timePeriod = 1
        }
        // 立即预约
        if(this.appointDate!='' && this.isFull=='可约'){
          console.log(this.appointDate)
          if(!this.flag) {
            // 创建
            this.axios.post('api/frontdesk/appointment/add',qs.stringify({timePeriod:timePeriod,orderId:this.orderId,appointDate:this.appointDate})).then(({data})=>{
              console.log(data)
              this.$router.push('/yy_succ')
            }).catch(({message}) => {
              this.$toast(message)
            })
          }else{
            // 修改
            this.axios.post('api/frontdesk/appointment/edit',qs.stringify({timePeriod:timePeriod,orderId:this.orderId,appointDate:this.appointDate})).then(({data})=>{
              console.log(data)
              this.$router.push('/yy_succ')
            }).catch(({data}) => {
              this.$toast(data.message)
            })
          }
        }else{
          this.$toast('信息错误,预约失败');
        }
      }
       
    },
  },
  mounted(){

    console.log('***********',this.gender)

    // 获取商品信息
    this.axios.get(`api/frontdesk/product/get?id=${this.productId}`).then(({data})=>{
      // console.log(res.data);
      this.product = data
    })

    // 获取预约时间
    this.axios.get(`api/frontdesk/appointment/getAppointDate?orderId=${this.orderId}`).then(({data})=>{
      console.log(data)
      data.data.forEach(el => {
        const item=el.appointDate.split('T')[0];
        // console.log(item)
        this.tdata[item]=1;
      });
      console.log(this.tdata);
      for(var key in this.tdata){
        // console.log(key);
        const year = key.split('-')[0];
        const month = key.split('-')[1];
        const day = key.split('-')[2];
        this.yyList[0].values.push(`${year}年${month}月${day}日`);
      }
      // alert(JSON.stringify(this.yyList))
    })

    this.currGender = this.gender

  }
}
</script>
