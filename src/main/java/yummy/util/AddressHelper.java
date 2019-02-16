package yummy.util;

import yummy.entity.AddressEntity;

import java.math.BigDecimal;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 14:50 2019/2/15
 */
public class AddressHelper {
    private AddressHelper() {
        throw new IllegalStateException(NamedContext.UTILCLASS);
    }

    public static Double calculateDistance(AddressEntity mainAddress, AddressEntity addressEntity) {
        double DEF_PI180 = Math.PI / 180.0;
        double ew1 =  mainAddress.getLongitude() * DEF_PI180;
        double ns1 =  mainAddress.getLatitude() * DEF_PI180;
        double ew2 =  addressEntity.getLongitude() * DEF_PI180;
        double ns2 =  addressEntity.getLatitude() * DEF_PI180;

        double distance = Math.sin(ns1) * Math.sin(ns2)+ Math.cos(ns1)* Math.cos(ns2)* Math.cos(ew1- ew2);
        if(distance >1.0) distance = 1.0;
        else if(distance <-1.0) distance = -1.0;
        double DEF_R = 6370996.81;
        distance = DEF_R *Math.acos(distance) / 1000;

        BigDecimal bd = new BigDecimal(distance);
        return bd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue() * 3;
    }
}
