<template>
  <div>
    <a-form @submit.prevent="handleSubmit" layout="inline" :form="searchForm">
      <a-form-item label='创建时间：' class="date-start">
        <a-date-picker v-decorator="[ 'startCreatedDate' ]" style="width: 120px"></a-date-picker>
      </a-form-item>
      <a-form-item label='-' :colon="false">
        <a-date-picker v-decorator="[ 'endCreatedDate' ]" style="width: 120px"></a-date-picker>
      </a-form-item>
      <a-form-item label='名称'>
        <a-input v-decorator="[ 'name' ]" allowClear />
      </a-form-item>
      <a-form-item>
        <a-button type='primary' htmlType="submit">
          <a-icon type="search"/>
          查询
        </a-button>
      </a-form-item>
    </a-form>
    <div class="actions">
      <router-link to="/platform/permission/add">
        <a-button type="primary">
          <a-icon type="plus-circle"/>
          添加权限
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
        <router-link :to="`/platform/permission/edit?id=${record.id}`">
          <a href="javascript:">修改</a>
        </router-link>
      </template>
    </a-table>
  </div>
</template>

<script>
  import {listPermissions} from "../../api/platform";
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
        filters: {}
      }
    },

    created() {
      this.fetch();
    },

    methods: {
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
        listPermissions({
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
