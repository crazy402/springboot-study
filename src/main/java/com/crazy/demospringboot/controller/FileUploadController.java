package com.crazy.demospringboot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName FileUploadController
 * @Description //TODO
 * @Author crazy402
 * @Date 2020/11/22 16:20
 * @Version 1.0
 **/
@RestController
public class FileUploadController {
    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");

    @PostMapping("/upload")
    public String upload(MultipartFile file, HttpServletRequest req) {
        String format = sdf.format(new Date());

        String s = req.getServletContext().getRealPath("/img") + format;

        File file1 = new File(s);

        if (!file1.exists()) {
            file1.mkdirs();
        }
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));

        try {
            file.transferTo(new File(file1, newName));
            //动态获取地址
            String url = req.getScheme() + "://" + req.getServerName() + req.getServerPort() + "/img" + format + newName;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";

    }
}
