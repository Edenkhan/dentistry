<template>
  <a-config-provider :locale="zh_CN">
    <div v-if="initialized">
      <a-layout class="layout">
        <a-layout-header style="padding: 0 24px;">
          <div class="logo-box">
            <router-link to="/" class="logo">
              <logo style="width:32px;"/>
              <span style="margin-left: 8px;">直播课</span>
            </router-link>
          </div>
          <div class="user-nav">
            <a-dropdown :trigger="[ 'hover' ]">
              <a class="ant-dropdown-link" href="javascript:">
                <a-avatar icon="user" style="background-color: #40a9ff;"/>
                <span class="username">{{ username }}</span>
                <a-icon type="down"/>
              </a>
              <a-menu slot="overlay" @click="onUserNavClick">
                <a-menu-item key="changePassword">
                  <a-icon type="edit"/>
                  修改登录密码
                </a-menu-item>
                <a-menu-divider />
                <a-menu-item key="logout">
                  <a-icon type="logout"/>
                  退出
                </a-menu-item>
              </a-menu>
            </a-dropdown>
          </div>
        </a-layout-header>
        <a-layout-content style="padding: 0 24px;">
          <router-view>

          </router-view>
          <a-layout-footer style="text-align: center">©2020 成都优软科技有限公司</a-layout-footer>
        </a-layout-content>
      </a-layout>
    </div>
    <div class="spin-wrapper" v-else>
      <a-spin size="large"/>
    </div>
  </a-config-provider>
</template>

<style scoped>
  .logo {
    float: left;
    height: 64px;
    display: flex;
    align-items: center;
    text-decoration: none;
    color: #fff;
  }

  .logo-box {
    float: left;
  }

  html, body {
    min-height: 100%;
  }

  .spin-wrapper {
    height: 100%;
    align-items: center;
    justify-content: center;
    display: flex;
  }
</style>

<style lang="scss">
  .user-nav {
    float: right;
    color: #fff;

    .username {
      margin-left: 6px;
    }

    .ant-dropdown-link {
      height: 64px;
      line-height: 64px;
      display: block;
      padding: 0 16px;
      transition: all .3s;
      text-decoration: none;
      color: #fff;

      &:hover {
        background: darken(#001529, 5%);
      }
    }
  }

  .ant-form-inline .ant-form-item.date-start {
    margin-right: 6px;
  }
</style>

<script>
  import zh_CN from 'ant-design-vue/lib/locale-provider/zh_CN';
  import Logo from "./components/Logo";
  import ALayoutSider from "ant-design-vue/es/layout/Sider";
  import {checkLogin, logout} from "./api/platform";

  export default {
    created() {
      checkLogin()
        .then(({authenticated, username}) => {
          if (!authenticated) {
            window.location.replace('/login');
            return;
          }
          this.username = username;
          this.authenticated = authenticated;
        })
        .then(() => {
          this.initialized = true;
        })
        .catch(() => {
          this.$message.error('初始化失败，请刷新页面重试。');
        });
    },

    components: {
      ALayoutSider,
      Logo
    },

    data() {
      return {
        authenticated: false,
        initialized: false,
        username: null,
        zh_CN
      }
    },

    methods: {
      onUserNavClick(e) {
        if (e.key === 'logout') {
          logout()
            .then(() => {
              window.location.href = '/login';
            })
            .catch(() => {
              window.location.href = '/login';
            });
        } else if (e.key === 'changePassword') {
          this.$router.push('/platform/employee/changePassword');
        }
      }
    }
  }
</script>
