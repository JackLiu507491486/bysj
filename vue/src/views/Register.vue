<template>
  <div style="height: 100vh; overflow: hidden; display: flex; align-items: center; justify-content: center; background-color:skyblue" >
    <div style="display: flex; background-color: white; width: 50%;border-radius: 5px; overflow: hidden">
      <div style="flex: 1;display: flex;align-items: center; justify-content: center">
        <el-form :model="student" :rules="rules" style="width: 75%" ref="registerRef">
          <div style="font-size: 20px; font-weight: bold; text-align: center;margin-bottom: 20px">欢迎注册账号</div>
          <el-form-item prop="id">
            <el-input size="medium" prefix-icon="el-icon-user" placeholder="请输入账号" v-model="student.id"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input size="medium" prefix-icon="el-icon-lock" placeholder="请输入密码" show-password v-model="student.password"></el-input>
          </el-form-item>
          <el-form-item prop="rePassword">
            <el-input size="medium" prefix-icon="el-icon-key" placeholder="请再次输入密码" show-password v-model="student.rePassword"></el-input>
          </el-form-item>
          <el-form-item>
              <el-button type="primary" style="width: 100%;" @click="register">注  册</el-button>
          </el-form-item>
          <div style="display: flex">
            <div style="flex: 1">已经有账号？请<span @click="$router.push('/login')" style="color: deepskyblue;cursor: pointer">登录</span></div>
          </div>
        </el-form>
      </div>
      <div style="flex: 1">
        <img src="@/assets/register.jpg" alt="" style="width: 100%;height: 100%">
      </div>
    </div>
  </div>
</template>

<script>
import ValidCode from "@/conponents/ValidCode.vue";

export default {
  name: "Register",
  components: {
    ValidCode
  },
  data(){
    //再次输入密码验证
    const validRePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请确认密码'));
      } else if(value !== this.student.password){
        callback(new Error('两次密码输入不一致'));
      }else{
        callback();
      }
    };
    return{
      student: {
         id:'',
         password:'',
        rePassword:''
      },
      rules: {
        id: [
          { required: true, message: '请输入账号', trigger: 'blur' },
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
        rePassword: [
          { validator: validRePassword, trigger: 'blur' }
        ],
      },
    }
  },
  methods: {
    register(){
      this.$refs['registerRef'].validate((valid) => {
          if(valid) {
            //验证通过，发送请求
            this.$request.post('/register',this.student).then(
                res=>{
                  if (res.code === '200'){
                    this.$router.push('/login');
                    this.$message.success("注册成功");
                  }else{
                    this.$message.error(res.msg);
                  }
                })
          }
        })
    },
  }
}

</script>


<style>

</style>