<template>
  <div class="edit-container">
    <a-form :form="form" @submit="handleSubmit" :loading="loading">
      <a-form-item label="名称" :label-col="{ span: 4 }" :wrapper-col="{ span: 8 }">
        <a-input autoFocus autocomplete="off" v-decorator="[ 'name',  { rules: [{ required: true, message: '请输入名称' }] } ]"/>
      </a-form-item>
      <a-form-item label="描述" :label-col="{ span: 4 }" :wrapper-col="{ span: 8 }">
        <a-input autocomplete="off" v-decorator="[ 'description',  { rules: [{ required: true, message: '请输入描述' }] } ]"/>
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
  import {addRole, editRole, getRole} from "../../api/platform";

  export default {
    data() {
      return {
        id: this.$route.query.id,

        form: this.$form.createForm(this),
        loading: false,
        fetching: false
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
        getRole({id: this.id})
          .then(data => {
            const {name, description} = data;
            this.form.setFieldsValue({
              name, description
            });
            this.fetching = false;
          })
          .catch(({message}) => {
            this.$message.error('获取失败。' + message)
          });
      },

      save(values) {
        this.loading = true;

        if (this.id == null) {
          return addRole(values)
            .then(() => {
              this.$message.success('添加成功');
              this.$router.push('/platform/role/list');
            })
            .catch(({message}) => {
              this.$message.error('添加失败。' + message);
            })
            .then(() => {
              this.loading = false;
            });
        }

        return editRole({
          id: this.id,
          ...values
        }).then(() => {
          this.$message.success('修改成功');
          this.$router.push('/platform/role/list');
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
