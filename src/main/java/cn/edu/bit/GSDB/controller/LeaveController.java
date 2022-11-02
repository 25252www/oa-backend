package cn.edu.bit.GSDB.controller;


import cn.edu.bit.GSDB.constant.Role;
import cn.edu.bit.GSDB.entity.User;
import cn.edu.bit.GSDB.exception.BasicException;
import cn.edu.bit.GSDB.service.LeaveService;
import cn.edu.bit.GSDB.utils.CommonUtils;
import cn.edu.bit.GSDB.utils.ResultBody;
import cn.edu.bit.GSDB.utils.UploadUtils;
import cn.edu.bit.GSDB.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.util.Objects;

/**
 * (TbLeave)表控制层
 *
 * @author wu
 * @since 2022-10-20 11:33:57
 */
@RestController
@RequestMapping("/leaves")
public class LeaveController {
    /**
     * 服务对象
     */
    @Autowired
    private LeaveService leaveService;

    @PostMapping("/upload")
    public ResultBody uploadPhoto(@RequestPart MultipartFile file, HttpSession session){
        UserUtils.checkRole(Role.Student, "用户无权限", session);
        String fullPath = UploadUtils.uploadPicture(file, "/leave/");
        return ResultBody.success((Object) fullPath);
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> download(HttpSession session){
        if(!UserUtils.isLoggedIn(session)){
            throw new BasicException(400, "未登录");
        }
        User user = (User) session.getAttribute(UserUtils.sessionStatusKey);
        ByteArrayOutputStream res;
        StringBuilder filename = new StringBuilder();
        if(Objects.equals(user.getRole(), Role.Student)){
            res = leaveService.getCSVByStudentId(user.getRoleId());
            filename.append("student_");

        }else{
            res = leaveService.getCSVByTeacherId(user.getRoleId());
            filename.append("teacher_");
        }
        ByteArrayResource tmp = new ByteArrayResource(res.toByteArray());
        filename.append(user.getRoleId())
                .append("_")
                .append(CommonUtils.dateToStr())
                .append("_")
                .append(CommonUtils.timeToStr())
                .append(".csv");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-disposition", "attachment; filename="+ filename)
                .body(tmp);
    }

}

