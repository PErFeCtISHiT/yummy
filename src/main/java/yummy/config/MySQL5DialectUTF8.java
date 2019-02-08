package yummy.config;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 14:10 2019/1/18
 */
public class MySQL5DialectUTF8 extends MySQL5InnoDBDialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
