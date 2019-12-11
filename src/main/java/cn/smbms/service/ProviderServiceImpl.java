package cn.smbms.service;

import cn.smbms.dao.ProviderMapper;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.ProviderExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProviderServiceImpl implements ProviderService {
@Autowired
private ProviderMapper providerMapper;

    @Override
    public List<Provider> queryProviderList(String proCode, String proName) {
        Map<String,String> param = new HashMap<>();
        param.put("proCode",proCode);
        param.put("proName",proName);
        List<Provider> providerList = providerMapper.findProviderList(param);
        return providerList;
    }

    @Override
    public List<Provider> queryProvider1List() {
        ProviderExample example = new ProviderExample();
        return providerMapper.selectByExample(example);
    }

    @Override
    public Provider queryById(String proId) {
        Provider provider = providerMapper.selectByPrimaryKey(Long.parseLong(proId));

        return provider;
    }

    @Override
    public int updateProvider(Provider provider) {
        ProviderExample providerExample = new ProviderExample() ;
        ProviderExample.Criteria criteria = providerExample.createCriteria();
        criteria.andProCodeEqualTo(provider.getProCode());
        int i = providerMapper.updateByExampleSelective(provider,providerExample);
        return i;

    }

    @Override
    public String deleteProvider(String proId) {
        int i = providerMapper.deleteByPrimaryKey(Long.parseLong(proId));
        if(i>0){
            return "true";
        }
        return "false";
    }

    @Override
    public int  addProvider(Provider provider) {
        int i = providerMapper.insert(provider);

        return i;
    }


}
