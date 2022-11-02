package cn.edu.bit.GSDB.service.impl;

import cn.edu.bit.GSDB.dao.StudentDao;
import cn.edu.bit.GSDB.entity.Leave;
import cn.edu.bit.GSDB.entity.Student;
import cn.edu.bit.GSDB.exception.BasicException;
import cn.edu.bit.GSDB.service.LeaveService;
import cn.edu.bit.GSDB.service.StudentService;
import cn.edu.bit.GSDB.vo.PageRspData;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * (TbStudent)表服务实现类
 *
 * @author wu
 * @since 2022-10-20 11:34:21
 */
@Service("StudentService")
public class StudentServiceImpl extends ServiceImpl<StudentDao, Student> implements StudentService {

    @Autowired
    private LeaveService leaveService;

    @Override
    public List<Leave> getLeaveList(Integer studentId) {
        LambdaQueryWrapper<Leave> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Leave::getStudentId, studentId);
        return leaveService.list(wrapper);
    }

    @Override
    public PageRspData<Leave> getLeaveListByPage(Integer pageNum, Integer pageSize, Integer studentId) {
        IPage<Leave> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Leave> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Leave::getStudentId, studentId);
        page = leaveService.page(page, wrapper);
        // 构造结果
        List<Leave> records = page.getRecords();
        return PageRspData.of(page.getRecords(), page.getTotal(), page.getPages());
    }

    @Override
    public Integer getStatus(Integer id) {
        return this.baseMapper.getStatus(id);
    }

    @Override
    public boolean deleteLeaveById(Integer id, Integer studentId) {
        LambdaQueryWrapper<Leave> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Leave::getId, id).eq(Leave::getStudentId, studentId);
        if (!leaveService.remove(wrapper)) {
            throw new BasicException(400, "删除失败");
        }
        return true;
    }

    private Leave checkLeave(Leave leave) {
        if (leave.getStudentId() == null ||
                leave.getLeaveTime() == null ||
                leave.getDays() == null ||
                leave.getDestination() == null ||
                leave.getTransportation() == null ||
                leave.getReason() == null ||
                leave.getMaterial() == null) {
            throw new BasicException(400, "请假单信息不完整");
        }
        leave.setCreateTime(new Date());
        return leave;
    }

    @Override
    public boolean addLeave(Leave leave) {
        this.baseMapper.addLeave(checkLeave(leave));
        return true;
    }

    @Override
    public boolean updateLeave(Leave leave) {
        LambdaQueryWrapper<Leave> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Leave::getId, leave.getId()).eq(Leave::getStudentId, leave.getStudentId());
        leave.setCreateTime(null);
        leave.setState(null);
        if (!leaveService.update(leave, wrapper)) {
            throw new BasicException(400, "更新失败");
        }
        return true;
    }
}

