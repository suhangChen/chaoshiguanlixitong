package cn.smbms.service;

import cn.smbms.pojo.User;
import cn.smbms.vo.UserVo;

import java.util.List;

public interface UserService {
    User login(String userCode,String password);

    int queryUserCount();

    List<UserVo> queryUserList(String queryName,Integer roleId,int currentPageNo,int pageSize);

    UserVo findUserById(String userId);

    String delUserById(String userId);

    Boolean addUser(User user, Long id);

    Boolean findUserByUserCode(String userCode);

    void updateUser(User user, Long uid);

    int updateUserPassword(String newpassword, User user);
}
