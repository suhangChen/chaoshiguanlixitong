package cn.smbms.controller;

import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.RoleService;
import cn.smbms.service.UserService;
import cn.smbms.vo.UserVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/jsp/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    //分页查询用户管理 userlist.jsp里寻找
    @RequestMapping("/userlist.html")
    public String queryUserList(Model model,
                                @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
                                @RequestParam(value = "queryname", required = false) String queryname,
                                @RequestParam(value = "queryUserRole", required = false) Integer roleId) {

        int totalCount = userService.queryUserCount();
        int pageSize = 5;
        int totalPageCount = totalCount % 5 == 0 ? totalCount / 5 : totalCount / 5 + 1;
        int currentPageNo = 1;
        if (pageIndex != null) {
            currentPageNo = pageIndex;
        }
        String queryUserName = queryname;


        List<UserVo> userList = userService.queryUserList(queryname, roleId, currentPageNo, pageSize);
        List<Role> roleList = roleService.queryRoleList();
        model.addAttribute("userList", userList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPageNo", currentPageNo);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("roleList", roleList);
        model.addAttribute("queryUserName", queryUserName);
        model.addAttribute("queryUserRole", roleId);


        return "userlist";

    }

    //查看用户信息映射路径在userlist.js里找
    @RequestMapping("/userview/{userId}")
    public String queryUserInfo(@PathVariable("userId") String userId, Model model) {
        UserVo userVo = userService.findUserById(userId);

        model.addAttribute("user", userVo);
        return "userview";
    }

    //删除用户在userlist.js里找
    @RequestMapping("/deluser/{userId}")
    @ResponseBody
    public String delUser(@PathVariable("userId") String userId) {
        String s = userService.delUserById(userId);
        Map<String, String> result = new HashMap<>();
        result.put("delResult", s);

        String s1 = null;
        try {
            s1 = new ObjectMapper().writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s1;
    }

    //修改用户信息映射路径在userlist.js里去找
    @RequestMapping("/usermodify/{userId}")
    public String modifyUser(@PathVariable("userId") String userId, Model model) {
        UserVo userVo = userService.findUserById(userId);
        model.addAttribute("user", userVo);
        return "usermodify";
    }
    //修改成功页面跳转
    @RequestMapping("/user.do")

    public String updateUser(User user ,Long uid){
        System.out.println(user+"修改成功");
        System.out.println(uid);
        userService.updateUser(user,uid);
        return "usermodify";

}
//        @RequestMapping()
//        @ResponseBody
//        public Stirng old()

    //添加用户里的用户角色的选择映射路径在useradd.js里
    @RequestMapping("/rolelist")
    @ResponseBody
    public List<Role> queryRoleList(){
        List<Role> roles=roleService.queryRoleList();
        return roles;
    }
    //进入添加页面路径在userlist.jsp
    @RequestMapping("/add.html")
    public String addUser(){

        return "useradd";
    }
    //添加用户 useradd.jsp
    @RequestMapping("/adduser")
    public String addUser(User user, HttpSession session){
        System.out.println(user);
        User loginUser= (User)session.getAttribute("userSession");
        Boolean b = userService.addUser(user, loginUser.getId());
        return "useradd";
    }
    //添加用户时验证用户账号存不存在 useradd.js
    @RequestMapping("/ucexist/{userCode}")
    @ResponseBody
    public Map<String,String> userCodeExist(@PathVariable("userCode") String userCode){
        Boolean have = userService.findUserByUserCode(userCode);

        Map<String, String> map = new HashMap<>();
        if(have) {
            map.put("userCode","exist");
        }else {
            map.put("userCode","");
        }
        return map;
    }
    //head.jsp进入密码修改页面
    @RequestMapping("/pwdmodify.html")
    public String updateUser(){
        return "pwdmodify";
    }

    //修改密码
    @RequestMapping("/pwd/")
    public String oldpwd() throws JsonProcessingException {
        String s="error";
        Map<String,String> result =new HashMap<>();
        result.put("result",s);
        String s1 = new ObjectMapper().writeValueAsString(result);
        return s1;
    }
    //验证旧密码对不对pwdmodify.js
    @RequestMapping("/pwd/{oldpassword}")
    @ResponseBody
    public  String oldpwd(@PathVariable("oldpassword")String oldpassword,HttpSession session) {
        User loginUser = (User) session.getAttribute("userSession");
        String s = null;
        System.out.println(loginUser.getUserPassword());
        System.out.println(oldpassword.getClass() + "-----------------------------------------------");
        System.out.println(oldpassword == loginUser.getUserPassword());
        System.out.println(loginUser.getUserPassword().getClass());
        System.out.println(loginUser.getUserPassword() + "..............................................");
        if (loginUser.getUserPassword().equals(oldpassword)) {
            s = "true";
        } else if (session == null) {
            s = "sessionerror";
        } else if (!loginUser.getUserPassword().equals(oldpassword)) {
            s = "false";
        }
        Map<String, String> result = new HashMap<>();
        result.put("result", s);
        String s1 = null;
        try {
            s1 = new ObjectMapper().writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();

        }
        return s1;
    }
    //pwdmodify.jsp修改密码
        @RequestMapping("updatepwd")
        public String updatepwd(String newpassword,HttpSession session){
        User loginUser = (User) session.getAttribute("userSession");
        int i =userService.updateUserPassword(newpassword,loginUser);
        return "forward:/login.jsp";
    }
//    @RequestMapping("updatepwd")
//    public String updatepwd(String newpassword,HttpSession session){
//        User loginUser=(User)session.getAttribute("userSession");
//        int i=userService.updateUserPassword(newpassword,loginUser);
//
//        return "forward:/login.jsp";
//    }


}
