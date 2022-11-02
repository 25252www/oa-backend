package cn.edu.bit.GSDB.exception;

import lombok.Data;

/**
 * @author wjk
 * @since 2022-10-20 11:33:50
 */
@Data
public class BasicException extends RuntimeException {

    private int code;
    private String msg;

    public BasicException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
