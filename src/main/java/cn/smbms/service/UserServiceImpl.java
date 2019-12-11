package cn.smbms.service;

import cn.smbms.dao.RoleMapper;
import cn.smbms.dao.UserMapper;
import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.pojo.UserExample;
import cn.smbms.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public User login(String userCode, String password) {
        UserExample param=new UserExample();
        UserExample.Criteria criteria = param.createCriteria();
        criteria.andUserCodeEqualTo(userCode);
        criteria.andUserPasswordEqualTo(password);
        List<User> users = userMapper.selectByExample(param);
        if (users.size()==1){
            return users.get(0);
        }
        return null;
    }



    @Override
    public List<UserVo> queryUserList(String queryName,Integer roleId,int currentPageNo,int pageSize){
        int startIndex=(currentPageNo-1)*pageSize;
        Map<String,Object> param=new HashMap<>();
        param.put("startIndex",startIndex);
        param.put("pageSize",pageSize);
        param.put("queryName",queryName);
        param.put("roleId",roleId);
        return userMapper.queryUserList(param);

    }

    @Override
    public UserVo findUserById(String userId) {
        User user = userMapper.selectByPrimaryKey(Long.parseLong(userId));
        Role role = roleMapper.selectByPrimaryKey((long) user.getUserRole());
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);
        userVo.setUserRoleName(role.getRoleName());
        return userVo;
    }

    @Override
    public String delUserById(String userId) {
        int i = userMapper.deleteByPrimaryKey(Long.parseLong(userId));
        if (i>0){
            return "true";
        }
        return "false";
    }
    @Override
    public Boolean addUser(User user, Long id) {
        user.setCreatedBy(id);
        user.setCreationDate(new Date());
        int i = userMapper.insert(user);
        if(i>0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean findUserByUserCode(String userCode) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andAddressEqualTo(userCode);
        List<User> users = userMapper.selectByExample(example);
        if(users.size()>0){
            return  true;
        }
        return false;
    }

    @Override
    public void updateUser(User user, Long uid) {
        user.setId(uid);
       userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int updateUserPassword(String newpassword, User user) {
        user.setUserPassword(newpassword);
        int i = userMapper.updateByPrimaryKeySelective(user);
        return i;
    }


    @Override
    public int queryUserCount() {
        UserExample example = new UserExample();
        long l = userMapper.countByExample(example);
        int count = (int) l;
        return count;
    }
}
