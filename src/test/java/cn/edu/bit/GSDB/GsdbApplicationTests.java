package cn.edu.bit.GSDB;

import cn.edu.bit.GSDB.dao.LeaveDao;
import cn.edu.bit.GSDB.dao.StudentDao;
import cn.edu.bit.GSDB.dao.TeacherDao;
import cn.edu.bit.GSDB.entity.Leave;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class GsdbApplicationTests {

    @Autowired
    TeacherDao teacherDao;

    @Autowired
    StudentDao studentDao;

    @Test
    void getStudentIds() {
        List<Integer> studentIds = teacherDao.getStudentIds(3);
        System.out.println(studentIds);
    }

    @Test
    void checkStudentId() {
        Integer id = teacherDao.checkStudentId(3, 1);
        System.out.println(id);
    }

    @Test
    void addLeave() {
        Leave leave = new Leave();

        leave.setStudentId(1);
        leave.setLeaveTime(new Date());
        leave.setDays(5);
        leave.setDestination("123");
        leave.setTransportation("456");
        leave.setReason("100");
        leave.setMaterial("/1/1/1");
        leave.setCreateTime(new Date());

        studentDao.addLeave(leave);
    }

}
