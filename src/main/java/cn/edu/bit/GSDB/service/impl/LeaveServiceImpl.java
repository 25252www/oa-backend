package cn.edu.bit.GSDB.service.impl;

import cn.edu.bit.GSDB.dao.LeaveDao;
import cn.edu.bit.GSDB.entity.Leave;
import cn.edu.bit.GSDB.service.LeaveService;
import cn.edu.bit.GSDB.service.StudentService;
import cn.edu.bit.GSDB.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * (TbLeave)表服务实现类
 *
 * @author wu
 * @since 2022-10-20 11:33:57
 */
@Service("leaveService")
public class LeaveServiceImpl extends ServiceImpl<LeaveDao, Leave> implements LeaveService {

    @Autowired
    @Lazy
    StudentService studentService;

    @Autowired
    @Lazy
    TeacherService teacherService;

    private ByteArrayOutputStream List2CSV(List<Leave> list) throws IOException, IllegalAccessException {
        Class<Leave> l = Leave.class;
        Field[] fields = l.getDeclaredFields();
        ByteArrayOutputStream res = new ByteArrayOutputStream();
        for(Field title : fields){
            title.setAccessible(true);
            res.write(title.getName().getBytes());
            res.write(',');
        }
        res.write('\r');
        res.write('\n');
        for (Leave obj : list) {
            for (Field property : fields) {
                res.write(String.valueOf(property.get(obj)).getBytes());
                res.write(',');
            }
            res.write('\r');
            res.write('\n');
        }
        return res;
    }

    @Override
    public ByteArrayOutputStream getCSVByStudentId(Integer studentId) {
        List<Leave> leaveList = studentService.getLeaveList(studentId);
        try {
            return List2CSV(leaveList);
        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ByteArrayOutputStream getCSVByTeacherId(Integer teacherId) {
        List<Leave> leaveList = teacherService.getLeaveList(teacherId);
        try {
            return List2CSV(leaveList);
        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

