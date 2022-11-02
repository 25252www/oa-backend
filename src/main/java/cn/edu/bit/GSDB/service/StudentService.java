package cn.edu.bit.GSDB.service;

import cn.edu.bit.GSDB.entity.Leave;
import cn.edu.bit.GSDB.entity.Student;
import cn.edu.bit.GSDB.vo.PageRspData;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (TbStudent)表服务接口
 *
 * @author wu
 * @since 2022-10-20 11:34:21
 */
public interface StudentService extends IService<Student> {

    List<Leave> getLeaveList(Integer studentId);

    PageRspData<Leave> getLeaveListByPage(Integer pageNum, Integer pageSize, Integer studentId);

    Integer getStatus(Integer id);

    @Transactional
    boolean deleteLeaveById(Integer id, Integer studentId);

    @Transactional
    boolean addLeave(Leave leave);

    @Transactional
    boolean updateLeave(Leave leave);
}

