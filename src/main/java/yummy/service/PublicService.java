package yummy.service;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 14:56 2019/1/28
 */
public interface PublicService {
    public Object findByID(Integer id);
    public boolean add(Object o);
    public boolean modify(Object o);
    public boolean delete(Object o);
    public Integer count();
    public boolean has(Integer integer);
    public boolean deleteAll();
}
