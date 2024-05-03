<template>
  <div>
    <el-container>
      <el-main>
        <div ref="chatContainer" style="height:550px;overflow:auto;background:#EEEEEE; display: flex; flex-direction: column;">
          <div v-for="(chat, index) in dbChat" :key="index">
            <div class="chat-message" :class="{ 'user-chat': chat.role === 'user', 'teacher-chat': chat.role !== 'user' }">
              <div v-html="replaceCodeBlock(chat.message)"></div>
            </div>
          </div>
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
                <el-button type="danger" style="margin-left: 20px;width: 100%;" @click="confirmDelete">删除记录</el-button>
              </el-form-item>
            </div>
          </el-form>
        </div>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Main",
  data(){
    return{
      dbChat:[],
      lastDbChat: [], // 上一次获取的 dbChat
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
    this.fetchMessages(); // 初始化时获取一次消息
    this.timer = setInterval(this.fetchMessages, 1000); // 每 1 秒发送一次请求获取消息
  },
  destroyed() {
    clearInterval(this.timer); // 组件销毁时清除定时器
  },
  methods:{
    fetchMessages() {
      request.get(`/getMessage/${this.chat.id}`).then(res => {
        this.dbChat = res.data;
        if (JSON.stringify(this.dbChat) !== JSON.stringify(this.lastDbChat)) {
          this.lastDbChat = [...this.dbChat]; // 更新 lastDbChat
          this.$nextTick(() => {
            this.scrollToBottom();
          });
        }
      });
    },
    scrollToBottom() {
      const chatContainer = this.$refs.chatContainer;
      if (chatContainer) {
        chatContainer.scrollTop = chatContainer.scrollHeight;
      }
    },
    sendQuestion(){
      //将当前用户ID发送到后端
      this.$request.post('/chatID', this.user.id).then(
          res => {
            if (res.code !== '200') {
              this.$message.error(res.msg);
            }
          }
      )
      //将用户输入传给后端接口
      this.$request.post('/chat', this.chat.question).then(
          res => {
            if (res.code !== '200') {
              this.$message.error(res.msg);
            }
          })
      this.chat.question = '';
    },
    confirmDelete() {
      this.$confirm('确定要删除所有记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.clearAll();
      }).catch(() => {
        // 用户取消操作
      });
    },
    clearAll() {
      // 执行删除记录操作
      request.delete(`/deleteChat/${this.chat.id}`).then(res => {});
      this.dbChat = [];
      // 提示删除成功
      this.$message.success('记录已删除');
    },
    replaceCodeBlock(message) {
      // 将消息中的 ``` 替换为 —————————————<br>
      return message.replace(/```/g, '————————————————————————————————————————————<br>');
    },
  },
}
</script>

<style scoped>
.chat-message {
  display: inline-block;
  max-width: 80%; /* 最大宽度，可以根据需要调整 */
  padding: 10px;
  border-radius: 10px;
  margin: 5px 0;
  white-space: pre-wrap; /* 保留换行符和空格 */
}
.user-chat {
  background-color: #DCF8C6; /* 用户聊天框的背景颜色 */
  float: right; /* 将用户聊天框浮动到右侧 */
}

.teacher-chat {
  background-color: #E0E0E0; /* 教师聊天框的背景颜色 */
  float: left; /* 将教师聊天框浮动到左侧 */
}
</style>
