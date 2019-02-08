package yummy.util;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import yummy.entity.UserEntity;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 17:41 2018/3/28
 */
public class PasswordHelper {
    private PasswordHelper() {
        throw new IllegalStateException(NamedContext.UTILCLASS);
    }

    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private static final int HASHITERATIONS = 2;

    public static void encryptPassword(UserEntity user) {
        // User对象包含最基本的字段Username和Password
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        // 将用户的注册密码经过散列算法替换成一个不可逆的新密码保存进数据，散列过程使用了盐
        String algorithmName = "md5";
        String newPassword = new SimpleHash(algorithmName, user.getUserPassword(),
                ByteSource.Util.bytes(user.getLoginToken() + user.getSalt()), HASHITERATIONS).toHex();
        user.setUserPassword(newPassword);
    }
}