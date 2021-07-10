<template>
  <div class="edit-container">
    <a-form :form="form" @submit="handleSubmit">
      <a-form-item label="联系电话" :label-col="{ span: 4 }" :wrapper-col="{ span: 8 }">
        <a-input autocomplete="off"
                 v-decorator="[ 'phoneNumber',  { rules: [
          { required: true, message: '请输入联系电话' },
          { pattern: /^1\d{10}$/, message: '请输入正确的联系电话' }
        ] } ]"/>
      </a-form-item>
      <a-form-item label="是否锁定" :label-col="{ span: 4 }" :wrapper-col="{ span: 8 }">
        <a-radio-group
          v-decorator="[ 'locked', {initialValue: false, rules: [{ required: true, message: '请选择是否锁定' }]}]">
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
      <a-form-item :wrapper-col="{ offset: 4, span: 8 }">
        <a-button html-type="submit" type="primary" :disabled="fetching" :loading="loading">保存</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<style scoped lang="scss">
</style>


<script>
  import {addUser, editUser, getUser} from "../../api/user";

  export default {
    data() {
      return {
        id: this.$route.query.id,
        fetching: false,

        form: this.$form.createForm(this),
        loading: false
      };
    },

    created() {
      if (this.id != null) {
        this.fetchUser();
      }
    },

    methods: {
      fetchUser() {
        this.fetching = true;
        getUser(this.id)
          .then(data => {
            const {realName, phoneNumber, idCardNumber, type, locked, qqNo, weChatNo, address} = data;
            this.form.setFieldsValue({
              realName, phoneNumber, idCardNumber, type, locked, qqNo, weChatNo, address
            });
          })
          .catch(({message}) => {
            this.$message.error('获取失败。' + message);
          })
          .then(() => {
            this.fetching = false;
          });
      },

      save(values) {
        this.loading = true;

        if (this.id == null) {
          return addUser(values)
            .then(() => {
              this.$message.success('添加成功');
              this.$router.push('/user/list');
            })
            .catch(({message}) => {
              this.$message.error('添加失败。' + message);
            })
            .then(() => {
              this.loading = false;
            });
        }

        return editUser({
          id: this.id,
          ...values
        }).then(() => {
          this.$message.success('修改成功');
          this.$router.push('/user/list');
        }).catch(({message}) => {
          this.$message.error('修改失败。' + message);
        }).then(() => {
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
