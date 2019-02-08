package yummy.util;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 15:12 2019/2/2
 */

@ControllerAdvice
public class ExceptionAdvice {

    /**
     * 全局捕获AuthorizationException异常，并进行相应处理
     */
    @ExceptionHandler(value = Exception.class)
    public void handleException(HttpServletRequest request,HttpServletResponse response,Exception e) {
        e.printStackTrace();
        request.getServletContext().setAttribute(NamedContext.ERROR,NamedContext.UNAUTHORIZED);
        JSONObject ret = new JSONObject();
        ret.put(NamedContext.MES,NamedContext.UNAUTHORIZED);
        JsonHelper.jsonToResponse(response,ret);
    }

}