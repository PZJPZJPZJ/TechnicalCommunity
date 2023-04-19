package com.pzj.technicalcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pzj.technicalcommunity.entity.TcPicture;
import com.pzj.technicalcommunity.service.ITcPictureService;
import com.pzj.technicalcommunity.util.ResultPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.pzj.technicalcommunity.util.PictureUtils.generateUUID;
import static com.pzj.technicalcommunity.util.PictureUtils.getFileExtension;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pzj
 * @since 2023-02-06
 */
@RestController
@RequestMapping("/picture")
public class TcPictureController {
    @Value("${data.domain}")
    private String domain;
    @Autowired
    private ITcPictureService iTcPictureService;

    /**
     * Description 查询当前帖子内所有图片
     * Param 帖子ID(url)
     * Return 所有图片URL(json)
     */
    @GetMapping("/download")
    public ResultPackage download(Integer id){
        //设置查询条件
        QueryWrapper<TcPicture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("picture_post",id);
        //执行查询
        List<TcPicture> list = iTcPictureService.list(queryWrapper);
        return ResultPackage.pack(list, (long) list.size());
    }

    /**
     * Description 新建帖子上传图片
     * Param 多个文件,帖子ID(url)
     * Return 执行结果(bool)
     */
    @RequestMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("files") List<MultipartFile> files, Integer id) throws IOException {
        for (MultipartFile file : files) {
            // 生成新的文件名
            String name = generateUUID() + getFileExtension(file.getOriginalFilename());
            String path = System.getProperty("user.dir") + "\\target\\classes\\static\\img\\"+name;
            String url = domain + "/img/" + name;
            //写入文件
            file.transferTo(new File(path));
            //实体类赋值
            TcPicture tcPicture = new TcPicture();
            tcPicture.setPicturePost(id);
            tcPicture.setPictureUrl(url);
            //分别执行写入
            iTcPictureService.save(tcPicture);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
