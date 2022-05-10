package com.example.common;

import cn.hutool.captcha.*;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.lang.Console;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TestCaptcha {
    public static void main(String[] args) {

        exciseOne();
//        exciseTwo();
//        exciseThree();
//        exciseFour();
//        exciseFive();
//        exciseSix();
//        exciseSeven();

    }

    public static void exciseZero() {
        // test 方法
    }

    // LineCaptcha 线段干扰的验证码
    public static void exciseOne() {
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);

        //图形验证码写出，可以写出到文件，也可以写出到流
        lineCaptcha.write("lineBug.png");
        //输出code
        Console.log(" = one = " + lineCaptcha.getCode());
        //验证图形验证码的有效性，返回boolean值
        Boolean resOne = lineCaptcha.verify(lineCaptcha.getCode());
        Console.log(" = resOne = " + resOne);

        //重新生成验证码
        lineCaptcha.createCode();
        lineCaptcha.write("d://lineTwo.png");
        //新的验证码
        Console.log(" = one-Two = " + lineCaptcha.getCode());
        //验证图形验证码的有效性，返回boolean值
        Boolean resTwo = lineCaptcha.verify("1234");
        Console.log(" = resOneTwo = " + resTwo);
    }

    // CircleCaptcha 圆圈干扰验证码
    public static void exciseTwo() {
        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
        //CircleCaptcha captcha = new CircleCaptcha(200, 100, 4, 20);
        //图形验证码写出，可以写出到文件，也可以写出到流
        captcha.write("d:/circle.png");
        //新的验证码
        Console.log(" = Two = " + captcha.getCode());
        //验证图形验证码的有效性，返回boolean值
        Boolean resTwo = captcha.verify("1234");
        Console.log(" = resTwo = " + resTwo);
    }

    // ShearCaptcha 扭曲干扰验证码
    public static void exciseThree() {
        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
        //ShearCaptcha captcha = new ShearCaptcha(200, 100, 4, 4);
        //图形验证码写出，可以写出到文件，也可以写出到流
        captcha.write("d:/shear.png");
        //新的验证码
        Console.log(" = Three = " + captcha.getCode());
        //验证图形验证码的有效性，返回boolean值
        Boolean resThree = captcha.verify("1234");
        Console.log(" = resThree = " + resThree);
    }

    // 写出到浏览器（Servlet输出）
    public static void exciseFour() {
        //ICaptcha captcha = ...;
        //captcha.write(response.getOutputStream());
        //Servlet的OutputStream记得自行关闭哦！
        /**
        try {
            // 方法一： 向浏览器输出验证码图片
            ServletOutputStream outputStream = response.getOutputStream();
            captcha.write(outputStream);
            // 方法二： 向浏览器输出验证码图片
            BufferedImage image = captcha.getImage();
            ImageIO.write(image, "jpeg", outputStream);
            // 关闭流
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
         */
    }

    // 自定义验证码
    // 有时候标准的验证码不满足要求，比如我们希望使用纯字母的验证码、纯数字的验证码、
    // 加减乘除的验证码，此时我们就要自定义CodeGenerator
    public static void exciseFive() {
        // 自定义纯数字的验证码（随机4位数字，可重复）
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        lineCaptcha.setGenerator(randomGenerator);
        // 重新生成code
        lineCaptcha.createCode();
        //图形验证码写出，可以写出到文件，也可以写出到流
        lineCaptcha.write("d:/fiveLineCaptcha.png");
        //新的验证码
        Console.log(" = Five = " + lineCaptcha.getCode());
        //验证图形验证码的有效性，返回boolean值
        Boolean resFive = lineCaptcha.verify("1234");
        Console.log(" = resFive = " + resFive);
    }

    public static void exciseSix() {
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 45, 4, 4);
        // 自定义验证码内容为四则运算方式
        captcha.setGenerator(new MathGenerator());
        // 重新生成code
        captcha.createCode();

        //图形验证码写出，可以写出到文件，也可以写出到流
        captcha.write("d:/sixLineCaptcha.png");
        //新的验证码
        Console.log(" = Six = " + captcha.getCode());
        //验证图形验证码的有效性，返回boolean值
        Boolean resSix = captcha.verify("1234");
        Console.log(" = resSix = " + resSix);
    }

    // GIF我最闪亮验证码
    public static void exciseSeven() {
        GifCaptcha gifCaptcha = CaptchaUtil.createGifCaptcha(500, 200, 8);
        gifCaptcha.write("d:/gifCaptcha.gif");
        String gifCaptchaCode = gifCaptcha.getCode();
        // 打印验证码字符
        Console.log("gifCaptchaCode", gifCaptchaCode);
        //新的验证码
        Console.log(" = Seven = " + gifCaptcha.getCode());
        //验证图形验证码的有效性，返回boolean值
        Boolean resSeven = gifCaptcha.verify("1234");
        Console.log(" = resSeven = " + resSeven);
    }
}
