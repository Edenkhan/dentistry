<template>
  <div>

    <a-form-model @submit.prevent="handleQuerySubmit" layout="inline" >
      <a-form-model-item label='创建时间：' class="date-start">
        <a-date-picker v-model="dictionaryItemListForm.startCreatedDate" style="width: 120px"></a-date-picker>
      </a-form-model-item>
      <a-form-model-item label='-' :colon="false">
        <a-date-picker v-model="dictionaryItemListForm.endCreatedDate" style="width: 120px"></a-date-picker>
      </a-form-model-item>
      <a-form-model-item label='字典名称'>
        <a-input v-model="dictionaryItemListForm.dictionaryName" style="width: 120px"/>
      </a-form-model-item>
      <a-form-model-item label='详情名称'>
        <a-input v-model="dictionaryItemListForm.name" style="width: 120px"/>
      </a-form-model-item>
      <a-form-model-item label='状态'>
        <a-select v-model="dictionaryItemListForm.enabled" style="width: 80px" >
          <a-select-option value="">
            全部
          </a-select-option>
          <a-select-option :value="true">
            启用
          </a-select-option>
          <a-select-option :value="false">
            停用
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
      <a-button type="primary" @click="addDictionaryItem">
        <a-icon type="plus-circle"/>
        添加
      </a-button>
      <a-modal
        v-model="visible"
        :title="modalTitle"
        @ok="handleSubmit">
        <a-form-model :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-form-model-item label="详情名称">
            <a-input v-model="dictionaryItemForm.name" />
          </a-form-model-item>
          <a-form-model-item label="数据字典名称">
            <a-select style="width: 80px" v-model="dictionaryItemForm.dictionaryId" v-if="dictionaryItemForm.id">
              <a-select-option v-for="(item,index) in dictionarys" :value="item.id" :key="index" disabled>
                {{item.name}}
              </a-select-option>
            </a-select>
            <a-select style="width: 80px" v-model="dictionaryItemForm.dictionaryId" v-else>
              <a-select-option v-for="(item,index) in dictionarys" :value="item.id" :key="index" >
                {{item.name}}
              </a-select-option>
            </a-select>
          </a-form-model-item>
          <a-form-model-item label="状态">
            <a-switch default-checked v-model="dictionaryItemForm.enabled" />
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
      <template slot="lastModifiedDate" slot-scope="lastModifiedDate">
        {{lastModifiedDate | filterDate('YYYY-MM-DD HH:mm:ss')}}
      </template>
      <template slot="enabled" slot-scope="enabled">
        <a-tag color="#87d068" v-if="enabled">启用</a-tag>
        <a-tag color="#f5222d" v-if="!enabled">停用</a-tag>
      </template>
      <template slot="operation" slot-scope="record">
        <a-popconfirm
          title="确认改变状态？"
          ok-text="是"
          cancel-text="否"
          @confirm="changeStatus(record.id)"
        >
          <a-button style="background: green" v-if="!record.enabled">启用</a-button>
          <a-button style="background: red" v-if="record.enabled" >停用</a-button>
        </a-popconfirm>
        <a-button type="primary" @click="editDictionaryItem(record.id)">
          编辑
        </a-button>
      </template>
    </a-table>
  </div>
</template>

<script>
import {
  addDictionaryItem,
  editDictionaryItem,
  getDictionaryItem,
  listDictionaryItems,
  listAllDictionarys,
  changeDicItemStatus,
} from "../../api/backstage"
import moment from "moment"
import PhoneNumber from "../user/PhoneNumber"

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
    title: '父字典名称',
    dataIndex: 'dictionary.name',
  },
  {
    title: '详情名称',
    dataIndex: 'name',
  },
  {
    title: '字典标识',
    dataIndex: 'dictionary.mark',
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
  components: {
    PhoneNumber,
  },
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
      dictionaryItemForm: {},
      modalTitle: '',
      dictionaryItemListForm: {
        sortField: 'createdDate',
        sortOrder: 'descend',
      },
      dictionarys: [],
    }
  },
  created() {
    this.fetch()
  },
  methods: {
    changeStatus(id) {
      changeDicItemStatus({id:id})
        .then(() => {
          this.$message.success('改变状态成功')
          this.pagination.current = 1
          this.fetch()
        }).catch(({message}) => {
          this.$message.error(message)
      })
    },
    getDictionarys() {
      listAllDictionarys().
        then(({data}) => {
          this.dictionarys = data
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
    addDictionaryItemSubmit() {
      addDictionaryItem(this.dictionaryItemForm).then(() => {
        this.$message.success("添加成功")
      }).catch(({message}) => {
        this.$message.error(message)
      })
    },
    editDictionaryItemSubmit() {
      editDictionaryItem(this.dictionaryItemForm).then(() => {
        this.$message.success("修改成功")
      }).catch(({message}) => {
        this.$message.error(message)
      })
    },
    handleSubmit() {
      this.visible = false
      if(this.dictionaryItemForm.id) {
        this.editDictionaryItemSubmit()
      }else{
        this.addDictionaryItemSubmit()
      }
      this.pagination.current = 1
      this.fetch()
    },
    addDictionaryItem(){
      this.dictionaryItemForm = {}
      this.getDictionarys()
      this.modalTitle = '添加数据字典详情'
      this.visible = true
    },
    editDictionaryItem(id){
      this.modalTitle = '修改数据字典详情'
      this.showModal(id)
      this.getDictionaryItem(id)
    },
    getDictionaryItem(id){
      getDictionaryItem({id: id})
        .then(dictionaryItem => {
          this.dictionaryItemForm = Object.assign({},this.dictionaryItemForm,{
            name: dictionaryItem.name,
            enabled: dictionaryItem.enabled,
            dictionaryId: dictionaryItem.dictionaryId,
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
    showModal(id) {
      this.dictionaryItemForm.id = id
      this.getDictionarys()
      this.visible = true
    },
    fetch() {
      const {startCreatedDate,endCreatedDate} = this.dictionaryItemListForm
      this.dictionaryItemListForm = Object.assign({}, this.dictionaryItemListForm, {
        page: this.pagination.current,
        startCreatedDate: startCreatedDate && moment(startCreatedDate).format('YYYY-MM-DD 00:00:00'),
        endCreatedDate: endCreatedDate && moment(endCreatedDate).format('YYYY-MM-DD 23:59:59')
      })
      this.loading = true
      listDictionaryItems(this.dictionaryItemListForm).then(({data, rows}) => {
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
