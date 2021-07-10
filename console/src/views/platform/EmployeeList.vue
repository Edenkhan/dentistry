<template>
  <div>
    <a-modal
      title="分配角色"
      :visible="assignRolesModalVisible"
      @ok="handleAssignRolesOk"
      :confirmLoading="assignRolesLoading"
      width="728px"
      okText="保存"
      cancelText="关闭"
      @cancel="assignRolesModalVisible = false">
      <a-skeleton active v-if="assignOptionsFetching"/>
      <a-transfer
        v-else
        showSearch
        :filterOption="filterOption"
        @change="handleAssignedRolesChange"
        :listStyle="{ width: '300px', height: '324px', }"
        :dataSource="allRoles"
        :titles="['未分配', '已分配']"
        :operations="['分配', '取消']"
        :targetKeys="assignedRoleIds"
        :render="item => ({label: item.title + '（' + item.description + '）', value: item.title + ' · ' + item.description})"
      />
    </a-modal>

    <a-form @submit.prevent="handleSubmit" layout="inline" :form="searchForm">
      <a-form-item label='创建时间：' class="date-start">
        <a-date-picker v-decorator="[ 'startCreatedDate' ]" style="width: 120px"/>
      </a-form-item>
      <a-form-item label='-' :colon="false">
        <a-date-picker v-decorator="[ 'endCreatedDate' ]" style="width: 120px" />
      </a-form-item>
      <a-form-item label='用户名'>
        <a-input v-decorator="[ 'username' ]" allowClear/>
      </a-form-item>
      <a-form-item label='姓名'>
        <a-input v-decorator="[ 'realName' ]" allowClear/>
      </a-form-item>
      <a-form-item label='手机号'>
        <a-input v-decorator="[ 'phoneNumber' ]" allowClear/>
      </a-form-item>
      <a-form-item label="是否锁定">
        <a-radio-group v-decorator="[ 'locked', { initialValue: null } ]">
          <a-radio-button :value="null">全部</a-radio-button>
          <a-radio-button :value="false">
            <a-icon type="unlock"/>
            正常
          </a-radio-button>
          <a-radio-button :value="true">
            <a-icon type="lock"/>
            锁定
          </a-radio-button>
        </a-radio-group>
      </a-form-item>
      <a-form-item>
        <a-button type='primary' htmlType="submit">
          <a-icon type="search"/>
          查询
        </a-button>
      </a-form-item>
    </a-form>
    <div class="actions">
<!--      <router-link to="/platform/employee/add">-->
      <a-button type="primary" @click="showModal">
        <a-icon type="plus-circle"/>
        添加员工
      </a-button>
      <a-modal
        v-model="visible"
        title="添加员工"
        @ok="handleOk">
        <a-form-model @submit="addEmployeeSubmit" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-form-model-item label="用户名">
            <a-input v-model="employeeAddForm.username" />
          </a-form-model-item>
          <a-form-model-item label="姓名">
            <a-input v-model="employeeAddForm.realName" />
          </a-form-model-item>
          <a-form-model-item label="邮箱">
            <a-input v-model="employeeAddForm.email" />
          </a-form-model-item>
          <a-form-model-item label="手机号">
            <a-input v-model="employeeAddForm.phone" />
          </a-form-model-item>
        </a-form-model>
      </a-modal>
<!--      </router-link>-->
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
      <template slot="phoneNumber" slot-scope="phoneNumber">
        <PhoneNumber :value="phoneNumber"/>
      </template>
      <template slot="locked" slot-scope="locked">
        <a-tag color="#87d068" v-if="!locked">正常</a-tag>
        <a-tag color="#f5222d" v-if="locked">禁用</a-tag>
      </template>
      <template slot="shopName" slot-scope="shopName">
        <span v-if="shopName"></span>
        <span v-else>未绑定</span>
      </template>
      <template slot="actions" slot-scope="record">
        <a-popconfirm
          title="确定要改变状态？"
          ok-text="是"
          cancel-text="否"
          @confirm="changeStatus(record.id,!record.locked)"
        >
          <a-button style="background: #52c41a" v-if="record.locked">启用</a-button>
          <a-button style="background: red" v-else>禁用</a-button>
        </a-popconfirm>
        <br>
        <a-button @click="handleAssignRoles(record.id)" type="primary">
          添加角色
        </a-button>
        <br>
        <a-button style="background:slategray">绑定门店</a-button>
      </template>
    </a-table>
  </div>
</template>

<script>
import {
  assignRoles,
  getAssignRolesOptions,
  listEmployees,
  resetPassword,
  changeStatus, addEmployee,
} from "../../api/platform"
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
      title: '用户名',
      dataIndex: 'username'
    },
    {
      title: '姓名',
      dataIndex: 'realName',
    },
    {
      title: '手机号',
      dataIndex: 'phoneNumber',
      scopedSlots: {customRender: 'phoneNumber'}
    },
    {
      title: '状态',
      dataIndex: 'locked',
      scopedSlots: {customRender: 'locked'}
    },
    {
      title: '店名',
      dataIndex: 'shopName',
      scopedSlots: {customRender: 'shopName'}
    },
    {
      title: '操作',
      scopedSlots: {customRender: 'actions'}
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
        searchForm: this.$form.createForm(this),
        sortField: 'createdDate',
        sortOrder: 'descend',
        filters: {},

        currentEmployeeId: null,
        assignRolesModalVisible: false,
        assignOptionsFetching: false,
        assignRolesLoading: false,
        allRoles: [],
        assignedRoleIds: [],
        originalAssignedRoleIds: [],

        filterOption(inputValue, option) {
          const {title, description} = option
          const source = title + description
          const keywords = inputValue.split(/\s+/)
          return keywords.every(keyword => source.indexOf(keyword) !== -1)
        },
        visible: false,
        labelCol: { span: 2 },
        wrapperCol: { span: 4 },
        employeeAddForm: {},

      }
    },

    created() {
      this.fetch()
    },

    methods: {

      addEmployeeSubmit() {
        addEmployee(this.employeeAddForm)
          .then(() => {

          }).catch(error => {

        })
      },
      showModal() {
        this.visible = true;
      },
      handleOk(e) {
        console.log(e);
        this.visible = false;
      },

      changeStatus(id,locked) {
        console.log(locked)
        changeStatus({id:id,locked:locked})
          .then(() => {
            this.fetch()
          })
      },

      handleResetPassword(id) {
        const hideMessage = this.$message.loading('正在重置 ...')
        resetPassword({id})
          .then(({newPassword}) => {
            hideMessage()
            this.$success({
              title: '密码重置成功',
              content: '新的密码为：' + newPassword
            })
          })
      },

      handleAssignRoles(id) {
        this.currentEmployeeId = null
        this.assignedRoleIds = []
        this.originalAssignedRoleIds = []
        this.assignRolesModalVisible = true
        this.assignOptionsFetching = true
        getAssignRolesOptions({id})
          .then(({roles, assignedRoleIds}) => {
            this.allRoles = roles.map(({id, name, description}) => ({
              key: String(id),
              title: name,
              description
            }))
            this.assignedRoleIds = assignedRoleIds.map(String)
            this.originalAssignedRoleIds = assignedRoleIds.map(String)
            this.assignOptionsFetching = false
            this.currentEmployeeId = id
          })
          .catch(({message}) => {
            this.$message.error('加载失败。' + message)
          })
      },

      handleAssignedRolesChange(targetKeys) {
        this.assignedRoleIds = targetKeys
      },

      handleAssignRolesOk() {
        if (this.currentEmployeeId == null) {
          this.$message.error('未选中角色')
          return false
        }
        this.assignRolesLoading = true
        assignRoles({
          employeeId: this.currentEmployeeId,
          assignedIds: this.assignedRoleIds.filter(id => this.originalAssignedRoleIds.indexOf(id) === -1),
          unassignedIds: this.originalAssignedRoleIds.filter(id => this.assignedRoleIds.indexOf(id) === -1)
        }).then(() => {
          this.handleAssignRoles(this.currentEmployeeId)
          this.$message.success('保存成功')
        }).catch(({message}) => {
          this.$message.error(message)
        }).then(() => {
          this.assignRolesLoading = false
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
        const {startCreatedDate, endCreatedDate} = this.searchForm.getFieldsValue()
        this.loading = true
        listEmployees({
          page: this.pagination.current,
          sortField: this.sortField,
          sortOrder: this.sortOrder,
          ...this.searchForm.getFieldsValue(),
          startCreatedDate: startCreatedDate && moment(startCreatedDate).format('YYYY-MM-DD 00:00:00'),
          endCreatedDate: endCreatedDate && moment(endCreatedDate).format('YYYY-MM-DD 23:59:59')
        }).then(({data, rows}) => {
          this.data = data
          this.pagination = Object.assign({}, this.pagination, {
            total: rows
          })
          console.log(this.data)
        }).catch(({message}) => {
          this.$message.error(message)
        }).then(() => {
          this.loading = false
        })
      }
    }
  }
</script>
