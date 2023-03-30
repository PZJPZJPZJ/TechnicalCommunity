package com.pzj.technicalcommunity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.technicalcommunity.entity.TcUser;
import com.pzj.technicalcommunity.entity.TcUserDTO;
import com.pzj.technicalcommunity.service.ITcUserService;
import com.pzj.technicalcommunity.util.JwtUtils;
import com.pzj.technicalcommunity.util.PasswordEncoder;
import com.pzj.technicalcommunity.util.ResultPackage;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("/user")
public class TcUserController {
    @Autowired
    private ITcUserService iTcUserService;

    /**
     * Description 分页查询用户信息
     * Param 分页信息(json)
     * Return 分页用户数据(bool)
     */
    @PostMapping("/page")
    public ResultPackage listPage(@RequestBody HashMap hashMapBody,@RequestHeader HashMap hashMapHeader){
        //根据token获取当前用户
        Integer userId = Integer.valueOf(JwtUtils.getClaimByToken((String) hashMapHeader.get("authorization")).getSubject());
        //验证当前用户是否为管理员
        if (iTcUserService.getById(userId).getUserAdmin()){
            Page<TcUserDTO> page = new Page<>();
            page.setCurrent((int)hashMapBody.get("pageNum"));
            page.setSize((int)hashMapBody.get("pageSize"));
            //执行查询
            IPage<TcUserDTO> iPage = iTcUserService.pageSafe(page);
            return ResultPackage.pack(iPage.getRecords(),iPage.getTotal());
        }
        else {
            return ResultPackage.message(401,"Unauthorized");
        }
    }

    /**
     * 获取用户信息不包含密码
     * @param id
     * @return json
     */
    @GetMapping("/info")
    public ResultPackage info(Integer id){
        TcUserDTO tcUserDTO = new TcUserDTO();
        TcUser tcUser = iTcUserService.getById(id);
        //防止不必要信息返回到前端
        BeanUtils.copyProperties(tcUser,tcUserDTO);
        return ResultPackage.pack(tcUserDTO);
    }

    //模糊查询用户信息
    @PostMapping ("/search")
    public ResultPackage search(@RequestBody TcUser tcUser){
        QueryWrapper<TcUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("user_name",tcUser.getUserName()).select("user_id").select("user_name");
        //执行查询
        List<TcUser> list = iTcUserService.list(queryWrapper);
        return ResultPackage.pack(list);
    }

    /**
     * Description 用户注册
     * Param 用户实体(json)
     * Return 执行结果(bool)
     */
    @PostMapping("/save")
    public ResultPackage save(@RequestBody TcUser tcUser){
        //加密密码
        tcUser.setUserPassword(PasswordEncoder.encode(tcUser.getUserPassword()));
        //防止前端修改数据
        tcUser.setUserAdmin(false);
        //执行方法
        iTcUserService.save(tcUser);
        //返回用户ID
        return ResultPackage.pack(tcUser.getUserId());
    }

    /**
     * 移除用户
     * @param id
     * @return 状态码
     */
    @GetMapping("/delete")
    public ResponseEntity<String> delete(Integer id){
        if (iTcUserService.removeById(id)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Description 用户信息修改
     * Param 用户实体(json)
     * Return 执行结果(bool)
     */
    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody TcUser tcUser,@RequestHeader HashMap hashMapHeader){
        //根据token写入用户id
        Integer userId = Integer.valueOf(JwtUtils.getClaimByToken((String) hashMapHeader.get("authorization")).getSubject());
        tcUser.setUserId(userId);
        //密码不为空时更新加密密码
        if(tcUser.getUserPassword() != null){
            tcUser.setUserPassword(PasswordEncoder.encode(tcUser.getUserPassword()));
        }
        if (iTcUserService.updateById(tcUser)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 更新用户头像
     * @return
     */
    @RequestMapping("/cover")
    public ResponseEntity<String> cover(@RequestHeader HashMap hashMapHeader, @RequestParam("file") MultipartFile file) throws IOException {
        //根据token获取当前用户
        Integer userId = Integer.valueOf(JwtUtils.getClaimByToken((String) hashMapHeader.get("authorization")).getSubject());
        //创建新对象
        TcUser tcUser = new TcUser();
        tcUser.setUserId(userId);
        //处理图片
        String name = generateUUID() + getFileExtension(file.getOriginalFilename());
        String path = "C:\\Users\\13425\\Documents\\JetBrains\\TechnicalCommunity\\target\\classes\\static\\img\\"+name;
        String url = "http://localhost:8080/img/" + name;
        file.transferTo(new File(path));
        tcUser.setUserCover(url);
        //写入数据库
        if (iTcUserService.updateById(tcUser)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 查询用户登录Token是否过期
     * @param hashMap
     * @return HTTP状态码
     */
    @PostMapping("/token")
    public ResponseEntity<String> token(@RequestBody HashMap hashMap){
        if(JwtUtils.isTokenExpired((String) hashMap.get("token"))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    /**
     * 根据token获取用户名
     * @param token
     * @return 用户名
     */
    @GetMapping("/name")
    public String name(String token){
        return JwtUtils.getClaimByToken(token).getSubject();
    }

    /**
     * 提权普通用户为管理员
     * @param hashMapHeader
     * @param id
     * @return 状态码
     */
    @GetMapping("/authority")
    public ResponseEntity<String> authority(@RequestHeader HashMap hashMapHeader,Integer id){
        //根据token获取当前用户
        Integer userId = Integer.valueOf(JwtUtils.getClaimByToken((String) hashMapHeader.get("authorization")).getSubject());
        //验证当前用户是否为管理员
        if (iTcUserService.getById(userId).getUserAdmin()){
            TcUser tcUser = new TcUser();
            tcUser.setUserId(id);
            tcUser.setUserAdmin(true);
            iTcUserService.updateById(tcUser);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 降权普通用户为用户
     * @param hashMapHeader
     * @param id
     * @return 状态码
     */
    @GetMapping("/unauthority")
    public ResponseEntity<String> unauthority(@RequestHeader HashMap hashMapHeader,Integer id){
        //根据token获取当前用户
        Integer userId = Integer.valueOf(JwtUtils.getClaimByToken((String) hashMapHeader.get("authorization")).getSubject());
        //验证当前用户是否为管理员
        if (iTcUserService.getById(userId).getUserAdmin()){
            TcUser tcUser = new TcUser();
            tcUser.setUserId(id);
            tcUser.setUserAdmin(false);
            iTcUserService.updateById(tcUser);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 查询用户是否为管理员
     * @param token
     * @return 状态码
     */
    @GetMapping("/admin")
    public ResponseEntity<String> admin(String token){
        Integer userId = Integer.valueOf(JwtUtils.getClaimByToken(token).getSubject());
        if (iTcUserService.getById(userId).getUserAdmin()){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
