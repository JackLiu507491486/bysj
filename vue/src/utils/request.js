import axios from 'axios'
//创建可一个新的axios对象
const request = axios.create({
    baseURL: 'http://localhost:9090',//后端的接口地址 ip:port
    timeout: 30000
})
// 请求拦截器
request.interceptors.request.use(config => {
        // 在发送请求之前做些什么
        config.headers['Content-Type'] = 'application/json;charset=utf-8';
        return config;
    },
    error => {
        // 对请求错误做些什么
        console.error('request error: ' + error)
        return Promise.reject(error)
    }
);

// 响应拦截器
request.interceptors.response.use(
    response => {
        // 对响应数据做些什么
        let res = response.data;

        if(typeof res === 'string'){
            res = res ? JSON.parse(res) :res
        }
        return res;
    },
    error => {
        // 对响应错误做些什么
        console.error('response error:' + error)
        return Promise.reject(error);
    }
);

export default request
