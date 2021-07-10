<template>
  <div class="edit-container">
    <a-form :form="form" @submit="handleSubmit" :loading="loading">
      <a-form-item label="原密码" :label-col="{ span: 4 }" :wrapper-col="{ span: 8 }">
        <a-input-password v-decorator="[ 'originalPassword',  { rules: [{ required: true, message: '请输入原密码' }] } ]"/>
      </a-form-item>
      <a-form-item label="新密码" :label-col="{ span: 4 }" :wrapper-col="{ span: 8 }">
        <a-input-password v-decorator="[ 'newPassword',  { rules: [{ required: true, message: '请输入新密码' }] } ]"/>
      </a-form-item>
      <a-form-item label="确认新密码" :label-col="{ span: 4 }" :wrapper-col="{ span: 8 }">
        <a-input-password v-decorator="[ 'confirmNewPassword',  { rules: [{ required: true, message: '请确认新密码' }, { validator: confirmNewPasswordValidator, message: '两次输入的新密码不一致' }] }]"/>
      </a-form-item>
      <a-form-item :wrapper-col="{ offset: 4, span: 8 }">
        <a-button html-type="submit" type="primary" :loading="loading">确认修改</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<style scoped lang="scss">
</style>


<script>
  import {changePassword, logout} from "../../api/platform";

  export default {
    data() {
      const form = this.$form.createForm(this);
      return {
        form,
        loading: false,
        confirmNewPasswordValidator(rule, value) {
          return form.getFieldValue('newPassword') === value;
        }
      };
    },

    methods: {
      save(values) {
        return changePassword(values)
          .then(() => {
            this.$message.success('修改成功，请重新登录');
            logout()
              .finally(() => {
                setTimeout(function () {
                  window.location.href = '/login';
                }, 600);
              });
          })
          .catch(({message}) => {
            this.$message.error('修改失败。' + message);
          })
          .then(() => {
            this.loading = false;
          });
      },

      handleSubmit(e) {
        e.preventDefault();
        this.form.validateFields((err, values) => {
          if (err) {
            return;
          }
          this.save(values);
        });
      }
    }
  };
</script>
