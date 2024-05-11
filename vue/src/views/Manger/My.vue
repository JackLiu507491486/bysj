<template>
  <div>
    <el-card style="width: 50%">
      <el-form ref="formRef" :model="user" :rules="rules" label-width="80px" style="padding-right: 30px">
        <el-form-item label="原始密码" prop="password">
          <el-input show-password v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input show-password  v-model="user.newPassword"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="RPassword">
          <el-input show-password v-model="user.RPassword"></el-input>
        </el-form-item>
        <div style="text-align: center; margin-bottom: 20px">
          <el-button type="primary" @click="update">确  认</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>


<script>
export default {
  name: "Password",
  data(){
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请确认密码'));
      }else if(value !== this.user.newPassword){
        callback(new Error('两次输入密码不一致'));
      }else{
        callback();
      }
    }
    return{
      user:JSON.parse(localStorage.getItem('NowUser')||'{}'),
      rules: {
        password: [
          {required: true, message: '请输入原始密码', trigger: 'blur'},
        ],
        newPassword: [
          {required: true, message: '请输入新密码', trigger: 'blur'},
        ],
        RPassword: [
          {validator:validatePassword, required: true, trigger: 'blur'},
        ],
      }
    }
  },
  created() {

  },
  methods: {
    update(){
      if (this.user.newPassword.length < 6 || this.user.newPassword.length > 20){
        this.$message.error('新密码长度应该在6-20位');
      }else {
        this.user.password = this.user.newPassword;
        this.$refs.formRef.validate((valid) => {
          //保存更改信息
          this.$request.put('/manger/update', this.user).then(
              res => {
                if (res.code === '200') {
                  //成功更新
                  this.$message.success("保存成功");
                  this.$router.push('/login');
                } else {
                  this.$message.error(res.msg);
                }
              }
          )
        })
      }
    }
  }
}
</script>


<style scoped>
/deep/.el-form-item__label{
  font-weight: bold;
}
</style>