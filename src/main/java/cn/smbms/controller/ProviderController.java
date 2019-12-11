package cn.smbms.controller;

import cn.smbms.pojo.Provider;
import cn.smbms.service.ProviderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/jsp")
public class ProviderController {
    @Autowired
    ProviderService providerService;

    //按条件查询providerlist.jsp
    @RequestMapping("/provider.do")
    public String queryProviderList(@RequestParam(value = "proCode", required = false) String proCode,
                                    @RequestParam(value = "proName", required = false) String proName,
                                    Model model) {
        List<Provider> providerList = providerService.queryProviderList(proCode, proName);
        model.addAttribute("queryProCode", proCode);
        model.addAttribute("queryProName", proName);
        model.addAttribute("providerList", providerList);
        return "providerlist";
    }

    //查看供应商信息 providerlist.js
    @RequestMapping("/proview/{proId}")
    public String queryProviderInfo(@PathVariable("proId") String proId, Model model) {
        Provider provider = providerService.queryById(proId);
        System.out.println(provider + "........................");
        model.addAttribute("provider", provider);
        return "providerview";
    }

    //进入修改供应商信息 providerlist.js
    @RequestMapping("/promodify/{proId}")
    public String modifyProvider(@PathVariable("proId") String proId, Model model) {
        Provider provider = providerService.queryById(proId);
        model.addAttribute("provider", provider);
        return "providerview";
    }

    //提交修改信息providerlist.jsp
    @RequestMapping("/provider/provider.do")
    public String updataProvider(Provider provider) {
        providerService.updateProvider(provider);
        return "providerlist";
    }

    //删除信息provider.js
    @RequestMapping("/delprovider/{proId}")
    @ResponseBody
    public String deleteProvider(@PathVariable("proId") String proId) throws JsonProcessingException {
        System.out.println(proId);
        String s = providerService.deleteProvider(proId);
        Map<String, String> map = new HashMap<>();
        map.put("delResult", s);


        String s2 = new ObjectMapper().writeValueAsString(map);


        return s2;
    }

    //进入添加供应商也面providerlist.jsp
    @RequestMapping("/addpro.html")
    public String addProvider() {

        return "provideradd";
    }

    //添加供应商provideradd.jsp
    @RequestMapping("/addprovider")
    public String addProvider(Provider provider) {
       int i = providerService.addProvider(provider);

        return "providerlist";
    }
}
