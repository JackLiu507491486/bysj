package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.UserService.StudentService;
import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 功能：实现用户信息前后端交互
 * 作者：jackLiu
 * 日期：2024/4/16 22:03
 */

@RestController
@RequestMapping("/user")
@CrossOrigin
public class StudentController {

    @Autowired
    StudentService studentService;
    /**
     * 新增用户信息
     * @param student
     * @return
     */
    @AuthAccess
    @PostMapping("/add")
    public Result add(@RequestBody Student student) {
        try{
            studentService.save(student);
        }catch (Exception e){
            if(e instanceof DuplicateKeyException){
                return Result.error("插入数据库错误");
            }else {
                return Result.error("系统错误");
            }
        }
        return Result.success();
    }

    /**
     * 修改用户信息
     * @param student  用户信息
     * @return
     */
    @AuthAccess
    @PutMapping("/update")
    public Result update(@RequestBody Student student) {
        try{
            studentService.updateById(student);
        }catch (Exception e){
            if(e instanceof DuplicateKeyException){
                return Result.error("插入数据库错误");
            }else {
                return Result.error("系统错误");
            }
        }
        return Result.success();

    }


    /**
     * 删除用户信息
     * @param id  用户id
     * @return
     */
    @AuthAccess
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable String id) {
        studentService.removeById(id);
        return Result.success();
    }

    /**
     * 批量删除用户信息
     * @param ids  用户们id
     * @return
     */
    @AuthAccess
    @DeleteMapping("/delete/batch")
    public Result batchDelete(@RequestBody List<String> ids) {
        studentService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询全部用户信息
     * @return 要返回数据
     */
    @AuthAccess
    @GetMapping("/selectAll")
    public Result selectAll() {
                                                    //select * from student order by id asc
        List<Student> students = studentService.list(new QueryWrapper<Student>().orderByAsc("id"));
        return Result.success(students);
    }

    /**
     * 查询id对应用户信息
     * @return 要返回数据
     */
    @AuthAccess
    @GetMapping("/selectById/{id}")
    public Result selectAll(@PathVariable String id) {
        Student student = studentService.getById(id);
        return Result.success(student);
    }


    @AuthAccess
    @GetMapping("/selectByPage")
    public Result selectByPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,@RequestParam String id,@RequestParam String name) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<Student>().orderByAsc("id");
        //select * from user where id like '%#{id}%' and name like '%#{name}%'
        queryWrapper.like(StrUtil.isNotBlank(id),"id", id);
        queryWrapper.like(StrUtil.isNotBlank(name),"name", name);
        Page<Student> page = studentService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(page);
    }

}
