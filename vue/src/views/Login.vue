<template>
  <div style="height: 100vh; overflow: hidden; display: flex; align-items: center; justify-content: center; background-color:aliceblue" >
    <div style="display: flex; background-color: white; width: 50%;border-radius: 5px; overflow: hidden">
      <div style="flex: 1">
          <img src="@/assets/Login.png" alt="" style="width: 100%;height: 100%">
      </div>
      <div style="flex: 1;display: flex;align-items: center; justify-content: center">
        <el-form :model="student" :rules="rules" style="width: 75%" ref="loginRef">
          <div style="font-size: 20px; font-weight: bold; text-align: center;margin-bottom: 20px">欢迎登录编程教学系统</div>
          <div style="margin-bottom:20px;display: flex;align-items: center; justify-content: center">
            <el-radio-group v-model="radioTreaty" @change="agreeChange">
              <el-radio label="1">我是学生</el-radio>
              <el-radio label="2">我是管理员</el-radio>
            </el-radio-group>
          </div>
          <el-form-item prop="id">
            <el-input size="medium" prefix-icon="el-icon-user" placeholder="请输入账号" v-model="student.id"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input size="medium" prefix-icon="el-icon-lock" placeholder="请输入密码" show-password v-model="student.password"></el-input>
          </el-form-item>
          <el-form-item prop="code">
            <div style="display: flex;">
                <el-input size="medium" placeholder="请输入验证码" prefix-icon="el-icon-circle-check"  v-model="student.code" style="flex: 1"></el-input>
                <div style="flex: 1;height: 36px">
                  <valid-code ref="validcode" @update:value="getCode"/>
                </div>
            </div>
          </el-form-item>
            <el-form-item>
              <el-button type="primary" style="width: 100%;" @click="login">登  录</el-button>
            </el-form-item>
          <div style="display: flex">
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
import students from "@/views/Student.vue";

export default {
  name: "Login",
  computed: {
    student() {
      return students
    }
  },
  components: {
    ValidCode
  },
  data(){
    //验证码自定义校验
    const validateCode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入验证码'));

      } else if(value.toLowerCase() !== this.code){
        callback(new Error('验证码错误'));
      }else{
        callback();
      }
    };
    return{
      radioTreaty: this.$route.path === '/login' ? '1' : '2',
      student: {
         code:'',
         id:'',
         password:''
      },
      rules: {
        id: [
          { required: true, message: '请输入账号', trigger: 'blur' },
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        code: [
          { validator: validateCode, trigger: 'blur' }
        ],
      },
      code:''
    }
  },
  methods: {
    agreeChange:function(){
      if(this.radioTreaty === '1')
        this.$router.push('/login')
      else this.$router.push('/loginManager')
    },
    login(){
      this.$refs['loginRef'].validate((valid) => {
          if(valid) {
            //验证通过，发送请求
              let path = this.radioTreaty === '1' ? '/login' : '/loginManager';
              this.$request.post(path,this.student).then(
                  res=>{
                    let type = this.radioTreaty === '1' ? '/student' : '/admin';
                    if (res.code === '200'){
                      this.$router.push(type);
                      this.$message.success("登录成功");
                      localStorage.setItem("NowUser",JSON.stringify(res.data));
                      localStorage.setItem("NowUserType",this.radioTreaty);//存储正在运行的用户数据
                    }else{
                      this.$message.error(res.msg);
                      this.$refs.validcode.refreshCode();
                    }
                  })
          }else this.$refs.validcode.refreshCode();
      })

    },
    getCode(code){
      this.code = code.toLowerCase();
    }
  }
}

</script>


<style>

</style>