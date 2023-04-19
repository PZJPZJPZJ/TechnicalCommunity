package com.pzj.technicalcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcTag;
import com.pzj.technicalcommunity.service.ITcTagService;
import com.pzj.technicalcommunity.util.ResultPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

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
@RequestMapping("/tag")
public class TcTagController {
    @Value("${data.domain}")
    private String domain;
    @Autowired
    private ITcTagService iTcTagService;

    @GetMapping("/show")
    public ResultPackage show(Integer id){
        return ResultPackage.pack(iTcTagService.getById(id));
    }

    /**
     * 获取全部标签
     * @return
     */
    @GetMapping("/list")
    public ResultPackage list(){
        return ResultPackage.pack(iTcTagService.list());
    }

    /**
     * Description 分页查询标签信息
     * Param 分页信息(json)
     * Return 分页标签(json)
     */
    @PostMapping("/page")
    public ResultPackage listPage(@RequestBody HashMap hashMap){
        Page<TcTag> page = new Page<>();
        page.setCurrent((int)hashMap.get("pageNum"));
        page.setSize((int)hashMap.get("pageSize"));
        IPage<TcTag> iPage = iTcTagService.page(page);
        return ResultPackage.pack(iPage.getRecords(),iPage.getTotal());
    }

    @GetMapping("/introduce")
    public ResultPackage introduce(){
        Page<TcTag> page = new Page<>();
        page.setCurrent(1);
        page.setSize(10);
        //设置查询条件
        QueryWrapper<TcTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("ORDER BY RAND()");
        IPage<TcTag> iPage = iTcTagService.page(page,queryWrapper);
        return ResultPackage.pack(iPage.getRecords(), iPage.getTotal());
    }

    /**
     * Description 展示标签键入查询
     * Param 分页信息，查询关键词(json)
     * Return 查询结果(json)
     */
    @GetMapping ("/search")
    public ResultPackage search(String str){
        //设置查询条件
        QueryWrapper<TcTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("tag_name",str);
        //执行查询
        return ResultPackage.pack(iTcTagService.list(queryWrapper));
    }

    /**
     * Description 用户新建标签
     * Param 标签实体(json)
     * Return 执行结果(bool)
     */
    @RequestMapping("/save")
    public ResponseEntity<String> save(@RequestParam("tagName") String tagName, @RequestParam("file") MultipartFile file) throws IOException {
        String name = generateUUID() + getFileExtension(file.getOriginalFilename());
        String path = System.getProperty("user.dir") + "\\target\\classes\\static\\img\\"+name;
        String url = domain + "/img/" + name;
        //写入文件
        TcTag tcTag = new TcTag();
        file.transferTo(new File(path));
        tcTag.setTagName(tagName);
        tcTag.setTagCover(url);
        if (iTcTagService.save(tcTag)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Description 管理员修改标签信息
     * Param 标签实体(json)
     * Return 执行结果(bool)
     */
    @PostMapping("/update")
    public boolean update(@RequestBody TcTag tcTag){
        return iTcTagService.updateById(tcTag);
    }

    /**
     * Description 管理员维护标签
     * Param 标签ID(url)
     * Return 执行结果(bool)
     */
    @PostMapping("/mod")
    public boolean delete(Integer id){
        return iTcTagService.removeById(id);
    }
}
