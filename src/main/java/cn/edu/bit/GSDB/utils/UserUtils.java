package cn.edu.bit.GSDB.utils;

import cn.edu.bit.GSDB.entity.User;
import cn.edu.bit.GSDB.exception.BasicException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author wjk
 * @since 2022-10-20 11:33:50
 */
@Component
public class UserUtils {
    public static final String sessionStatusKey = "userStatus";
    private static final Pattern roleNamePattern = Pattern.compile("（.+）$");

    public static boolean isLoggedIn(HttpSession session) {
        return session.getAttribute(sessionStatusKey) != null;
    }

    /**
     * 检查用户角色。
     *
     * @throws BasicException 当用户不具有指定权限时抛出。如果用户具有指定权限，此函数正常返回。
     */
    public static void checkRole(Integer role, String msg, HttpSession session) {
        if (!isLoggedIn(session)) throw new BasicException(HttpStatus.UNAUTHORIZED.value(), "登录超时，请重新登录");
        User user = (User) session.getAttribute(sessionStatusKey);
        if (Objects.equals(user.getRole(), role)) return;
        throw new BasicException(HttpStatus.FORBIDDEN.value(), msg);
    }

}
