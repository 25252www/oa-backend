package cn.edu.bit.GSDB.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (TbUser)表实体类
 *
 * @author wu
 * @since 2022-10-20 11:34:39
 */
@Data
@TableName("tb_userm")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("userno")
    private String username;

    private String password;

    private Integer role;

    private Integer roleId;

    @TableField(exist = false)
    private Object roleInfo;
}

