package com.pzj.technicalcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pzj.technicalcommunity.entity.TcPicture;
import com.pzj.technicalcommunity.service.ITcPictureService;
import com.pzj.technicalcommunity.util.ResultPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
    @Autowired
    private ITcPictureService iTcPictureService;

    /**
     * Description 查询当前帖子内所有图片
     * Param 帖子ID(url)
     * Return 所有图片URL(json)
     */
    @RequestMapping("/download")
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
    public ResultPackage upload(MultipartFile[] file,Integer id) throws IOException {
        //判断文件是否为空
        if (file == null || file.length == 0){
            return ResultPackage.message(204,"无图片");
        }
        else{
            int count=0,total=0;
            for(MultipartFile fileData:file){
                //获取源文件名
                String filename = fileData.getOriginalFilename();
                //分割后缀名
                String suffix=filename.substring(filename.lastIndexOf("."));
                if(suffix.equals(".png") || suffix.equals(".jpg")){
                    //生成时间
                    String time = String.valueOf(System.currentTimeMillis());
                    //生成唯一文件名
                    String uuid = UUID.randomUUID().toString().trim();
                    //组合文件名
                    String fileNames=time+uuid+suffix;
                    String pathName="C:\\Users\\13425\\Documents\\JetBrains\\TechnicalCommunity\\src\\main\\resources\\vue\\public\\"+fileNames;
                    //实体类赋值
                    TcPicture tcPicture = new TcPicture();
                    tcPicture.setPicturePost(id);
                    tcPicture.setPictureUrl(fileNames);
                    //分别执行写入
                    fileData.transferTo(new File(pathName));
                    iTcPictureService.save(tcPicture);
                    count++;
                }
                total++;
            }
            if(count==total){
                return ResultPackage.pack();
            }
            else{
                return ResultPackage.message(202,"部分后缀错误");
            }
        }
    }
}
