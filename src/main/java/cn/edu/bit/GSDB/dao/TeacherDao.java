package cn.edu.bit.GSDB.dao;

import cn.edu.bit.GSDB.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TbTeacher)表数据库访问层
 *
 * @author wu
 * @since 2022-10-20 11:34:21
 */
@Mapper
public interface TeacherDao extends BaseMapper<Teacher> {

    List<Integer> getStudentIds(@Param("teacherId") Integer teacherId);

    Integer checkStudentId(@Param("studentId") Integer studentId, @Param("teacherId") Integer teacherId);
}

