package com.pzj.technicalcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcNews;
import com.pzj.technicalcommunity.service.ITcNewsService;
import com.pzj.technicalcommunity.service.ITcUserService;
import com.pzj.technicalcommunity.util.JwtUtils;
import com.pzj.technicalcommunity.util.ResultPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
@RequestMapping("/news")
public class TcNewsController {
    @Autowired
    private ITcNewsService iTcNewsService;
    @Autowired
    private ITcUserService iTcUserService;

    /**
     * Description 展示最新4条新闻
     * Param 无
     * Return 当前标签所有帖子(json)
     */
    @GetMapping("/list")
    public ResultPackage list(){
        //设置查询条件
        QueryWrapper<TcNews> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("news_time").last("LIMIT 4");
        //执行查询
        List<TcNews> list = iTcNewsService.list(queryWrapper);
        return ResultPackage.pack(list);
    }

    /**
     * Description 新增新闻
     * Param 新闻实体(json)
     * Return 执行结果(bool)
     */
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam("newsTitle") String newsTitle,@RequestParam("newsContent") String newsContent, @RequestHeader HashMap hashMapHeader, @RequestParam("file") MultipartFile file) throws IOException {
        //根据token获取当前用户
        Integer userId = Integer.valueOf(JwtUtils.getClaimByToken((String) hashMapHeader.get("authorization")).getSubject());
        if (iTcUserService.getById(userId).getUserAdmin()){
            // 生成新的文件名
            String name = generateUUID() + getFileExtension(file.getOriginalFilename());
            String path = "C:\\Users\\13425\\Documents\\JetBrains\\TechnicalCommunity\\target\\classes\\static\\img\\"+name;
            String url = "http://localhost:8080/img/" + name;
            //写入文件
            TcNews tcNews = new TcNews();
            file.transferTo(new File(path));
            tcNews.setNewsCover(url);
            tcNews.setNewsAdmin(userId);
            tcNews.setNewsTitle(newsTitle);
            tcNews.setNewsContent(newsContent);
            iTcNewsService.save(tcNews);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 分页时间倒叙展示所有新闻
     * @param hashMapBody
     * @return json
     */
    @RequestMapping("/page")
    public ResultPackage page(@RequestBody HashMap hashMapBody){
        //设置页数和页大小
        Page<TcNews> page = new Page<>();
        page.setCurrent((int)hashMapBody.get("pageNum"));
        page.setSize((int)hashMapBody.get("pageSize"));
        //设置查询条件
        QueryWrapper<TcNews> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("news_time");
        //执行查询
        IPage<TcNews> iPage = iTcNewsService.page(page,queryWrapper);
        return ResultPackage.pack(iPage.getRecords(), iPage.getTotal());
    }

    /**
     * Description 修改新闻
     * Param 新闻实体(json)
     * Return 执行结果(bool)
     */
    @PostMapping("/update")
    public boolean update(@RequestBody TcNews tcNews){
        return iTcNewsService.updateById(tcNews);
    }

    /**
     * Description 新增或修改新闻
     * Param 新闻实体(json)
     * Return 执行结果(bool)
     */
    @PostMapping("/mod")
    public boolean mod(@RequestBody TcNews tcNews){
        return iTcNewsService.saveOrUpdate(tcNews);
    }

    /**
     * Description 删除新闻
     * Param 新闻ID(url)
     * Return 执行结果(bool)
     */
    @GetMapping("/delete")
    public ResponseEntity<String> delete(@RequestHeader HashMap hashMapHeader,Integer id){
        //根据token获取当前用户
        Integer userId = Integer.valueOf(JwtUtils.getClaimByToken((String) hashMapHeader.get("authorization")).getSubject());
        //验证当前用户是否为管理员
        if (iTcUserService.getById(userId).getUserAdmin()){
            iTcNewsService.removeById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
