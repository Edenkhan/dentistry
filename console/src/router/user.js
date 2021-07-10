const RegisteredUserList = () => import(/* webpackChunkName: "user" */ '../views/user/RegisteredUserList.vue');
const RegisteredUserEdit = () => import(/* webpackChunkName: "user" */ '../views/user/RegisteredUserEdit.vue');
const RegisteredUserBought = () => import(/* webpackChunkName: "user" */ '../views/user/RegisteredUserBought.vue');

export default [
  {
    path: '/user/list',
    component: RegisteredUserList,
    meta: {
      title: '用户列表',
      permission: 'user.user.list'
    }
  },
  {
    path: '/user/add',
    component: RegisteredUserEdit,
    meta: {
      title: '添加用户',
      permission: 'user.user.add',
      parents: [
        {
          path: '/user/list',
          title: '用户列表'
        }
      ]
    }
  },
  {
    path: '/user/edit',
    component: RegisteredUserEdit,
    meta: {
      title: '修改用户',
      permission: 'user.user.edit',
      parents: [
        {
          path: '/user/list',
          title: '用户列表'
        }
      ]
    }
  },
  {
    path: '/user/bought',
    component: RegisteredUserBought,
    meta: {
      title: '已购产品列表',
      permission: 'user.user.bought',
      parents: [
        {
          path: '/user/list',
          title: '用户列表'
        }
      ]
    }
  }
];
