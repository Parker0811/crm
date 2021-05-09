package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.FunnelVO;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.mapper.CustomerMapper;
import com.bjpowernode.crm.workbench.mapper.TranHistoryMapper;
import com.bjpowernode.crm.workbench.mapper.TranMapper;
import com.bjpowernode.crm.workbench.service.TranService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 姜宝
 * 2021/4/24
 */
@Service
public class TranServiceImpl implements TranService {

    @Autowired
    private TranMapper tranMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private TranHistoryMapper tranHistoryMapper;

    @Override
    public int insertTran(Tran tran) {
        return tranMapper.insertTran(tran);
    }

    @Override
    public Tran selectTranForDetailById(String id) {
        return tranMapper.selectTranForDetailById(id);
    }

    @Override
    public List<FunnelVO> selectCountOfTranGroupByStage() {
        return tranMapper.selectCountOfTranGroupByStage();
    }

    @Override
    public void saveCreateTran(Map<String, Object> map) {
        Tran tran = (Tran) map.get("tran");
        String customerId = tran.getCustomerId();
        String customerName = (String) map.get("customerName");
        User user = (User) map.get("sessionUser");
        if (customerId == null || customerId.trim().length() == 0) {
            //向数据库中插入一个客户
            Customer customer = new Customer();
            customer.setId(UUIDUtils.getUUID());
            customer.setName(customerName);
            customer.setOwner(user.getId());
            customer.setCreateTime(DateUtils.formatDateTime(new Date()));
            customer.setCreateBy(user.getId());
            customerMapper.insertCustomer(customer);
            tran.setCustomerId(customer.getId());
        }

        //向交易表中插入一条数据
        tranMapper.insertTran(tran);

        //向交易历史表中插入一条数据
        TranHistory tranHistory=new TranHistory();
        tranHistory.setCreateBy(user.getId());
        tranHistory.setCreateTime(DateUtils.formatDateTime(new Date()));
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setId(UUIDUtils.getUUID());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setStage(tran.getStage());
        tranHistory.setTranId(tran.getId());
        tranHistoryMapper.insertTranHistory(tranHistory);
    }
}
