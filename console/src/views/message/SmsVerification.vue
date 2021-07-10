<template>
  <div>
    <a-form @submit.prevent="handleSubmit" layout="inline" :form="searchForm">
      <a-form-item label='创建时间：' class="date-start">
        <a-date-picker v-decorator="[ 'startCreatedDate' ]" style="width: 120px"></a-date-picker>
      </a-form-item>
      <a-form-item label='-' :colon="false">
        <a-date-picker v-decorator="[ 'endCreatedDate' ]" style="width: 120px"></a-date-picker>
      </a-form-item>
      <a-form-item label='手机号'>
        <a-input v-decorator="[ 'phoneNumber' ]" />
      </a-form-item>
      <a-form-item label='请求 IP'>
        <a-input v-decorator="[ 'requestIp' ]" />
      </a-form-item>
      <a-form-item label="状态">
        <a-radio-group v-decorator="[ 'state', { initialValue: null } ]">
          <a-radio-button :value="null">全部</a-radio-button>
          <a-radio-button :value="0">待验证</a-radio-button>
          <a-radio-button :value="1">已验证</a-radio-button>
          <a-radio-button :value="2">已无效</a-radio-button>
        </a-radio-group>
      </a-form-item>
      <a-form-item label="类型">
        <a-radio-group v-decorator="[ 'state', { initialValue: null } ]">
          <a-radio-button :value="null">全部</a-radio-button>
          <a-radio-button :value="0">登录</a-radio-button>
        </a-radio-group>
      </a-form-item>
      <a-form-item>
        <a-button type='primary' htmlType="submit">
          <a-icon type="search"/>
          查询
        </a-button>
      </a-form-item>
    </a-form>
    <a-table
      :columns="columns"
      rowKey="id"
      :dataSource="data"
      :pagination="pagination"
      :loading="loading"
      bordered
      @change="handleTableChange">
      <template slot="date" slot-scope="date">
        <EmptyTip :content="date | filterDate('YYYY-MM-DD HH:mm:ss')" />
      </template>
      <template slot="emptyTip" slot-scope="value">
        <EmptyTip :content="value" />
      </template>
      <template slot="phoneNumber" slot-scope="phoneNumber">
        <PhoneNumber :value="phoneNumber" />
      </template>
      <template slot="type" slot-scope="type">
        <a-tag v-if="type === 0">登录</a-tag>
        <a-tag v-if="type === 1">借款申请</a-tag>
      </template>
      <template slot="state" slot-scope="state">
        <a-tag color="#1890ff" v-if="state === 0">待验证</a-tag>
        <a-tag color="#87d068" v-else-if="state === 1">已验证</a-tag>
        <a-tag color="#f5222d" v-else-if="state === 2">已无效</a-tag>
      </template>
    </a-table>
  </div>
</template>

<script>
  import {listSmsVerifications} from "../../api/message";
  import moment from "moment";
  import PhoneNumber from "../user/PhoneNumber";
  import DescriptionItem from "../../components/DescriptionItem";
  import EmptyTip from "../../components/EmptyTip";

  const columns = [
    {
      title: '创建时间',
      dataIndex: 'createdDate',
      sorter: true,
      defaultSortOrder: 'descend',
      scopedSlots: {customRender: 'date'}
    },
    {
      title: '手机号',
      dataIndex: 'phoneNumber',
      scopedSlots: {customRender: 'phoneNumber'}
    },
    {
      title: '验证码',
      dataIndex: 'code',
    },
    {
      title: '请求 IP',
      dataIndex: 'requestIp',
    },
    {
      title: '重试次数',
      dataIndex: 'retryCount',
    },
    {
      title: '过期时间',
      dataIndex: 'expirationDate',
      scopedSlots: {customRender: 'date'}
    },
    {
      title: '类型',
      dataIndex: 'type',
      scopedSlots: {customRender: 'type'}
    },
    {
      title: '状态',
      dataIndex: 'state',
      scopedSlots: {customRender: 'state'}
    }
  ];

  export default {
    components: {
      EmptyTip,
      DescriptionItem,
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
        listSmsVerifications({
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
