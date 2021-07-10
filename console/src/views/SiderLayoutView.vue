<template>
  <div class="container">
    <a-layout style="background: #fff; border: 1px solid #e8e8e8">
      <a-layout-sider width="200px" :trigger="null" collapsible v-model="collapsed" style="background: #fff;">
        <a-menu
          mode="inline"
          @select="handleMenuItemSelect"
          :defaultOpenKeys="defaultOpenKeys"
          :selectedKeys="selectedKeys"
          :style="{ height: '100%', borderRight: 0 }">
          <a-sub-menu v-for="menu in menus" :key="menu.key">
            <span slot="title"><a-icon :type="menu.icon"/><span>{{ menu.title }}</span></span>
            <a-menu-item v-for="subMenu in menu.subMenus" :key="subMenu.key">{{ subMenu.title }}</a-menu-item>
          </a-sub-menu>
        </a-menu>
      </a-layout-sider>
      <a-layout-content style="padding:0 24px 24px;border-left: 1px solid #e8e8e8;">
        <div style="padding: 12px 24px; border-bottom: 1px solid rgb(232, 232, 232);margin: 0 -24px 16px;">
          <a-icon
            title="展开/折叠菜单栏"
            class="trigger"
            :type="collapsed ? 'menu-unfold' : 'menu-fold'"
            @click="()=> collapsed = !collapsed"
          />
          <a-breadcrumb style="display: inline-block; margin-left: 24px;">
            <a-breadcrumb-item>
              <router-link to="/"><a-icon type="home" /> 首页</router-link>
            </a-breadcrumb-item>
            <a-breadcrumb-item v-for="item in parentBreadcrumbItems" :key="item.path">
              <router-link :to="item.path">{{ item.title }}</router-link>
            </a-breadcrumb-item>
            <a-breadcrumb-item v-if="currentBreadcrumbItem">{{ currentBreadcrumbItem }}</a-breadcrumb-item>
          </a-breadcrumb>
        </div>
        <router-view>

        </router-view>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script>
  import {getMenus} from "./menus";

  export default {
    data() {
      const menus = getMenus();
      const defaultOpenKeys = menus.map(menu => menu.key);
      return {
        menus,
        defaultOpenKeys,
        selectedKeys: null,

        collapsed: false,

        parentBreadcrumbItems: [],
        currentBreadcrumbItem: null
      }
    },

    created() {
      this.selectedKeys = [this.$route.path];
      this.currentBreadcrumbItem = this.$route.meta.title;
      this.parentBreadcrumbItems = this.$route.meta.parents;
    },

    mounted() {
      this.$router.afterEach((to, from) => {
        this.selectedKeys = [to.path];
        this.currentBreadcrumbItem = to.meta.title;
        this.parentBreadcrumbItems = to.meta.parents;
      });
    },

    methods: {
      handleMenuItemSelect({item, key}) {
        this.$router.push(key);
      }
    }
  }
</script>

<style scoped>
  .container {
    margin-top: 24px;
  }
  .ant-breadcrumb-link a {
    text-decoration: none;
  }
</style>
