<template>
  <div>
    <el-card style="width: 50%">
      <el-form :model="manager" :rules="rules" style="width: 75%" ref="registerRef">
        <el-form-item prop="id" label="职工号">
          <el-input size="medium" prefix-icon="el-icon-user" placeholder="请输入新的教师账号" v-model="manager.id"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input size="medium" prefix-icon="el-icon-lock" placeholder="请输入密码" show-password v-model="manager.password"></el-input>
        </el-form-item>
        <el-form-item prop="rePassword" label="再次输入密码">
          <el-input size="medium" prefix-icon="el-icon-key" placeholder="请再次输入密码" show-password v-model="manager.rePassword"></el-input>
        </el-form-item>
        <div style="text-align: center;">
          <el-button type="primary" style="width: 25%;" @click="register">确  定</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>


<script>
export default {
  name: "Add",
  data(){
    //再次输入密码验证
    const validRePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请确认密码'));
      } else if(value !== this.manager.password){
        callback(new Error('两次密码输入不一致'));
      }else{
        callback();
      }
    };
    return{
      manager: {
        id:'',
        password:'',
        rePassword:''
      },
      rules: {
        id: [
          {required: true, message: '请输入账号', trigger: 'blur' },
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
        rePassword: [
          {required: true, validator: validRePassword, trigger: 'blur' }
        ],
      },
    }
  },
  methods: {
    register(){
      this.$refs['registerRef'].validate((valid) => {
        if(valid) {
          //验证通过，发送请求
          this.$request.post('/manger/insert',this.manager).then(
              res=>{
                if (res.code === '200'){
                  this.$message.success("添加成功");
                  this.manager = {};
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

<style scoped>
/deep/.el-form-item__label{
  font-weight: bold;
}
</style>