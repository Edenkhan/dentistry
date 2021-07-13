const DictionaryList = () => import(/* webpackChunkName: "backstage" */ '../views/backstage/DictionaryList.vue');
const DictionaryItemList = () => import(/* webpackChunkName: "backstage" */ '../views/backstage/DictionaryItemList.vue');
const ProductList = () => import(/* webpackChunkName: "backstage" */ '../views/backstage/ProductList.vue');
const ProductEdit = () => import(/* webpackChunkName: "backstage" */ '../views/backstage/ProductEdit.vue');
const ShopList = () => import(/* webpackChunkName: "backstage" */ '../views/backstage/ShopList.vue');
const AppointManageList = () => import(/* webpackChunkName: "backstage" */ '../views/backstage/AppointManageList.vue');
const OrderRecordList = () => import(/* webpackChunkName: "backstage" */ '../views/backstage/OrderRecordList.vue');
const AppointRecordList = () => import(/* webpackChunkName: "backstage" */ '../views/backstage/AppointRecordList.vue');
const RedeemCodeList = () => import(/* webpackChunkName: "backstage" */ '../views/backstage/RedeemCodeList.vue');

export default [
  // 字典
  {
    path: '/backstage/dictionary/list',
    component: DictionaryList,
    meta: {
      title: '数据字典',
      permission: 'backstage.dictionary.list'
    }
  },
  // 字典详情
  {
    path: '/backstage/dictionaryItem/list',
    component: DictionaryItemList,
    meta: {
      title: '数据字典详情',
      permission: 'backstage.dictionaryItem.list'
    }
  },
  // 产品列表
  {
    path: '/backstage/product/list',
    component: ProductList,
    meta: {
      title: '产品',
      permission: 'backstage.product.list'
    }
  },
  // 产品添加
  {
    path: '/backstage/product/add',
    component: ProductEdit,
    meta: {
      title: '添加',
      permission: 'backstage.product.add',
      parents: [
        {
          path: '/backstage/product/list',
          title: '产品',
        }
      ]
    }
  },
  // 产品编辑
  {
    path: '/backstage/product/edit',
    component: ProductEdit,
    meta: {
      title: '编辑',
      permission: 'backstage.product.edit',
      parents: [
        {
          path: '/backstage/product/list',
          title: '产品',
        }
      ]
    }
  },
  // 门店列表
  {
    path: '/backstage/shop/list',
    component: ShopList,
    meta: {
      title: '门店',
      permission: 'backstage.shop.list'
    }
  },
  // 预约管理列表
  {
    path: '/backstage/appointManage/list',
    component: AppointManageList,
    meta: {
      title: '预约',
      permission: 'backstage.appointManage.list',
      parents: [
        {
          path: '/backstage/shop/list',
          title: '门店',
        }
      ]
    }
  },

  // 订单记录列表
  {
    path: '/backstage/orderRecord/list',
    component: OrderRecordList,
    meta: {
      title: '订单记录',
      permission: 'backstage.orderRecord.list',
    }
  },

  // 预约记录列表
  {
    path: '/backstage/appointRecord/list',
    component: AppointRecordList,
    meta: {
      title: '订单记录',
      permission: 'backstage.appointRecord.list',
    }
  },

  // 兑换码列表
  {
    path: '/backstage/redeemcode/list',
    component: RedeemCodeList,
    meta: {
      title: '兑换码',
      permission: 'backstage.redeemcode.list',
    }
  },

];
