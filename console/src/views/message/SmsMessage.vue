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
      <a-form-item label="状态">
        <a-radio-group v-decorator="[ 'state', { initialValue: null } ]">
          <a-radio-button :value="null">全部</a-radio-button>
          <a-radio-button :value="0">待发送</a-radio-button>
          <a-radio-button :value="1">发送成功</a-radio-button>
          <a-radio-button :value="2">发送失败</a-radio-button>
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
      <template slot="state" slot-scope="state">
        <a-tag color="#1890ff" v-if="state === 0">待发送</a-tag>
        <a-tag color="#87d068" v-else-if="state === 1">发送成功</a-tag>
        <a-tag color="#f5222d" v-else-if="state === 2">发送失败</a-tag>
      </template>
    </a-table>
  </div>
</template>

<script>
  import {listSmsMessages} from "../../api/message";
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
      title: '内容',
      dataIndex: 'content',
    },
    {
      title: '结束时间',
      dataIndex: 'closeDate',
      scopedSlots: {customRender: 'date'}
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
        listSmsMessages({
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
