<template>
  <div>
    <el-card style="width: 50%">
      <el-form :model="user" label-width="80px" style="padding-right: 30px">
        <div style="margin: 15px; text-align: center">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:9090/file/upload"
              :headers="{ token:user.token }"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
          >
            <img v-if="user.avatar" :src="user.avatar" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
        <el-form-item label="职工号" prop="ID">
          <el-input v-model="user.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="user.name"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="user.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="user.email"></el-input>
        </el-form-item>
        <div style="text-align: center; margin-bottom: 20px">
          <el-button type="primary" @click="update">保  存</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>


<script>
import login from "@/views/Login.vue";

export default {
  name: "My",
  data(){
    return{
      user:JSON.parse(localStorage.getItem('NowUser')||'{}')
    }
  },
  created() {

  },
  methods:{
    update(){
      //保存更改信息
      this.$request.put('/manger/update',this.user).then(
          res=>{
            if (res.code === '200'){
              //成功更新
              this.$message.success("保存成功");
              //更新浏览器缓存
              localStorage.setItem('NowUser',JSON.stringify(this.user));
              //触发student界面更新
              this.$emit("update:my", this.user)
            }else{
              this.$message.error(res.msg);
            }
          }
      )
      //更新浏览器
    },
    handleAvatarSuccess(res,file,fileList){
      //把Uesr头像换成链接
      this.user.avatar = res.data
    }
  }
}
</script>

<style scoped>
/deep/.el-form-item__label{
  font-weight: bold;
}
/deep/.el-upload{
  border-radius:50%;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
  border-radius:50%;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
  border-radius:50%;
}
</style>