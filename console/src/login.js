import Vue from 'vue';
import Login from "./Login.vue";
import {Button, Form, Icon, Input, message, Checkbox, Avatar} from "ant-design-vue";
import "./main.scss";

Vue.use(Form);
Vue.use(Button);
Vue.use(Input);
Vue.use(Icon);
Vue.use(Avatar);
Vue.use(Checkbox);

Vue.prototype.$message = message;

new Vue({
  render: h => h(Login),
}).$mount('#app');
