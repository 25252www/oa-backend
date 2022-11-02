package cn.edu.bit.GSDB.service;

import cn.edu.bit.GSDB.entity.Leave;
import cn.edu.bit.GSDB.entity.Teacher;
import cn.edu.bit.GSDB.vo.PageRspData;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (TbTeacher)表服务接口
 *
 * @author wu
 * @since 2022-10-20 11:34:21
 */
public interface TeacherService extends IService<Teacher> {

    List<Leave> getLeaveList(Integer teacherId);

    PageRspData<Leave> getLeaveListByPage(Integer pageNum, Integer pageSize, Integer teacherId);

    @Transactional
    boolean deleteLeaveById(Integer id, Integer teacherId);

    @Transactional
    boolean processLeave(Integer id, Integer toState, Integer teacherId);
}

