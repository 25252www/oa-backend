package cn.edu.bit.GSDB.controller;

import cn.edu.bit.GSDB.constant.Role;
import cn.edu.bit.GSDB.entity.Student;
import cn.edu.bit.GSDB.entity.User;
import cn.edu.bit.GSDB.service.StudentService;
import cn.edu.bit.GSDB.service.TeacherService;
import cn.edu.bit.GSDB.service.UserService;
import cn.edu.bit.GSDB.utils.ResultBody;
import cn.edu.bit.GSDB.utils.UserUtils;
import cn.edu.bit.GSDB.vo.UserReqData;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * (TbUser)表控制层
 *
 * @author wu
 * @since 2022-10-20 11:34:39
 */
@RestController
@RequestMapping("/users")
public class UserController {
    /**
     * 服务对象
     */
    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    //登录
    @PostMapping("/login")
    public ResultBody login(@RequestBody UserReqData userReqData, HttpSession session) {
        //判断登录状态
        if (UserUtils.isLoggedIn(session)) {
            return ResultBody.success("已登录！", session.getAttribute(UserUtils.sessionStatusKey));
        }
        //查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, userReqData.getUsername());
        wrapper.eq(User::getPassword, userReqData.getPassword());
        User user = userService.getOne(wrapper);
        if (Objects.isNull(user)) {
            return ResultBody.error(HttpStatus.BAD_REQUEST.value(), "用户名或密码错误");
        }
        //获得角色
        Object roleInfo;
        if (Role.Student.equals(user.getRole())) {
            roleInfo = studentService.getById(user.getRoleId());
        } else {
            roleInfo = teacherService.getById(user.getRoleId());
        }
        if (Objects.isNull(roleInfo)) {
            return ResultBody.error(HttpStatus.BAD_REQUEST.value(), "用户对应角色不存在");
        }
        user.setRoleInfo(roleInfo);//加入角色信息
        user.setPassword(null);//清除密码信息
        session.setAttribute(UserUtils.sessionStatusKey, user);
        return ResultBody.success(user);
    }

    //查询是否登录
    @GetMapping("/login/check")
    public ResultBody loginCheck(HttpSession session) {
        if (UserUtils.isLoggedIn(session)) {
            User user = (User) session.getAttribute(UserUtils.sessionStatusKey);
            if(Objects.equals(user.getRole(), Role.Student)){
                Student roleInfo = (Student) user.getRoleInfo();
                roleInfo.setStatus(studentService.getStatus(user.getRoleId()));
            }
            return ResultBody.success("已登录！", user);
        }
        return ResultBody.error("未登录");
    }

    //退出登录
    @PostMapping("/logout")
    public ResultBody logout(HttpSession session) {
        if (!UserUtils.isLoggedIn(session)) {
            return ResultBody.error(HttpStatus.BAD_REQUEST.value(), "未登录");
        }
        session.removeAttribute(UserUtils.sessionStatusKey);
        return ResultBody.success("已退出登录！");
    }
}

