<template>
  <div>
    <div style="display:flex;">
      <el-card style="width: 45%; margin-right: 20px; height: 50%">
        <div style="margin-bottom: 10px">
          <el-input style="width: 200px" placeholder="查询学号" v-model="id"></el-input>
          <el-input style="width: 200px; margin: 0 5px" placeholder="查询姓名" v-model="name"></el-input>
          <el-button type="primary" @click="load(1)">查询</el-button>
        </div>
        <el-table ref="singleTable" highlight-current-row @current-change="handleCurrentChange" :data="tableData" stripe :header-cell-style="{backgroundColor:'aliceblue', color:'#666'}">
          <el-table-column prop="id" label="学号" align="center"></el-table-column>
          <el-table-column prop="name" label="姓名"></el-table-column>
        </el-table>
        <div style="margin: 10px 0">
          <el-pagination
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-sizes="[100, 200, 300, 400]"
              :page-size="pageSize"
              layout="total, prev, pager, next"
              :total="total">
          </el-pagination>
        </div>
      </el-card>
      <el-card style="width: 50%">
        <div ref="chatContainer" style="height:550px;overflow:auto;background:#EEEEEE; display: flex; flex-direction: column;">
          <div v-for="(chat, index) in dbChat" :key="index">
            <div class="chat-message" :class="{ 'user-chat': chat.role === 'user', 'teacher-chat': chat.role !== 'user' }">
              <div v-html="replaceCodeBlock(chat.message)"></div>
            </div>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>


<script>
import request from "@/utils/request";

export default {
  name: "Search",
  data(){
    return{
      loginUser: JSON.parse(localStorage.getItem("NowUser") || '{}'),
      tableData: [], //所有数据
      pageNum: 1,    //当前页码
      pageSize: 8,   //每页显示个数
      id: '',
      name: '',
      total: 0,
      value: '',
      dbChat:[],
      currentRow: null,
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
    }
  },
  created() {
    this.load();
  },
  methods:{
    load(pageNum) {
      if (pageNum) {
        this.pageNum = pageNum;
      }
      this.$request.get('user/selectByPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          id: this.id,
          name: this.name
        }
      }).then(res => {
        if (res.data && res.data.records) {
          this.tableData = res.data.records;
          this.total = res.data.total;
        } else {
          // 处理数据为空的情况，比如给出提示
          this.$message.error("数据为空");
        }
      }).catch(error => {
        // 处理请求失败的情况，比如给出提示
        this.$message.error("数据请求失败：" + error.message);
      });
    },
    handleCurrentChange(val) {
      this.currentRow = val;
        request.get(`/getMessageTeacher/${this.currentRow.id}`).then(res => {
          this.dbChat = res.data;
        });
    },
    replaceCodeBlock(message) {
      // 将消息中的 ``` 替换为 —————————————<br>
      return message.replace(/```/g, '—————————————————————————<br>');
    },
  }
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