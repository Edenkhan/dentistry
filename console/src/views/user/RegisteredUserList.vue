<template>
  <div>
    <a-form-model @submit.prevent="handleSubmit" layout="inline" >
      <a-form-model-item label='注册时间：' class="date-start">
        <a-date-picker v-model="userListForm.startCreatedDate" style="width: 120px"></a-date-picker>
      </a-form-model-item>
      <a-form-model-item label='-' :colon="false">
        <a-date-picker v-model="userListForm.endCreatedDate" style="width: 120px"></a-date-picker>
      </a-form-model-item>
      <a-form-model-item label='姓名'>
        <a-input v-model="userListForm.realName" style="width: 120px"/>
      </a-form-model-item>
      <a-form-model-item label='手机号'>
        <a-input v-model="userListForm.phoneNumber" style="width: 120px"/>
      </a-form-model-item>
      <a-form-model-item label='模板消息状态'>
        <a-select v-model="userListForm.state" style="width: 80px" >
          <a-select-option value="">
            全部
          </a-select-option>
          <a-select-option :value="0">
            不接收
          </a-select-option>
          <a-select-option :value="1">
            接收
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
      <template slot="createdDate" slot-scope="createdDate">
        {{createdDate | filterDate('YYYY-MM-DD HH:mm:ss')}}
      </template>
      <template slot="gender" slot-scope="gender">
        <span v-if="gender===1">男</span>
        <span v-if="gender===0">女</span>
      </template>
      <template slot="phoneNumber" slot-scope="phoneNumber">
        <PhoneNumber :value="phoneNumber"/>
      </template>
      <template slot="reportNum" slot-scope="record">
        <router-link :to="`/user/report?id=${record.id}`" v-if="record.reportNum>0">
          <a>{{record.reportNum}}</a>
        </router-link>
        <span v-else>{{record.reportNum}}</span>
      </template>
      <template slot="productNum" slot-scope="record">
        <router-link :to="`/user/bought?id=${record.id}`" v-if="record.productNum>0">
          <a>{{record.productNum}}</a>
        </router-link>
        <span v-else>{{record.productNum}}</span>
      </template>
      <template slot="state" slot-scope="state">
        <span v-if="state===1">是</span>
        <span v-if="state===0">否</span>
      </template>
      <template slot="operation" slot-scope="record">
        <a-button type="primary" @click="showModal(record.id)">
          添加报告
        </a-button>
      </template>
    </a-table>
    <!--对话框-->
    <a-modal v-model="visible" title="添加检查报告" @ok="handleOk">
      <a-form-model :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-form-model-item label="选择预约">
          <a-select style="width: 300px" v-model="reportForm.appointId" >
            <a-select-option :value="item.id" v-for="(item, index) in appointList" :key="index">
              {{item.appointDate.split('T')[0] }} {{item.timePeriod===0?'上午':'下午'}} /
              {{item.productName}} /
              {{item.shopName}} /
              {{`${item.peopleNum}人`}}
            </a-select-option>
          </a-select>
        </a-form-model-item>
      </a-form-model>
      <a-form-model-item>
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
      </a-form-model-item>
    </a-modal>
  </div>
</template>

<script>
  import {listUsers, listReportable,} from "../../api/user";
  import {addReport} from '../../api/backstage'
  import moment from "moment";
  import Gender from "./Gender";
  import PhoneNumber from "./PhoneNumber";


  const columns = [
    {
      title: '注册时间',
      dataIndex: 'createdDate',
      sorter: true,
      defaultSortOrder: 'descend',
      scopedSlots: {customRender: 'createdDate'}
    },
    {
      title: '姓名',
      dataIndex: 'realName',
    },
    {
      title: '性别',
      dataIndex: 'gender',
      scopedSlots: {customRender: 'gender'}
    },
    {
      title: '手机号',
      dataIndex: 'phoneNumber',
      scopedSlots: {customRender: 'phoneNumber'}
    },
    {
      title: '检查报告数量',
      scopedSlots: {customRender: 'reportNum'}
    },
    {
      title: '购买产品数量',
      scopedSlots: {customRender: 'productNum'}
    },
    {
      title: '是否接收模板消息',
      dataIndex: 'state',
      scopedSlots: {customRender: 'state'}
    },
    {
      title: '操作',
      scopedSlots: {customRender: 'operation'}
    }
  ];

  export default {
    components: {
      Gender,
      PhoneNumber
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
            return `共 ${total} 项`;
          }
        },
        loading: false,
        filters: {},
        userListForm: {
          sortField: 'createdDate',
          sortOrder: 'descend',
        },
        headers: {
          authorization: 'authorization-text',
        },
        reportForm: {},
        visible: false,
        labelCol: { span: 5 },
        wrapperCol: { span: 10 },
        appointList: [],
        pathList: [],
      }
    },

    created() {
      this.fetch();
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
        addReport(this.reportForm)
          .then(() => {
            this.$message.success('添加报告成功')
            this.visible = false
            this.fetch()
          }).catch(({message}) => {
            this.$message.error(message)
        })
      },
      showModal(id){
        this.reportForm = {}
        this.pathList = []
        this.reportForm.userId = id
        listReportable({id:id})
          .then(({data}) => {
            this.appointList = data
            // console.log(this.appointList)
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

      handleSubmit() {
        this.pagination = Object.assign({}, this.pagination, {
          current: 1
        });
        this.fetch();
      },

      handleTableChange(pagination, filters, sorter) {
        this.pagination = Object.assign({}, this.pagination, {
          current: pagination.current
        });

        this.sortField = sorter.field;
        this.sortOrder = sorter.order;
        this.filters = filters;
        this.fetch();
      },

      fetch() {
        const {startCreatedDate, endCreatedDate} = this.userListForm
        this.userListForm = Object.assign({},this.userListForm,{
          page: this.pagination.current,
          startCreatedDate: startCreatedDate && moment(startCreatedDate).format('YYYY-MM-DD 00:00:00'),
          endCreatedDate: endCreatedDate && moment(endCreatedDate).format('YYYY-MM-DD 23:59:59')
        })
        this.loading = true;
        listUsers(this.userListForm).then(({data, rows}) => {
          this.data = data;
          this.pagination = Object.assign({}, this.pagination, {
            total: rows
          });
        }).catch(({message}) => {
          this.$message.error(message);
        }).then(() => {
          this.loading = false;
        });
      }
    }
  }
</script>
