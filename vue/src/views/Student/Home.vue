<script>
export default {
  name: "Home",
  data() {
    return {
      value: new Date(),
      time: '' + new Date().getFullYear()+'年'+(new Date().getMonth()+1)+'月'+ new Date().getUTCDate() + '日',
      students: [],
      currentTime: '',
      calendarData: [
        { day: "2024-04-03",status:"✔️", },
      ],
      loginUser: JSON.parse(localStorage.getItem("NowUser") || '{}')
    }
  },
  mounted() {
    // 每秒更新一次时间
    setInterval(() => {
      this.updateTime();
    }, 1000);

    // 初始化时更新时间
    this.updateTime();
  },
  methods: {
    updateTime() {
      const now = new Date();
      const hours = this.padZero(now.getHours());
      const minutes = this.padZero(now.getMinutes());
      const seconds = this.padZero(now.getSeconds());
      this.currentTime = `${hours}:${minutes}:${seconds}`;
    },
    padZero(value) {
      return value < 10 ? `0${value}` : value;
    },
  },
};
</script>

<template>
  <div>
    <div style="box-shadow: 0 0 10px rgba(0,0,0,.1); padding: 10px 20px; border-radius: 5px ; margin-bottom: 10px">
              <span style="align-items: center; font-size: 20px">
               {{loginUser.name}}同学欢迎使用该系统
              </span>
    </div>
    <div style="display:flex;">
      <el-card style="width: 45%; margin-right: 20px; height: 50%">
        <el-form :model="user" label-width="80px" style="padding-right: 30px">
          <div style="margin: 15px; text-align: center">
            <img :src="loginUser.avatar" alt=""
                 style="width: 100px;height: 100px; border-radius: 50%;overflow: hidden;">
          </div>
          <p style="margin-top: 10px"><strong>学号：</strong>{{ loginUser.id }}</p>
          <p style="margin-top: 10px"><strong>姓名：</strong>{{ loginUser.name }}</p>
          <p style="margin-top: 10px"><strong>电话：</strong>{{ loginUser.phone }}</p>
          <p style="margin-top: 10px"><strong>邮箱：</strong>{{ loginUser.email }}</p>
          <p style="margin-top: 10px"><strong>地址：</strong>{{ loginUser.address }}</p>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<style scoped>
</style>