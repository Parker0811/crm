package com.bjpowernode.crm.workbench.web.controller;

import com.alibaba.druid.sql.dialect.sqlserver.visitor.SQLServerSchemaStatVisitor;
import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.impl.UserService;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.domain.ContactsActivityRelation;
import com.bjpowernode.crm.workbench.mapper.ActivityRemarkMapper;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.sun.jmx.snmp.SnmpUnknownModelLcdException;
import com.sun.org.apache.bcel.internal.generic.PUTFIELD;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import com.sun.xml.internal.ws.resources.HttpserverMessages;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.rmi.activation.ActivationID;
import java.util.*;

/**
 * 姜宝
 * 2021/4/19
 */
@Controller
public class ActivityController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRemarkMapper activityRemarkMapper;


    //返回市场活动页面
    @RequestMapping("workbench/activity/index.do")
    public String index(Model model) {
        List<User> userList = userService.selectAllUsers();
        model.addAttribute("userList", userList);
        return "workbench/activity/index";
    }

    //通过保存按钮添加一条数据到表tbl_activity中
    @RequestMapping("workbench/activity/saveCreateActivity.do")
    public @ResponseBody
    ReturnObject saveCreateActivity(Activity activity, HttpSession session) {
        //封装参数
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.formatDateTime(new Date()));
        User user = (User) (session.getAttribute(Contants.SESSION_USER));
        String id = user.getId();
        activity.setCreateBy(id);
        //创建返回对象
        ReturnObject returnObject = new ReturnObject();
        //调用ActivityService添加一条数据到表tbl_activity中
        int result = activityService.insertActivity(activity);
        if (result > 0) {
            //添加成功的情况下的返回对象的属性值
            returnObject.setCode(Contants.RETURNOBJECT_CODE_SUCCESS);
        } else {
            //添加失败的情况下,返回对象的属性值
            returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
            returnObject.setMessage("保存失败");
        }
        return returnObject;
    }

    //市场活动页面的数据展示
    @RequestMapping("/workbench/activity/queryActivityForPageByCondition.do")
    public @ResponseBody
    Object queryActivityForPageByCondition(int pageNo, int pageSize, String name, String owner, String startDate, String endDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("beginNo", (pageNo - 1) * pageSize);
        map.put("pageSize", pageSize);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("name", name);
        //查询出所有需要展示的数据
        List<Activity> activityList = activityService.selectActivityForPageByCondition(map);
        //查询总行数
        long totalRows = activityService.selectCountOfActivityByCondition(map);
        //Map封装返回结果
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("activityList", activityList);
        retMap.put("totalRows", totalRows);
        return retMap;
    }

    //点击修改按钮后,显示被选中的id对应的市场活动信息
    @RequestMapping("workbench/activity/editActivity.do")
    public @ResponseBody
    Object editActivity(String id) {
        Activity activity = activityService.selectActivityById(id);
        return activity;
    }

    //根据更新按钮,更新一条数据
    @RequestMapping("/workbench/activity/saveEditActivity.do")
    public @ResponseBody
    Object saveEditActivity(Activity activity, HttpSession session) {
        activity.setEditTime(DateUtils.formatDateTime(new Date()));
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        activity.setEditBy(user.getId());
        ReturnObject returnObject = new ReturnObject();
        int result = activityService.updateActivity(activity);
        if (result > 0) {
            returnObject.setCode(Contants.RETURNOBJECT_CODE_SUCCESS);
        } else {
            returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
            returnObject.setMessage(("更新失败"));
        }
        return returnObject;
    }

    //根据删除按钮批量删除数据
    @RequestMapping("workbench/activity/deleteActivityByIds.do")
    public @ResponseBody
    Object deleteActivityByIds(String[] id) {
        ReturnObject returnObject = new ReturnObject();
        int result = activityService.deleteActivityByIds(id);
        if (result > 0) {
            returnObject.setCode(Contants.RETURNOBJECT_CODE_SUCCESS);
        } else {
            returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
            returnObject.setMessage("删除失败");
        }
        return returnObject;
    }

    //根据批量导出按钮,将表中的所有数据导出到一个新建的Excel表中
    @RequestMapping("workbench/activity/exportAllActivity.do")
    public void exportAllActivity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Activity> activityList = activityService.selectAllActivityForDetail();
        //创建工作簿
        HSSFWorkbook sheets = new HSSFWorkbook();
        //根据工作簿创建表
        HSSFSheet sheet = sheets.createSheet();
        //根据表创建一行,第一行参数为0
        HSSFRow row = sheet.createRow(0);
        //创建第一行的第一个单元格
        HSSFCell cell = row.createCell(0);
        //给第一个单元格赋值
        cell.setCellValue("ID");
        //创建第一行的第二个单元格
        cell = row.createCell(1);
        //给第二个单元格赋值
        cell.setCellValue("所有者");
        cell = row.createCell(2);
        cell.setCellValue("名称");
        cell = row.createCell(3);
        cell.setCellValue("开始日期");
        cell = row.createCell(4);
        cell.setCellValue("结束日期");
        cell = row.createCell(5);
        cell.setCellValue("成本");
        cell = row.createCell(6);
        cell.setCellValue("描述");
        cell = row.createCell(7);
        cell.setCellValue("创建者");
        cell = row.createCell(8);
        cell.setCellValue("创建时间");
        cell = row.createCell(9);
        cell.setCellValue("修改者");
        cell = row.createCell(10);
        cell.setCellValue("修改时间");
        //创建样式对象
        HSSFCellStyle cellStyle = sheets.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Activity activity = new Activity();
        if (activityList != null) {
            for (int i = 0; i < activityList.size(); i++) {

                activity = activityList.get(i);
                row = sheet.createRow(i + 1);
                cell = row.createCell(0);
                cell.setCellValue(activity.getId());
                cell = row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell = row.createCell(2);
                cell.setCellValue(activity.getName());
                cell = row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell = row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell = row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell = row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell = row.createCell(7);
                cell.setCellValue(activity.getCreateBy());
                cell = row.createCell(8);
                cell.setCellValue(activity.getCreateTime());
                cell = row.createCell(9);
                cell.setCellValue(activity.getEditBy());
                cell = row.createCell(10);
                cell.setCellValue(activity.getEditTime());
            }
        }

        //设置响应类型,返回值类型
        response.setContentType("application/octet-stream;charset=UTF-8");
        String browser = request.getHeader("User-Agent");
        String fileName = URLEncoder.encode("市场活动列表", "UTF-8");
        /*if (browser.contains("firefox")) {
            //火狐采用 ISO8859-1
            fileName = new String("市场活动列表".getBytes("UTF-8"), "ISO8859-1");
        }*/
        //设置响应头信息，使浏览器接收到响应信息之后，在下载窗口打开
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
        //获取输出流
        OutputStream os2 = response.getOutputStream();


        sheets.write(os2);
        os2.flush();
        sheets.close();
    }

    //选择导出
    @RequestMapping("/workbench/activity/exportActivitySelective.do")
    public void exportActivitySelective(String[] id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //调用service层方法，查询市场活动
        List<Activity> activityList = activityService.selectActivityForDetailByIds(id);
        //根据查询结果，生成excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("市场活动列表");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell = row.createCell(1);
        cell.setCellValue("所有者");
        cell = row.createCell(2);
        cell.setCellValue("名称");
        cell = row.createCell(3);
        cell.setCellValue("开始日期");
        cell = row.createCell(4);
        cell.setCellValue("结束日期");
        cell = row.createCell(5);
        cell.setCellValue("成本");
        cell = row.createCell(6);
        cell.setCellValue("描述");
        cell = row.createCell(7);
        cell.setCellValue("创建者");
        cell = row.createCell(8);
        cell.setCellValue("创建时间");
        cell = row.createCell(9);
        cell.setCellValue("修改者");
        cell = row.createCell(10);
        cell.setCellValue("修改时间");

        //创建HSSFCellStyle对象，对应样式
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        //5.遍历activityList，显示数据行
        if (activityList != null) {
            Activity activity = null;
            for (int i = 0; i < activityList.size(); i++) {
                activity = activityList.get(i);//获取每一条数据

                row = sheet.createRow(i + 1);//创建一行

                cell = row.createCell(0);//column：列的编号,从0开始，0表示第一列，1表示第二列，....
                cell.setCellValue(activity.getId());
                cell = row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell = row.createCell(2);
                cell.setCellValue(activity.getName());
                cell = row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell = row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell = row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell = row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell = row.createCell(7);
                cell.setCellValue(activity.getCreateBy());
                cell = row.createCell(8);
                cell.setCellValue(activity.getCreateTime());
                cell = row.createCell(9);
                cell.setCellValue(activity.getEditBy());
                cell = row.createCell(10);
                cell.setCellValue(activity.getEditTime());
            }
        }
        response.setContentType("application/octet-stream;charset=UTF-8");
        String browser = request.getHeader("User-Agent");
        String fileName = URLEncoder.encode("市场活动列表", "UTF-8");
        if (browser.contains("firefox")) {
            fileName = new String("市场活动列表".getBytes("UTF-8"), "ISO8859-1");
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
        OutputStream os2 = response.getOutputStream();
        wb.write(os2);
        os2.flush();
        wb.close();
    }

    //通过点击名称下的值,跳转到详细页面,并携带对应id的activity对象
    @RequestMapping("/workbench/activity/detailActivity.do")
    public String detailActivity(String id, Model model) {
        Activity activity = activityService.selectActivityForDetailById(id);
        model.addAttribute("activity", activity);
        List<ActivityRemark> remarkList = activityRemarkMapper.selectActivityRemarkForDetailByActivityId(id);
        model.addAttribute("remarkList", remarkList);
        return "workbench/activity/detail";
    }

    @RequestMapping("/workbench/activity/importActivity.do")
    public @ResponseBody
    Object importActivity(MultipartFile activityFile, HttpSession session) {
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        Map<String, Object> retMap = new HashMap<>();
        try {

            List<Activity> activityList = new ArrayList<>();

            InputStream is = activityFile.getInputStream();
            HSSFWorkbook wb = new HSSFWorkbook(is);//效率低
            //根据wb获取HSSFSheet对象，对应一页的数据
            HSSFSheet sheet = wb.getSheetAt(0);
            //根据sheet获取HSSFRow对象，对应一行的数据
            HSSFRow row = null;
            HSSFCell cell = null;
            Activity activity = null;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {//sheet.getLastRowNum()：获取的是最后一行的编号
                row = sheet.getRow(i);
                //创建一个市场活动对象
                activity = new Activity();
                activity.setId(UUIDUtils.getUUID());
                activity.setOwner(user.getId());
                activity.setCreateBy(user.getId());
                activity.setCreateTime(DateUtils.formatDateTime(new Date()));

                //根据row获取HSSFCell对象，对应一列的数据
                for (int j = 0; j < row.getLastCellNum(); j++) {//row.getLastCellNum()：获取的是最后一列的编号+1
                    cell = row.getCell(j);
                    String cellValue = getCellValue(cell);
                    if (j == 0) {
                        activity.setName(cellValue);
                    } else if (j == 1) {
                        activity.setStartDate(cellValue);
                    } else if (j == 2) {
                        activity.setEndDate(cellValue);
                    } else if (j == 3) {
                        activity.setCost(cellValue);
                    } else if (j == 4) {
                        activity.setDescription(cellValue);
                    }
                }

                //一行遍历完，都封装成了activity对象，把activity对象保存到list中
                activityList.add(activity);
            }

            //调用service层方法，保存数据
            int ret = activityService.insertActivityByList(activityList);

            retMap.put("code", Contants.RETURNOBJECT_CODE_SUCCESS);
            retMap.put("count", ret);
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("code", Contants.RETURNOBJECT_CODE_FAIL);
            retMap.put("message", "系统忙，请稍后重试...");
        }
        return retMap;
    }

    public static String getCellValue(HSSFCell cell) {
        String ret = "";

        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                ret = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                ret = cell.getBooleanCellValue() + "";
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                ret = cell.getNumericCellValue() + "";
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                ret = cell.getCellFormula();
                break;
            default:
                ret = "";
        }
        return ret;
    }



}
