<template>
  <div>
    <div>
      <el-input style="width: 200px" placeholder="查询学号" v-model="id"></el-input>
      <el-input style="width: 200px; margin: 0 5px" placeholder="查询姓名" v-model="name"></el-input>
      <el-button type="primary" @click="load(1)">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>
    <el-table  @selection-change="handleSelectionChange" :data="tableData" stripe :header-cell-style="{backgroundColor:'aliceblue', color:'#666'}">
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <el-table-column prop="id" label="学号" align="center"></el-table-column>
      <el-table-column prop="name" label="姓名" width="100"></el-table-column>
      <el-table-column prop="phone" label="手机号"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column label="头像" width="80px" align="center">
        <template v-slot="scope">
          <div style="display: flex; align-items: center">
            <el-image style="width: 50px; height: 50px; border-radius: 50%" :src="scope.row.avatar" ></el-image>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作"  align="center" width="300px">
        <template v-slot="scope">
          <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
          <el-button size="mini" type="info" plain @click="resetPassword(scope.row)">重置密码</el-button>
        </template>
      </el-table-column>
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

<!--    新增学生表单-->
    <el-dialog title="新增学生信息" :visible.sync="formVisible" width="30%">
      <el-form :model="form" label-width="80px" style="padding-right: 30px" :rules="rules" ref="formRef">
        <el-form-item label="学号" prop="id">
          <el-input v-model="form.id"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input type="textarea" v-model="form.address"></el-input>
        </el-form-item>
        <el-form-item label="头像" prop="avatar">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:9090/file/upload"
              :headers="{ token:user.token }"
              :file-list="form.avatar ? [form.avatar] : []"
              list-type="picture"
              :on-success="handleAvatarSuccess"
          >
            <el-button type="primary">上传头像</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="formVisible = false">取 消</el-button>
        <el-button type="primary" @click="add">添 加</el-button>
      </div>
    </el-dialog>

<!--   修改学生信息表单-->
    <el-dialog title="修改学生信息" :visible.sync="formEditVisible" width="30%">
      <el-form :model="form" label-width="80px" style="padding-right: 30px" :rules="rules" ref="formRef">
        <el-form-item label="学号" prop="id">
          <el-input v-model="form.id"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input type="textarea" v-model="form.address"></el-input>
        </el-form-item>
        <el-form-item label="头像" prop="avatar">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:9090/file/upload"
              :headers="{ token:user.token }"
              :file-list="form.avatar ? [form.avatar] : []"
              list-type="picture"
              :on-success="handleAvatarSuccess"
          >
            <el-button type="primary">修改头像</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="formEditVisible = false">取 消</el-button>
        <el-button type="primary" @click="change">修 改</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
export default {
  name: "Student",
  data(){
    return{
      tableData: [], //所有数据
      pageNum: 1,    //当前页码
      pageSize: 8,   //每页显示个数
      id: '',
      name: '',
      total: 0,
      formVisible: false,
      formEditVisible: false,
      form:{},
      user:JSON.parse(localStorage.getItem('NowUser')||'{}'),
      rules:{
        id: [
          {required:true, message:'请输入学号', trigger:'blur'}
        ],
      },
      ids:[]
    }
  },
  created() {
    this.load();
  },
  methods:{
    delBatch(){
      if (!this.ids.length){
        this.$message.warning("数据为空！");
      }else {
        this.$confirm("确认删除这些数据？","确认删除",{type:"warning"}).then(response =>{
              this.$request.delete('user/delete/batch',{data:this.ids}).then(res=>{
                if(res.code === '200'){
                  this.$message.success("删除成功");
                  this.load(1);
                }else{
                  this.$message.error(res.msg);
                }
              })
            }
        ).catch(()=>{})
      }
    },
    handleSelectionChange(rows){
      this.ids = rows.map(v => v.id);
    },
    del(id){
      this.$confirm("确认删除？","确认删除",{type:"warning"}).then(response =>{
          this.$request.delete('user/delete/'+id).then(res=>{
            if(res.code === '200'){
              this.$message.success("删除成功");
              this.load(1);
           }else{
              this.$message.error(res.msg);
            }
          })
        }
      ).catch(()=>{})
    },
    resetPassword(row){
      this.$confirm("确认重置？","确认重置",{type:"warning"}).then(response =>{
        this.form = row;
        this.form.password = '123456';
        this.$request.put('user/update', this.form).then(res => {
          if(res.code === '200'){
            this.$message.success("重置成功");
            this.load(1);
          }else{
            this.$message.error(res.msg);
          }
        })}
      ).catch(()=>{})

    },
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row));
      this.formEditVisible = true;
    },
    handleAdd(){
      this.form = {};  //清空表单
      this.formVisible = true;  //打开弹窗
    },
    change(){
      this.$refs.formRef.validate((valid) =>{
        if (valid){
          this.$request.put('user/update',this.form).then(res => {
            if(res.code === '200'){
              this.$message.success("修改成功");
              this.load(1);
            }else{
              this.$message.error(res.msg);
            }
            this.formEditVisible = false;
          })
        }
      })
    },
    add(){
      this.$refs.formRef.validate((valid) =>{
        if (valid){
          if (this.form.id.length<6 || this.form.id.length > 20){
            this.$message.error("学号长度应该在6-20位");
          }else {
            this.form.avatar = this.form.avatar ? this.form.avatar:'http://localhost:9090/file/download/1715405493092_none.jpg';
            this.$request.post('user/add',this.form).then(res => {
              if(res.code === '200'){
                this.$message.success("添加成功");
                this.load(1);
              }else{
                this.$message.error(res.msg);
                console.log(res);
              }
              this.formVisible = false;
            })
          }
        }
      })
    },
    load(pageNum){
      if(pageNum){
        this.pageNum = pageNum;
      }
      this.$request.get('user/selectByPage',{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          id:this.id,
          name:this.name
        }
      }).then(res =>{
        this.tableData = res.data.records;
        this.total = res.data.total;
      })
    },
    handleCurrentChange(pageNum){
        this.load(pageNum);
    },
    reset(){
      this.id = '';
      this.name = '';
      this.load();
    },
    handleAvatarSuccess(res,file,fileList){
      //把Uesr头像换成链接
      this.form.avatar = res.data
    }
  }
}
</script>

<style scoped>

</style>