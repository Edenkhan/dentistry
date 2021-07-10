<template>
  <div>
    <a-modal
      title="分配权限"
      :visible="assignPermissionsModalVisible"
      @ok="handleAssignPermissionsOk"
      :confirmLoading="assignPermissionsLoading"
      width="728px"
      okText="保存"
      cancelText="关闭"
      @cancel="assignPermissionsModalVisible = false">
      <a-skeleton active v-if="assignOptionsFetching"/>
      <a-transfer
        v-else
        showSearch
        @change="handleAssignedPermissionsChange"
        :listStyle="{ width: '300px', height: '324px', }"
        :dataSource="allPermissions"
        :filterOption="filterOption"
        :titles="['未分配', '已分配']"
        :operations="['分配', '取消']"
        :targetKeys="assignedPermissionIds"
        :render="item => ({label: item.description + '（' + item.title + '）', value: item.description + ' · ' + item.title})"
      />
    </a-modal>

    <a-form @submit.prevent="handleSubmit" layout="inline" :form="searchForm">
      <a-form-item label='创建时间：' class="date-start">
        <a-date-picker v-decorator="[ 'startCreatedDate' ]" style="width: 120px"></a-date-picker>
      </a-form-item>
      <a-form-item label='-' :colon="false">
        <a-date-picker v-decorator="[ 'endCreatedDate' ]" style="width: 120px"></a-date-picker>
      </a-form-item>
      <a-form-item label='名称'>
        <a-input v-decorator="[ 'name' ]" allowClear/>
      </a-form-item>
      <a-form-item>
        <a-button type='primary' htmlType="submit">
          <a-icon type="search"/>
          查询
        </a-button>
      </a-form-item>
    </a-form>
    <div class="actions">
      <router-link to="/platform/role/add">
        <a-button type="primary">
          <a-icon type="plus-circle"/>
          添加角色
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
      <template slot="actions" slot-scope="record">
        <router-link :to="`/platform/role/edit?id=${record.id}`">
          <a href="javascript:">修改</a>
        </router-link>
        <a-divider type="vertical"/>
        <a href="javascript:" @click="handleAssignPermissions(record.id)">分配权限</a>
      </template>
    </a-table>
  </div>
</template>

<script>
  import {assignPermissions, getAssignPermissionsOptions, listRoles} from "../../api/platform";
  import moment from "moment";
  import PhoneNumber from "../user/PhoneNumber";

  const columns = [
    {
      title: '创建时间',
      dataIndex: 'createdDate',
      sorter: true,
      defaultSortOrder: 'descend',
      scopedSlots: {customRender: 'createdDate'}
    },
    {
      title: '名称',
      dataIndex: 'name'
    },
    {
      title: '描述',
      dataIndex: 'description',
    },
    {
      title: '操作',
      scopedSlots: {customRender: 'actions'}
    }
  ];

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
            return `共 ${total} 项`;
          }
        },
        loading: false,
        searchForm: this.$form.createForm(this),
        sortField: 'createdDate',
        sortOrder: 'descend',
        filters: {},

        currentRoleId: null,
        assignPermissionsModalVisible: false,
        assignOptionsFetching: false,
        assignPermissionsLoading: false,
        allPermissions: [],
        assignedPermissionIds: [],
        originalAssignedPermissionIds: [],

        filterOption(inputValue, option) {
          const {title, description} = option;
          const source = title + description;
          const keywords = inputValue.split(/\s+/);
          return keywords.every(keyword => source.indexOf(keyword) !== -1);
        }
      }
    },

    created() {
      this.fetch();
    },

    methods: {
      handleAssignPermissions(id) {
        this.currentRoleId = null;
        this.assignedPermissionIds = [];
        this.originalAssignedPermissionIds = [];
        this.assignPermissionsModalVisible = true;
        this.assignOptionsFetching = true;
        getAssignPermissionsOptions({id})
          .then(({permissions, assignedPermissionIds}) => {
            this.allPermissions = permissions.map(({id, name, description}) => ({
              key: String(id),
              title: name,
              description
            }));
            this.assignedPermissionIds = assignedPermissionIds.map(String);
            this.originalAssignedPermissionIds = assignedPermissionIds.map(String);
            this.assignOptionsFetching = false;
            this.currentRoleId = id;
          })
          .catch(({message}) => {
            this.$message.error('加载失败。' + message);
          });
      },

      handleAssignedPermissionsChange(targetKeys) {
        this.assignedPermissionIds = targetKeys;
      },

      handleAssignPermissionsOk() {
        if (this.currentRoleId == null) {
          this.$message.error('未选中角色');
          return false;
        }
        this.assignPermissionsLoading = true;
        assignPermissions({
          roleId: this.currentRoleId,
          assignedIds: this.assignedPermissionIds.filter(id => this.originalAssignedPermissionIds.indexOf(id) === -1),
          unassignedIds: this.originalAssignedPermissionIds.filter(id => this.assignedPermissionIds.indexOf(id) === -1)
        }).then(() => {
          this.handleAssignPermissions(this.currentRoleId);
          this.$message.success('保存成功');
        }).catch(({message}) => {
          this.$message.error(message);
        }).then(() => {
          this.assignPermissionsLoading = false;
        });
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
        const {startCreatedDate, endCreatedDate} = this.searchForm.getFieldsValue();
        this.loading = true;
        listRoles({
          page: this.pagination.current,
          sortField: this.sortField,
          sortOrder: this.sortOrder,
          ...this.searchForm.getFieldsValue(),
          startCreatedDate: startCreatedDate && moment(startCreatedDate).format('YYYY-MM-DD 00:00:00'),
          endCreatedDate: endCreatedDate && moment(endCreatedDate).format('YYYY-MM-DD 23:59:59')
        }).then(({data, rows}) => {
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
