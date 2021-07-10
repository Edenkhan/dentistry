<template>
<div class="zs">
  <!-- 顶部标题 -->
  <div class="tit">
    <van-nav-bar title="账户设置" left-text="返回" left-arrow @click-left="zSclick"/>
  </div>

  <div class="deset">
    <!-- 头像选择 -->
    <van-field name="uploader" label="头像" size='large' center is-link   input-align='right'>
      <template #input>
        <van-uploader v-model="uploader" preview-size='40px' max-count='1'/>
      </template>
    </van-field>
    
    <van-cell title="姓名" is-link :value="realName==''?'请填写':realName" size='large' to='/sm'/>
    <van-cell title="性别" is-link :value="gender==1?'男':'女'" size='large' @click='chooseGender'/>
    <van-cell title="手机号" is-link :value="phoneNumber" size='large' to='/cp'/>
    <van-cell-group>
      <van-switch-cell v-model="checked" title="微信消息推送" cell-size="large"/>
    </van-cell-group>

    
  </div>

  <van-overlay :show="zshow" @click="zshow = false">
    <div class="wai" @click.stop>
      <van-radio-group v-model="zradio">
        <van-cell-group>
          <van-cell title="男" clickable @click="biannan">
            <template #right-icon>
              <van-radio :name="1" checked-color='#05C58F'/>
            </template>
          </van-cell>
          <van-cell title="女" clickable @click="biannv">
            <template #right-icon>
              <van-radio :name="0" checked-color='#05C58F'/>
            </template>
          </van-cell>
        </van-cell-group>
      </van-radio-group>
    </div>
  </van-overlay>


  <div class="login_btn">
    <button @click='tuilogin'>退出登录</button>
  </div>

</div>
</template>

<style scoped>
.zs{
  background: #e8e8e8;
  height:177.7vw;
}
.zs .tit .van-nav-bar{
    background: #000;
  }
.zs .tit .van-nav-bar__text,.tit .van-nav-bar .van-icon,.tit .van-nav-bar__title{
  color:#fff;
}

.wai{
  width:90%;
  height:22vw;
  margin:0 auto;
  margin-top: 70vw;
  background-color: #fff;
  border-radius: 0.8rem;
  overflow: hidden;
}


.zs .login_btn{
  margin:20vw auto;
  text-align:center;
}
.zs .login_btn button{
  border:0;
  outline: 0;
  width:75vw;
  height:13vw;
  border-radius: 30px;
  background:rgb(102 205 170);
  color:#fff;
}
</style>

<script>
import qs from 'qs'
import {mapMutations} from 'vuex';
import {mapState} from 'vuex';
export default {
  computed:{
    ...mapState([
        'realName','phoneNumber','id','gender'
    ])
  },
  data(){
    return {
      checked:false,
      zshow:false,
      zradio: 1,
      gender: 1,
      uploader:[],
    }
  },
  created() {
    this.fetch()
  },
  methods:{
    ...mapMutations({
      'lo':'loginout'
    }),
    ...mapMutations([
			'logined'
		]),
    chooseGender(){
      this.zshow=true;
    },
    biannan(){
      this.zradio= 1;
      this.gender= 1;
      this.zshow=false;
    },
    biannv(){
      this.zradio= 0
      this.gender= 0;
      this.zshow=false;
    },
    zSclick(){
      this.$router.push('/me');
    },
    tuilogin(){
      this.lo();
      this.$toast('退出登录成功');
      this.$router.push('/me');
    },
    mehimg(){
      console.log('ssss');
    },
    fetch() {
      this.axios.get('/api/registeredUser/get')
        .then(({data}) => {
          // alert(JSON.stringify(data))
          sessionStorage.setItem('avatar',data.avatar)
          sessionStorage.setItem('realName',data.realName)
          sessionStorage.setItem('gender',data.gender)
          sessionStorage.setItem('phoneNumber',data.phoneNumber)
        })
    },
  },
  watch:{
    zradio(){
      // 修改用户性别
      this.axios.post('api/registeredUser/changeGender',qs.stringify({id:this.id,gender:this.gender})).then(res=>{
        console.log(res.data);
        // 存储到sess里面
        this.zradio = this.gender
        this.logined({gender:this.gender});
        sessionStorage.setItem('gender',this.gender);
      })

    },
    // 上传头像
    uploader(){
      this.$toast.loading({
        message: '上传中请稍后',
        forbidClick: true,
        duration:0
      });

      let file = new FormData();
      file.append('file',this.uploader[0].file);
      file.append('id',this.id);
      console.log(file);

      this.axios.post('api/registeredUser/uploadAvatar',file).then(({data})=>{
        alert(JSON.stringify(data))
        this.$toast.clear();
        // 存储到sess里面
        this.logined({avatar: data.avatar});
        sessionStorage.setItem('avatar', data.avatar);
      })



    }
  },
  
}
</script>

