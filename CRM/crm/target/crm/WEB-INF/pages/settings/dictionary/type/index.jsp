<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function () {
			//给"创建"按钮添加单击事件
			$("#createDicTypeBtn").click(function () {
				//发送请求
				window.location.href="settings/dictionary/type/toSave.do";
			});

			//实现"全选"和"取消全选"
			//给"全选"按钮添加单击事件
			$("#chkedAll").click(function () {
				//让列表中的checkbox的checked属性值和全选按钮的checked属性值保持一致
				$("#tBody input[type='checkbox']").prop("checked",$("#chkedAll").prop("checked"))
			});

			//给列表中所有的checkbox添加单击事件


			//给"删除"按钮添加单击事件
			$("#deleteDicTypeBtn").click(function () {
				//收集参数
				var chkedCodes=$("#tBody input[type='checkbox']:checked");//获取列表中所有被选中的checkbox
				//表单验证
				if(chkedCodes.size()==0){
					alert("请选择要删除的记录");
					return;
				}

				var codesStr="";
				//遍历出来的每一个值是dom对象
				$.each(chkedCodes,function () {
					codesStr+="code="+this.value+"&";
				})
				codesStr.substr(0,codesStr.length-1);
				if(window.confirm("确定删除吗?")){
					//发送请求
					$.ajax({
						url:'settings/dictionary/type/deleteDicTypeByCodes.do?'+codesStr,
						//data:codesStr,
						type:'post',
						dataType:'json',
						success:function (data) {
							if(data.code=="1"){
								window.location.href="settings/dictionary/type/index.do";
							}else{
								alert(data.message);
							}
						}
					});
				}
			});

			//给"编辑"按钮添加单击事件
			$("#editDicTypeBtn").click(function () {
				//收集参数
				var chkedCodes=$("#tBody input[type='checkbox']:checked");
				//表单验证
				if(chkedCodes.size()==0){
					alert("请选择要编辑的记录");
					return;
				}
				if(chkedCodes.size()>1){
					alert("一次只能修改一条记录");
					return;
				}
				var code=chkedCodes[0].value;

				//发送同步请求
				window.location.href="settings/dictionary/type/editDicType.do?code="+code;
			});
		});
	</script>
</head>
<body>

	<div>
		<div style="position: relative; left: 30px; top: -10px;">
			<div class="page-header">
				<h3>字典类型列表</h3>
			</div>
		</div>
	</div>
	<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
		<div class="btn-group" style="position: relative; top: 18%;">
		  <button id="createDicTypeBtn" type="button" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span> 创建</button>
		  <button id="editDicTypeBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
		  <button id="deleteDicTypeBtn" type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	<div style="position: relative; left: 30px; top: 20px;">
		<table class="table table-hover">
			<thead>
				<tr style="color: #B3B3B3;">
					<td><input type="checkbox" id="chkedAll"/></td>
					<td>序号</td>
					<td>编码</td>
					<td>名称</td>
					<td>描述</td>
				</tr>
			</thead>
			<tbody id="tBody">
				<!--从request获取dicTypeList，遍历dicTypeList，显示所有的数据-->
				<c:forEach items="${dicTypeList}" var="dt" varStatus="vs">
					<c:if test="${vs.count%2==0}">
						<tr class="active">
							<td><input type="checkbox" value="${dt.code}"/></td>
							<td>${vs.count}</td>
							<td>${dt.code}</td>
							<td>${dt.name}</td>
							<td>${dt.description}</td>
						</tr>
					</c:if>
					<c:if test="${vs.count%2!=0}">
							<tr>
								<td><input type="checkbox" value="${dt.code}"/></td>
								<td>${vs.count}</td>
								<td>${dt.code}</td>
								<td>${dt.name}</td>
								<td>${dt.description}</td>
							</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
</body>
</html>