package cn.edu.bit.GSDB.service.impl;

import cn.edu.bit.GSDB.dao.TeacherDao;
import cn.edu.bit.GSDB.entity.Leave;
import cn.edu.bit.GSDB.entity.Student;
import cn.edu.bit.GSDB.entity.Teacher;
import cn.edu.bit.GSDB.exception.BasicException;
import cn.edu.bit.GSDB.service.LeaveService;
import cn.edu.bit.GSDB.service.StudentService;
import cn.edu.bit.GSDB.service.TeacherService;
import cn.edu.bit.GSDB.vo.PageRspData;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * (TbTeacher)表服务实现类
 *
 * @author wu
 * @since 2022-10-20 11:34:21
 */
@Service("TeacherService")
public class TeacherServiceImpl extends ServiceImpl<TeacherDao, Teacher> implements TeacherService {

    @Autowired
    LeaveService leaveService;

    @Autowired
    StudentService studentService;

    private List<Integer> getStudentIds(Integer teacherId) {
        return this.baseMapper.getStudentIds(teacherId);
    }

    private boolean checkStudentId(Integer teacherId, Integer studentId){
        Integer id = this.baseMapper.checkStudentId(studentId, teacherId);
        return Objects.nonNull(id);
    }


    private boolean checkLeave(Integer teacherId, Integer id){
        Leave leave = leaveService.getById(id);
        if(Objects.isNull(leave)){
            throw new BasicException(400,"请假单不存在");
        }
        return checkStudentId(teacherId,leave.getStudentId());
    }

    @Override
    public List<Leave> getLeaveList(Integer teacherId) {
        LambdaQueryWrapper<Leave> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Leave::getStudentId, getStudentIds(teacherId));
        return leaveService.list(wrapper);
    }

    @Override
    public PageRspData<Leave> getLeaveListByPage(Integer pageNum, Integer pageSize, Integer teacherId) {
        IPage<Leave> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Leave> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Leave::getStudentId, getStudentIds(teacherId));
        page = leaveService.page(page, wrapper);
        // 构造结果
        List<Leave> records = page.getRecords();
        return PageRspData.of(page.getRecords(), page.getTotal(), page.getPages());
    }

    @Override
    public boolean deleteLeaveById(Integer id, Integer teacherId) {
        if (!checkLeave(teacherId,id)){
            throw new BasicException(400, "无权删除");
        }
        if (!leaveService.removeById(id)) {
            throw new BasicException(400, "删除失败");
        }
        return true;
    }

    @Override
    public boolean processLeave(Integer id, Integer toState, Integer teacherId) {
        if (!checkLeave(teacherId,id)){
            throw new BasicException(400, "无权操作");
        }
        Leave leave = new Leave();
        leave.setId(id);
        leave.setState(toState);
        if (!leaveService.updateById(leave)) {
            throw new BasicException(400, "操作失败");
        }
        return true;
    }
}

