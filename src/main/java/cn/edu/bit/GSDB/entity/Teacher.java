package cn.edu.bit.GSDB.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (TbTeacher)表实体类
 *
 * @author wu
 * @since 2022-10-20 11:34:21
 */
@Data
@TableName("tb_teacher")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;
}

