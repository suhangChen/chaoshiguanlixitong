package cn.smbms.service;

import cn.smbms.pojo.Bill;
import cn.smbms.vo.BillVo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface BillService {
     int queryBillCount();

    List<BillVo> queryBillList(String productName, Integer providerId, Integer isPayment,int currentPageNo,int pageSize);

    String deleteById(String id);

    BillVo queryById(String id);

    int addBill(Bill bill);

    void updateBill(Bill bill);
}
