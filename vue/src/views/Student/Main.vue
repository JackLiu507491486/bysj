<template>
<div>
  <el-container>
    <el-main>
      <div style="height:550px;overflow:auto;background:#EEEEEE;">

      </div>
    </el-main>
    <el-footer>
      <div style="height:50px;background:white;">
        <el-form :model="chat" :rules="rules" style="width: 100%" ref="questionRef">
          <div style="display: flex;align-items: center; justify-content: center">
            <el-form-item prop="question">
              <el-input size="medium" style="width: 980px" prefix-icon="el-icon-s-promotion" placeholder="请输入你遇到的问题" v-model="chat.question"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" style="margin-left: 10px;width: 100%;" @click="sendQuestion">发  送</el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="danger" style="margin-left: 20px;width: 100%;" @click="clearAll">删除记录</el-button>
            </el-form-item>
          </div>
        </el-form>
      </div>
    </el-footer>
  </el-container>
</div>
</template>


<script>
export default {
  name: "Main",
  data(){
    return{
      user:JSON.parse(localStorage.getItem("NowUser") || '{}'),
      chat:{
        id: '',
        question: ''
      },rules: {
        question: [
          { required: true, message: '请输入你的问题', trigger: 'blur' },
        ],
      },
    }
  },
  created() {
    this.chat.id = this.user.id;

  },
  methods:{
    sendQuestion(){
              //将当前用户ID发送到后端
              this.$request.post('/chatID',this.user.id).then(
                  res=>{
                    if (res.code !== '200') {
                      this.$message.error(res.msg);
                    }
                  }
              )

              this.$request.post('/chat',this.chat.question).then(
                  res=>{
                    if (res.code !== '200'){
                      this.$message.error(res.msg);
                    }
                  })
            },
    clearAll(){
    }
  },
}
</script>


<style scoped>
.el-main {
  background-color: white;
  color: #333;
  text-align: center;
  line-height: 550px;
}
.el-footer {
  background-color: white;
  color: #333;
  text-align: center;
  line-height: 50px;
}
</style>