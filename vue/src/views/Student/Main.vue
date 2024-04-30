<template>
<div>
  <el-card style="height: 87vh">
    <el-card style="height: 76vh "></el-card>
    <el-card style="margin-top: 10px; height: 5vh">
      <el-form-item label="学号" prop="ID">
        <el-input v-model="question" disabled></el-input>
      </el-form-item>
    </el-card>
  </el-card>
</div>
</template>


<script>
export default {
  name: "Main",
  data(){
    return{
      question:'',
    }
  }
}
</script>


<style scoped>

</style>