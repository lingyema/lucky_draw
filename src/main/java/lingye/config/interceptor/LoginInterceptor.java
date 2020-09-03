package lingye.config.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lingye.base.ResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {
    private ObjectMapper objectMapper;
    public LoginInterceptor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    //if(session!=null && session.getAttribute(user)!=null)return true ==>继续执行
    //else返回一个带错误码和错误信息的json
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session!=null && session.getAttribute("user") != null){
            return true;//已经登陆，继续执行Controller中的方法
        }
        //前端通过状态码已经处理过了，这里可以不用处理
       response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);//浏览器解析数据格式和编码
        response.setCharacterEncoding("UTF-8");//设置响应体编码
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ResponseResult r = ResponseResult.error("SES000", "用户没有登录");
        PrintWriter writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(r));
        writer.flush();
        return false;
    }
}
