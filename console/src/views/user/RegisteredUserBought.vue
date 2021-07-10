<template>
  <div>
    <a-form-model @submit.prevent="handleSubmit" layout="inline" >
      <a-form-model-item label='购买时间：' class="date-start">
        <a-date-picker v-model="boughtListForm.startCreatedDate" style="width: 120px"></a-date-picker>
      </a-form-model-item>
      <a-form-model-item label='-' :colon="false">
        <a-date-picker v-model="boughtListForm.endCreatedDate" style="width: 120px"></a-date-picker>
      </a-form-model-item>
      <a-form-model-item label='产品名称'>
        <a-input v-model="boughtListForm.productName" style="width: 120px"/>
      </a-form-model-item>
      <a-form-model-item label='产品类型'>
        <a-select v-model="boughtListForm.productType" style="width: 80px" >
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
        <a-select v-model="boughtListForm.userType" style="width: 80px" >
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
      <a-form-model-item label='产品状态'>
        <a-select v-model="boughtListForm.productState" style="width: 80px" >
          <a-select-option value="">
            全部
          </a-select-option>
          <a-select-option :value="0">
            下架
          </a-select-option>
          <a-select-option :value="1">
            销售中
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

    <a-table
      :columns="columns"
      rowKey="id"
      :dataSource="data"
      :pagination="pagination"
      :loading="loading"
      bordered
      @change="handleTableChange">
      <template slot="boughtTime" slot-scope="boughtTime">
        {{boughtTime | filterDate('YYYY-MM-DD HH:mm:ss')}}
      </template>
      <template slot="type" slot-scope="record">
        <span v-if="record.productType===0">线上远程</span>
        <span v-else-if="record.userType===0">线下/个人</span>
        <span v-else-if="record.userType===1">线下/团体</span>
      </template>
      <template slot="validNum" slot-scope="record">
        {{record.totalNum - record.appointNum}}
      </template>
      <template slot="productState" slot-scope="productState">
        <a-tag v-if="productState===1" color="green">可用</a-tag>
        <a-tag v-if="productState===0" color="red">不可用</a-tag>
      </template>
    </a-table>
  </div>
</template>

<script>
import {listBought} from "../../api/user"
import moment from "moment"


const columns = [
  {
    title: '购买时间',
    dataIndex: 'boughtTime',
    sorter: true,
    defaultSortOrder: 'descend',
    scopedSlots: {customRender: 'boughtTime'}
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
    title: '购买价格',
    dataIndex: 'price',
  },
  {
    title: '包含次数',
    dataIndex: 'totalNum',
  },
  {
    title: '包含人数',
    dataIndex: 'peopleNum',
  },
  {
    title: '医生',
    dataIndex: 'dicItemName',
  },
  {
    title: '剩余预约/门诊次数',
    scopedSlots: {customRender: 'validNum'}
  },
  {
    title: '产品状态',
    dataIndex: 'productState',
    scopedSlots: {customRender: 'productState'}
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
      boughtListForm: {
        userId: this.$route.query.id,
        sortField: 'boughtTime',
        sortOrder: 'descend',
      },
    }
  },

  created() {
    this.fetch()
  },

  methods: {
    handleSubmit() {
      this.pagination = Object.assign({}, this.pagination, {
        current: 1
      })
      this.fetch()
    },

    handleTableChange(pagination, filters, sorter) {
      this.pagination = Object.assign({}, this.pagination, {
        current: pagination.current
      })

      this.sortField = sorter.field
      this.sortOrder = sorter.order
      this.filters = filters
      this.fetch()
    },

    fetch() {
      const {startCreatedDate, endCreatedDate} = this.boughtListForm
      this.boughtListForm = Object.assign({},this.boughtListForm,{
        page: this.pagination.current,
        startCreatedDate: startCreatedDate && moment(startCreatedDate).format('YYYY-MM-DD 00:00:00'),
        endCreatedDate: endCreatedDate && moment(endCreatedDate).format('YYYY-MM-DD 23:59:59')
      })
      this.loading = true
      listBought(this.boughtListForm).then(({data, rows}) => {
        this.data = data
        this.pagination = Object.assign({}, this.pagination, {
          total: rows
        })
      }).catch(({message}) => {
        this.$message.error(message)
      }).then(() => {
        this.loading = false
      })
    }
  }
}
</script>
