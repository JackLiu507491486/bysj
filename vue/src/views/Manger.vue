<template>
  <div>
    <el-container>
      <!-- 侧边栏 -->
      <el-aside :width="asideWidth" style="min-height: 100vh; background-color: lightskyblue">
        <div
            style="height: 60px; line-height: 60px; display: flex; align-items: center; justify-content: center; color: white;">
          <img src="@/assets/logo.jpg" alt="" style="width: 40px;height: 40px; border-radius: 50%;overflow: hidden;">
          <span class="logoTitle" v-show="!isCollapse">教学管理系统</span>
        </div>
        <el-menu :collapse="isCollapse" :collapse-transition="false" background-color="lightskyblue"
                 text-color="rgba(255,255,255,1)" active-text-color="#fff" router style="border: none;"
                 :default-active="$route.path">
          <el-menu-item index="/manger/home">
            <i class="el-icon-s-home"></i>
            <span slot="title">系统首页</span>
          </el-menu-item>
          <el-menu-item index="/manger/search">
            <i class="el-icon-s-opportunity"></i>
            <span slot="title">学生学习情况</span>
          </el-menu-item>
          <el-submenu index="info">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>信息管理</span>
            </template>
            <el-menu-item index="/manger/student">管理学生信息</el-menu-item>
            <el-menu-item index="/manger/my">修改个人信息</el-menu-item>
            <el-menu-item index="/manger/password">修改个人密码</el-menu-item>
            <el-menu-item index="/manger/add">添加教师账号</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-container>
        <!-- 顶栏 -->
        <el-header>
          <i :class="collapseIcon" style="font-size: 30px;" @click="handleCollapse"></i>
          <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-left: 20px;">
            <el-breadcrumb-item :to="{ path: '/student/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: $route.path }">{{$route.meta.name}}</el-breadcrumb-item>
          </el-breadcrumb>

          <div style="flex: 1;widows: 0; display: flex; align-items: center; justify-content: flex-end">
            <el-dropdown>
              <div style="display: flex; align-items:center">
                <img :src="loginUser.avatar" alt=""
                     style="width: 40px;height: 40px; border-radius: 50%;overflow: hidden;">
                <span style="margin-left: 10px;font-size: 18px">{{ loginUser.name }}</span>
              </div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item  @click.native="$router.push('/manger/my')">修改个人信息</el-dropdown-item>
                <el-dropdown-item  @click.native="$router.push('/manger/password')">修改密码</el-dropdown-item>
                <el-dropdown-item @click.native="logout">退出系统</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>


        </el-header>

        <!-- 主体 -->
        <el-main>
          <router-view @update:my="updateStudent"/>
        </el-main>

      </el-container>
    </el-container>
  </div>
</template>


<script>
import request from "@/utils/request";


export default {
  name: 'Manager',
  data() {
    return {
      isCollapse: false,  //不收缩
      asideWidth: '200px',
      collapseIcon: 'el-icon-s-fold',
      loginUser:JSON.parse(localStorage.getItem("NowUser") || '{}'),
    }
  },

  methods: {
    updateStudent(user){
      this.loginUser = JSON.parse(JSON.stringify(uesr));  //父对象≠子对象
    },
    handleCollapse() {
      this.isCollapse = !this.isCollapse;
      this.asideWidth = this.isCollapse ? '64px' : '200px';
      this.collapseIcon = this.isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold';
    },
    logout(){
      localStorage.removeItem('NowUser')  //清除token和数据
      this.$router.push('/login')
    }
  }
}
</script>


<style>
.el-menu--inline {
  background-color: rgba(139, 139, 139) !important;
}

.el-menu--inline .el-menu-item {
  background-color: rgba(15, 137, 225, 0.55) !important;
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
  background-color: deepskyblue !important;
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