package com.example.controller;

import cn.hutool.captcha.CaptchaUtil;
import com.example.entity.User;
import com.example.entity.vo.ResultInfo;
import com.example.service.LoginService;
import com.example.util.MD5Util;
import com.example.util.StringUtil;
import com.wf.captcha.ArithmeticCaptcha;
import org.apache.el.parser.ArithmeticNode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/login", produces = "application/json; charset=UTF-8")
public class LoginController {

    /**
     * Swagger 接口文档
     * http://localhost:8085/swagger-ui/index.html#/login-controller
     * 快捷键  control + alt + O  去除无用的引用包
     * 快捷键  alt + enter 导入依赖的包
     */

    String md5SoltString = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Resource
    LoginService loginService;

    @PostMapping("/account")
    public String loginAccount(@RequestBody User user) {
        User user2 = loginService.userLogin(user);
        System.out.println("======= user2 = " + user2.getUsername());
        return "true";
    }

    /**
     * 生成验证码 算术类型
     */
    @GetMapping("/getCaptcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);
        captcha.setLen(3);
        captcha.getArithmeticString();
        captcha.text();
        request.getSession().setAttribute("captcha", captcha.text());
        captcha.out(response.getOutputStream());
    }

    // 用户注册
    @PostMapping("/register")
    public ResultInfo registerUser(@RequestBody User userParam) {
        String email = userParam.getEmail();
        String password = userParam.getPassword();
        if (StringUtil.isEmptyString(email)) {
            return ResultInfo.error("40002", "用户名为空，请输入用户名！");
        }
        // 从数据库中查询账号，是否已经存在，已经存在，则提示注册账号已存在，不可注册
        User user = loginService.getAccountByEmail(email);
        if (user != null) {
            // 查询到 user 不为空，说明账号已存在
            return ResultInfo.error("40008", "账号已存在，请重新输入账号！");
        }
        if (StringUtil.isEmptyString(password)) {
            return ResultInfo.error("40002", "密码为空，请输入密码！");
        }
        // 使用 MD5 对用户输入的密码进行加密
        String md5Pwd = MD5Util.md5(email + "" + password, md5SoltString);
        System.out.println("加密后的密码： ==== " + md5Pwd);
        // 将加密后的 用户名和密码存入数据库
        Integer resultCode = loginService.registerUser(email, md5Pwd);
        if (resultCode == 0) {
            return ResultInfo.error("40001", "注册失败！");
        } else {
            return ResultInfo.success("注册成功！");
        }
    }

    // 用户登录
    @PostMapping("/login")
    @ResponseBody
    // @RequestBogy User user 这种形式会将 JSON 字符串中的值赋予user中对应的属性上
    // 需要注意的是：JSON字符串中的 key 必须对应 User 中的属性名，否则是请求不过去的
    public ResultInfo loginUser(HttpServletRequest request,
                                String accountEmail,
                                String password,
                                String code,
                                boolean rememberMe) {
        if (StringUtil.isBlank(accountEmail, password)) {
            return ResultInfo.failed("账号或密码不能为空");
        }
        if (!EmailIsMatches(accountEmail)){
            return ResultInfo.failed("邮箱格式不符");
        }
        // 验证验证码是否正确
        String sessionCode = (String) request.getSession().getAttribute("captcha");
        if (code == null || !sessionCode.equals(code.trim().toLowerCase())) {
            return ResultInfo.failed("验证码不正确");
        }

        // 先查询数据库中的用户名，如果查到了用户名，再去比对密码，如果没有查询到用户名，直接返回用户名或密码错误
        // 查询数据库，找到的用户名和密码
        User userFind = loginService.getAccountByEmail(accountEmail);
        if (userFind != null) {
            // 将用户输入的密码 和 从数据库中查询到的密文密码进行比较
            Boolean isRight = MD5Util.verify(accountEmail + "" + password,
                    md5SoltString,
                    userFind.getPassword());
            if (isRight) {
                return ResultInfo.success("登录成功");
            } else {
                return ResultInfo.error("40003", "用户名或密码错误，登录失败！");
            }
        } else {
            return ResultInfo.error("40003", "用户名或密码错误，登录失败！");
        }
    }

    // 忘记密码
    @PostMapping("/forget")
    public ResultInfo forgetPassword(@RequestBody User userParam) {
        // 查询数据库，找到的用户名和密码
        String email = userParam.getEmail();
        String password = userParam.getPassword();
        if (StringUtil.isEmptyString(email)) {
            return ResultInfo.error("40002", "用户名为空，请输入用户名！");
        }
        if (StringUtil.isEmptyString(password)) {
            return ResultInfo.error("40002", "密码为空，请输入密码！");
        }
        User userFind = loginService.getAccountByEmail(userParam.getEmail());
        if (userFind != null) {
            // 查询用户的主键ID
            Integer userID = loginService.getUserIdByAccount(email);
            // 使用 MD5 对用户输入的密码进行加密
            String md5Pwd = MD5Util.md5(email + "" + password, md5SoltString);
            System.out.println("加密后的密码： ==== " + md5Pwd);
            // 将加密后的 用户名和密码存入数据库
            Integer resultCode = loginService.userForgetPassword(email, md5Pwd, userID);
            if (resultCode == 0) {
                return ResultInfo.error("40001", "修改密码失败！");
            } else {
                return ResultInfo.success("修改密码成功！");
            }
        } else {
            return ResultInfo.error("40008", "当前用户不存在，请检查并重新输入！");
        }
    }

    private boolean EmailIsMatches(String email){
        // 邮箱正则表达式
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        check = "/^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}$/";
        Pattern regex = Pattern.compile(check);

        Matcher matcher = regex.matcher(email);//匹配邮箱格式

        boolean isMatched = matcher.matches();
        return isMatched;
    }
}
