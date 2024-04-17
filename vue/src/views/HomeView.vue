<template>
  <div>
    <el-container>
      <!-- 侧边栏 -->
      <el-aside :width="asideWidth" style="min-height: 100vh; background-color: #545c64">
        <div
            style="height: 60px; line-height: 60px; display: flex; align-items: center; justify-content: center; color: white;">
          <img src="@/assets/logo.jpg" alt="" style="width: 40px;height: 40px; border-radius: 50%;overflow: hidden;">
          <span class="logoTitle" v-show="!isCollapse">编程教学系统</span>
        </div>
        <el-menu :collapse="isCollapse" :collapse-transition="false" background-color="#545c64"
                 text-color="rgba(255,255,255,0.65)" active-text-color="#fff" router style="border: none;"
                 :default-active="$route.path">
          <el-menu-item index="/">
            <i class="el-icon-house"></i>
            <span slot="title">系统首页</span>
          </el-menu-item>

          <el-menu-item index="/1">
            <i class="el-icon-house"></i>
            <span slot="title">系统首页</span>
          </el-menu-item>

          <el-submenu>
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>信息管理</span>
            </template>
            <el-menu-item>用户信息</el-menu-item>
            <el-menu-item index="/">学生信息</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-container>
        <!-- 顶栏 -->
        <el-header>
          <i :class="collapseIcon" style="font-size: 30px;" @click="handleCollapse"></i>
          <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-left: 20px;">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/user' }">用户管理</el-breadcrumb-item>
          </el-breadcrumb>

          <div style="flex: 1;widows: 0; display: flex; align-items: center; justify-content: flex-end">
            <el-dropdown>
              <div style="display: flex; align-items:center">
                <img src="@/assets/logo.jpg" alt=""
                     style="width: 40px;height: 40px; border-radius: 50%;overflow: hidden;">
                <span>管理员</span>
              </div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>个人信息</el-dropdown-item>
                <el-dropdown-item>修改密码</el-dropdown-item>
                <el-dropdown-item @click="">退出系统</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>


        </el-header>

        <!-- 主体 -->
        <el-main>
          <div
              style="box-shadow: 0 0 10px rgba(0,0,0,.1); padding: 10px 20px; border-radius: 5px ; margin-bottom: 10px">
            <span style="align-items: center; font-size: 20px">
              欢迎使用该系统
            </span>
          </div>
          <div style="display: flex">
            <el-card style="width: 100%">
              <div slot="header">
                <span>用户数据</span>
              </div>
              <div>
                <el-table :data="users" border :header-cell-style="{background:'aliceblue',fontSize:'16px'}">
                  <el-table-column label="ID" prop="id"></el-table-column>
                  <el-table-column label="用户名" prop="username"></el-table-column>
                  <el-table-column label="姓名" prop="name"></el-table-column>
                  <el-table-column label="地址" prop="address"></el-table-column>
                </el-table>
              </div>
            </el-card>
          </div>
        </el-main>

      </el-container>
    </el-container>
  </div>
</template>


<script>
import request from "@/utils/request";
import axios from "axios";

export default {
  name: 'HomeView',
  data() {
    return {
      isCollapse: false,  //不收缩
      asideWidth: '200px',
      collapseIcon: 'el-icon-s-fold',
      users: []
    }
  },

  mounted() {
    request.get("/user/selectAll").then(
        res=>{
          this.users = res.data;
        }
    )
    // axios.get('http://localhost:9090/user/selectAll').then(
    //     res =>{
    //       this.users = res.data.data;
    //     }
    // )
  },
  methods: {
    handleCollapse() {
      this.isCollapse = !this.isCollapse;
      this.asideWidth = this.isCollapse ? '64px' : '200px';
      this.collapseIcon = this.isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold';
    }
  }
}
</script>


<style>
.el-menu--inline {
  background-color: rgba(139, 139, 139) !important;
}

.el-menu--inline .el-menu-item {
  background-color: rgba(139, 139, 139, 0.556) !important;
  padding-left: 49px !important;
}

.el-menu-item:hover, .el-submenu__title:hover {
  color: #fff !important;
}

.el-menu-item {
  height: 40px !important;
  line-height: 40px !important;
}

.el-submenu__title:hover i {
  color: #fff !important;
}

.el-submenu__title {
  height: 40px !important;
  line-height: 40px !important;
}

.el-menu-item.is-active {
  background-color: #40a9ff !important;
  border-radius: 5% !important;
  width: calc(100% - 8px);
  margin-left: 4px;
}

.el-menu-item.is-active i, .el-menu-item.is-active.el-tooltip {
  margin-left: -4px !important;
}

.el-submenu__icon-arrow {
  margin-top: -5px;
}

.el-submenu .el-menu-item {
  min-width: 0 !important;
}

.el-menu--inline .el-menu-item.is-active {
  padding-left: 45px !important;
}

.el-aside {
  transition: width .3s;
}

.logoTitle {
  margin-left: 10px;
  font-size: 20px;
  transition: all .3s;
}

.el-header {
  box-shadow: 2px 0 6px rgba(0, 21, 41, 0.35);
  display: flex;
  align-items: center;
}

</style>