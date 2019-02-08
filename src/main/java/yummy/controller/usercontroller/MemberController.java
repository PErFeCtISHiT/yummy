package yummy.controller.usercontroller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import yummy.entity.AddressEntity;
import yummy.entity.MemberMessageEntity;
import yummy.entity.UserEntity;
import yummy.service.AddressService;
import yummy.service.MemberService;
import yummy.service.UserService;
import yummy.util.JsonHelper;
import yummy.util.NamedContext;
import yummy.util.PasswordHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 14:07 2019/1/18
 */
@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    private final JavaMailSender mailSender;
    private final AddressService addressService;

    private final UserService userService;
    @Autowired
    public MemberController(MemberService memberService, JavaMailSender mailSender, AddressService addressService, UserService userService) {
        this.memberService = memberService;
        this.mailSender = mailSender;
        this.addressService = addressService;
        this.userService = userService;
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public void signUp(@RequestBody UserEntity userEntity, HttpServletRequest request, HttpServletResponse response) {
        PasswordHelper.encryptPassword(userEntity);
        userEntity.setSysRoleEntity(userService.findRoleById(1));
        userEntity.setMemberMessageEntity(new MemberMessageEntity());
        UserEntity user = userService.findByLoginToken(userEntity.getLoginToken());
        JSONObject ret = new JSONObject();
        String regex = ".*@.*";
        if (user != null) {
            request.getServletContext().setAttribute(NamedContext.ERROR, NamedContext.REPEATUSER);
            ret.put(NamedContext.MES, NamedContext.FAILED);
            JsonHelper.jsonToResponse(response, ret);
            return;
        } else if (!Pattern.matches(regex, userEntity.getLoginToken())) {
            request.getServletContext().setAttribute(NamedContext.ERROR, NamedContext.MAILINVALID);
            ret.put(NamedContext.MES, NamedContext.FAILED);
            JsonHelper.jsonToResponse(response, ret);
            return;
        }

        memberService.add(userEntity);

        StringBuilder sb = new StringBuilder("点击下面链接激活账号\n");
        sb.append("http://localhost:8080/member/activate?&email=");
        sb.append(userEntity.getLoginToken());
        sb.append("&salt=");
        sb.append(userEntity.getSalt());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("849798320@qq.com");
        message.setTo(userEntity.getLoginToken());
        message.setSubject("会员邮箱激活");
        message.setText(sb.toString());

        mailSender.send(message);
        ret.put(NamedContext.MES, NamedContext.SUCCESS);
        JsonHelper.jsonToResponse(response, ret);
    }

    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public void activate(@RequestParam String email, @RequestParam String salt, HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        UserEntity userEntity = userService.findByLoginToken(email);
        if (userEntity != null && userEntity.getSalt().equals(salt)) {
            userEntity.setStatus(NamedContext.ACTIVE);
            memberService.modify(userEntity);
            request.getServletContext().setAttribute(NamedContext.USER, userEntity);
            request.getRequestDispatcher("/user/info.jsp").forward(request, response);
        } else {
            request.getServletContext().setAttribute(NamedContext.ERROR, NamedContext.ACTIVEFAILED);
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }

    }
    @RequiresRoles("member")
    @RequestMapping(value = "/modifyUser",method = RequestMethod.POST)
    public void modifyUser(@RequestBody MemberMessageEntity memberMessageEntity,HttpServletRequest request,HttpServletResponse response){
        String loginToken = SecurityUtils.getSubject().getPrincipal().toString();
        UserEntity userEntity = userService.findByLoginToken(loginToken);
        MemberMessageEntity oldMessage = userEntity.getMemberMessageEntity();
        oldMessage.setMemberName(memberMessageEntity.getMemberName());
        oldMessage.setTelephone(memberMessageEntity.getTelephone());
        JSONObject ret = new JSONObject();
        if(memberService.saveMemberMessage(oldMessage)){
            request.getSession(true).setAttribute(NamedContext.USER,userEntity);
            ret.put(NamedContext.MES,NamedContext.SUCCESS);
        }else
            ret.put(NamedContext.MES,NamedContext.FAILED);
        JsonHelper.jsonToResponse(response,ret);

    }
    @RequiresRoles("member")
    @RequestMapping(value = "/addAddress", method = RequestMethod.POST)
    public void addAddress(@RequestBody AddressEntity addressEntity, HttpServletRequest request, HttpServletResponse response) {
        String loginToken = SecurityUtils.getSubject().getPrincipal().toString();
        UserEntity userEntity = userService.findByLoginToken(loginToken);
        addressEntity.setMemberMessageEntity(userEntity.getMemberMessageEntity());
        userEntity.getMemberMessageEntity().getAddressEntitySet().add(addressEntity);
        JSONObject ret = new JSONObject();

        if(addressService.add(addressEntity)) {
            HttpSession session = request.getSession(true);
            session.setAttribute(NamedContext.USER, userEntity);
            ret.put(NamedContext.MES, NamedContext.SUCCESS);
        }else
            ret.put(NamedContext.MES,NamedContext.FAILED);
        JsonHelper.jsonToResponse(response, ret);
    }
    @RequiresRoles("member")
    @RequestMapping(value = "/removeAddress", method = RequestMethod.POST)
    public void removeAddress(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = JsonHelper.requestToJson(request);
        JSONArray array = new JSONArray(jsonObject.getJSONArray(NamedContext.ADDRESS).toString());
        HttpSession session = request.getSession(true);
        UserEntity userEntity = (UserEntity) session.getAttribute(NamedContext.USER);
        for (Object o : array) {
            String address = (String) o;
            AddressEntity addressEntity = addressService.findAddressByAddressName(address);
            userEntity.getMemberMessageEntity().getAddressEntitySet().removeIf(addressEntity1 -> addressEntity1.getId().equals(addressEntity.getId()));
            addressService.delete(addressEntity);
        }
        session.setAttribute(NamedContext.USER,userEntity);

        JSONObject ret = new JSONObject();
        ret.put(NamedContext.MES, NamedContext.SUCCESS);
        JsonHelper.jsonToResponse(response, ret);
    }
    @RequiresRoles("member")
    @RequestMapping(value = "/destroyUser", method = RequestMethod.GET)
    public void destroyUser(HttpServletResponse response) {
        String loginToken = SecurityUtils.getSubject().getPrincipal().toString();
        UserEntity userEntity = userService.findByLoginToken(loginToken);
        userEntity.setStatus(NamedContext.DESTROYED);
        JSONObject ret = new JSONObject();
        System.out.println(userEntity.getStatus());
        SecurityUtils.getSubject().logout();
        if(!memberService.modify(userEntity)){
            ret.put(NamedContext.MES,NamedContext.FAILED);
        }else {
            ret.put(NamedContext.MES,NamedContext.SUCCESS);
        }
        JsonHelper.jsonToResponse(response,ret);
    }

}
