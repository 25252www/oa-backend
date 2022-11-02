package cn.edu.bit.GSDB.service.impl;

import cn.edu.bit.GSDB.dao.UserDao;
import cn.edu.bit.GSDB.entity.User;
import cn.edu.bit.GSDB.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * (TbUser)表服务实现类
 *
 * @author wu
 * @since 2022-10-20 11:34:39
 */
@Service("tbUserService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}

