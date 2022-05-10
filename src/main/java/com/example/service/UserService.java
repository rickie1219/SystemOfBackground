package com.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    UserMapper userMapper;

    public Integer updateOldUser(User user) {
        return userMapper.updateOldUser(user);
    }

    public Integer saveAndUpdate(User user) {
        Integer uID = user.getId();
        System.out.println("uID = " + uID);
        if (user.getId() == null) { // user的ID为空，就插入一条数据
            return userMapper.insertNewUser(user);
        } else { // 不为空，就更新一条数据
            return userMapper.updateOldUser(user);
        }
    }

    public Integer removeUserByIdWithTag(String isDeleted, Integer userId) {
        return userMapper.removeByIdWithTag(isDeleted, userId);
    }

    // Limit 的第一个参数: (pageNum - 1) * pageSize, 第二个参数不变
    public Map<String, Object> selectAllWithPage(Integer pageNum, Integer pageSize) {
        Map<String, Object> result = new HashMap<>();
        Integer offset = (pageNum - 1) * pageSize;
        List<User> data = userMapper.selectAllWithPage(offset, pageSize);
        Integer count = userMapper.selectTotal();
        result.put("data", data);
        result.put("total", count);
        return result;
    }

    // 升级1：模糊查询搜索，根据用户名查询
    public Map<String, Object> selectAllWithPageSearch(Integer pageNum, Integer pageSize, String username) {
        Map<String, Object> res = new HashMap<>();
        Integer offset = (pageNum - 1) * pageSize;
        List<User> data = userMapper.selectAllWithPageSearch(offset, pageSize, username);
        Integer count = userMapper.selectTotalSearch(username);
        res.put("data", data);
        res.put("total", count);
        return res;
    }

    // 升级2：模糊查询搜索，根据用户名、邮箱、地址进行查询
    public Map<String, Object> selectAllWithPageSearchKey(Integer pageNum,
                                                          Integer pageSize,
                                                          String key,
                                                          String type) {
        Map<String, Object> res = new HashMap<>();
        Integer offset = (pageNum - 1) * pageSize;
        List<User> data = userMapper.selectAllWithPageSearchKey(offset, pageSize, key, type);
        Integer count = userMapper.selectTotalSearchKey(key, type);
        res.put("data", data);
        res.put("total", count);
        return res;
    }

    public boolean saveMyBatisPlusUser(User user) {
        return saveOrUpdate(user);
    }
}
