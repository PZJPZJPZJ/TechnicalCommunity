package com.pzj.technicalcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcPost;
import com.pzj.technicalcommunity.entity.TcPostDTO;
import com.pzj.technicalcommunity.service.ITcPostService;
import com.pzj.technicalcommunity.service.ITcUserService;
import com.pzj.technicalcommunity.util.JwtUtils;
import com.pzj.technicalcommunity.util.ResultPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pzj
 * @since 2023-02-06
 */
@RestController
@RequestMapping("/post")
public class TcPostController {
    @Autowired
    private ITcPostService iTcPostService;
    @Autowired
    private ITcUserService iTcUserService;

    /**
     * Description 精确展示当前帖子
     * Param 帖子ID(url)
     * Return 当前帖子内容(json)
     */
    @GetMapping("/show")
    public ResultPackage show(Integer id){
        //执行自定义查询
        TcPostDTO tcPostDTO = iTcPostService.listOne(id);
        return ResultPackage.pack(tcPostDTO);
    }

    /**
     * Description 模糊查询帖子
     * Param 分页数据,部分标题(json)
     * Return 当前标签所有帖子(json)
     */
    @RequestMapping("/search")
    public ResultPackage search(@RequestBody HashMap hashMap){
        //设置页数和页大小
        Page<TcPost> page = new Page<>();
        page.setCurrent((int)hashMap.get("pageNum"));
        page.setSize((int)hashMap.get("pageSize"));
        //设置查询条件
        QueryWrapper<TcPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("post_title",hashMap.get("postTitle"));
        //执行查询
        IPage<TcPost> iPage = iTcPostService.page(page,queryWrapper);
        return ResultPackage.pack(iPage.getRecords(),iPage.getTotal());
    }

    /**
     * 列出当前tag所有帖子
     * @param hashMap
     * @param id
     * @return 封装
     */
    @RequestMapping("/tag")
    public ResultPackage tag(@RequestBody HashMap hashMap,Integer id){
        //设置页数和页大小
        Page<TcPostDTO> page = new Page<>();
        page.setCurrent((int)hashMap.get("pageNum"));
        page.setSize((int)hashMap.get("pageSize"));
        //执行查询
        IPage<TcPostDTO> iPage = iTcPostService.pageTag(page,id);
        return ResultPackage.pack(iPage.getRecords(),iPage.getTotal());
    }

    /**
     * Description 随机展示首页热门帖子
     * Param 分页数据(json)
     * Return 随机热门帖子(json)
     */
    @RequestMapping("/hot")
    public ResultPackage hot(@RequestBody HashMap hashMap){
        //设置页数和页大小
        Page<TcPostDTO> page = new Page<>();
        page.setCurrent((int)hashMap.get("pageNum"));
        page.setSize((int)hashMap.get("pageSize"));
        //执行自定义查询
        IPage<TcPostDTO> iPage = iTcPostService.pageTop(page);
        return ResultPackage.pack(iPage.getRecords(),iPage.getTotal());
    }

    /**
     * Description 时间倒叙展示当前用户所有已发布帖子,若是管理员则列出所有帖子
     * Param 分页数据(json)
     * Return 用户帖子(json)
     */
    @RequestMapping("/user")
    public ResultPackage user(@RequestBody HashMap hashMapBody,@RequestHeader HashMap hashMapHeader){
        //设置页数和页大小
        Page<TcPostDTO> page = new Page<>();
        page.setCurrent((int)hashMapBody.get("pageNum"));
        page.setSize((int)hashMapBody.get("pageSize"));
        //根据token获取当前用户
        Integer userId = Integer.valueOf(JwtUtils.getClaimByToken((String) hashMapHeader.get("authorization")).getSubject());
        IPage<TcPostDTO> iPage;
        //若是管理员则列出所有帖子
        if (iTcUserService.getById(userId).getUserAdmin()){
            iPage = iTcPostService.pageAll(page);
        }
        else{
            iPage = iTcPostService.pageUser(page,userId);
        }
        return ResultPackage.pack(iPage.getRecords(),iPage.getTotal());
    }

    /**
     * Description 置顶当前帖子
     * Param 帖子ID(url)
     * Return 执行结果(bool)
     */
    @RequestMapping ("/top")
    public ResponseEntity<String> top(@RequestHeader HashMap hashMapHeader, Integer id){
        //根据token获取当前用户
        Integer userId = Integer.valueOf(JwtUtils.getClaimByToken((String) hashMapHeader.get("authorization")).getSubject());
        //验证当前用户是否为管理员
        if (iTcUserService.getById(userId).getUserAdmin()){
            //设置实体数据
            TcPost tcPost = new TcPost();
            tcPost.setPostId(id);
            tcPost.setPostTop(true);
            iTcPostService.updateById(tcPost);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Description 取消置顶当前帖子
     * Param 帖子ID(url)
     * Return 执行结果(bool)
     */
    @RequestMapping("/bottom")
    public ResponseEntity<String> bottom(@RequestHeader HashMap hashMapHeader, Integer id){
        //根据token获取当前用户
        Integer userId = Integer.valueOf(JwtUtils.getClaimByToken((String) hashMapHeader.get("authorization")).getSubject());
        //验证当前用户是否为管理员
        if (iTcUserService.getById(userId).getUserAdmin()){
            //设置实体数据
            TcPost tcPost = new TcPost();
            tcPost.setPostId(id);
            tcPost.setPostTop(false);
            iTcPostService.updateById(tcPost);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Description 新建帖子
     * Param 帖子实体(json)
     * Return 新帖子ID(json)
     */
    @PostMapping("/save")
    public ResultPackage save(@RequestBody TcPost tcPost){
        iTcPostService.save(tcPost);
        return ResultPackage.pack(tcPost.getPostId());
    }

    /**
     * Description 删除帖子
     * Param 帖子ID(url)
     * Return 执行结果(bool)
     */
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return iTcPostService.removeById(id);
    }

}
