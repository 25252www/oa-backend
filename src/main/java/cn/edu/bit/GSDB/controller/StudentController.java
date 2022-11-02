package cn.edu.bit.GSDB.controller;


import cn.edu.bit.GSDB.constant.Role;
import cn.edu.bit.GSDB.entity.Leave;
import cn.edu.bit.GSDB.entity.User;
import cn.edu.bit.GSDB.exception.BasicException;
import cn.edu.bit.GSDB.service.LeaveService;
import cn.edu.bit.GSDB.service.StudentService;
import cn.edu.bit.GSDB.utils.ResultBody;
import cn.edu.bit.GSDB.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * (TbStudent)表控制层
 *
 * @author wu
 * @since 2022-10-20 11:34:21
 */
@RestController
@RequestMapping("/students")
public class StudentController {
    /**
     * 服务对象
     */
    @Autowired
    private StudentService studentService;

    @GetMapping("/leave/list")
    public ResultBody getLeaveList(HttpSession session) {
        UserUtils.checkRole(Role.Student, "用户无权限", session);
        User user = (User) session.getAttribute(UserUtils.sessionStatusKey);
        return ResultBody.success(studentService.getLeaveList(user.getRoleId()));
    }

    @PostMapping("/leave/list")
    public ResultBody getLeaveListByPage(@RequestParam Integer pageNum,
                                         @RequestParam Integer pageSize,
                                         HttpSession session) {
        UserUtils.checkRole(Role.Student, "用户无权限", session);
        User user = (User) session.getAttribute(UserUtils.sessionStatusKey);
        return ResultBody.success(studentService.getLeaveListByPage(pageNum, pageSize, user.getRoleId()));
    }

    @DeleteMapping("/leave/{id}")
    public ResultBody deleteLeaveById(@PathVariable Integer id, HttpSession session) {
        UserUtils.checkRole(Role.Student, "用户无权限", session);
        User user = (User) session.getAttribute(UserUtils.sessionStatusKey);
        studentService.deleteLeaveById(id, user.getRoleId());
        return ResultBody.success("删除成功");
    }

    @PutMapping("/leave")
    public ResultBody addLeave(@RequestBody Leave leave, HttpSession session) {
        UserUtils.checkRole(Role.Student, "用户无权限", session);
        User user = (User) session.getAttribute(UserUtils.sessionStatusKey);
        if(studentService.getStatus(user.getRoleId())!=0){
            throw new BasicException(400, "还有未审核的请假，禁止新的请假");
        }
        leave.setStudentId(user.getRoleId());
        studentService.addLeave(leave);
        return ResultBody.success("增加成功");
    }

    @PostMapping("/leave")
    public ResultBody updateLeave(@RequestBody Leave leave, HttpSession session) {
        UserUtils.checkRole(Role.Student, "用户无权限", session);
        User user = (User) session.getAttribute(UserUtils.sessionStatusKey);
        if (!Objects.equals(leave.getStudentId(), user.getRoleId())) {
            return ResultBody.error("无权操作");
        }
        studentService.updateLeave(leave);
        return ResultBody.success("修改成功");
    }

}

