package org.example.reggie.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @description:
 * @Title: CommonController
 * @Author: 刘成龙
 * @Version:1.0
 * @time: 2022/11/02 16:27
 */
@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    /**
     * 图片上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){

        //上传的文件首先会存储到一个临时文件中,当本次请求结束后会自动删除该临时文件。
        //log.info(file.toString());

        //原始文件名称，不建议使用，可能会上传相同文件名的文件，造成覆盖
        String fileName = file.getOriginalFilename();

        //截取上传问件的文件名
        String suffix = fileName.substring(fileName.lastIndexOf("."));

        //log.info(file.getOriginalFilename());

        //使用UUID随机生成一个问件名,在添加后缀
        String filePath = UUID.randomUUID().toString() + suffix;

        //判断文件目录是否存在
        File file1 = new File(basePath);
        if (!file1.exists()){
            file1.mkdirs();
        }

        try {
            file.transferTo(new File(basePath+filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.success(filePath);

    }

    /**
     * 图片下载
     * @param name
     * @return
     */
    @GetMapping("/download")
    public void download(HttpServletResponse response, String name){

        //log.info("下载的名字："+name);
        response.setContentType("image/jpeg");
        try(
                FileInputStream fis = new FileInputStream(new File(basePath+name));
                ServletOutputStream os = response.getOutputStream();
                ){
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fis.read(bytes))!=-1){
                os.write(bytes,0,len);
                os.flush();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
