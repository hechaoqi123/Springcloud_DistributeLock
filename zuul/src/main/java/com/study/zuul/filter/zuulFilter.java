package com.study.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * fileName:zuulFilter
 * description:
 * author:hcq
 * createTime:2019-03-20 19:20
 */
@Component
public class zuulFilter extends ZuulFilter {
    @Override
    public String filterType() {
        /**
         pre：可以在请求被路由之前调用
         route：在路由请求时候被调用
         post：在route和error过滤器之后被调用
         error：处理请求时发生错误时被调用
         */
        return "pre";
    }

    @Override
    public int filterOrder() {
        //过滤器优先级： 如果是多个过滤器，返回值数字越大，优先级越低
        return 0;
    }

    @Override //ip拦截
    public boolean shouldFilter() {//可以通过业务判断是否启用此过滤器
        //通过Zuul组件提供的上下文对象
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpServletResponse resp = currentContext.getResponse();
        String host = request.getRemoteHost();
        System.out.println(host);
        if (host.startsWith("192.168")) {
            try {
                resp.setCharacterEncoding("utf-8");
                resp.sendError(403, "权限不足");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }

    @Override  //脏字过滤
    public Object run() throws ZuulException {//过滤器具体执行的内容
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpServletResponse resp = currentContext.getResponse();
        Enumeration<String> parameters = request.getParameterNames();
        System.out.println("开始过滤脏字");
        while (parameters.hasMoreElements()) {
            String str = parameters.nextElement();
            System.out.println(str);
            if (request.getParameter(str).indexOf("tmd") >= 0) {
                try {
                    resp.setCharacterEncoding("utf-8");
                    resp.sendError(500, "紧张说脏话");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
