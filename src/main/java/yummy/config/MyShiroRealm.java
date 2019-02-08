package yummy.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import yummy.dao.UserRepository;
import yummy.entity.UserEntity;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserRepository userRepository;

    //获得授权信息
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserEntity userEntity = userRepository.findByLoginToken(principals.getPrimaryPrincipal().toString());
        authorizationInfo.addRole(userEntity.getSysRoleEntity().getRole());
        return authorizationInfo;
    }

    //身份认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        //获取用户的输入的账号.
        String loginToken = (String) token.getPrincipal();
        UserEntity userEntity = userRepository.findByLoginToken(loginToken);
        if (userEntity == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(
                userEntity.getLoginToken(), //用户名
                userEntity.getUserPassword(), //密码
                ByteSource.Util.bytes(userEntity.getLoginToken() + userEntity.getSalt()),//salt=username+salt
                getName()  //realm name
        );
    }

}