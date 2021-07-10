import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    //存储id
    id:sessionStorage.getItem('id')?sessionStorage.getItem('id'):3,
    //存储订单id
    pid:sessionStorage.getItem('orderId')?sessionStorage.getItem('orderId'):0,
    //存储用户名
    realName:sessionStorage.getItem('realName')?sessionStorage.getItem('realName'):'',
    //用户是否登录
    iSlogin:sessionStorage.getItem('iSlogin')?sessionStorage.getItem('iSlogin'):false,
    // 用户电话
    phoneNumber:sessionStorage.getItem('phoneNumber')?sessionStorage.getItem('phoneNumber'):'199****9415',
    // 存储用户性别
    gender:sessionStorage.getItem('gender')?sessionStorage.getItem('gender'):'',
    // 存储用户头像
    avatar:sessionStorage.getItem('avatar')?sessionStorage.getItem('avatar'):''
  },
  getters:{
    
  },
  mutations: {
    logined(state,payload){
      state.id=payload.id;
      state.avatar=payload.avatar;
      state.gender=payload.gender;
      state.realName=payload.realName;
      state.phoneNumber=payload.phoneNumber;
      state.iSlogin=true;
    },
    loginout(state){
      state.id=0;
      state.gender='';
      state.avatar='';
      state.realName='';
      state.phoneNumber='';
      state.iSlogin=false;

      sessionStorage.removeItem('iSlogin');
      sessionStorage.removeItem('gender');
      sessionStorage.removeItem('id');
      sessionStorage.removeItem('avatar');
      sessionStorage.removeItem('realName');
      sessionStorage.removeItem('phoneNumber');
    }
  },
  actions: {
  },
  modules: {
  }
})
