<template>
  <div>
    <a-form-model @submit.prevent="handleQuerySubmit" layout="inline" >
      <a-form-model-item label='创建时间：' class="date-start">
        <a-date-picker v-model="productListForm.startCreatedDate" style="width: 120px"></a-date-picker>
      </a-form-model-item>
      <a-form-model-item label='-' :colon="false">
        <a-date-picker v-model="productListForm.endCreatedDate" style="width: 120px"></a-date-picker>
      </a-form-model-item>
      <a-form-model-item label='产品名称'>
        <a-input v-model="productListForm.name" style="width: 120px"/>
      </a-form-model-item>
      <a-form-model-item label='产品类型'>
        <a-select v-model="productListForm.type" style="width: 80px" >
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
        <a-select v-model="productListForm.userType" style="width: 80px" >
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
        <a-select v-model="productListForm.state" style="width: 80px" >
          <a-select-option value="">
            全部
          </a-select-option>
          <a-select-option :value="0">
            下架
          </a-select-option>
          <a-select-option :value="1">
            销售中
          </a-select-option>
          <a-select-option :value="2">
            待发布
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
      <router-link to="/backstage/product/add">
        <a-button type="primary">
          <a-icon type="plus-circle"/>
          添加
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
      <template slot="lastModifiedDate" slot-scope="lastModifiedDate">
        {{lastModifiedDate | filterDate('YYYY-MM-DD HH:mm:ss')}}
      </template>
      <template slot="type" slot-scope="text,record">
        <span v-if="record.type===0">线上远程</span>
        <span v-else-if="record.userType===0">线下/个人</span>
        <span v-else-if="record.userType===1">线下/团队</span>
      </template>
      <template slot="state" slot-scope="state">
        <a-badge v-if="state===0" status="red" text="下架"/>
        <a-badge v-else-if="state===1" status="green" text="销售中"/>
        <a-badge v-else-if="state===2" status="yellow" text="待发布"/>
      </template>
      <template slot="operation" slot-scope="record">
        <a-popconfirm
          title="确认改变状态？"
          ok-text="是"
          cancel-text="否"
          @confirm="changeState(record.id)"
        >
          <a-button style="background: green" v-if="record.state===0" >上架</a-button>
          <a-button style="background: red" v-if="record.state===1">下架</a-button>
          <a-button style="background: green" v-if="record.state===2">上架</a-button>
        </a-popconfirm>

        <router-link :to="`/backstage/product/edit?id=${record.id}`">
          <a-button type="primary">
            编辑
          </a-button>
        </router-link>
      </template>
    </a-table>
  </div>
</template>

<script>
import {
  listProducts,
  changeState,
} from "../../api/backstage"
import moment from "moment"

const columns = [
  {
    title: '创建时间',
    dataIndex: 'createdDate',
    sorter: true,
    defaultSortOrder: 'descend',
    scopedSlots: {customRender: 'createdDate'}
  },
  {
    title: '最后修改时间',
    dataIndex: 'lastModifiedDate',
    sorter: true,
    defaultSortOrder: 'descend',
    scopedSlots: {customRender: 'lastModifiedDate'}
  },
  {
    title: '产品名称',
    dataIndex: 'name',
  },
  {
    title: '产品类型',
    dataIndex: 'type',
    scopedSlots: {customRender: 'type'},
  },
  {
    title: '产品价格',
    dataIndex: 'price',
  },
  {
    title: '包含次数',
    dataIndex: 'totalAppointNum',
  },
  {
    title: '包含人数',
    dataIndex: 'peopleNum',
  },
  {
    title: '销量',
    dataIndex: 'sales',
  },
  {
    title: '状态',
    dataIndex: 'state',
    scopedSlots: {customRender: 'state'},
  },
  {
    title: '操作',
    scopedSlots: {customRender: 'operation'}
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
      labelCol: { span: 5 },
      wrapperCol: { span: 10 },
      productListForm: {
        sortField: 'createdDate',
        sortOrder: 'descend',
      },
    }
  },
  created() {
    this.fetch()
  },
  methods: {
    changeState(id) {
      changeState({id:id})
        .then(() => {
          this.fetch();
          this.$message.success('改变状态成功')
        }).catch(({message}) => {
        this.$message.error(message)
      })
    },
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
      const {startCreatedDate,endCreatedDate} = this.productListForm
      this.productListForm = Object.assign({}, this.productListForm, {
        page: this.pagination.current,
        startCreatedDate: startCreatedDate && moment(startCreatedDate).format('YYYY-MM-DD 00:00:00'),
        endCreatedDate: endCreatedDate && moment(endCreatedDate).format('YYYY-MM-DD 23:59:59')
      })
      this.loading = true
      listProducts(this.productListForm).then(({data, rows}) => {
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
