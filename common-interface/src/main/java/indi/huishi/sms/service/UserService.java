package indi.huishi.sms.service;


import indi.huishi.sms.pojo.User;

//业务
public interface UserService {

    /**
     * 注册用户
     *
     * @param user
     */
    void registerUser(User user) throws Exception;

    /**
     * 登录
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 用户名是否已经存在
     * @param username
     * @return
     */
    boolean existsUsername(String username);
}
