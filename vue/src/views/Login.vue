<template>
  <div style="height: 100vh; overflow: hidden; display: flex; align-items: center; justify-content: center; background-color:aliceblue" >
    <div style="display: flex; background-color: white; width: 50%;border-radius: 5px; overflow: hidden">
      <div style="flex: 1">
          <img src="@/assets/Login.png" alt="" style="width: 100%">
      </div>
      <div style="flex: 1;display: flex;align-items: center; justify-content: center">
        <el-form :model="user" style="width: 75%">
          <div style="font-size: 20px; font-weight: bold; text-align: center;margin-bottom: 20px">欢迎登录编程教学系统</div>
          <el-form-item prop="username">
            <el-input size="medium" prefix-icon="el-icon-user" placeholder="请输入账号" v-model="user.username"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input size="medium" prefix-icon="el-icon-lock" placeholder="请输入密码" show-password v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="validCode">
            <div style="display: flex">
                <el-input size="medium" prefix-icon="el-icon-circle-check"  v-model="user.code" style="flex: 1"></el-input>
                <div style="flex: 1;height: 36px">
                  <valid-code @update:value="getCode"/>
                </div>
            </div>
            <el-form-item>
              <el-button type="primary" style="width: 100%; margin-top: 20px" @click="login">登  录</el-button>
            </el-form-item>
          </el-form-item>
          <div style="margin-top: -20px;display: flex">
            <div style="flex: 1">还没有账号？请<span style="color: deepskyblue;cursor: pointer">注册</span></div>
            <div style="flex: 1; text-align: right"><span style="color: red; cursor: pointer; ">忘记密码？</span></div>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import ValidCode from "@/conponents/ValidCode.vue";

export default {
  name: "Login",
  components: {
    ValidCode
  },
  data(){
    return{
      user: {
         code:'',
         username:'',
         password:''
      },
      code:''
    }
  },
  methods: {
    login(){
      this.$request.post('/login',this.user).then(
          res=>{
            console.log(res);
          }
      )
    },
    getCode(code){
      console.log(code);
      this.code = code;
    }
  }
}

</script>


<style scoped>

</style>