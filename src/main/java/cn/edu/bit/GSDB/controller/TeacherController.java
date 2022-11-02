package cn.edu.bit.GSDB.controller;


import cn.edu.bit.GSDB.constant.Role;
import cn.edu.bit.GSDB.entity.Leave;
import cn.edu.bit.GSDB.entity.User;
import cn.edu.bit.GSDB.service.TeacherService;
import cn.edu.bit.GSDB.utils.ResultBody;
import cn.edu.bit.GSDB.utils.UserUtils;
import cn.edu.bit.GSDB.vo.ProcessLeaveReqData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * (TbTeacher)表控制层
 *
 * @author wu
 * @since 2022-10-20 11:34:21
 */
@RestController
@RequestMapping("/teachers")
public class TeacherController {
    /**
     * 服务对象
     */
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/leave/list")
    public ResultBody getLeaveList(HttpSession session) {
        UserUtils.checkRole(Role.Teacher, "用户无权限", session);
        User user = (User) session.getAttribute(UserUtils.sessionStatusKey);
        return ResultBody.success(teacherService.getLeaveList(user.getRoleId()));
    }

    @PostMapping("/leave/list")
    public ResultBody getLeaveListByPage(@RequestParam Integer pageNum,
                                         @RequestParam Integer pageSize,
                                         HttpSession session) {
        UserUtils.checkRole(Role.Teacher, "用户无权限", session);
        User user = (User) session.getAttribute(UserUtils.sessionStatusKey);
        return ResultBody.success(teacherService.getLeaveListByPage(pageNum, pageSize, user.getRoleId()));
    }

    @DeleteMapping("/leave/{id}")
    public ResultBody deleteLeaveById(@PathVariable Integer id, HttpSession session) {
        UserUtils.checkRole(Role.Teacher, "用户无权限", session);
        User user = (User) session.getAttribute(UserUtils.sessionStatusKey);
        teacherService.deleteLeaveById(id,user.getRoleId());
        return ResultBody.success("删除成功");
    }

    @PostMapping("/leave/process")
    public ResultBody updateLeave(@RequestBody ProcessLeaveReqData processLeave, HttpSession session) {
        UserUtils.checkRole(Role.Teacher, "用户无权限", session);
        User user = (User) session.getAttribute(UserUtils.sessionStatusKey);
        teacherService.processLeave(processLeave.getId(),processLeave.getToState(),user.getRoleId());
        return ResultBody.success("操作成功");
    }

}

