package com.pzj.technicalcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pzj.technicalcommunity.entity.TcPicture;
import com.pzj.technicalcommunity.entity.TcPost;
import com.pzj.technicalcommunity.service.ITcPictureService;
import com.pzj.technicalcommunity.util.ResultPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        return ResultPackage.pack(list,list.stream().count());
    }

    /**
     * Description 新建帖子上传图片
     * Param
     * Return
     */
    @RequestMapping("/upload")
    public boolean upload(){
        return true;
    }
}
