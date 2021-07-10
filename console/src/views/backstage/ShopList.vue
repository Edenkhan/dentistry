<template>
  <div>
    <a-form-model @submit.prevent="handleQuerySubmit" layout="inline">
      <a-form-model-item label='创建时间：' class="date-start">
        <a-date-picker v-model="shopListForm.startCreatedDate" style="width: 120px"></a-date-picker>
      </a-form-model-item>
      <a-form-model-item label='-' :colon="false">
        <a-date-picker v-model="shopListForm.endCreatedDate" style="width: 120px"></a-date-picker>
      </a-form-model-item>
      <a-form-model-item label='门店名称'>
        <a-input v-model="shopListForm.name" style="width: 120px"/>
      </a-form-model-item>
      <a-form-model-item label='门店地址'>
        <a-input v-model="shopListForm.address" style="width: 120px"/>
      </a-form-model-item>
      <a-form-model-item label='门店状态'>
        <a-select v-model="shopListForm.enabled" style="width: 80px">
          <a-select-option value="">
            全部
          </a-select-option>
          <a-select-option :value="true">
            启用
          </a-select-option>
          <a-select-option :value="false">
            禁用
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
      <a-button type="primary" @click="addShop">
        <a-icon type="plus-circle"/>
        添加
      </a-button>
      <a-modal
        v-model="visible"
        :title="modalTitle"
        @ok="handleSubmit">
        <a-form-model :label-col="labelCol" :wrapper-col="wrapperCol" v-if="modalTitle!==this.modalTitles[2]">
          <a-form-model-item label="门店名称">
            <a-input v-model="shopForm.name"/>
          </a-form-model-item>
          <a-form-model-item label="门店地址">
            <a-input v-model="shopForm.address"/>
          </a-form-model-item>
          <a-form-model-item label="门店手机">
            <a-input v-model="shopForm.phone"/>
          </a-form-model-item>
          <a-form-model-item label="状态">
            <a-switch default-checked v-model="shopForm.enabled"/>
          </a-form-model-item>
        </a-form-model>
        <a-form-model :label-col="labelCol" :wrapper-col="wrapperCol" v-if="modalTitle===this.modalTitles[2]">
          <a-form-model-item label="可预约次数">
            <a-input v-model="shopForm.validNum"/>
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
        {{ createdDate | filterDate('YYYY-MM-DD HH:mm:ss') }}
      </template>
      <template slot="lastModifiedDate" slot-scope="lastModifiedDate">
        {{ lastModifiedDate | filterDate('YYYY-MM-DD HH:mm:ss') }}
      </template>
      <template slot="address" slot-scope="text,record">
        <span v-if="record.address===0">线上远程</span>
        <span v-else-if="record.userType===0">线下/个人</span>
        <span v-else-if="record.userType===1">线下/团队</span>
      </template>
      <template slot="enabled" slot-scope="enabled">
        <a-tag color="#87d068" v-if="enabled">启用</a-tag>
        <a-tag color="#f5222d" v-if="!enabled">停用</a-tag>
      </template>
      <template slot="operation" slot-scope="record">
        <a-button type="primary" @click="editShop(record.id)">
          编辑
        </a-button>
        <a-button type="primary" @click="addValid(record.id)">
          添加可预约次数
        </a-button>
        <router-link :to="`/backstage/appointManage/list?id=${record.id}`">
          <a-button type="primary">
            预约情况
          </a-button>
        </router-link>
      </template>
    </a-table>
  </div>
</template>

<script>
import {
  addShop, editShop, getShop, listShops, addValid
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
    title: '门店名称',
    dataIndex: 'name',
  },
  {
    title: '门店地址',
    dataIndex: 'address',
  },
  {
    title: '门店手机',
    dataIndex: 'phone',
  },
  {
    title: '可预约次数',
    dataIndex: 'validNum',
  },
  {
    title: '已预约次数',
    dataIndex: 'appointedNum',
  },
  {
    title: '状态',
    dataIndex: 'enabled',
    scopedSlots: {customRender: 'enabled'},
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
      labelCol: {span: 5},
      wrapperCol: {span: 10},
      visible: false,
      shopListForm: {
        sortField: 'createdDate',
        sortOrder: 'descend',
      },
      shopForm: {},
      modalTitle: '',
      modalTitles: ['添加门店', '修改门店', '添加可预约次数'],
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
    addShopSubmit() {
      addShop(this.shopForm).then(() => {
        this.$message.success("添加成功")
      }).catch(({message}) => {
        this.$message.error(message)
      })
    },
    editShopSubmit() {
      editShop(this.shopForm).then(() => {
        this.$message.success("修改成功")
      }).catch(({message}) => {
        this.$message.error(message)
      })
    },
    addValidSubmit() {
      addValid(this.shopForm).then(() => {
        this.$message.success("修改成功")
      }).catch(({message}) => {
        this.$message.error(message)
      })
    },
    handleSubmit() {
      this.visible = false
      if (this.modalTitle === this.modalTitles[0]) {
        this.addShopSubmit()
      } else if (this.modalTitle === this.modalTitles[1]) {
        this.editShopSubmit()
      } else if (this.modalTitle === this.modalTitles[2]) {
        this.addValidSubmit()
      }
      this.pagination.current = 1
      this.fetch()
    },
    addShop() {
      this.shopForm = {}
      this.modalTitle = this.modalTitles[0]
      this.visible = true
    },
    editShop(id) {
      this.modalTitle = this.modalTitles[1]
      this.getShop(id)
      this.visible = true
    },
    addValid(id) {
      this.modalTitle = this.modalTitles[2]
      this.shopForm = {}
      this.shopForm.id = id
      this.visible = true
    },
    getShop(id) {
      this.shopForm.id = id
      getShop({id: id})
        .then(shop => {
          this.shopForm = Object.assign({}, this.shopForm, {
            name: shop.name,
            address: shop.address,
            phone: shop.phone,
            validNum: shop.validNum,
            appointedNum: shop.appointedNum,
            enabled: shop.enabled,
          })
        })
    },
    handleTableChange(pagination, filters, sorter) {
      this.pagination.current = pagination.current
      this.sortField = sorter.field
      this.sortOrder = sorter.order
      this.filters = filters
      this.fetch()
    },
    fetch() {
      const {startCreatedDate, endCreatedDate} = this.shopListForm
      this.shopListForm = Object.assign({}, this.shopListForm, {
        page: this.pagination.current,
        startCreatedDate: startCreatedDate && moment(startCreatedDate).format('YYYY-MM-DD 00:00:00'),
        endCreatedDate: endCreatedDate && moment(endCreatedDate).format('YYYY-MM-DD 23:59:59')
      })
      this.loading = true
      listShops(this.shopListForm).then(({data, rows}) => {
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
