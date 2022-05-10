package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface LoginMapper {
    @Select("select * from tb_user where username = #{username}")
    User findUserByUserName(User user);

    @Select("select * from tb_user where email = #{email}")
    User selectUserByEmail(String email);

    @Update("INSERT INTO `tb_user` (`email`, `password`) VALUES (#{email}, #{password})")
    @Transactional
    Integer registerUser(String email, String password);

    @Select("select * from tb_user where email = #{email}")
    Integer findIdByEmail(String email);

    @Update("UPDATE `tb_user` SET `email` = #{email}, `password` = #{password} WHERE `id` = #{userId}")
    @Transactional
    Integer modifyAccountPassword(String email, String password, Integer userId);
}
