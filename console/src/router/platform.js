import EmployeeChangePassword from "../views/platform/EmployeeChangePassword";

const EmployeeList = () => import(/* webpackChunkName: "platform" */ '../views/platform/EmployeeList.vue');
const EmployeeEdit = () => import(/* webpackChunkName: "platform" */ '../views/platform/EmployeeEdit.vue');
const PermissionList = () => import(/* webpackChunkName: "platform" */ '../views/platform/PermissionList.vue');
const PermissionEdit = () => import(/* webpackChunkName: "platform" */ '../views/platform/PermissionEdit.vue');
const RoleList = () => import(/* webpackChunkName: "platform" */ '../views/platform/RoleList.vue');
const RoleEdit = () => import(/* webpackChunkName: "platform" */ '../views/platform/RoleEdit.vue');

export default [
  // 员工
  {
    path: '/platform/employee/list',
    component: EmployeeList,
    meta: {
      title: '员工列表',
      permission: 'platform.employee.list'
    }
  },
  {
    path: '/platform/employee/add',
    component: EmployeeEdit,
    meta: {
      title: '添加员工',
      permission: 'platform.employee.add',
      parents: [
        {
          path: '/platform/employee/list',
          title: '员工列表'
        }
      ]
    }
  },
  {
    path: '/platform/employee/edit',
    component: EmployeeEdit,
    meta: {
      title: '修改员工',
      permission: 'platform.employee.edit',
      parents: [
        {
          path: '/platform/employee/list',
          title: '员工列表'
        }
      ]
    }
  },
  {
    path: '/platform/employee/changePassword',
    component: EmployeeChangePassword,
    meta: {
      title: '修改登录密码'
    }
  },
  // 权限
  {
    path: '/platform/permission/list',
    component: PermissionList,
    meta: {
      title: '权限列表',
      permission: 'platform.permission.list'
    }
  },
  {
    path: '/platform/permission/add',
    component: PermissionEdit,
    meta: {
      title: '添加权限',
      permission: 'platform.permission.add',
      parents: [
        {
          path: '/platform/permission/list',
          title: '权限列表'
        }
      ]
    }
  },
  {
    path: '/platform/permission/edit',
    component: PermissionEdit,
    meta: {
      title: '修改权限',
      permission: 'platform.permission.edit',
      parents: [
        {
          path: '/platform/permission/list',
          title: '权限列表'
        }
      ]
    }
  },
  // 角色
  {
    path: '/platform/role/list',
    component: RoleList,
    meta: {
      title: '角色列表',
      permission: 'platform.role.list'
    }
  },
  {
    path: '/platform/role/add',
    component: RoleEdit,
    meta: {
      title: '添加角色',
      permission: 'platform.role.add',
      parents: [
        {
          path: '/platform/role/list',
          title: '角色列表'
        }
      ]
    }
  },
  {
    path: '/platform/role/edit',
    component: RoleEdit,
    meta: {
      title: '修改角色',
      permission: 'platform.role.edit',
      parents: [
        {
          path: '/platform/role/list',
          title: '角色列表'
        }
      ]
    }
  },
];
