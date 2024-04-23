package com.example.springboot.controller;

import cn.hutool.core.io.FileUtil;
import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * 功能：上传文件接口
 * 作者：jackLiu
 * 日期：2024/4/19 下午5:56
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${ip:localhost}")
    String ip;

    @Value("${server.port}")
    String port;

    private static String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files";


    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();//文件的原始名称
        String mainName = FileUtil.mainName(originalFilename);
        String exName = FileUtil.extName(originalFilename);//文件的后缀
        File parentFile = new File(ROOT_PATH);
        if (!parentFile.exists()) {
            parentFile.mkdirs();  //创建目录不存在;创建目录
        }
        String filerealPath = ROOT_PATH + "\\" + originalFilename;
        File savefile  = new File(filerealPath);
        if (savefile.exists()) {//文件已经存在，那么重命名
            originalFilename = System.currentTimeMillis() + "_" +  mainName + "." + exName;
            filerealPath = ROOT_PATH + "\\" + originalFilename;
            savefile = new File(filerealPath);
        }
        file.transferTo(savefile);//在本地存储
        String url = "http://" + ip + ":" + port + "/file/download/" + originalFilename;
        return Result.success(url);//返回文件链接，即下载地址，后台提供
    }

    @AuthAccess
    @GetMapping("/download/{filename}")
    public void download(@PathVariable String filename, HttpServletResponse response) throws IOException {
        String filePath = ROOT_PATH + "\\" + filename;
        if(!FileUtil.exist(filePath)) {
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);   //字节数组，文件字节流
        outputStream.flush();
        outputStream.close();
    }
}
