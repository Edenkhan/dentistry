<template>
<div class='succ'>
  <!-- 顶部标题 -->
  <div>
    <h2>手机验证成功</h2>
    新手机：<input name="phoneNumber" v-model="phoneNumber"><br>
    <button @click="changePhoneNumber">确认</button>

  </div>

</div>
</template>

<script>
import {mapMutations} from 'vuex'
export default {
  data(){
    return {
      phoneNumber: '',
    }
  },
  methods: {
    ...mapMutations([
      'logined'
    ]),
    changePhoneNumber() {
      this.axios.post('/api/registeredUser/changePhoneNumber?phoneNumber='+this.phoneNumber)
        .then(({data}) => {
          // alert(data)
          sessionStorage.setItem('phoneNumber',data)
          this.logined({phoneNumber: data})
          this.$router.push('/me')
        })
    }
  }
}
</script>
