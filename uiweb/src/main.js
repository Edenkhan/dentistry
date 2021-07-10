import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import qs from 'qs'

// axios.defaults.baseURL = 'http://dentistry1.ymys.usooft.com' 

Vue.prototype.axios = axios;
Vue.prototype.qs = qs;
Vue.config.productionTip = false

Vue.filter('timeFormat',(date) => {
	let time = new Date(date)
	let year = String(time.getFullYear())
	let month = String((time.getMonth() + 1)).padStart(2, '0')
	let day = String(time.getDate()).padStart(2, '0')
	let hour = String(time.getHours()).padStart(2, '0')
	let min = String(time.getMinutes()).padStart(2, '0')
	let second = String(time.getSeconds()).padStart(2, '0')
	return `${year}-${month}-${day} ${hour}:${min}:${second}`
})


// 引入vant组件
import Vant from 'vant'
import 'vant/lib/index.css'

Vue.use(Vant);

new Vue({
  router,
	store,
	qs,
  render: h => h(App),
}).$mount('#app')
