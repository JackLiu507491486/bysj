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
    <el-card style="width: 100%">
      <div style="display: flex;margin-bottom: 30px; align-items: center;justify-content: center" class="clock"><span style="font-size: 20px">现在是{{time}} {{ currentTime }}，快去学习吧！！</span></div>
      <div style="flex: 1">
        <el-card style="width: 100%">
          <el-calendar :first-day-of-week="7">
            <template slot="dateCell" slot-scope="{ data }">
              <p>{{ data.day.split("-").slice(2).join("-") }}<br/></p>
              <div
                  v-for="(item, index) in calendarData"
                  :key="index"
              >
                <div v-if="data.day == item.day">
                  {{item.status}}
                </div>
              </div>
            </template>
          </el-calendar>
        </el-card>
      </div>
    </el-card>
  </div>
</template>

<style scoped>
</style>