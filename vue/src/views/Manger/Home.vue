<template>
  <div>
    <div>
      <div style="box-shadow: 0 0 10px rgba(0,0,0,.1); padding: 10px 20px; border-radius: 5px ; margin-bottom: 10px">
              <span style="align-items: center; font-size: 20px">
               {{loginUser.name}}老师欢迎使用该系统
              </span>
      </div>
      <div style="display:flex;">
        <el-card style="width: 45%; margin-right: 20px; height: 50%">
          <el-form :model="user" label-width="80px" style="padding-right: 30px">
            <div style="margin: 15px; text-align: center">
              <img :src="loginUser.avatar" alt=""
                   style="width: 100px;height: 100px; border-radius: 50%;overflow: hidden;">
            </div>
            <p style="margin-top: 10px"><strong>职工号：</strong>{{ loginUser.id }}</p>
            <p style="margin-top: 10px"><strong>姓名：</strong>{{ loginUser.name }}</p>
            <p style="margin-top: 10px"><strong>电话：</strong>{{ loginUser.phone }}</p>
            <p style="margin-top: 10px"><strong>邮箱：</strong>{{ loginUser.email }}</p>
          </el-form>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Home",
  data(){
    return{
      loginUser: JSON.parse(localStorage.getItem("NowUser") || '{}')
    }
  }
}
</script>


<style scoped>

</style>