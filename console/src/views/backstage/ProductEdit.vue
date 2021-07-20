<template>
  <div>
    <a-form-model @submit="handleSubmit" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-card title="基础信息">
        <a-form-model-item label="产品名称">
          <a-input v-model="productForm.name"/>
        </a-form-model-item>
        <a-form-model-item label="产品简介">
          <a-input v-model="productForm.intro" type="textarea"/>
        </a-form-model-item>
        <a-form-model-item label="产品类型">
          <a-radio-group v-model="productForm.type">
            <a-radio :value="0">
              远程
            </a-radio>
            <a-radio :value="1">
              常规
            </a-radio>
          </a-radio-group>
        </a-form-model-item>
        <a-form-model-item label="客户类型" v-if="productForm.type === 1">
          <a-radio-group v-model="productForm.userType">
            <a-radio :value="0">
              单人
            </a-radio>
            <a-radio :value="1">
              团队
            </a-radio>
          </a-radio-group>
        </a-form-model-item>
      </a-card>
      <a-card size="small" title="销售信息">
        <a-form-model-item label="销售价格">
          <a-input type="number" v-model="productForm.price"/>
        </a-form-model-item>
        <a-form-model-item label="包含次数">
          <a-input type="number" v-model="productForm.totalAppointNum" :disabled="frequencyDisabled"/>
        </a-form-model-item>
        <a-form-model-item label="包含人数">
          <a-input type="number" v-model="productForm.peopleNum" :disabled="peopleDisabled"/>
        </a-form-model-item>
      </a-card>
      <a-card title="图文信息">
        <a-form-model-item label="产品列表图片">
          <a-upload
            name="file"
            list-type="picture-card"
            class="avatar-uploader"
            :show-upload-list="false"
            action="/api/backstage/product/upload?directory=producticon"
            :before-upload="beforeUpload"
            @change="handleChange"
          >
            <img v-if="productForm.iconPath" :src="productForm.iconPath" alt="avatar"/>
            <div v-else>
              <a-icon :type="loading ? 'loading' : 'plus'"/>
              <div class="ant-upload-text">
                上传列表主图
              </div>
            </div>
          </a-upload>
        </a-form-model-item>
        <a-form-model-item label="产品轮播图片">
          <div class="clearfix">
            <a-upload
              action="/api/backstage/product/upload?directory=productdetail"
              list-type="picture-card"
              :file-list="fileList"
              @preview="handlePreview"
              @change="handleChangeList"
            >
              <div v-if="fileList.length < 5">
                <a-icon type="plus" />
                <div class="ant-upload-text">
                  上传轮播图
                </div>
              </div>
            </a-upload>
            <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
              <img alt="example" style="width: 100%" :src="previewImage" />
            </a-modal>
          </div>
        </a-form-model-item>
        <a-form-model-item label="产品描述">
          <div id="editor"></div>
        </a-form-model-item>
      </a-card>
      <a-card>
        <a-button type="primary" html-type="submit" :disabled="fetching">
          确定
        </a-button>
      </a-card>

    </a-form-model>

  </div>

</template>
<script>
import {login} from "../../api/platform";

function getBase64(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
  });
}

import E from 'wangeditor'
import {
  uploadDetails,
  addProduct,
  getProduct,
  editProduct,
} from '../../api/backstage.js'

export default {
  data() {
    return {
      labelCol: {span: 4},
      wrapperCol: {span: 8},
      productForm: {
        pathList: [],
        peopleNum: null,
        totalAppointNum: null,
        type: null,
        userType: null,
      },
      loading: false,
      fetching: false,
      editor: {},
      temp: '',
      uploading: false,
      id: this.$route.query.id,
      frequencyDisabled: false,
      peopleDisabled: false,
      previewVisible: false,
      previewImage: '',
      fileList: [],
      pathList: [],
    }
  },
  watch: {
    'productForm.type'(val) {
      if (val === 0) {
        this.productForm.userType = null
        this.frequencyDisabled = true
        this.peopleDisabled = true
        this.productForm = Object.assign(this.productForm, {
          peopleNum: 1,
          totalAppointNum: 1,
        })
      } else if(val === 1) {
        this.productForm.userType = this.productForm.userType?this.productForm.userType:0
        this.frequencyDisabled = false
        this.peopleDisabled = false
        this.productForm.totalAppointNum = this.productForm.userType===0?this.productForm.totalAppointNum:1
        this.productForm.peopleNum = this.productForm.userType===1?this.productForm.peopleNum:1
      }
    },
    'productForm.userType'(val) {
      if (val === 0) {
        this.frequencyDisabled = false
        this.peopleDisabled = true
        this.productForm.peopleNum = 1
      } else if (val === 1) {
        this.frequencyDisabled = true
        this.peopleDisabled = false
        this.productForm.totalAppointNum = 1
      }
    }
  },
  created() {
    if (this.id) {
      this.fetchProduct()
    }
    // console.log(this.productForm)
  },
  mounted() {
    this.editor = new E('#editor');
    // console.log(this.editor);
    this.editor.config.onchange = (html) => {
      this.productForm.description = html
    }
    this.editor.config.height = 200
    // 创建一个富文本编辑器
    this.editor.create()
  },
  methods: {
    handleCancel() {
      this.previewVisible = false;
    },
    async handlePreview(file) {
      // console.log(file)
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj);
      }
      this.previewImage = file.url || file.preview;
      this.previewVisible = true;
    },
    handleChangeList({fileList}) {
      this.fileList = fileList;
    },
    handleSubmit() {
      this.fileList.forEach(item => {
        this.pathList.push(item.response)
      })
      console.log(this.pathList)
      this.productForm = Object.assign(this.productForm,{
        pathList: this.pathList,
      })
      if (!this.productForm.id) {
        addProduct(this.productForm)
          .then(() => {
            this.$message.success("添加成功")
          }).catch(({message}) => {
          this.$message.error(message)
        })
      } else {
        editProduct(this.productForm)
          // console.log(this.productForm)
          .then(() => {
            this.$message.success("修改成功")
          }).catch(({message}) => {
          this.$message.error(message)
        })
      }
      this.$router.push('/backstage/product/list')
    },
    handleChange(info) {
      if (info.file.status === 'uploading') {
        this.loading = true
        return
      }
      if (info.file.status === 'done') {
        // Get this url from response in real world.
        getBase64(info.file.originFileObj, imageUrl => {
          this.loading = false
        })
        this.productForm.iconPath = info.file.response
        // console.log(this.productForm)
      }
    },

    beforeUpload(file) {
      const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
      if (!isJpgOrPng) {
        this.$message.error('You can only upload JPG file!')
      }
      const isLt500KB = file.size / 1024 < 500
      if (!isLt500KB) {
        this.$message.error('Image must smaller than 500KB!')
      }
      return isJpgOrPng && isLt500KB
    },

    fetchProduct() {
      this.fetching = true
      getProduct({id: this.id})
        .then(data => {
          data.detailPathList.forEach((item, index) => {
            this.fileList.push({
              uid: index,
              name: 'image.png',
              status: 'done',
              url: item,
            })
          })
          this.pathList = data.detailPathList
          this.productForm = data
          this.editor.txt.html(data.description)
          this.fetching = false
          // console.log(this.productForm)
        })
    },

  },
}
</script>
<style>
.avatar-uploader > .ant-upload {
  width: 128px;
  height: 128px
}

.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666
}

/* you can make up upload button and sample style by using stylesheets */
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}

</style>
