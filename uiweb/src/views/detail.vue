<template>
<div class="detail">
  <!-- 顶部标题 -->
  <div class="tit" v-if='tclist.length>0'>
    <van-nav-bar :title="'【'+tclist[0].ti+'】'+tclist[0].title" left-text="返回" left-arrow @click-left="declick"/>
  </div>

<!--  <div class="de_img">-->
<!--    <img :src="tibanner">-->
<!--  </div>-->

  <div class="banner">
    <van-swipe class="my-swipe" :autoplay="6000" indicator-color="white">
      <van-swipe-item v-for="(b,index) in tibanner" :key="index">
        <a :href="b">
          <img :src="b" alt="" width="100%"/>
        </a>
      </van-swipe-item>
    </van-swipe>
  </div>

  <ul class="home_list" v-if='tclist.length>0'>
    <li>
      <table></table>
      <p class='c_price'>{{tclist[0].pr}}<span>/3次</span><span>已售{{tclist[0].num}}</span></p>

      <p class='an'>
        <span>【{{tclist[0].ti}}】</span>
        <span>{{tclist[0].title}}</span>
      </p>

      <p class='yc'>{{tclist[0].co}}</p>
    </li>
  </ul>

  <div class="tianc"></div>

  <!-- 查看详情 -->
  <div class="ckxq" v-if='tclist.length>0'>
    <p class='login_text'>查看详情</p>
    <p v-html='tclist[0].content'></p>
  </div>

  <!-- 底部按钮 -->
  <div class="di_btn">
    <div>
      <img src="../assets/img/sy.png">
      <p><router-link to='/'>首页</router-link></p>
    </div>
    <div @click.stop='zxys'>
      <img src="../assets/img/zx.png">
      <p>咨询</p>
    </div>
    <button @click.stop='buynow'>立即购买</button>
  </div>

  <div class="chdoc" v-show='dshow'>
    <van-popup
    v-model="dshow"
    closeable
    close-icon-position="top-right"
    position="bottom"
    :style="{ height: '50%' }"
    />
    <div class="cdoc">
      <van-picker show-toolbar title=" " :columns="columns" confirm-button-text=' ' cancel-button-text=' ' @change="onChange" @confirm='onConfirm' ref='zg'/>

      <span>{{judti==true?'请选择问诊医生':'请选择附近门店'}}</span>
      <div class="login_btn">
        <button @click='pickdown'>已选好，去支付</button>
      </div>
    </div>
  </div>
  
  <!-- 支付信息 -->
  <van-overlay :show="ddshow" @click="ddshow = false">
    <div class="ddwrapper" @click.stop>
      <div class="ddblock">
        <p class="p_f1"><span @click='spanclose'>×</span><span>使用密码</span></p>
        <hr>
        <p class="p_f2">向{{shop?shop.name:dicItem.name}}转账</p>
        <p class="p_f3">{{tclist[0].pr}}</p>

        <p class="p_f1 f4"><span class='xicon'></span><span>支付方式</span><span>零钱></span></p>

        <p class="p_f5_btn" @click='generateOrder'>确认支付</p>

      </div>
    </div>
  </van-overlay>

</div>
</template>

<style scoped>
.ddwrapper {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}
.ddblock {
  width:80vw;
  height:65vw;
  background-color: #fff;
  border-radius: 0.5rem;
}
.ddblock p.p_f1{
  margin:0;padding:0;
  height: 8vw;line-height: 10vw;
  padding:0 5vw;
  position: relative;
}
.ddblock p.p_f1 span:last-child{
  position: absolute;
  right:6%;color:#5B6A96;
}
.ddblock p.p_f2{
  margin:0;padding:0;
  height: 8vw;line-height: 8vw;
  text-align:center;
  font-size: 0.8rem;
}
.ddblock p.p_f3{
  margin:0;padding:0;
  height: 8vw;line-height: 8vw;
  text-align:center;
  font-size: 2rem;
  font-weight: bold;
}
.ddblock p.p_f1.f4{
  margin-top: 5vw;color:#6E6E6E;
  font-size: 0.8rem;position: relative;
}
.ddblock p.p_f1.f4 span:last-child{
  color:#6E6E6E;
}
.ddblock p.p_f1.f4 span.xicon{
  border:1px solid #000;width: 3vw;height:3vw;
  right:17%;position: absolute;top:37%;
  background: rgb(224, 126, 56);
}
.ddblock p.p_f5_btn{
  margin:6vw auto;padding:0;color:#fff;
  height:10vw;width:60%;
  text-align: center;line-height: 10vw;
  background:rgb(102 205 170);
}



.cdoc /deep/.van-picker-column__item--selected{
  color:#05C58F;
}
.cdoc .login_btn{
  margin:0vw auto;
  text-align:center;
  margin-bottom: 5vw;
}
.cdoc .login_btn button{
  border:0;
  outline: 0;
  width:75vw;
  height:13vw;
  border-radius: 30px;
  background:rgb(102 205 170);
  color:#fff;
}
.chdoc{
  width:100%;
}
.cdoc{
  background: #fff;
  width:100%;
  height:80vw;
  position: fixed;
  bottom:0%;
  left:0%;
  z-index: 3000;
}
.cdoc span{
  position: absolute;
  top:-6%;left:36%;
}

.detail a{
  color:#939393;
  text-decoration: none;
}
.detail .van-nav-bar{
  background: #000;
}
.detail .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
  color:#fff;
}
.de_img{
  height:48vw;
  width:100%;
  overflow: hidden;
}
.de_img img{
  width:100%;
}
.home_list{
  width:90%;
  margin:0 auto;
  list-style: none;
}
.detail .home_list li{
  width:100%;
  height:35vw;
  position: relative;
}
.an{
  margin:0;
  padding:0;
  margin-top: 1vw;
}
.an span:last-child{
  font-weight: 600;
}
.detail .yc{
  font-size: 0.8rem;
  margin:0;padding:0;
  margin-top: 2vw;
  color:#949494;
  padding:0 2vw;
}
.detail .c_price{
  color:#f00;
  font-size: 1.3rem;
  font-weight: bold;
}
.c_price span:first-child{
  font-size: 0.5rem;
}
.detail .c_price span:last-child{
  font-weight:normal;
  font-size: 0.5rem;
  color:#000;
  display: inline-block;
  margin-left: 45vw;
  margin-right: 5vw;
}

.tianc{
  height:5vw;
  background: #e8e8e8;
}

.ckxq{
  width:90%;
  margin:0 auto;
}
.ckxq .login_text{
  margin:0;
  padding-left: 0;
  margin-top: 5vw;
}
.ckxq p:last-child{
  margin-top:0;
  margin-bottom: 20vw;
}

.di_btn{
  position: fixed;
  bottom:0;
  width:100%;
  display: flex;
  z-index: 100;
  background: #fff;
}
.di_btn div{
  width:25%;
  font-size: 0.6rem;
  line-height: 0.3rem;
  text-align: center;
  padding-top: 0.3rem;
  color:#939393;
}
.di_btn button{
  border:0;
  outline: 0;
  width:50%;
  background: #03C590;
  color:#fff;
}


</style>

<script>
import qs from 'qs'
import {mapState} from 'vuex';
import {mapMutations} from 'vuex';
export default {
  computed:{
    ...mapState([
        'iSlogin','id','phoneNumber','avatar','gender','realName'
    ])
  },
  data(){
    return {
      ddshow:false,
      name:'',
      dshow:false,
      productId:0,
      tclist:[],
      columns:[
        // 第一列
        {
          values: [],
          defaultIndex: 1,
        },
        // 第二列
        {
          values: ['✔'],
          defaultIndex: 2,
        },
      ],
      judti:false,
      shopList: [],
      dicItemList: [],
      tibanner: [],
      notfar:1,
      shop: {},
      dicItem: {},
    }
  },
  methods:{
    ...mapMutations([
			'logined'
		]),
    checkInfo() {
      return this.iSlogin && this.phoneNumber && this.avatar && this.gender && this.realName
    },
    declick(){
      this.$router.push('/');
    },
    // 选择医生
    zxys(){
      if(this.iSlogin){
        this.dshow=true;
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
    // 立即购买
    buynow(){
      // alert(this.phoneNumber)
      // alert(this.avatar)
      // alert(this.gender)
      // alert(this.realName)
      if(this.checkInfo()){
        this.dshow=true;
      }else{
        this.$dialog.confirm({
          title:'未完善信息',
          message: '是否立即完善',
          confirmButtonText:'是',
          confirmButtonColor:"#05C58F"
        })
        .then(() => {
          this.$router.push('/zs');
        }).catch(()=>{})
      }
    },
    pickdown(){
      this.dshow=false;
      this.$refs.zg.$el.children[0].children[2].click();
      this.ddshow=true;
      // alert(JSON.stringify(this.name))
      this.shop = this.shopList.find(item => item.name===this.name);
      this.dicItem = this.dicItemList.find(item => item.name===this.name);
      // alert(JSON.stringify(this.dicItem))
    },
    onConfirm(value){
      // alert(JSON.stringify(value[0]))
      this.name=value[0];
    },
    // onChange(value/*,index*/) {
    //   // Toast(`Value:${value},Index:${index}`)
    //   // alert(JSON.stringify(value))
    //   alert(JSON.stringify(value))
    // },
    spanclose(){
      this.ddshow=false;
    },
    generateOrder(){
      // 立即购买生成订单
      this.axios.post('api/frontdesk/orders/add',qs.stringify({
        price: this.tclist[0].price,
        userId: sessionStorage.getItem('id'),
        productId: this.productId,
        dicItemId: this.dicItem?this.dicItem.id:null,
        shopId: this.shop?this.shop.id:null,
      })).then(({data})=>{
        console.log(data);
        if(this.notfar==0){
          this.notfar=1;
        }
        // 在sess里面存储订单id 和门店id
        this.logined({orderId:data.id});
        sessionStorage.setItem('orderId',data.id);
        sessionStorage.setItem('shopId',this.shop.id)
        this.toPay(data.id)
      })
    },
    toPay(orderId) {
      // 调起支付
      this.axios.post('api/frontdesk/orders/toPay',qs.stringify({orderId:orderId})).then(({data}) => {
        // alert(JSON.stringify(data))
        WeixinJSBridge.invoke(
            'getBrandWCPayRequest',
            {
              "appId": data.result.appId,
              "timeStamp": data.result.timeStamp,
              "signType": data.result.signType,
              "nonceStr": data.result.nonceStr,
              "package": data.result.package,
              "paySign": data.result.paySign
            },
            function(res) {
              if (res.err_msg == "get_brand_wcpay_request:ok") {
                // 使用以上方式判断前端返回,微信团队郑重提示：
                //res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
                this.$router.push({path:'/succ',query:{id:0,orderId:data.id,productId:this.productId,notfar:undefined}});
                location.reload();
              }
            }
        )

      })

    },

  },
  mounted(){
    this.productId=this.$route.query.count;

    // 获取订单信息
    this.axios.get('api/frontdesk/product/get?'+`id=${this.productId}`).then(({data})=>{
      // alert(JSON.stringify(data))
      // 保存顶部图片路径
      this.tibanner = data.detailPathList

      var ti='';
      if(data.type==0){
        ti='远程';
        this.judti=true;
        this.notfar=0;
      }else if(data.userType==0){
        ti='线下/个人';
      }else if(data.userType==1){
        ti='线下/团体';
      }
      this.tclist.push({
        ti:ti,
        title:data.intro,
        content:data.description,
        num:data.sales,
        icon:data.iconPath,
        id:data.id,
        pr:'￥'+data.price+'.00',
        price: data.price,
      })

      // 判断线上还是线下
      if(this.judti){
      // 获取医生
        this.axios.get('api/frontdesk/orders/getDoctor').then(({data})=>{
          // console.log(res.data);
          for(let key of data){
            this.columns[0].values.push(key.name)
          }
          this.dicItemList = data
          // alert(JSON.stringify(this.dicItemList))
        // console.log(this.columns[0].values);
        })
      }else{
        // 获取门店
        this.axios.get('api/frontdesk/orders/getShop').then(({data})=>{
          // console.log(res.data);
          for(let key of data){
            this.columns[0].values.push(key.name)
          }
          this.shopList = data
          // alert(JSON.stringify(this.shopList))
          // console.log(this.shopIdList);
        })
      }

    })



    

  }
}
</script>
