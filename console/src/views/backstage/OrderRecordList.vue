<template>
  <div>
    <a-form-model @submit.prevent="handleQuerySubmit" layout="inline" >
      <a-form-model-item label='下单时间：' class="date-start">
        <a-date-picker v-model="orderRecordListForm.startCreatedDate" style="width: 120px"></a-date-picker>
      </a-form-model-item>
      <a-form-model-item label='-' :colon="false">
        <a-date-picker v-model="orderRecordListForm.endCreatedDate" style="width: 120px"></a-date-picker>
      </a-form-model-item>
      <a-form-model-item label='订单编号'>
        <a-input v-model="orderRecordListForm.orderNo" style="width: 120px"/>
      </a-form-model-item>
      <a-form-model-item label='用户名称'>
        <a-input v-model="orderRecordListForm.realName" style="width: 120px"/>
      </a-form-model-item>
      <a-form-model-item label='用户手机'>
        <a-input v-model="orderRecordListForm.phoneNumber" style="width: 120px"/>
      </a-form-model-item>
      <a-form-model-item label='店面名称'>
        <a-input v-model="orderRecordListForm.shopName" style="width: 120px"/>
      </a-form-model-item>
      <a-form-model-item label='产品类型'>
        <a-select v-model="orderRecordListForm.productType" style="width: 80px" >
          <a-select-option value="">
            全部
          </a-select-option>
          <a-select-option :value="0">
            线上
          </a-select-option>
          <a-select-option :value="1">
            线下
          </a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label='用户类型'>
        <a-select v-model="orderRecordListForm.userType" style="width: 80px" >
          <a-select-option value="">
            全部
          </a-select-option>
          <a-select-option :value="0">
            个人
          </a-select-option>
          <a-select-option :value="1">
            团队
          </a-select-option>
        </a-select>
      </a-form-model-item>
      <a-form-model-item>
        <a-button type='primary' htmlType="submit" >
          <a-icon type="search"/>
          查询
        </a-button>
      </a-form-model-item>
    </a-form-model>

    <div class="actions">
      <router-link to="">
        <a-button type="primary">
          <a-icon type="plus-circle"/>
          导出表格
        </a-button>
      </router-link>
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
      <template slot="type" slot-scope="text,record">
        <span v-if="record.productType===0">线上远程</span>
        <span v-else-if="record.userType===0">线下/个人</span>
        <span v-else-if="record.userType===1">线下/团队</span>
      </template>
    </a-table>
  </div>
</template>

<script>
import {
  listOrderRecord,
} from "../../api/backstage"
import moment from "moment"

const columns = [
  {
    title: '下单时间',
    dataIndex: 'createdDate',
    sorter: true,
    defaultSortOrder: 'descend',
    scopedSlots: {customRender: 'createdDate'}
  },
  {
    title: '订单金额',
    dataIndex: 'price',
  },
  {
    title: '订单编号',
    dataIndex: 'orderNo',
  },
  {
    title: '产品名称',
    dataIndex: 'productName',
  },
  {
    title: '产品类型',
    scopedSlots: {customRender: 'type'}
  },
  {
    title: '门店名称',
    dataIndex: 'shopName',
  },
  {
    title: '用户名称',
    dataIndex: 'realName',
  },
  {
    title: '用户手机',
    dataIndex: 'phoneNumber',
  },
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
      labelCol: { span: 5 },
      wrapperCol: { span: 10 },
      orderRecordListForm: {
        sortField: 'createdDate',
        sortOrder: 'descend',
      },
    }
  },
  created() {
    this.fetch()
  },
  methods: {
    handleQuerySubmit() {
      this.pagination = Object.assign({}, this.pagination, {
        current: 1
      })
      this.fetch()
    },
    handleTableChange(pagination, filters, sorter) {
      this.pagination.current = pagination.current
      this.sortField = sorter.field
      this.sortOrder = sorter.order
      this.filters = filters
      this.fetch()
    },
    fetch() {
      const {startCreatedDate,endCreatedDate} = this.orderRecordListForm
      this.orderRecordListForm = Object.assign({}, this.orderRecordListForm, {
        page: this.pagination.current,
        startCreatedDate: startCreatedDate && moment(startCreatedDate).format('YYYY-MM-DD 00:00:00'),
        endCreatedDate: endCreatedDate && moment(endCreatedDate).format('YYYY-MM-DD 23:59:59')
      })
      this.loading = true
      listOrderRecord(this.orderRecordListForm).then(({data, rows}) => {
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
