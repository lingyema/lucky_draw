package lingye.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lingye.config.interceptor.LoginInterceptor;
import lingye.config.web.RequestResponseBodyMethodProcessorWrapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
//系统配置类
@Configuration
public class SysConfig implements WebMvcConfigurer, InitializingBean{

    @Resource
    private RequestMappingHandlerAdapter adapter;
    //SpringMVC初始化时就会注册的对象
    @Autowired
    private ObjectMapper objectMapper;
    //之前以@ControllerAdvice+实现ResponseBodyAdvice接口完成统一处理返回数据包装时不能处理返回值为null，
    //改用现在这种方式，可以解决返回值为null包装为自定义类型
    @Override
    public void afterPropertiesSet() {
        List<HandlerMethodReturnValueHandler> returnValueHandlers = adapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> handlers = new ArrayList(returnValueHandlers);
        for(int i=0; i<handlers.size(); i++){
            HandlerMethodReturnValueHandler handler = handlers.get(i);
            if(handler instanceof RequestResponseBodyMethodProcessor){
                handlers.set(i, new RequestResponseBodyMethodProcessorWrapper(handler));
            }
        }
        adapter.setReturnValueHandlers(handlers);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(objectMapper))
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/user/login")
                .excludePathPatterns("/api/user/register")
                .excludePathPatterns("/api/user/logout");
        registry.addInterceptor(new LoginInterceptor(objectMapper))
                .addPathPatterns("/*.html")
                .excludePathPatterns("/index.html");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //Controller路径统一添加请求的路径前缀，第二个参数表示是否添加
        //所有Controller请求路径都要加上/api前缀
        configurer.addPathPrefix("api",c->true);
    }
}


