<template>
  <div>

    <a-form-model @submit.prevent="handleSubmit" layout="inline" >
      <a-form-model-item label='生成时间：' class="date-start">
        <a-date-picker v-model="redeemCodeListForm.startCreatedDate" style="width: 120px"></a-date-picker>
      </a-form-model-item>
      <a-form-model-item label='-' :colon="false">
        <a-date-picker v-model="redeemCodeListForm.endCreatedDate" style="width: 120px"></a-date-picker>
      </a-form-model-item>
      <a-form-model-item label='绑定状态'>
        <a-select v-model="redeemCodeListForm.bound" style="width: 80px" >
          <a-select-option value="">
            全部
          </a-select-option>
          <a-select-option :value="true">
            已绑定
          </a-select-option>
          <a-select-option :value="false">
            未绑定
          </a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label='使用状态'>
        <a-select v-model="redeemCodeListForm.used" style="width: 80px" >
          <a-select-option value="">
            全部
          </a-select-option>
          <a-select-option :value="true">
            已使用
          </a-select-option>
          <a-select-option :value="false">
            未使用
          </a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item>
        <a-button type='primary' htmlType="submit">
          <a-icon type="search"/>
          查询
        </a-button>
      </a-form-model-item>
    </a-form-model>

    <div class="actions">
      <a-button type="primary" @click="showModal">
        <a-icon type="plus-circle"/>
        生成兑换码
      </a-button>
      <a-modal
        v-model="visible"
        title="生成兑换码"
        @ok="handleOk">
        <a-form-model :label-col="labelCol" :wrapper-col="wrapperCol" :model="redeemCodeForm">
          <a-form-model-item label="产品">
            <a-select style="width: 200px" v-model="redeemCodeForm.productId" @change="checkType">
              <a-select-option v-for="(item,index) in productList" :value="item.id" :key="index"  >
                {{item.name}}
              </a-select-option>
            </a-select>
          </a-form-model-item>
          <a-form-model-item label="门店">
            <a-select style="width: 200px" v-model="redeemCodeForm.shopId" :disabled="disabled">
              <a-select-option v-for="(item,index) in shopList" :value="item.id" :key="index" >
                {{item.name}}
              </a-select-option>
            </a-select>
          </a-form-model-item>
          <a-form-model-item label="生成数量">
            <a-input v-model="redeemCodeForm.codeNum" />
          </a-form-model-item>
        </a-form-model>
      </a-modal>
    </div>
    <a-table
      :columns="columns"
      rowKey="id"
      :dataSource="data"
      :pagination="pagination"
      :loading="loading"
      bordered
      @change="handleTableChange">
      <template slot="createdDate" slot-scope="createdDate">
        {{createdDate | filterDate('YYYY-MM-DD HH:mm:ss')}}
      </template>
      <template slot="bound" slot-scope="bound">
        <a-tag color="red" v-if="bound">已绑定</a-tag>
        <a-tag color="green" v-else>未绑定</a-tag>
      </template>
      <template slot="used" slot-scope="used">
        <a-tag color="red" v-if="used">已使用</a-tag>
        <a-tag color="green" v-else>可用</a-tag>
      </template>
    </a-table>
  </div>
</template>

<script>
import {
  listRedeemCodes,
  listRedeemProduct,
  listRedeemShop,
  addRedeemCode, listProducts,
} from "../../api/backstage"
import moment from "moment"

const columns = [
  {
    title: '生成时间',
    dataIndex: 'createdDate',
    sorter: true,
    defaultSortOrder: 'descend',
    scopedSlots: {customRender: 'createdDate'}
  },
  {
    title: '兑换码',
    dataIndex: 'code',
  },
  {
    title: '是否绑定',
    dataIndex: 'bound',
    scopedSlots: {customRender: 'bound'}
  },
  {
    title: '是否可用',
    dataIndex: 'used',
    scopedSlots: {customRender: 'used'}
  },
  {
    title: '产品名',
    dataIndex: 'product.name',


  },
  {
    title: '门店名',
    dataIndex: 'shop.name',
  }
]

export default {
  data() {
    return {
      columns,
      data: [],
      pagination: {
        current: 1,
        pageSize: 10,
        total: 0,
        showTotal(total) {
          return `共 ${total} 项`
        }
      },
      loading: false,
      filters: {},
      visible: false,
      labelCol: { span: 5 },
      wrapperCol: { span: 10 },
      redeemCodeForm: {
        productId:null,
        shopId:null,
        codeNum:null
      },
      redeemCodeListForm: {
        sortField: 'createdDate',
        sortOrder: 'descend',
      },
      productList: [],
      shopList: [],
      disabled: false,
    }
  },
  created() {
    this.fetch()
  },
  methods: {

    checkType(id) {
      this.disabled = this.productList.find(item=>item.id===id).type ===0;
      this.redeemCodeForm.shopId = null;
    },
    handleSubmit() {
      this.pagination = Object.assign({}, this.pagination, {
        current: 1
      })
      this.fetch()
    },
    handleOk() {
      addRedeemCode(this.redeemCodeForm)
        .then(() => {
          this.$message.success('添加成功')
        })
      this.visible = false
      this.pagination.current = 1
      this.fetch()
    },
    showModal() {
      Object.assign(this.redeemCodeForm, {
        productId: null,
        shopId: null,
        codeNum: null
      });
      listRedeemShop().then(({data}) => {
        this.shopList = data
      });
      listRedeemProduct().then(({data}) => {
        this.productList = data
      })
      this.visible = true
    },
    handleTableChange(pagination, filters, sorter) {
      this.pagination.current = pagination.current
      this.sortField = sorter.field
      this.sortOrder = sorter.order
      this.filters = filters
      this.fetch()
    },

    fetch() {
      const {startCreatedDate,endCreatedDate} = this.redeemCodeListForm
      this.redeemCodeListForm = Object.assign({}, this.redeemCodeListForm, {
        page: this.pagination.current,
        startCreatedDate: startCreatedDate && moment(startCreatedDate).format('YYYY-MM-DD 00:00:00'),
        endCreatedDate: endCreatedDate && moment(endCreatedDate).format('YYYY-MM-DD 23:59:59')
      })
      this.loading = true
      listRedeemCodes(this.redeemCodeListForm).then(({data, rows}) => {
        this.data = data
        this.pagination.total = rows
      }).catch(({message}) => {
        this.$message.error(message)
      }).then(() => {
        this.loading = false
      })
    }
  }
}
</script>
