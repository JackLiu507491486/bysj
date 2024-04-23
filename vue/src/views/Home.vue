<script>
export default {
  name: "Home",
  data() {
    return {
      students: [],
      loginUser: JSON.parse(localStorage.getItem("NowUser") || '{}')
    }
  }
}
</script>

<template>
  <div>
    <div style="box-shadow: 0 0 10px rgba(0,0,0,.1); padding: 10px 20px; border-radius: 5px ; margin-bottom: 10px">
              <span style="align-items: center; font-size: 20px">
               {{loginUser.name}}欢迎使用该系统
              </span>
    </div>
  </div>
</template>

<style scoped>

</style>