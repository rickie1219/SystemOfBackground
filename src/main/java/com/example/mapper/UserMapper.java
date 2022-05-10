package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from tb_user where is_deleted = '1'")
    List<User> findAllUser();

    @Insert("insert into tb_user (username, password, nickname, email, phone, address) " +
            "values (#{username}, #{password}, #{nickname}, #{email}, #{phone}, #{address})")
    @Transactional
    Integer insertNewUser(User user);

    // 使用动态的sql语句，使用 mybatis 的 xml 配置
    Integer updateOldUser(User user);

    // 查询语句中的参数 userid 名称 必须与 方法中 @Param 注解中的自定义参数名称一样，可以和方法名中的参数名不一样
    @Delete("delete from tb_user where id = #{userid}")
    @Transactional
    Integer removeById(@Param("userid") Integer id);

    // 删除记录，不是真的删除，只是标记为已删除
    @Update("UPDATE `tb_user` SET `is_deleted` = #{isDeleted} WHERE `id` = #{userId}")
    @Transactional
    Integer removeByIdWithTag(String isDeleted, Integer userId);


    // Limit 的第一个参数: (pageNum - 1) * pageSize, 第二个参数不变
    @Select("select * from tb_user where is_deleted = '1' limit #{offset}, #{pageSize}")
    List<User> selectAllWithPage(Integer offset, Integer pageSize);

    @Select("select count(id) from tb_user where is_deleted = '1'")
    Integer selectTotal();

    @Select("select * from tb_user where is_deleted = '1' and username " +
            "like concat('%', #{username}, '%')  limit #{offset}, #{pageSize}")
    List<User> selectAllWithPageSearch(Integer offset, Integer pageSize, String username);

    @Select("select count(id) from tb_user where is_deleted = '1' and username like concat('%', #{username}, '%') ")
    Integer selectTotalSearch(String username);

    @Select("select * from tb_user where is_deleted = '1' and #{type} " +
            "like concat('%', #{key}, '%')  limit #{offset}, #{pageSize}")
    List<User> selectAllWithPageSearchKey(Integer offset, Integer pageSize, String key, String type);

    @Select("select count(id) from tb_user where is_deleted = '1' and #{type} " +
            "like concat('%', #{key}, '%') ")
    Integer selectTotalSearchKey(String key, String type);
}
