const SmsMessage = () => import(/* webpackChunkName: "message" */ '../views/message/SmsMessage.vue');
const SmsVerification = () => import(/* webpackChunkName: "message" */ '../views/message/SmsVerification.vue');

export default [
  {
    path: '/message/sms/list',
    component: SmsMessage,
    meta: {
      title: '短信消息',
      permission: 'message.sms.list'
    }
  },
  {
    path: '/message/smsVerification/list',
    component: SmsVerification,
    meta: {
      title: '验证短信',
      permission: 'message.smsVerification.list'
    }
  }
];
