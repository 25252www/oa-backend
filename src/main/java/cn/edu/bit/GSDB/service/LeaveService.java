package cn.edu.bit.GSDB.service;

import cn.edu.bit.GSDB.entity.Leave;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;

/**
 * (TbLeave)表服务接口
 *
 * @author wu
 * @since 2022-10-20 11:33:56
 */
public interface LeaveService extends IService<Leave> {

    ByteArrayOutputStream getCSVByStudentId(Integer studentId);

    ByteArrayOutputStream getCSVByTeacherId(Integer teacherId);
}

