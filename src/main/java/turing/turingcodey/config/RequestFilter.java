package turing.turingcodey.config;

import org.springframework.stereotype.Component;
import turing.turingcodey.data.model.User;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
public class RequestFilter implements Filter{
    private FilterConfig filterConfig;
    private int index = 0;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println(RequestFilter.class.getName() + ".init()");
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Cookie[] cookies = request.getCookies();
        StringBuffer sb = new StringBuffer();
        if(cookies!=null){
            for(Cookie cookie : cookies){
                sb.append(cookie.getName()+":"+cookie.getValue()+",");
            }
            sb.deleteCharAt(sb.length()-1);
        }
        System.out.println("[第"+(++index)+"次访问]from client request, cookies:" + sb);
        System.out.println("[第"+(index)+"次访问]from server response, header-Set-Cookie:" + response.getHeader("Set-Cookie"));

        if(request.getSession()!=null) {
            System.out.println("sessionId:"+request.getSession().getId());
            User user = (User) request.getSession().getAttribute("user");
            System.out.println(">>>>>user"+user);
//            String account = user.getString("account");
//            String passwd = user.getString("passwd");
//            if(account.equals(AdminConstant.account) && passwd.equals(AdminConstant.passwd)){
//                System.out.println(RequestFilter.class.getName() + ".doFilter()");
        }
        String referer = request.getHeader("Referer");
        String domain = referer != null ? referer.split("//")[1].split("/")[0] : "";
        System.out.println("referer:"+referer);
        // 设置跨域，符合下列域名的可以访问
        switch (domain) {
            case "localhost:3000":
            case "localhost:8080":
            case "localhost:63380":
            case "172.19.1.103:3000":
            case "localhost:63342":
            case "172.19.1.103:8088":
            case "172.17.1.29:80":
            case "172.17.1.29:63342":
                response.setHeader("Access-Control-Allow-Origin","http://"+domain);
                break;
            default:
                response.setHeader("Access-Control-Allow-Origin","*");
                break;
        }
        // 跨域请求
       // response.setHeader("Access-Control-Allow-Origin", "http://localhost:63342");
//        response.setHeader("Access-Control-Allow-Origin", "*");

        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Accept, token ,Authorization");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        filterChain.doFilter(request, response);
//            } else {
//                checkAuth(response);
//            }
//        } else {
//            checkAuth(response);
//        }
    }

    @Override
    public void destroy() {

    }
}
