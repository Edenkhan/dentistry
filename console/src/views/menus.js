import {everyPermissions} from "../api/permission";

const allMenus = [
  {
    title: '用户管理',
    key: 'user',
    icon: 'user',
    subMenus: [
      {
        key: '/user/list',
        title: '用户列表',
        permission: 'user.user.list'
      }
    ]
  },
  {
    title: '消息管理',
    key: 'message',
    icon: 'message',
    subMenus: [
      {
        key: '/message/sms/list',
        title: '短信消息',
        permission: 'message.sms.list'
      },
      {
        key: '/message/smsVerification/list',
        title: '验证短信',
        permission: 'message.smsVerification.list'
      }
    ]
  },
  {
    title: '系统管理',
    key: 'platform',
    icon: 'setting',
    subMenus: [
      {
        key: '/platform/employee/list',
        title: '员工列表',
        permission: 'platform.employee.list'
      },
      {
        key: '/platform/role/list',
        title: '角色列表',
        permission: 'platform.role.list'
      },
      {
        key: '/platform/permission/list',
        title: '权限列表',
        permission: 'platform.permission.list'
      }
    ]
  },
  // 字典管理
  {
    title: '字典管理',
    key: 'dictionary',
    icon: 'setting',
    subMenus: [
      {
        key: '/backstage/dictionary/list',
        title: '数据字典',
        permission: 'backstage.dictionary.list'
      },
      {
        key: '/backstage/dictionaryitem/list',
        title: '数据字典详情',
        permission: 'backstage.dictionaryitem.list'
      },
    ]
  },
  // 产品管理
  {
    title: '产品管理',
    key: 'product',
    icon: 'setting',
    subMenus: [
      {
        key: '/backstage/product/list',
        title: '产品',
        permission: 'backstage.product.list'
      },
    ]
  },
  // 门店管理
  {
    title: '门店管理',
    key: 'shop',
    icon: 'setting',
    subMenus: [
      {
        key: '/backstage/shop/list',
        title: '门店',
        permission: 'backstage.shop.list'
      },
    ]
  },
  // 记录管理
  {
    title: '记录管理',
    key: 'recordManage',
    icon: 'setting',
    subMenus: [
      {
        key: '/backstage/orderRecord/list',
        title: '订单记录',
        permission: 'backstage.orderRecord.list'
      },
      {
        key: '/backstage/appointRecord/list',
        title: '预约记录',
        permission: 'backstage.appointRecord.list'
      },
    ]
  },
];

export function getMenus() {
  return allMenus.reduce((prev, menu) => {
    const subMenus = menu.subMenus.reduce((subPrev, subMenu) => {
      if (everyPermissions(subMenu.permission)) {
        subPrev.push(Object.assign({}, subMenu));
      }
      return subPrev;
    }, []);
    if (subMenus.length > 0) {
      prev.push(Object.assign({}, menu, {
        subMenus
      }));
    }
    return prev;
  }, []);
}
