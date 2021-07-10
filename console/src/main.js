import Vue from 'vue'
import {
  Avatar,
  Drawer,
  Badge,
  Breadcrumb,
  Button,
  Calendar,
  Card,
  Checkbox,
  Col,
  DatePicker,
  Skeleton,
  Dropdown,
  Form,
  FormModel,
  Icon,
  Input,
  InputNumber,
  Layout,
  ConfigProvider,
  Menu,
  message,
  Modal,
  Transfer,
  Pagination,
  Row,
  Select,
  Spin,
  Table,
  Tag,
  Tooltip,
  Upload,
  Alert,
  Radio,
  Divider,
  Popconfirm,
  Switch,
  Empty
} from 'ant-design-vue'
import router from './router'
import App from './App'
import Viewer from 'v-viewer'
import 'viewerjs/dist/viewer.css'
import moment from 'moment'
import 'moment/locale/zh-cn'
import './main.scss'
import {everyPermissions, somePermissions} from "./api/permission"

Vue.filter('filterAmount', function (amount) {
  amount = Number(amount)
  return '¥' + amount.toFixed(2).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
})

Vue.filter('filterDate', function (timestamp, pattern = 'YYYY-MM-DD') {
  if (timestamp == null) {
    return ''
  }
  return moment(timestamp).format(pattern)
})

Vue.filter('filterBankAccount', function (account) {
  return account.replace(/(\d{4})(?=\d)/g, "$1 ")
})

Vue.mixin({
  methods: {
    everyPermissions(names) {
      return everyPermissions(names)
    },
    somePermissions(names) {
      return somePermissions(names)
    }
  }
})

Vue.use(Layout)
Vue.use(Row)
Vue.use(Col)
Vue.use(Menu)
Vue.use(Breadcrumb)
Vue.use(Icon)
Vue.use(Viewer)

Vue.use(Form)
Vue.use(FormModel)
Vue.use(Divider)
Vue.use(Button)
Vue.use(Input)
Vue.use(Select)
Vue.use(Checkbox)
Vue.use(InputNumber)
Vue.use(Radio)

Vue.use(Table)
Vue.use(Badge)
Vue.use(Pagination)
Vue.use(ConfigProvider)
Vue.use(Dropdown)
Vue.use(DatePicker)
Vue.use(Spin)
Vue.use(Card)
Vue.use(Tooltip)
Vue.use(Upload)
Vue.use(Tag)
Vue.use(Avatar)
Vue.use(Modal)
Vue.use(Alert)
Vue.use(Drawer)
Vue.use(Skeleton)
Vue.use(Empty)
Vue.use(Transfer)
Vue.use(Popconfirm)
Vue.use(Switch)
Vue.use(Calendar)

Vue.prototype.$message = message
Vue.prototype.$confirm = Modal.confirm
Vue.prototype.$success = Modal.success

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')


