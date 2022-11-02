package cn.edu.bit.GSDB.dao;

import cn.edu.bit.GSDB.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * (TbUser)表数据库访问层
 *
 * @author wu
 * @since 2022-10-20 11:34:39
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

}

