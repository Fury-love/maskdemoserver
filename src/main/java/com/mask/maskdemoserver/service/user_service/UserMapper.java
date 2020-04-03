package com.mask.maskdemoserver.service.user_service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mask.maskdemoserver.domains.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: marico.lv
 * @CreateTime: 2020-03-13 15:59
 * @Description:
 */
@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
