<template>
  <div>
    <a-form-model @submit.prevent="handleSubmit" layout="inline">
      <a-form-model-item label='上传时间：' class="date-start">
        <a-date-picker v-model="reportListForm.startCreatedDate" style="width: 120px"></a-date-picker>
      </a-form-model-item>
      <a-form-model-item label='-' :colon="false">
        <a-date-picker v-model="reportListForm.endCreatedDate" style="width: 120px"></a-date-picker>
      </a-form-model-item>
      <a-form-model-item label='产品名称'>
        <a-input v-model="reportListForm.productName" style="width: 120px"/>
      </a-form-model-item>
      <a-form-model-item label='产品类型'>
        <a-select v-model="reportListForm.productType" style="width: 80px">
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
        <a-select v-model="reportListForm.userType" style="width: 80px">
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
        <a-button type='primary' htmlType="submit">
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
      <template slot="createdDate" slot-scope="createdDate">
        {{ createdDate | filterDate('YYYY-MM-DD HH:mm:ss') }}
      </template>
      <template slot="type" slot-scope="record">
        <span v-if="record.productType===0">线上远程</span>
        <span v-else-if="record.userType===0">线下/个人</span>
        <span v-else-if="record.userType===1">线下/团体</span>
      </template>
      <template slot="sync" slot-scope="sync">
        <a-tag color="green" v-if="sync">已同步</a-tag>
        <a-tag color="#ABABAB" v-if="!sync">未同步</a-tag>
      </template>
      <template slot="operation" slot-scope="record">
        <a-popconfirm
          title="确认同步？"
          ok-text="是"
          cancel-text="否"
          @confirm="changeSync(record.id,!record.sync)"
        >
          <a-button style="background: #faad14" v-if="!record.sync">同步</a-button>
        </a-popconfirm>
        <a-button disabled style="background: #ABABAB" v-if="record.sync">同步</a-button>
        <a-button style="background: #52c41a"><a :href="record.path.split(',')[0]" target="_blank">下载</a></a-button>
        <a-button style="background: aquamarine">查看</a-button>
        <a-button style="background: navajowhite" @click="showModal(record.id)">重新上传</a-button>
        <a-upload
          name="file"
          :multiple="true"
          action="/api/backstage/report/upload"
          :headers="headers"
          @change="handleChange"
        >

        </a-upload>
      </template>
    </a-table>

    <a-modal v-model="visible" title="重新上传报告" @ok="handleOk">
      <p>
        {{appoint.appointDate?appoint.appointDate.split('T')[0]:''}} {{appoint.timePeriod===0?'上午':'下午'}} /
        {{appoint.productName}} /
        {{appoint.shopName}} /
        {{`${appoint.peopleNum}人`}}
      </p>
      <hr>
      <a-upload
        name="file"
        :multiple="true"
        action="/api/backstage/report/upload"
        :headers="headers"
        @change="handleChange"
        :before-upload="beforeUpload"
      >
        <a-button type="primary">
          <a-icon type="upload"/>
          添加文件
        </a-button>
      </a-upload>
    </a-modal>

  </div>
</template>

<script>
import {
  listReport,
  editReport,
  getAppoint,
} from "../../api/backstage"
import moment from "moment"
import {resetReport} from "../../api/backstage";


const columns = [
  {
    title: '上传时间',
    dataIndex: 'createdDate',
    sorter: true,
    defaultSortOrder: 'descend',
    scopedSlots: {customRender: 'createdDate'}
  },
  {
    title: '报告编号',
    dataIndex: 'reportNo',
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
    title: '同步状态',
    dataIndex: 'sync',
    scopedSlots: {customRender: 'sync'}
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
      visible: false,
      filters: {},
      reportListForm: {
        userId: this.$route.query.id,
        sortField: 'createdDate',
        sortOrder: 'descend',
      },
      headers: {authorization: 'authorization-text'},
      pathList: [],
      reportForm: {},
      appoint: {},

    }
  },

  created() {
    this.fetch()
  },

  methods: {
    beforeUpload(file) {
      const isPdf = file.type === 'application/pdf'
      if(!isPdf) {
        this.$message.error('只能上传pdf格式文件')
      }
      return isPdf
    },
    handleOk() {
      this.reportForm = Object.assign({},this.reportForm,{
        pathList: this.pathList
      })
      console.log(this.reportForm)
      resetReport(this.reportForm)
        .then(() => {
          this.$message.success('添加报告成功')
          this.visible = false
          this.fetch()
        }).catch(({message}) => {
        this.$message.error(message)
      })
    },
    showModal(id) {
      this.appoint = {}
      this.pathList = []
      this.reportForm = {}
      this.reportForm.id = id
      getAppoint({id: id})
        .then(data => {
          this.appoint = data
        })
      this.visible = true
    },
    handleChange(info) {
      if (info.file.status === 'done') {
        this.pathList.push(info.file.response)
        this.$message.success(`${info.file.name} file uploaded successfully`);
      } else if (info.file.status === 'error') {
        this.$message.error(`${info.file.name} file upload failed.`);
      }
    },
    changeSync(id, sync) {
      editReport({id: id, sync: sync})
        .then(() => {
          this.$message.success('已同步')
          this.fetch()
        })
    },
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
      const {startCreatedDate, endCreatedDate} = this.reportListForm
      this.reportListForm = Object.assign({}, this.reportListForm, {
        page: this.pagination.current,
        startCreatedDate: startCreatedDate && moment(startCreatedDate).format('YYYY-MM-DD 00:00:00'),
        endCreatedDate: endCreatedDate && moment(endCreatedDate).format('YYYY-MM-DD 23:59:59')
      })
      this.loading = true
      listReport(this.reportListForm).then(({data, rows}) => {
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
