package yummy.controller.usercontroller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import yummy.entity.AddressEntity;
import yummy.entity.UserEntity;
import yummy.service.MemberService;
import yummy.service.UserService;
import yummy.util.JsonHelper;
import yummy.util.NamedContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class HomeController {
    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(@RequestBody UserEntity userEntity, HttpServletRequest request, HttpServletResponse response) {
        JSONObject msg = new JSONObject();

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userEntity.getLoginToken(), userEntity.getUserPassword());
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            request.getServletContext().setAttribute(NamedContext.ERROR, NamedContext.UNKNOWNUSER);
            msg.put(NamedContext.MES, NamedContext.FAILED);
            JsonHelper.jsonToResponse(response, msg);
            return;
        } catch (IncorrectCredentialsException e) {
            request.getServletContext().setAttribute(NamedContext.ERROR, NamedContext.INCORRECTPASSWORD);
            msg.put(NamedContext.MES, NamedContext.FAILED);
            JsonHelper.jsonToResponse(response, msg);
            return;
        }
        userEntity = userService.findByLoginToken(userEntity.getLoginToken());
        if (!userEntity.getStatus().equals(NamedContext.ACTIVE)) {
            request.getServletContext().setAttribute(NamedContext.ERROR, userEntity.getStatus());
            msg.put(NamedContext.MES, NamedContext.FAILED);
            JsonHelper.jsonToResponse(response, msg);
            return;
        }
        HttpSession session = request.getSession(true);
        session.setAttribute(NamedContext.USER, userEntity);
        msg.put(NamedContext.MES, NamedContext.SUCCESS);
        msg.put(NamedContext.USERTYPE, userEntity.getSysRoleEntity().getRole());
        JsonHelper.jsonToResponse(response, msg);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        JSONObject ret = new JSONObject();
        JsonHelper.jsonToResponse(response, ret);
    }

}