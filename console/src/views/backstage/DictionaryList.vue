<template>
  <div>
    <div class="actions">
      <a-button type="primary" @click="addDictionary">
        <a-icon type="plus-circle"/>
        添加
      </a-button>
      <a-modal
        v-model="visible"
        :title="modalTitle"
        @ok="handleSubmit">
        <a-form-model :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-form-model-item label="字典名">
            <a-input v-model="dictionaryForm.name" />
          </a-form-model-item>
          <a-form-model-item label="字典标识">
            <a-input v-model="dictionaryForm.mark" />
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
      <template slot="operation" slot-scope="record">
        <a-button type="primary" @click="editDictionary(record.id)">
          编辑
        </a-button>
      </template>
    </a-table>
  </div>
</template>

<script>
import {
  addDictionary,
  editDictionary,
  getDictionary,
  listDictionarys
} from "../../api/backstage"
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
    title: '名称',
    dataIndex: 'name',
  },
  {
    title: '标识',
    dataIndex: 'mark',
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
      dictionaryForm: {},
      modalTitle: '',
      dictionaryListForm: {
        sortField: 'createdDate',
        sortOrder: 'descend',
      },
    }
  },
  created() {
    this.fetch()
  },
  methods: {
    addDictionarySubmit() {
      addDictionary(this.dictionaryForm).then(() => {
        this.$message.success("添加成功")
        }).catch(({message}) => {
          this.$message.error(message)
      })
    },
    editDictionarySubmit() {
      editDictionary(this.dictionaryForm).then(() => {
        this.$message.success("修改成功")
      }).catch(({message}) => {
        this.$message.error(message)
      })
    },
    handleSubmit() {
      this.visible = false
      if(this.dictionaryForm.id) {
        this.editDictionarySubmit()
      }else{
        this.addDictionarySubmit()
      }
      this.pagination.current = 1
      this.fetch()
    },
    addDictionary(){
      this.modalTitle = '添加数据字典'
      this.dictionaryForm = {}
      this.visible = true
    },
    editDictionary(id){
      this.modalTitle = '修改数据字典'
      this.visible = true
      this.getDictionary(id)
    },
    getDictionary(id){
      this.dictionaryForm.id = id
      getDictionary({id: id})
        .then(dictionary => {
          this.dictionaryForm = Object.assign({},this.dictionaryForm,{
            name: dictionary.name,
            mark: dictionary.mark,
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
      this.dictionaryListForm = Object.assign({},this.dictionaryListForm,{
        page: this.pagination.current,
      })
      this.loading = true
      listDictionarys(this.dictionaryListForm).then(({data, rows}) => {
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
