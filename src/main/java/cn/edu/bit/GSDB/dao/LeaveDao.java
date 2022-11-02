package cn.edu.bit.GSDB.dao;

import cn.edu.bit.GSDB.entity.Leave;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * (TbLeave)表数据库访问层
 *
 * @author wu
 * @since 2022-10-20 11:33:55
 */
@Mapper
public interface LeaveDao extends BaseMapper<Leave> {

}

