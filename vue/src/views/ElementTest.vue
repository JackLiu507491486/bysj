<template>
    <div>
        <el-menu router style="border: none;" :default-active="$route.path">
            <el-menu-item index="/">
              <template slot="title">
                <i class="el-icon-house"></i>
                <span>系统首页</span>
              </template>
            </el-menu-item>
            <el-menu-item index="/element">Element实例</el-menu-item>
            <el-menu-item>系统首页</el-menu-item>
            <el-menu-item>系统首页</el-menu-item>
            <el-menu-item>系统首页</el-menu-item>

            <el-submenu>
              <template slot="title">
                <i class="el-icon-menu"></i>
                <span>信息管理</span>
              </template>
              <el-menu-item>用户信息</el-menu-item>
            </el-submenu>
          </el-menu>

        <el-row>
            <el-col :span="12">
                <div style="height: 100px; width: 100% ;background-color:blue"></div>
            </el-col> 
            <el-col :span="12">
                <div style="height: 100px; width: 100%;background-color:yellow"></div>
            </el-col> 
        </el-row>
        <el-row>
            <el-button>默认</el-button>
            <el-button type = "primary">主要</el-button>
            <el-button type = "danger">危险</el-button>
            <el-button type = "success">成功</el-button>
        </el-row>

        <el-row style="margin:20px">
            <el-col>
                <el-input style="width: 200px" v-model="value0" placeholder="请输入内容" suffix-icon ="el-icon-user"></el-input>
                <el-input type="textarea" style="width: 200px"  v-model="value1"></el-input>
                <el-input show-password style="width: 200px" v-model="value2" placeholder="密码框"></el-input>
                <el-input clearable style="width: 200px" v-model="value3" placeholder="请输入内容"></el-input>
                <el-input clearable style="width: 200px" v-model="value3" placeholder="请输入内容" prefix-icon="el-icon-search"></el-input>
            </el-col>
        </el-row>

        <el-row style="margin: 20px">
            <el-autocomplete style="width: 300px" aria-placeholder="输入内容" :fetch-suggestions="querySearch" :trigger-on-focus="false" v-model="value0"></el-autocomplete>
        </el-row>

        <el-row>
            <el-select v-model="select">
                <el-option value="香蕉"></el-option>
                <el-option value="苹果"></el-option>
                <el-option value="李子"></el-option>
            </el-select>

            <el-select v-model="select" @change="changeSelect" multiple>
                <el-option v-for="item in fruits" :key="item.id" :label="item.name" :value="item.name"></el-option>
            </el-select>
        </el-row>

        <el-row>
            <el-radio-group v-model="gender" @change="changeGender">
                <el-radio label="男"></el-radio>
                <el-radio label="女"></el-radio>
            </el-radio-group>
        </el-row>


        <el-row style="margin: 100px;">
            <el-table :data="student" border :header-cell-style="{background:'aliceblue',fontSize:'16px'}">
                <el-table-column label="姓名" prop="name"></el-table-column>
                <el-table-column label="学号" prop="id"></el-table-column>
                <el-table-column label="性别" prop="gender"></el-table-column>
                <el-table-column label="地址" prop="地址"></el-table-column>
            </el-table>
        </el-row>
    </div>
</template>

<script>
    export default{
        name:'Element',
        data(){
            return {
                value0:'',
                value1:'',
                value2:'',
                value3:'',
                coffees:[{value:'1星巴克'}, {value:'2瑞信'} ,{value:'3库迪'}],
                select:'',
                gender:'',
                fruits:[
                    {name:'苹果',id:1},
                    {name:'梨',id:2},
                    {name:'葡萄',id:3},
                    {name:'西红柿',id:4},
                    {name:'黄瓜',id:5},
                    {name:'香蕉',id:6},
                    {name:'西瓜',id:7}
                ],
                student:[
                    {name:'zhangsan',id:'112020201020513',gender:'男',地址:'山西省大同市'},
                    {name:'zhangsan',id:'112020201020513',gender:'男',地址:'山西省大同市'},
                    {name:'zhangsan',id:'112020201020513',gender:'男',地址:'山西省大同市'},
                    {name:'zhangsan',id:'112020201020513',gender:'男',地址:'山西省大同市'},
                    {name:'zhangsan',id:'112020201020513',gender:'男',地址:'山西省大同市'},
                    {name:'zhangsan',id:'112020201020513',gender:'男',地址:'山西省大同市'},
                    {name:'zhangsan',id:'112020201020513',gender:'男',地址:'山西省大同市'},
                ]
            }
        },
        methods:{
            querySearch(query,cb){
                let results = query ? this.coffees.filter(v=>v.value.includes(query)) : this.coffees;
                cb(results);
            },
            changeSelect(){
                console.log(this.select);
            },
            changeGender(){
                alert(this.gender);
            }
        }
    }
</script>