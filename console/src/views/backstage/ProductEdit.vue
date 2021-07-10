<template>
  <div>
      <a-form-model @submit="handleSubmit" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-card title="基础信息">
          <a-form-model-item label="产品名称">
            <a-input v-model="productForm.name" />
          </a-form-model-item>
          <a-form-model-item label="产品简介">
            <a-input v-model="productForm.intro" type="textarea" />
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
          <a-form-model-item label="客户类型">
            <a-radio-group v-model="productForm.userType" v-if="productForm.type===0">
              <a-radio :value="0">
                单人
              </a-radio>
            </a-radio-group>
            <a-radio-group v-model="productForm.userType" v-else-if="productForm.type===1">
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
            <a-input v-model="productForm.price"/>
          </a-form-model-item>
          <a-form-model-item label="包含次数">
            <a-input v-if="productForm.type===0" v-model="productForm.totalAppointNum" />
            <a-input v-else-if="productForm.userType===0" v-model="productForm.totalAppointNum" />
            <a-input v-else-if="productForm.userType===1" v-model="productForm.totalAppointNum" disabled />
          </a-form-model-item>
          <a-form-model-item label="包含人数">
            <a-input v-if="productForm.type===0" v-model="productForm.peopleNum" disabled />
            <a-input v-else-if="productForm.userType===0" v-model="productForm.peopleNum" disabled />
            <a-input v-else-if="productForm.userType===1" v-model="productForm.peopleNum" />
          </a-form-model-item>
        </a-card>
        <a-card title="图文信息">
          <a-form-model-item label="产品列表图片">
            <a-upload
              name="file"
              list-type="picture-card"
              class="avatar-uploader"
              :show-upload-list="false"
              action="/api/backstage/product/uploadIcon"
              :before-upload="beforeUpload"
              @change="handleChange"
            >
              <img v-if="productForm.iconPath" :src="productForm.iconPath" alt="avatar" />
              <div v-else>
                <a-icon :type="loading ? 'loading' : 'plus'" />
                <div class="ant-upload-text">
                  列表主图
                </div>
              </div>
            </a-upload>
          </a-form-model-item>
          <a-form-model-item label="产品轮播图片">
            <div class="clearfix">
              <a-upload :file-list="fileList" :remove="handleRemove" :before-upload="beforeUpload2">
                <a-button> <a-icon type="upload" /> Select File </a-button>
              </a-upload>
              <a-button
                type="primary"
                :disabled="fileList.length === 0"
                :loading="uploading"
                style="margin-top: 16px"
                @click="handleUpload"
              >
                {{ uploading ? 'Uploading' : 'Start Upload' }}
              </a-button>
            </div>
          </a-form-model-item>
          <a-form-model-item label="产品描述">
            <div id="editor"></div>
          </a-form-model-item>
        </a-card>
        <a-card title="状态">
          <a-form-model-item label="产品状态">
            <a-select v-model="productForm.state">
              <a-select-option :value="0">
                下架
              </a-select-option>
              <a-select-option :value="1">
                销售中
              </a-select-option>
              <a-select-option :value="2">
                待发布
              </a-select-option>
            </a-select>
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
function getBase64(img, callback) {
  const reader = new FileReader()
  reader.addEventListener('load', () => callback(reader.result))
  reader.readAsDataURL(img)
}

import E from 'wangeditor'
import {
  uploadDetails,
  addProduct,
  getProduct,
  editProduct,
}from '../../api/backstage.js'

export default {
  data() {
    return {
      labelCol: { span: 4 },
      wrapperCol: { span: 8 },
      productForm: {
        detailPaths: [],
      },
      loading: false,
      fetching: false,
      editor: {},
      temp: '',

      fileList: [],
      uploading: false,
      id:this.$route.query.id,
    }
  },
  created() {
    if(this.id) {
      this.fetchProduct()
    }
    console.log(this.productForm)
  },
  mounted() {
    this.editor = new E('#editor');
    console.log(this.editor);
    this.editor.config.onchange = (html) => {
      this.productForm.description = html
    }
    this.editor.config.height = 200
    // 创建一个富文本编辑器
    this.editor.create()
  },
  methods: {
    handleSubmit() {
      if(!this.productForm.id){
        addProduct(this.productForm)
          .then(() => {
            this.$message.success("添加成功")
          }).catch(({message}) => {
            this.$message.error(message)
        })
      }else{
        editProduct(this.productForm)
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
        this.productForm.iconPath = info.file.response.iconPath
        console.log(this.productForm)
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

    handleRemove(file) {
      const index = this.fileList.indexOf(file);
      const newFileList = this.fileList.slice();
      newFileList.splice(index, 1);
      this.fileList = newFileList;
    },
    beforeUpload2(file) {
      this.fileList = [...this.fileList, file];
      return false;
    },
    handleUpload() {
      const { fileList } = this;
      const formData = new FormData();
      fileList.forEach(file => {
        formData.append('files', file);
      });
      this.uploading = true;

      uploadDetails(formData)
        .then(({detailPaths}) => {
          this.productForm.detailPaths = detailPaths
          this.fileList = [];
          this.uploading = false;
          this.$message.success('upload successfully.');
        }).catch(() => {
        this.uploading = false;
        this.$message.error('upload failed.');
      })

    },
    fetchProduct() {
      this.fetching = true
      getProduct({id:this.id})
        .then(data => {
          this.productForm = data
          this.editor.txt.html(data.description)
          this.fetching = false
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
</style>
