package cn.edu.bit.GSDB.dao;

import cn.edu.bit.GSDB.entity.Leave;
import cn.edu.bit.GSDB.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (TbStudent)表数据库访问层
 *
 * @author wu
 * @since 2022-10-20 11:34:21
 */
@Mapper
public interface StudentDao extends BaseMapper<Student> {

    void addLeave(Leave leave);

    Integer getStatus(@Param("id") Integer id);
}

