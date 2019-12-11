package cn.smbms.service;

import cn.smbms.pojo.Provider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProviderService {
    List<Provider> queryProviderList(String proCode,String proName);


    List<Provider> queryProvider1List();

    Provider queryById(String proId);

    int updateProvider(Provider provider);

    String deleteProvider(String proId);

    int addProvider(Provider provider);
}
