package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.mapper.*;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.sun.jdi.ArrayReference;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 姜宝
 * 2021/4/22
 */
@Service
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueMapper clueMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ContactsMapper contactsMapper;

    @Autowired
    private ClueRemarkMapper clueRemarkMapper;

    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;

    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;

    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;

    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;

    @Autowired
    private TranMapper tranMapper;

    @Autowired
    private TranRemarkMapper tranRemarkMapper;

    @Override
    public int insertClue(Clue clue) {
        return clueMapper.insertClue(clue);
    }

    @Override
    public Clue selectClueForDetailById(String id) {
        return clueMapper.selectClueForDetailById(id);
    }

    @Override
    public Clue selectClueById(String id) {
        return clueMapper.selectClueById(id);
    }

    @Override
    public int deleteClueById(String id) {
        return clueMapper.deleteClueById(id);
    }

    @Override
    //线索转换的业务处理
    public void saveConvertClue(Map<String, Object> map) {

        //1.根据封装的clueId创建clue对象
        String clueId = (String) map.get("clueId");
        Clue clue = clueMapper.selectClueById(clueId);

        //2.根据线索对象的信息创建一条客户信息保存到数据库中
        Customer customer = new Customer();
        customer.setId(UUIDUtils.getUUID());
        User user = (User) map.get("sessionUser");
        customer.setOwner(user.getId());
        customer.setName(clue.getCompany());
        customer.setWebsite(clue.getWebsite());
        customer.setPhone(clue.getPhone());
        customer.setCreateBy(user.getId());
        customer.setCreateTime(DateUtils.formatDateTime(new Date()));
        customer.setContactSummary(clue.getContactSummary());
        customer.setNextContactTime(clue.getNextContactTime());
        customer.setDescription(clue.getDescription());
        customer.setAddress(clue.getAddress());
        customerMapper.insertCustomer(customer);

        //3.根据线索信息创建一条联系人信息,保存到数据库中
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtils.getUUID());
        contacts.setOwner(user.getId());
        contacts.setSource(clue.getSource());
        contacts.setCustomerId(customer.getId());
        contacts.setFullName(clue.getFullName());
        contacts.setAppellation(clue.getAppellation());
        contacts.setEmail(clue.getEmail());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setCreateBy(user.getId());
        contacts.setCreateTime(DateUtils.formatDateTime(new Date()));
        contacts.setDescription(clue.getDescription());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setAddress(clue.getAddress());
        contactsMapper.insertContacts(contacts);

        //4.将线索备注表中的所有数据转移到客户备注表与联系人备注表各一份
        List<ClueRemark> clueRemarkList = clueRemarkMapper.selectClueRemarkByClueId(clueId);
        CustomerRemark customerRemark = null;
        ContactsRemark contactsRemark = null;
        List<CustomerRemark> customerRemarkList = new ArrayList<>();
        List<ContactsRemark> contactsRemarkList = new ArrayList<>();
        for (ClueRemark clueRemark : clueRemarkList) {
            customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtils.getUUID());
            customerRemark.setCreateBy(clueRemark.getCreateBy());
            customerRemark.setCreateTime(clueRemark.getCreateTime());
            customerRemark.setCustomerId(customer.getId());
            customerRemark.setNoteContent(clueRemark.getNoteContent());
            customerRemark.setEditBy(clueRemark.getEditBy());
            customerRemark.setEditTime(clueRemark.getEditTime());
            customerRemark.setEditFlag(clueRemark.getEditFlag());
            customerRemarkList.add(customerRemark);

            contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtils.getUUID());
            contactsRemark.setCreateBy(clueRemark.getCreateBy());
            contactsRemark.setCreateTime(clueRemark.getCreateTime());
            contactsRemark.setContactsId(contacts.getId());
            contactsRemark.setNoteContent(clueRemark.getNoteContent());
            contactsRemark.setEditBy(clueRemark.getEditBy());
            contactsRemark.setEditTime(clueRemark.getEditTime());
            contactsRemark.setEditFlag(clueRemark.getEditFlag());
            contactsRemarkList.add(contactsRemark);
        }
        customerRemarkMapper.insertCustomerRemarkByList(customerRemarkList);
        contactsRemarkMapper.insertContactsRemarkByList(contactsRemarkList);

        //5.将线索活动关联表中的指定线索id的所有数据向联系人活动关联表中备份一份
        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationMapper.selectClueActivityRelationByClueId(clueId);
        ContactsActivityRelation contactsActivityRelation = null;
        List<ContactsActivityRelation> contactsActivityRelationList = new ArrayList<>();
        if (clueActivityRelationList != null & clueActivityRelationList.size() > 0) {
            for (ClueActivityRelation clueActivityRelation : clueActivityRelationList) {
                contactsActivityRelation = new ContactsActivityRelation();
                contactsActivityRelation.setId(UUIDUtils.getUUID());
                contactsActivityRelation.setActivityId(clueActivityRelation.getActivityId());
                contactsActivityRelation.setContactsId(contacts.getId());
            }
            contactsActivityRelationList.add(contactsActivityRelation);
            contactsActivityRelationMapper.insertContactsActivityRelationByList(contactsActivityRelationList);
        }

        //6.如果创建了交易,向交易表中添加一条数据
        String isCreateTran = (String) map.get("isCreateTran");
        if ("true".equals(isCreateTran)){
            Tran tran = new Tran();
            tran.setActivityId((String)map.get("activityId"));
            tran.setContactsId(contacts.getId());
            tran.setCreateBy(user.getId());
            tran.setCreateTime(DateUtils.formatDateTime(new Date()));
            tran.setCustomerId(customer.getId());
            tran.setExpectedDate((String)map.get("expectedClosingDate"));
            tran.setId(UUIDUtils.getUUID());
            tran.setMoney((String)map.get("amountOfMoney"));
            tran.setName((String)map.get("tradeName"));
            tran.setOwner(user.getId());
            tran.setStage((String)map.get("stage"));
            tranMapper.insert(tran);

            //7.再把线索备注备份到交易备注中
            if(clueRemarkList!=null&&clueRemarkList.size()>0){
                TranRemark tr=null;
                List<TranRemark> trList=new ArrayList<>();
                for(ClueRemark cr:clueRemarkList){
                    tr=new TranRemark();
                    tr.setCreateBy(cr.getCreateBy());
                    tr.setCreateTime(cr.getCreateTime());
                    tr.setEditby(cr.getEditBy());
                    tr.setEditFlag(cr.getEditFlag());
                    tr.setEditTime(cr.getEditTime());
                    tr.setId(UUIDUtils.getUUID());
                    tr.setNoteContent(cr.getNoteContent());
                    tr.setTranId(tran.getId());
                    trList.add(tr);
                }
                tranRemarkMapper.insertTranRemarkByList(trList);
            }
        }
        //8.删除指定线索id的所有线索备注
        clueRemarkMapper.deleteClueRemarkByClueId(clueId);

        //9.删除指定线索id的所有线索活动关联表
        clueActivityRelationMapper.deleteClueActivityRelationByClueId(clueId);

        //10.删除指定线索id的线索表
        clueMapper.deleteClueById(clueId);
    }

}
