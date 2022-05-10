package com.example.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.sql.Struct;
import java.util.List;
import java.util.Map;

@RestController
// 添加参数  produces = "application/json; charset=UTF-8" 解决接口返回中文字符串变成 ？？？ 的问题
@RequestMapping(value = "/user", produces = "application/json; charset=UTF-8")
public class UserController {

    /**
     * Swagger 接口文档
     * http://localhost:8085/swagger-ui/index.html#/login-controller
     * 快捷键  control + alt + O  去除无用的引用包
     * 快捷键  alt + enter 导入依赖的包
     */

    @Resource
    UserMapper userMapper;

    @Resource
    UserService userService;

    @GetMapping("/test")
    public String getTestAPI() {
        return "好了，登录成功过哦！";
    }

    // 增
    @PostMapping("/save")
    public boolean save(@RequestBody User user) {
//        Integer result = userMapper.insert(user);
//        if (result == 1) {
//            return "插入一条数据成功！";
//        } else {
//            return "插入一条数据失败！";
//        }

        // 改造使用mybatis-plus直接获取
        return userService.saveMyBatisPlusUser(user);
    }

    @GetMapping("/myFindAll")
    public List<User> myFindAll() {
        return userService.list();
    }


    // 删
    // 注解 @PathVariable 标识的参数 id 必须与 Mapping 注解中的路径 花括号中 {} 的参数 id 对应
    // 也可以 用 @PathVariable("自定义名称") 标记路径中的参数 名称
    @DeleteMapping("/{id}")
    public boolean remove(@PathVariable Integer id) {
        //return userMapper.removeById(id);
        // 用下面的 mybatis-plus 改造
        return userService.removeById(id);
    }

    @DeleteMapping("/tag/{id}")
    public Integer removeUserWithTag(@PathVariable Integer id) {
        return userService.removeUserByIdWithTag("0", id);
    }


    // 改
    @PostMapping("/update")
    public String update(@RequestBody User user) {
        Integer result = userService.updateOldUser(user);
        if (result == 1) {
            return "更新成功！";
        } else {
            return "更新失败！";
        }
    }

    // 新增或者更新都在一起
    @PostMapping("/saveAndUpdate")
    public Integer saveTwo(@RequestBody User user) {
        // 新增或者更新 ， 新增和修改
        return userService.saveAndUpdate(user);
    }

    // 查
    // 查询所有
    @GetMapping("/listAll")
    public List<User> getUser() {
        List<User> list = userMapper.findAllUser();
        if (list.isEmpty()) {
            System.out.println("查询到数据为空");
        } else {
            System.out.println("查询到数据不为空");
        }
        return list;
    }

    // 分页查询
    // @RequestParam 注解接收参数，将 ?pageNum=1&pageSize=10 映射到方法参数中
    // Limit 的第一个参数: (pageNum - 1) * pageSize, 第二个参数不变
    @GetMapping("/page")
    public Map<String, Object> findAllWithPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize) {
        return userService.selectAllWithPage(pageNum, pageSize);
    }

    @GetMapping("/pageAndSearch")
    public Map<String, Object> findAllWithPageSearch(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam String username) {
        return userService.selectAllWithPageSearch(pageNum, pageSize, username);
    }

    // 多条件查询
    @GetMapping("/pageSearchKey")
    public Map<String, Object> findAllWithPageSearchKey(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam String key,
            @RequestParam String type) {
        // type 是 要查询的类型（用户名、邮箱、地址）
        return userService.selectAllWithPageSearchKey(pageNum, pageSize, key, type);
    }

    // 使用 Mybatis-plus 方式实现分页查询
    @GetMapping("/mybatisPlusPage")
    public IPage<User> findResultWithMybatisPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "") String email,
            @RequestParam(defaultValue = "") String address) {
        IPage<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper queryWrapper = new QueryWrapper<>();

        if (!"".equals(username)) {
            queryWrapper.like("username", username);
        }
        if (!"".equals(email)) {
            queryWrapper.like("email", email);
        }
        if (!"".equals(address)) {
            queryWrapper.like("address", address);
        }
        queryWrapper.eq("is_deleted", "1");
        queryWrapper.orderByDesc("id");
        return userService.page(page, queryWrapper);
    }

    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void exportData(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有数据
        List<User> list = userService.list();
        // 通过工具类创建 Writer 写出到磁盘的路径
        //ExcelWriter writer = ExcelUtil.getWriter(fileUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 自定义标题别名
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("nickname", "昵称");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("avatarUrl", "头像");
        // 将数据写入到表格中
        writer.write(list, true);
    }

}
