package cn.smbms.service;

import cn.smbms.dao.RoleMapper;
import cn.smbms.pojo.Role;
import cn.smbms.pojo.RoleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<Role> queryRoleList() {
        RoleExample example = new RoleExample();
        return roleMapper.selectByExample(example);
    }
}
