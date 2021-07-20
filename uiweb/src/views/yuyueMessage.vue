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
              <span>{{ product.name }}</span>
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
          <p><span>姓名</span>{{ realName }}</p>
          <p><span>性别</span>{{ currGender == 1 ? '男' : '女' }}</p>
          <p><span>电话</span>{{ phoneNumber }}</p>
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
          <div class="xzwc" v-else @click='yyshow=true'>
            <p>
              <span>{{ appointDate }}</span>
              <span>{{ timePeriod===0?'上午':'下午'}}</span>
            </p>
            <p>{{ isFull ? '爆满' : '可约' }}</p>
          </div>
          <van-overlay :show="yyshow" @click="yyshow = false">
            <div class="yywrapper" @click.stop>
              <!-- <van-area
              title="请选择时间"
              :area-list="appointDateList"
              confirm-button-text='×'
              cancel-button-text=' '
              @confirm="onConfirm"
              @change='onchange'
              ref='area' color='#f00'
              /> -->
              <van-picker show-toolbar title="请选择预约时间" :columns="appointDateList" confirm-button-text='×'
                          cancel-button-text=' '
                          @confirm="onConfirm"
                          @change='onchange'
                          ref='area' color='#f00'/>

              <div class="login_btn">
                <button @click='getyy'
                        :class='isFull?"bianh":""'
                        :disabled='isFull'>
                  {{ isFull ? "已爆满" : "确定" }}
                </button>
              </div>
            </div>
          </van-overlay>
        </div>
      </li>
    </ul>


    <div class="login_btn">
      <button @click='ljyy'>{{this.insertOrUpdate?'立即预约':'修改预约'}}</button>
    </div>
  </div>
</template>

<style scoped>
.yyMess .login_btn button.bianh {
  background: #f00;
}

.yyMess {
  background: #e8e8e8;
  height: 177.7vw;
}

.yyMess .tit .van-nav-bar {
  background: #000;
}

.yyMess .tit .van-nav-bar__text, .tit .van-nav-bar .van-icon, .tit .van-nav-bar__title {
  color: #fff;
}

.tdl {
  color: aquamarine !important;
}

.tdh {
  color: #f00 !important;
}

.yyMess .cc_img {
  overflow: hidden;
}

.yyMess .cc_img img {
  height: 100%;
}

.yyMess .login_text {
  font-size: 1.3rem;
  width: 40vw;
  height: 10vw;
  font-weight: bold;
  margin-left: 0vw;
  margin-top: 3vw;
  margin-bottom: 2vw;
}

.yyMess .home_list {
  background: #fff;
  width: 90%;
  margin: 0 auto;
  list-style: none;
  height: 30vw;
}

.yyMess .home_list li {
  width: 100%;
  height: 30vw;
  margin-bottom: 5vw;
  position: relative;
}

/* 预约套餐 */
.cc {
  display: flex;
  justify-content: space-around;
}

.cc_img {
  width: 35vw;
  height: 30vw;
  background: url('../assets/img/bbg.png') no-repeat;
  background-position: center;
  background-size: cover;
}

.yyMess .an {
  margin: 0;
  padding: 0;
  margin-top: 7vw;
}

.yyMess .an span:first-child {
  margin-left: -2vw;
}

.an span:last-child {
  font-weight: 600;
}

.yyMess .cc_c {
  width: 55vw;
  padding: 0 2vw;
  height: 30vw;
  overflow: hidden;
  position: relative;
}

.yyMess .yc {
  font-size: 0.8rem;
  margin: 0;
  padding: 0;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
  margin-top: 8vw;
}

/* 预约套餐 */

/* 预约人信息 */
.yyper {
  padding: 0vw 5vw;
  font-size: 1.1rem;
}

.yyper p {
  margin: 3vw 0;
}

.yyper p span:first-child {
  display: inline-block;
  margin-right: 5vw;
}

.yywrapper {
  position: absolute;
  bottom: 0;
  width: 100%;
}

.yyMess .yywrapper .login_btn {
  background: #fff;
  margin: 0 auto;
  padding: 5vw 0;
}

/* 预约时间 */
.yytime {
  text-align: center;
}

.yytime p {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 5vw;
  margin-top: 12vw;
}

.xzwc {
  width: 95%;
  height: 30vw;
  display: flex;
  align-items: center;
  padding: 0 4vw;
}

.xzwc p {
  margin: 0;
  padding: 0;
  height: 10vw;
  line-height: 10vw;
  margin-left: 2vw;
}

.xzwc p:first-child span {
  display: inline-block;
  margin-left: 2vw;
}

.yyMess .login_btn {
  margin: 8vw auto;
  text-align: center;
}

.yyMess .login_btn button {
  background: rgb(102 205 170);
}
</style>

<script>
import {mapState} from 'vuex';
import qs from 'qs'

export default {
  computed: {
    ...mapState([
      'id', 'realName', 'phoneNumber', 'gender'
    ])
  },
  data() {
    return {
      orderId: this.$route.query.orderId,
      productId: this.$route.query.productId,
      flag: this.$route.query.flag,
      yyshow: false,
      ischoose: true,
      tdata: {},
      appointDate: null,
      timePeriod: null,
      isFull: null,
      appointDateList: [
        {
          values: [],
          defaultIndex: 0,
        },
        {
          values: [],
          defaultIndex: 0,
        },
        {
          values: [],
          defaultIndex: 0,
        },
      ],
      product: {},
      currGender: null,
      insertOrUpdate: true,
    }
  },
  methods: {
    onConfirm(val) {
      this.yyshow = false;
      this.appointDate = val[0]
      this.timePeriod = val[1]==='上午'?0:1
    },
    getyy() {
      this.yyshow = false;
      this.$refs.area.$el.children[0].children[2].click();
      this.ischoose = false;
    },
    onchange(picker, value, index) {
      // this.appointDateList[1].values = []
      // this.appointDateList[2].values = []
      const appointDate = value[0]
      this.axios.get(`api/frontdesk/appointManage/getValidPeriod?orderId=${this.orderId}&appointDate=${appointDate}`)
          .then(({data}) => {
            let periodList = []
            data.periodList.forEach(item => {
              periodList.push(item === 0 ? '上午' : '下午')
            })
            let timePeriod;
            if (index === 0) {
              picker.setColumnValue(1, periodList[0])
              timePeriod = periodList[0] === '上午' ? 0 : 1;
            } else {
              timePeriod = picker.getColumnValue(1) === '上午' ? 0 : 1;
            }
            this.appointDateList[1].values = periodList

            this.axios.get(`api/frontdesk/appointManage/checkFull?orderId=${this.orderId}&appointDate=${appointDate}&timePeriod=${timePeriod}`)
                .then(({data}) => {
                  this.isFull = data
                  if (data) {
                    this.appointDateList[2].values = ['爆满']
                  } else {
                    this.appointDateList[2].values = ['可约']
                  }
                  // console.log(this.appointDateList)

                })

          })


      this.yyshow = true;
    },
    ljyy() {
      if (this.realName == '') {
        this.$dialog.confirm({
          message: '请完善个人信息后再预约',
          confirmButtonText: '去完成'
        })
            .then(() => {
              this.$router.push('/pw');
            })
            .catch(() => {
            });
      } else {
        if (this.timePeriod == '') {
          this.yyshow = true;
        }
        // 立即预约
        if (this.appointDate != '' && !this.isFull) {
          // console.log(this.appointDate)
          if (!this.flag) {
            // 创建
            this.axios.post('api/frontdesk/appointment/add',
                qs.stringify({timePeriod: this.timePeriod, orderId: this.orderId, appointDate: this.appointDate}))
                .then(() => {
                  // console.log(data)
                  this.$router.push('/yy_succ')
                }).catch(({message}) => {
              this.$toast(message)
            })
          } else {
            // 修改
            this.axios.post('api/frontdesk/appointment/edit',
                qs.stringify({timePeriod: this.timePeriod, orderId: this.orderId, appointDate: this.appointDate}))
                .then(() => {
                  // console.log(data)
                  this.$router.push('/yy_succ')
                }).catch(({data}) => {
              this.$toast(data.message)
            })
          }
        } else {
          this.$toast('信息错误,预约失败');
        }
      }

    },
  },
  mounted() {

    // console.log('***********',this.gender)

    // 获取商品信息
    this.axios.get(`api/frontdesk/product/get?id=${this.productId}`).then(({data}) => {
      // console.log(res.data);
      this.product = data
    })

    // 获取预约时间
    this.axios.get(`api/frontdesk/appointment/getAppointDate?orderId=${this.orderId}`)
        .then(({data}) => {
          // console.log(data)
          data.data.forEach(item => {
            const timeStr = item.appointDate.split('T')[0];
            // console.log(item)
            this.tdata[timeStr] = 1;
          });
          // console.log(this.tdata);
          for (var key in this.tdata) {
            // console.log(key);
            const arr = key.split('-');
            this.appointDateList[0].values.push(`${arr[0]}年${arr[1]}月${arr[2]}日`);
          }
          // alert(JSON.stringify(this.appointDateList))
        })

    // 获取预约信息
    this.axios.get(`api/frontdesk/appointment/getAppointing?orderId=${this.orderId}`)
        .then(({data}) => {
          if (data) {
            this.insertOrUpdate = false
            this.ischoose = false
            const arr = data.appointDate.split('T')[0].split('-')
            this.appointDate = `${arr[0]}年${arr[1]}月${arr[2]}日`
            this.timePeriod = data.timePeriod
            this.isFull = data.isFull
          }
        })

    this.currGender = this.gender

  }
}
</script>
