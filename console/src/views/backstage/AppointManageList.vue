<template>
  <div>
    <div class="actions">
      <a-button  @click="changeTopLimit">
        <a-icon type="setting"/>
        设置门店预约人数上限
      </a-button>
      <a-modal
        v-model="visible"
        :title="modalTitle"
        @ok="handleSubmit">
        <a-form-model :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-form-model-item label="预约上限人数">
            <a-input v-model="appointManageForm.topLimit" />
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
      <template v-for="(item,index) in columns" :slot="item.dataIndex" slot-scope="text,record" >
        <div v-if="index === 0">
          <span v-if="record.period===0">上午</span>
          <span v-if="record.period===1">下午</span>
        </div>
        <div v-else>
          <a-badge v-if="record.row[index-1].topLimit>record.row[index-1].appointNum" status="success" text="空闲" />
          <a-badge v-else status="error" text="爆满" />
          <p>上限：{{record.row[index-1].topLimit}}</p>
          <p>已约：{{record.row[index-1].appointNum}}</p>
          <a-popconfirm
            title="确定要改变状态？"
            ok-text="是"
            cancel-text="否"
            @confirm="changeStatus(record.row[index-1].id,!record.row[index-1].enabled)"
          >
            <a-button type="danger" v-if="record.row[index-1].enabled===true" >关闭预约</a-button>
            <a-button type="primary" v-if="record.row[index-1].enabled===false">开启预约</a-button>
          </a-popconfirm>
        </div>
      </template>
<!--      <template slot="day2" slot-scope="record">
        <a-badge v-if="record.topLimit>record.appointNum" status="success" text="空闲" />
        <a-badge v-else status="error" text="爆满" />
        <p>上限：{{record.topLimit}}</p>
        <p>已约：{{record.appointNum}}</p>
        <a-popconfirm
          title="确定要改变状态？"
          ok-text="是"
          cancel-text="否"
          @confirm="changeStatus(record.id,!record.enabled)"
        >
          <a-button type="danger" v-if="record.enabled===true" >关闭预约</a-button>
          <a-button type="primary" v-if="record.enabled===false">开启预约</a-button>
        </a-popconfirm>
      </template>
      <template slot="day3" slot-scope="record">
        <a-badge v-if="record.topLimit>record.appointNum" status="success" text="空闲" />
        <a-badge v-else status="error" text="爆满" />
        <p>上限：{{record.topLimit}}</p>
        <p>已约：{{record.appointNum}}</p>
        <a-popconfirm
          title="确定要改变状态？"
          ok-text="是"
          cancel-text="否"
          @confirm="changeStatus(record.id,!record.enabled)"
        >
          <a-button type="danger" v-if="record.enabled===true" >关闭预约</a-button>
          <a-button type="primary" v-if="record.enabled===false">开启预约</a-button>
        </a-popconfirm>
      </template>
      <template slot="day4" slot-scope="record">
        <a-badge v-if="record.topLimit>record.appointNum" status="success" text="空闲" />
        <a-badge v-else status="error" text="爆满" />
        <p>上限：{{record.topLimit}}</p>
        <p>已约：{{record.appointNum}}</p>
        <a-popconfirm
          title="确定要改变状态？"
          ok-text="是"
          cancel-text="否"
          @confirm="changeStatus(record.id,!record.enabled)"
        >
          <a-button type="danger" v-if="record.enabled===true" >关闭预约</a-button>
          <a-button type="primary" v-if="record.enabled===false">开启预约</a-button>
        </a-popconfirm>
      </template>
      <template slot="day5" slot-scope="record">
        <a-badge v-if="record.topLimit>record.appointNum" status="success" text="空闲" />
        <a-badge v-else status="error" text="爆满" />
        <p>上限：{{record.topLimit}}</p>
        <p>已约：{{record.appointNum}}</p>
        <a-popconfirm
          title="确定要改变状态？"
          ok-text="是"
          cancel-text="否"
          @confirm="changeStatus(record.id,!record.enabled)"
        >
          <a-button type="danger" v-if="record.enabled===true" >关闭预约</a-button>
          <a-button type="primary" v-if="record.enabled===false">开启预约</a-button>
        </a-popconfirm>
      </template>
      <template slot="day6" slot-scope="record">
        <a-badge v-if="record.topLimit>record.appointNum" status="success" text="空闲" />
        <a-badge v-else status="error" text="爆满" />
        <p>上限：{{record.topLimit}}</p>
        <p>已约：{{record.appointNum}}</p>
        <a-popconfirm
          title="确定要改变状态？"
          ok-text="是"
          cancel-text="否"
          @confirm="changeStatus(record.id,!record.enabled)"
        >
          <a-button type="danger" v-if="record.enabled===true" >关闭预约</a-button>
          <a-button type="primary" v-if="record.enabled===false">开启预约</a-button>
        </a-popconfirm>
      </template>
      <template slot="day7" slot-scope="record">
        <a-badge v-if="record.topLimit>record.appointNum" status="success" text="空闲" />
        <a-badge v-else status="error" text="爆满" />
        <p>上限：{{record.topLimit}}</p>
        <p>已约：{{record.appointNum}}</p>
        <a-popconfirm
          title="确定要改变状态？"
          ok-text="是"
          cancel-text="否"
          @confirm="changeStatus(record.id,!record.enabled)"
        >
          <a-button type="danger" v-if="record.enabled===true" >关闭预约</a-button>
          <a-button type="primary" v-if="record.enabled===false">开启预约</a-button>
        </a-popconfirm>
      </template>-->

    </a-table>
  </div>
</template>

<script>
import {
  addAppointManage,
  editAppointManage,
  editAllTopLimit,
  listAppointManages,
  getOneByShopId,
  checkDataSource,
} from "../../api/backstage"
import moment from "moment"

export default {
  data() {
    return {
      shopId: this.$route.query.id,
      columns: [],
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
      appointManageForm: {},
      modalTitle: '',
      appointManageListForm: {
        sortField: 'createdDate',
        sortOrder: 'descend',
      },
    }
  },
  created() {
    this.check()
    this.fetch()
  },
  methods: {
    check(){
      this.appointManageListForm.shopId = this.shopId
      checkDataSource(this.appointManageListForm).then(() => {
        this.$message.success("检查完毕")
      })
    },
    changeStatus(id,status) {
      editAppointManage({id:id,enabled:status,shopId:this.shopId})
        .then(() => {
          this.fetch()
        })
    },
    editAllTopLimitSubmit() {
      console.log(this.appointManageForm)
      editAllTopLimit(this.appointManageForm).then(() => {
        this.$message.success("修改成功")
      }).catch(({message}) => {
        this.$message.error(message)
      })
    },
    handleSubmit() {
      this.visible = false
      this.editAllTopLimitSubmit(this.shopId)
      this.pagination.current = 1
      this.fetch()
    },

    changeTopLimit(){
      this.modalTitle = '预约上限'
      this.visible = true
      this.getAppointManage(this.shopId)
    },
    getAppointManage(shopId){
      getOneByShopId({shopId: shopId})
        .then(appointManage => {
          this.appointManageForm = Object.assign({},this.appointManageForm,{
            id: appointManage.id,
            topLimit: appointManage.topLimit,
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
      this.columns = [
        {
          title: '',
          dataIndex: 'period',
          scopedSlots: {customRender: 'period'},
        },
      ]

      this.appointManageListForm = Object.assign({},this.appointManageListForm,{
        page: this.pagination.current,
      })
      this.loading = true
      this.appointManageListForm.shopId = this.shopId
      listAppointManages(this.appointManageListForm).then(({data, rows}) => {
        const subColumns = data[0].map((item,index) => {
          const columnName = 'day_'+index
          return {
            title: moment(item.appointDate).format('MM-DD'),
            dataIndex: columnName,
            scopedSlots: {customRender: columnName}
          }
        })
        this.columns = this.columns.concat(subColumns)
        // console.log('********')
        // console.log(this.columns)
        this.pagination.total = rows
        // for (let i = 1; i <= 7; i++) {
        //   this.columns.push({
        //     title: moment(data[0]['day'+i].appointDate).format('MM-DD'),
        //     dataIndex: 'day'+i,
        //     scopedSlots: {customRender: 'day'+i},
        //   })
        // }
        const am = {
          period: 0,
          row: data[0]
        }
        const pm = {
          period: 1,
          row: data[1]
        }
        this.data = [am,pm]
      }).catch(({message}) => {
        this.$message.error(message)
      }).then(() => {
        this.loading = false
      })
    }
  }
}
</script>
