<template>
  <div class="container">
    <div class="bg"></div>
    <div class="form-wrapper">
      <h1>登录</h1>
      <a-form :form="loginForm"
              @submit.prevent="handleSubmit"
              class='login-form'>
        <a-form-item>
          <a-input
            size="large"
            placeholder='用户名'
            v-decorator="[
          'username',
          { rules: [{ required: true, message: '请输入用户名' }] }
        ]"
          >
            <a-icon slot="prefix" type='user' style="color: rgba(0,0,0,.25)"/>
          </a-input>
        </a-form-item>
        <a-form-item>
          <a-input-password
            size="large"
            v-decorator="[ 'password', { rules: [{ required: true, message: '请输入密码' }] } ]"
            placeholder='密码'>
            <a-icon slot="prefix" type='lock' style="color: rgba(0,0,0,.25)"/>
          </a-input-password>
        </a-form-item>
        <a-form-item>
          <a-checkbox v-decorator="[ 'rememberMe' ]">记住登录</a-checkbox>
          <a-button type='primary' block size="large" htmlType='submit' class='login-form-button' :loading="loading">
            登录
          </a-button>
        </a-form-item>
      </a-form>
      <p class="text-center text-muted">©2020 成都优软科技有限公司</p>
    </div>
  </div>
</template>

<style lang="scss">
  .container {
    display: flex;
    margin: auto;
    min-height: 100%;
  }

  .bg {
    position: fixed;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;

    display: block;
    background: #f4f6f9;
  }

  .form-wrapper {
    border-radius: 6px;
    width: 420px;
    margin: auto;
    padding: 32px;
    position: relative;
    background: #fff;
    border-top: 3px solid #1890ff;
    box-shadow: 0 4px 8px rgba(0, 0, 0, .03);

    h1 {
      margin-bottom: 24px;
      color: #1890ff;
    }

    input.ant-input-lg {
      padding: 12px 24px;
      height: 48px;
      font-size: 16px;
    }

    .ant-btn-lg {
      height: 48px;
    }
  }
</style>

<script>
  import Logo from "@/components/Logo.vue";
  import {checkLogin, login} from "./api/platform";

  export default {
    components: {
      Logo
    },

    created() {
      checkLogin()
        .then(({authenticated}) => {
          if (authenticated) {
            window.location.replace('/');
          }
        });
    },

    data() {
      return {
        loading: false,
        loginForm: this.$form.createForm(this)
      }
    },

    methods: {
      handleSubmit() {
        this.loginForm.validateFields((err, values) => {
          if (err) {
            return;
          }

          this.loading = true;
          login(values.username, values.password)
            .then(() => {
              window.location.replace('/');
            })
            .catch(({message}) => {
              this.$message.error(message);
            })
            .then(() => {
              this.loading = false;
            });
        });
      }
    }
  }
</script>
