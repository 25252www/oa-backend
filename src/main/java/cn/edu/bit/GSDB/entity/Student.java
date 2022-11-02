package cn.edu.bit.GSDB.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (TbStudent)表实体类
 *
 * @author wu
 * @since 2022-10-20 11:34:21
 */
@Data
@TableName("tb_studentm")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String phone; //学生各类信息（为了用存储过程）

    private Integer status; //状态（由触发器更新） 0可以请假 1有未销假的请假记录，不能请假
}

