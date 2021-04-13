package com.usian.controller;

import com.usian.utils.QiniuUtils;
import com.usian.utils.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("file")
public class ImageUpload {

    @RequestMapping("upload")
    public Result selectTbItemAllByPage(@RequestBody MultipartFile file) {
        Result result = new Result();
        try {
            String path= UUID.randomUUID().toString()+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            QiniuUtils.upload2Qiniu(file.getBytes(),path);
            String path2="http://qrbsiiawh.hn-bkt.clouddn.com/"+path;
            result.setStatus(200);
            result.setMsg("图片上传成功");
            result.setData(path2);
        }catch (Exception e){
            result.setStatus(900);
            result.setMsg("图片上传失败");
        }
        return result;
    }

}
