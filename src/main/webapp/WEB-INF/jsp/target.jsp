<%@page import="bi.baiqiu.pojo.User"%>
<%@page import="org.apache.shiro.subject.Subject"%>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Subject sub = SecurityUtils.getSubject();
	User user = (User) sub.getPrincipal();
%>
<html>
<head>
<base href="<%=basePath%>">

</head>

<link rel="stylesheet" href="<%=basePath%>/css/bootstrap/bootstrap.css">
<link rel='stylesheet'
	href="<%=basePath%>/css/bootstrap/bootstrap.min.css">
<link rel="stylesheet" href="<%=basePath%>/css/fullcalendar.css">
<link rel="stylesheet" href="<%=basePath%>/css/jquery-ui.css">
<link rel="stylesheet"
	href="<%=basePath%>/css/bootstrap/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="<%=basePath%>/css/fileinput.min.css">
<jsp:include page="/page/left.do"></jsp:include>
<link rel="stylesheet" href="<%=basePath%>/css/localRightTable.css">


<style type="text/css">
.table {
	margin-bottom: 0px;
	
}
.popover-content{
width: 160px;
}
input {
	text-align: right;
}
#first>a{
background-color: 578EBE; color: white;
}
#last>a{
background-color: white;color: black;
}
</style>

</head>

</head>

<body>
	<div class="localRightpageTarget">
		<div class="localRightviewTarget">
			<ul class="nav nav-tabs" id="targetyTab">
				<li class="active" id=first><a href="javascript:void(0)"
					data-toggle="tab">gmv</a></li>
				<li>
				<li id="last"><a href="javascript:void(0)" data-toggle="tab">alipay</a>
				</li>
				<li></li>
			</ul>
			<table class="table table-bordered  targetTabel " >
				<tbody>
					<tr>
						<td rowspan="3"><select class="form-control" name="year"
							id="year">
						</select>
							<div id="gmvOrAlipay" class="gmvOrAlipay">GMV Sales</div> <input
							class="targetAmountInput" id="targetAmount" readonly="readonly"
							type="text"></td>
						<td colspan="5" align="center">Targets</td>
						<td style="border-left: 0">
							<button id="editMonthTarget"
								class=" queryBtn glyphicon  glyphicon-edit btnT btn-primary">
								<span>Edit</span>
							</button>
							<button id="btn_imput"
								class="queryBtn glyphicon  glyphicon-import btnT btn-primary">
								<span>Import</span>
							</button>
							<button id="cancelMonthTarget"
								class=" queryBtn glyphicon  glyphicon-remove  btnT btn-primary hidden">
								<span>Cancel</span>
							</button>
							<button id="saveMonthTarget" onclick="ClicksaveMonthTarget()"
								class=" queryBtn glyphicon glyphicon-saved btnT btn-primary hidden">
								<span>Save</span>
							</button>
						</td>
					</tr>
					<tr>

						<td class="monthUnit" value="01">January
							<div class="heithtBr"></div> <input hidden="true"
							id="monthTargetId" type="text"> <input
							class="targetInput" readonly="readonly" id="JanuaryTarget"
							placeholder="no Data" maxlength='10' type="text">
						<td id="month2" class="monthUnit" value="02">February
							<div class="heithtBr"></div> <input class="targetInput"
							readonly="readonly" id="FebruaryTarget" placeholder="no Data"
							maxlength='10' type="text">
						</td>
						<td class="monthUnit" value="03">March
							<div class="heithtBr"></div> <input class="targetInput"
							readonly="readonly" id="MarchTarget" placeholder="no Data"
							maxlength='10' type="text">

						</td>
						<td class="monthUnit" value="04">April
							<div class="heithtBr"></div> <input class="targetInput"
							readonly="readonly" id="AprilTarget" placeholder="no Data"
							maxlength='10' type="text">
						</td>
						<td class="monthUnit" value="05">May
							<div class="heithtBr"></div> <input class="targetInput"
							readonly="readonly" id="MayTarget" placeholder="no Data"
							maxlength='10' type="text">
						</td>
						<td class="monthUnit" value="06">June
							<div class="heithtBr"></div> <input class="targetInput"
							readonly="readonly" id="JuneTarget" placeholder="no Data"
							maxlength='10' type="text">
						</td>

					</tr>
					<tr>
						<td class="monthUnit" value="07">July
							<div class="heithtBr"></div> <input class="targetInput"
							readonly="readonly" id="JulyTarget" placeholder="no Data"
							maxlength='10' type="text">
						</td>
						<td class="monthUnit" value="08">August
							<div class="heithtBr"></div> <input class="targetInput"
							readonly="readonly" id="AugustTarget" placeholder="no Data"
							maxlength='10' type="text">
						</td>
						<td class="monthUnit" value="09">September
							<div class="heithtBr"></div> <input class="targetInput"
							readonly="readonly" placeholder="no Data" maxlength='10'
							id="SeptemberTarget" type="text">
						</td>
						<td class="monthUnit" value="10">October
							<div class="heithtBr"></div> <input class="targetInput"
							readonly="readonly" id="OctoberTarget" placeholder="no Data"
							maxlength='10' type="text">
						</td>
						<td class="monthUnit" value="10">November
							<div class="heithtBr"></div> <input class="targetInput"
							readonly="readonly" id="NovemberTarget" placeholder="no Data"
							maxlength='10' type="text">
						</td>
						<td class="monthUnit" value="12">December
							<div class="heithtBr"></div> <input class="targetInput"
							readonly="readonly" id="DecemberTarget" placeholder="no Data"
							maxlength='10' type="text">
						</td>
					</tr>
					<tr>
					</tr>
				</tbody>
			</table>

			<div id="gmvTarget" class="hidden ">
				<div class="tab-content">
					<div class="tab-pane active">
						<div id="main">
							<div class="dailyMainTitle">
								<input type="checkbox" onclick="changeShow()" id="showSchedule">
								<span style="margin-right: 5px">According to note</span>
								<input type="checkbox"onclick="changeShowSchedule()" >
								<span style="margin-right: 5px">Click on the add notes</span>
								<button onclick="saveDailyTarget()" id="saveDailyTarget"
									class="glyphicon glyphicon-saved btn btn-payment  queryBtn">
								<span>SaveDay</span>
								</button>
							</div>

							<form id='calendar'></form>
							<!--  -->
							<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
								aria-labelledby="myModalLabel">
								<div class="modal-dialog" role="document">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
											<h4 class="modal-title" id="myModalLabel">operate</h4>
										</div>
										<div class="modal-body">
											<input type="text" name="schedoleId"
												class="form-control hidden" id="schedoleId">
											<div class="form-group">
												<label for="txt_des">Title</label> <input type="text"
													name="title" class="form-control" maxlength="7" id="title"
													placeholder="Title(The maximum length is 7)">
											</div>
											<div class="form-group">
												<label for="txt_departmentlevel">Date</label> <input
													size="16" type="text" id='queryStartDate' name='startDate'
													readonly="readonly" class="form-control">

											</div>
											<div class="form-group">
												<label for="txt_des">Describe</label> <input type="text"
													name="schedoleDesc" class="form-control" id="schedoleDesc"
													placeholder="Describe">
											</div>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default"
												data-dismiss="modal">
												<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>close
											</button>
											<button type="button" id="btn_submit" class="btn btn-primary"
												onclick="saveSchedule()" data-dismiss="modal">
												<span class="glyphicon glyphicon-floppy-disk"
													aria-hidden="true"></span>Save
											</button>
										</div>
									</div>
								</div>
							</div>
							<!--  -->
						</div>
					</div>
				</div>
				<span id="targetCountAndValue"> <span id="monthYeardate">
				</span> Month Target <br> <input class="dailyTargetInput "
					id="monthTarget" readonly="readonly" type="text"> <br>
					Daily Target mount<br> <input
					class="dailyTargetInput blueColor" readonly="readonly"
					id="dailyTargerCount" type="text"> <br> Difference<br>
					<input readonly="readonly" class="dailyTargetInput"
					id="differencValue" type="text">
				</span>
			</div>

		</div>
	</div>
	<!--导入  -->
	<div class="modal fade" id="myModalImoprt" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content ">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Please select a
						file(Excel)</h4>
				</div>
				<div id="uploadRemark"></div>
				<div class="modal-body">
					<a id="downloadTemplate"
							href="<%=basePath%>download/downloadTemplate.do?fileName=DailyTargetTemplate"
							class="form-control" style="border: none;">Download the
							import template <button id="btn_imput" type="button" class="imputBtn btn btn-primary" style="float: right;">
							<span class="glyphicon  glyphicon-import" aria-hidden="true" ></span>
						</button></a> <input type="file" name="txt_file" id="txt_file"
							multiple class="file-loading" />
				</div>
			</div>
		</div>
	</div>
</body>
<script src="<%=basePath%>/js/jquery-1.8.3.min.js"></script>
<script src='<%=basePath%>/js/jquery-ui.js'></script>
<script src='<%=basePath%>/js/fullcalendar.min.js'></script>
<script src="<%=basePath%>/js/bootstrap/bootstrap.min.js"></script>
<script src='<%=basePath%>/js/formatCurrency.js'></script>
<script src="<%=basePath%>/js/fileinput.min.js"></script>
<script src="<%=basePath%>/js/util.js"></script>
<script src="<%=basePath%>/js/bootstrap/bootstrap-datetimepicker.js"></script>
<script src="<%=basePath%>/js/localTableTitle.js"></script>
<script type="text/javascript">
var showSchedule=false;
var showScheduleAdd=false;
var shopId=<%=user.getShopId()%>;
var selected = new Date().getFullYear();
	$(function() {
  	  $("#importData").attr("class", "nav nav-second-level in"); 

		$("#leftTarget").css("background","#578EBE");
		//年份初始化
		var nowYear=Number(new Date().getFullYear());
		for(var i=2013;i<=nowYear+1;i++){
			if(i==nowYear){
				$("#year").append("<option selected='true' value='"+i+"'>"+i+"</option>"); //为Select追加一个Option(下拉项) 
			}	else{
				$("#year").append("<option value='"+i+"'>"+i+"</option>"); 
		}
		}
		//月份绑定事件
		$('#calendar').fullCalendar({
			header : {
				left : '',
				center : 'title',
				right : '',
			},
			contentHeight : 200,
			aspectRatio : 0.5,
			defaultView : 'month',
			editable : false,
		    events: function(start, end, callback) {
			            $.ajax({
			                url: "<%=basePath%>dailySchedule/queryDailySchedule.do",
			                type:"POST",
			                dataType: 'JSON',
			                data: {
			                    startDate: start.format("yyyy-MM-dd"), 
			                    endDate: end.format("yyyy-MM-dd")
			                },
			                success: function(doc) {
			                    var events = [];
			                    if(showSchedule){
				                    addClass ="addPopover";
 			                  	  	}
				                    else{
					                    addClass="addPopover hidden";
				                    }
			                    	jQuery.each(doc.data, function(i, val) {  
			                    		events.push({
			                    			id:val.id,
				                            title: val.title,
				                            start: val.startDate,
				                            allDay:true,
				                            desc: val.scheduleDesc,
				                            className:addClass
			                    		});
			                    	      

			                    	});  
			                    	callback(events);
			                }
			            });
			        }
			, 
			
			dayClick: function(date, jsEvent, view) {
				if(showScheduleAdd){
						addCheckSchedule(date.format("yyyy-MM-dd"));
						$("#queryStartDate").val(date.format("yyyy-MM-dd"));
					}
				},
		    eventMouseover:function( event, jsEvent, view ) { 
		    	if($(this).attr("data-toggle")!="popover"){
		    	$(this).attr("data-toggle","popover");
				$(this).attr("data-placement","auto left");
				$(this).attr("data-trigger","hover");
				$(this).popover({  
			        html : true,    
		           title:"details",
			        delay:{show:200, hide:2000},  
			        content: function() {
			        	var showHTML=	"<form class='showHtml'><ul><li><span  class='icon_globe'></span>&nbsp;<font>Title:</font>" + event.title+ 
			             "</li><li><span    class='icon_piechart'></span>&nbsp;<font>Time:</font>" +event.start.format('yyyy-MM-dd')+
			             "</li><li><span    class='icon_search_alt'></span>"+  
			             "</li><li><input   class=' btn btn-default' type='button' value='Delete' onclick='delectSchedule("+Number(event.id)+")'/>"+
			             "<input class=' btn btn-default' type='button' value='Edit' onclick='addSchedule("+Number(event.id)+")'/>"
			             +"</li></form>";
			          return showHTML;    
			        }   
			    }); 
			}
			
		    },
		    eventMouseout:function( event, jsEvent, view ) { 
		    }
		    ,
		    eventClick:function( event, jsEvent, view ) { 
		    
		    }
		});
		
		//初始化上传
		 inportInit("txt_file", "target/uploadDailyTarget.do");
		//gmv查询
		$('#first').click(function() {
			$("#btn_imput").removeClass("hidden");
			$("#first a").css("background","#428bca");
			$("#first a").css("color","#fff");
			$("#last a").css("background","#fff");
			$("#last a").css("color","#000000");
			$("#gmvOrAlipay").text("GMV Sales");
				if($("#editMonthTarget").css("display")=='none'){
					
					cancelSave();
					
				}
					$('#targetyTab a:first').tab('show');
					var json = {
							"shopId" : shopId,
							"y" : selected
						};
						var url = "<%=basePath%>/target/queryGoalMonthSale.do";
						getDate(url, json);
						initDailyTarget();
				
		});
		//alipay查询
		$('#last').click(
		function() {
			$("#btn_imput").addClass("hidden");
			$("#gmvOrAlipay").text("Alipay Collection");
			$("#last a").css("background","#428bca");
			$("#last a").css("color","#fff");
			$("#first a").css("background","#fff");
			$("#first a").css("color","#000000");
			if($("#editMonthTarget").css("display")=='none'){
				cancelSave();
			}
			$('#targetyTab a:last').tab('show');
			var row = {
				"shopId" : shopId,
				"y" : selected
			};
			var url = "<%=basePath%>/target/queryGoalMonthAlip.do";
			getDate(url, row);
		});
		//月份滑动效果
		$(".monthUnit").mouseover(function(e) {
			$(this).css("background","#fff");
			$(this).children("input:last-child").css("background","#fff")
		});
		$(".monthUnit").mouseout(function(e) {

			$(this).css("background","#f9f9f9");
			$(this).children("input:last-child").css("background","#fff")

		});
		//月份点击效果
		$(".monthUnit")
				.click(
						function(e) {
							monthTargetClick($(this));
						});
		
		//年份变
		$("#year").change(
				function() {
					selected = $('#year option:selected').val();
						//跳转月份
						var row = {
							"shopId" : shopId,
							"y" : selected
						};
						var url = "<%=basePath%>/target/queryGoalMonthSale.do";
						if($("#last").attr("class")=='active')
						url = "<%=basePath%>/target/queryGoalMonthAlip.do";
						getDate(url, row);
						initDailyTarget();
						cancelSave();
				
				});

		 $("#txt_file").on("fileuploaded", function (event, data, previewId, index) {
			 $("#uploadRemark").append("<p>"+data.filenames.pop()+":"+data.response.data+"</p>");
	      });
		var json = {
			"shopId" : shopId,
			"y" : selected
		};
		var url = "<%=basePath%>/target/queryGoalMonthSale.do";
		getDate(url, json)
		//日
		initDailyTarget();
		/*初始化↑ */
		$('#btn_imput').on("click", function () {
      	  $("#myModalImoprt").modal().on("shown.bs.modal", function () {

            }).on('hidden.bs.modal', function () {
            });
        });
		
		//编辑
		$('#editMonthTarget').click(
				function() {
					var nowDate=$('#calendar').fullCalendar('getDate');	
					showSchedule=false;
					if($("#last").attr("class")!='active'){
						$("#gmvTarget").removeClass("hidden");	
						initDailyTarget();
					}
					$("#showSchedule").removeAttr("checked");
					$("#btn_imput").addClass("hidden");
					$(".targetInput").removeAttr("readonly");
					$(".targetInput").css("border","1px solid #d3d3d3")
					$("#saveMonthTarget").removeClass("hidden");
					$("#cancelMonthTarget").removeClass("hidden");
					$("#editMonthTarget").addClass("hidden");
				});
		//取消
		$('#cancelMonthTarget').click(
				function() {
					cancelSave();
				});
		//便签
	$('#btn_imput').on("click", function () {
       	  $("#myModalImoprt").modal().on("shown.bs.modal", function () {

             }).on('hidden.bs.modal', function () {
                 //关闭弹出框的时候清除绑定(这个清空包括清空绑定和清空注册事件)
             });
         });
	//事件绑定
	monthTargetAddChange();
	
	dailySummateAddChange();
		
	});

	//请求月目标数据
	function getDate(url, json) {
		$.ajax({
			url : url,
			data : json,
			dataType : 'JSON',
			success : function(data, status) {
				console.log(url,data)
				var getSumjson={shopId:json.shopId,startDate:json.y+"-01-01",endDate:json.y+"-12-31"};
				var Monthdata=data;
				//请求日计划合计
				$(".targetInput").css("color","#464646");
				if($("#last").attr("class")!='active'&&data.data.length>0){
				$.ajax({
						url : "<%=basePath%>/target/getDailyMonthTargetSum.do",
						type : "POST",
						data : getSumjson,
						cache:false,
						dataType : 'JSON',
						success : function(dailyData, dailyStatus) {
							console.log(dailyData)
							$(".targetInput").css("color","red");
							var dailyDataArray=dailyData.data;
							/* 请求数据不空 */
							if (dailyData != null && dailyData != "" ) {
								for(var aa in dailyDataArray){
									switch (Number(dailyDataArray[aa].dailyMonth)) {
									case 1:
										if(dailyDataArray[aa].dailyMonthSum==Monthdata.data[0].m01)
										$("#JanuaryTarget").css("color","#464646")
										break;
									case 2:
										if(Number(dailyDataArray[aa].dailyMonthSum)==Number(Monthdata.data[0].m02))
										$("#FebruaryTarget").css("color","#464646")
										break;
									case 3:
										if(dailyDataArray[aa].dailyMonthSum==data.data[0].m03)
										$("#MarchTarget").css("color","#464646")
										break;
									case 4:
										if(dailyDataArray[aa].dailyMonthSum==data.data[0].m04)
										$("#AprilTarget").css("color","#464646")
										break;
									case 5:
										if(dailyDataArray[aa].dailyMonthSum==data.data[0].m05)
										$("#MayTarget").css("color","#464646")
										break;
									case 6:
										if(dailyDataArray[aa].dailyMonthSum==data.data[0].m06)
										$("#JuneTarget").css("color","#464646")
										break;
									case 7:
										if(dailyDataArray[aa].dailyMonthSum==data.data[0].m07)
										$("#JulyTarget").css("color","#464646")
										break;
									case 8:
										if(dailyDataArray[aa].dailyMonthSum==data.data[0].m08)
										$("#AugustTarget").css("color","#464646")
										break;
									case 9:
										if(dailyDataArray[aa].dailyMonthSum==data.data[0].m09)
										$("#SeptemberTarget").css("color","#464646")
										break;
									case 10:
										if(dailyDataArray[aa].dailyMonthSum==data.data[0].m10)
										$("#OctoberTarget").css("color","#464646")
										break;
									case 11:
										if(dailyDataArray[aa].dailyMonthSum==data.data[0].m11)
										$("#NovemberTarget").css("color","#464646")
										break;
									case 12:
										if(dailyDataArray[aa].dailyMonthSum==data.data[0].m12)
										$("#DecemberTarget").css("color","#464646")
										break;
									default:
										break;
									}
								}
								
							}
						},
						error : function() {
						}
					});
				}
				/* 请求数据不空 */
				if (data.data != null && data.data != "") {
					monthTarget = data.data[0];

					$("#monthTargetId").val(monthTarget.id);
					if ($("#monthTarget").val() == null
							|| $("#monthTarget").val() == "") {
						$("#monthTarget").val(monthTarget.m01/100);
						$("#monthTarget").attr("month","JanuaryTarget");
					}
					$("#JanuaryTarget").val(monthTarget.m01/100);
					$("#FebruaryTarget").val(monthTarget.m02/100);
					$("#MarchTarget").val(monthTarget.m03/100);
					$("#AprilTarget").val(monthTarget.m04/100);
					$("#MayTarget").val(monthTarget.m05/100);
					$("#JuneTarget").val(monthTarget.m06/100);
					$("#JulyTarget").val(monthTarget.m07/100);
					$("#AugustTarget").val(monthTarget.m08/100);
					$("#SeptemberTarget").val(monthTarget.m09/100);
					$("#OctoberTarget").val(monthTarget.m10/100);
					$("#NovemberTarget").val(monthTarget.m11/100);
					$("#DecemberTarget").val(monthTarget.m12/100);
					var amount = Number(monthTarget.m01)
							+ Number(monthTarget.m02) + Number(monthTarget.m03)
							+ Number(monthTarget.m04) + Number(monthTarget.m05)
							+ Number(monthTarget.m06) + Number(monthTarget.m07)
							+ Number(monthTarget.m08) + Number(monthTarget.m09)
							+ Number(monthTarget.m10) + Number(monthTarget.m11)
							+ Number(monthTarget.m12);
					$("#targetAmount").val(amount/100);
					$(".targetInput").formatCurrency();
					$("#targetAmount").formatCurrency();
				} else {
					$(".monthUnit input").val("");
					$("#targetAmount").val("");
					$("#monthTargetId").val("");
					$("#differencValue").val("");
					$("#monthTarget").val("");
				}
				
			},
			error : function() {
				toastr.success('Please wait...');
			}
		});
	}
	//日目标
	function getDailyDate(json) {
		$.ajax({
			url : "<%=basePath%>/target/queryDailyTargetNopage.do",
			type : "POST",
			data : json,
			dataType : 'JSON',
			success : function(data, status) {
				/* 请求数据不空 */
				if (data != null && data != "") {
					monthTarget = data.data;
					$.each(monthTarget, function(idx, obj) {
						$("#" + obj.date + " .dailyPayment").val(Number(obj.payment)/100);
						$("#" + obj.date + " .dailyId").val(obj.id);

					});

				}
				$("#dailyTargerCount").val(dailysummate());
				
				differenc();
			},
			error : function() {
				toastr.success('Please wait ...');
			}
		});
	}
	function cancelSave(){
		$("#gmvTarget").addClass("hidden");
		cancleEditMonthTarget();
	
		var jsonMonth = {
				"shopId" : shopId,
				"y" : selected
			};
			var url = "<%=basePath%>/target/queryGoalMonthSale.do";
			if ($("#last").attr("class")=='active') {
				url = "<%=basePath%>/target/queryGoalMonthAlip.do";
			}
			getDate(url, jsonMonth)
		
	}
	function ClicksaveMonthTarget(){
	
		saveMonthTarget();
	
		if($("#last").attr("class")!='active'){
			
			$("#gmvTarget").addClass("hidden");
			initDailyTarget();
		differenc();
	}

	}
	function cancleEditMonthTarget(){
		$(".targetInput").attr("readonly","readonly")
		$(".targetInput").css("border","none")
		$("#editMonthTarget").removeClass("hidden");
		$("#saveMonthTarget").addClass("hidden");
		$("#cancelMonthTarget").addClass("hidden");
		if($("#last").attr("class")!='active')
		$("#btn_imput").removeClass("hidden");
	}
	
	
	/* 保存月目标 */
	function saveMonthTarget() {
		$(".targetInput").toNumber();
		var isSingleLessInteger=true;
		$(".targetInput").each(function(){
			if(!isSignLessInteger($(this).val())){
				isSingleLessInteger=false;
				$(this).css("border-color","red");
			}
		});
		if(!isSingleLessInteger){
			return;
		}
		
		var url;
		var target = {
		
			id : $("#monthTargetId").val(),
			shopId:shopId,
			y:selected,
			m01 : Number($("#JanuaryTarget").val())*100,
			m02 : Number($("#FebruaryTarget").val())*100,
			m03 : Number($("#MarchTarget").val())*100,
			m04 : Number($("#AprilTarget").val())*100,
			m05 : Number($("#MayTarget").val())*100,
			m06 : Number($("#JuneTarget").val())*100,
			m07 : Number($("#JulyTarget").val())*100,
			m08 : Number($("#AugustTarget").val())*100,
			m09 : Number($("#SeptemberTarget").val())*100,
			m10 : Number($("#OctoberTarget").val())*100,
			m11 : Number($("#NovemberTarget").val())*100,
			m12 : Number($("#DecemberTarget").val())*100
		};
		
		if ($("#last").attr("class")=='active') {
			url = "<%=basePath%>/target/insertOrUpdateGoalMonthAlip.do";
		} else {
			url = "<%=basePath%>/target/insertOrupdateGoalMonthSale.do";
		}
		$.ajax({
			url : url,
			type : "POST",
			data : target,
			dataType : 'JSON',
			success : function(data, status) {
				toastr.success(data.data);
				var jsonMonth = {
						"shopId" : shopId,
						"y" : selected
					};
					var url = "<%=basePath%>/target/queryGoalMonthSale.do";
					if ($("#last").attr("class")=='active') {
						url = "<%=basePath%>/target/queryGoalMonthAlip.do";
					}
					getDate(url, jsonMonth);
			}
		}); 
		
		$(".targetInput").formatCurrency();
		/*取消按钮操作  */
		cancleEditMonthTarget();
	}
	//月目标添加触发事件
	function monthTargetAddChange(){
	$(".targetInput").each(function (index,element){
		$(element).unbind("blur").bind("blur",function(){
			$("#targetAmount").toNumber();
			if(isSignLessInteger($(this).val())){
				$("#monthTarget").val($(element).val());
				if ($("#gmvTarget").css("display")!='none'){
					differenc();
				}
				$(this).css("border-color","#d3d3d3");
			}else{
					$(this).css("border-color","red");
					toastr.success("The format is incorrect.");
			}
			if(allIsSingLessInteger(targetInput)){
				$("#targetAmount").val(monthTargerAmount());
			
			}
			$("#targetAmount").formatCurrency();
		});
		$(element).unbind("focus").bind("focus",function(){								
			$(element).toNumber();
		}); 
	});
}
//日目标事件
function dailySummateAddChange() {
	//绑定事件
	$(".dailyPayment").each(function(e, t) {
		$(this).unbind("blur").bind("blur", function() {	
		/* 如果是正整数改变边框 */
			if(isSignLessInteger($(this).val())){
				$(this).css("border","1px solid rgba(0, 0, 0, 0)");
			}else{
				$(this).css("border-color","red")
				toastr.success("The format is incorrect");
			}
		if(allIsSingLessInteger("dailyPayment")){
			differenc();	
		}
		});
		$(this).unbind("mouseout").bind("mouseout", function() {
			if(isSignLessInteger($(this).val()))
				$(this).css("border","1px solid rgba(0, 0, 0, 0)")

		});
		$(this).unbind("mouseover").bind("mouseover", function() {
			if(isSignLessInteger($(this).val()))
			$(this).css("border","1px solid #d3d3d3")
		});
	})
}
	//日目标合计
	function dailysummate() {
		var moment = $('#calendar').fullCalendar('getDate');
		var daily = moment.format("yyyy-MM");
		var a = 0;

		$("div[id^='" + daily + "']").each(function(e, t) {
			$(this).children("input:last-child").css("display", "block");
			a = Number($(this).children("input:last-child").val()) + a;
		})
		return a;
	}
	//差值计算日目标合计和月目标
	function differenc() {
		$("#dailyTargerCount").val(dailysummate());
	  var monthTarget=$("#monthTarget");
	  monthTarget.toNumber();
		var a=Number(Number(monthTarget.val()-$("#dailyTargerCount").val()));
		if(a>0){
			$("#differencValue").css("color","green")
			$("#differencValue").css("border-color","red");
			$("#"+monthTarget.attr("month")).css("color","red");
		}else if(a<0){
			$("#differencValue").css("color","red")
			$("#differencValue").css("border-color","red");
			$("#"+monthTarget.attr("month")).css("color","red");
		}else{
			$("#differencValue").css("color","#464646");
			$("#differencValue").css("border-color","#d3d3d3");
			$("#"+monthTarget.attr("month")).css("color","#464646");
		}
		$("#monthYeardate").text($('#calendar').fullCalendar('getDate').format("yyyy-MM"));
		
		$("#differencValue").val(a);
	}
	//保存日目标
	function saveDailyTarget(){
	
		var dailyTargets=[];
		var moment = $('#calendar').fullCalendar('getDate');
		var daily = moment.format("yyyy-MM");
		var dailyTarget={};
		var isInteger=true;
		$("div[id^='" + daily + "']").each(function(e, t) {
			if(!isSignLessInteger($(this).children("input:last-child").val())){
				$(this).children("input:last-child").css("border-color","red")
				isInteger=false;
			}
				
			dailyTarget={
					id:$(this).children("input:first-child").val()==""?null:$(this).children("input:first-child").val(),
					date:$(this).attr("id"),
					payment:Number($(this).children("input:last-child").val())*100,
					shopId:shopId,
					startDate:"",
					endDate:""
			};
			dailyTargets.push(dailyTarget);
		});
		if(isInteger){
			differenc();
			if(Number($("#differencValue").val())!=0){
				toastr.success("oals need to be equal");
			}else if(Number($("#monthTarget").val())<=0){
				toastr.success("Goals need to be greater than zero");
			}
			else{
				var url="<%=basePath%>/target/insertOrUpdateDailyTargetByBatch.do";
				json={"dailyTargets":JSON.stringify(dailyTargets)};
			 	$.ajax({
					url : url,
					type : "POST",
					data :json,
					dataType : 'JSON',
					success : function(data, status) {
						toastr.success(data.data);
					},
					error : function() {
					}
				});
			}
	 	}else{
	 		toastr.success("The format is incorrect");
	 	}
	}
	function monthTargerAmount(){
		//年目标	
		var a=0;	
		$(".monthUnit").each(function (){
			$(this).children("input:last-child").toNumber();
		a=a+Number($(this).children("input:last-child").val());
		$(this).children("input:last-child").formatCurrency();
		});
		return a;
	}

	//上传
	  function inportInit(ctrlName, uploadUrl) {
          var control = $('#' + ctrlName);

          //初始化上传控件的样式
          control.fileinput({
              uploadUrl: uploadUrl, //上传的地址
              allowedFileExtensions: ['xlsx','xls'],//接收的文件后缀
              showPreview :true,//是否显示预览
              showUpload: true, //是否显示上传按钮
              showCaption: true,//是否显示标题
              browseClass: "btn btn-primary", //按钮样式     
              dropZoneEnabled: false,//是否显示拖拽区域
              //minImageWidth: 50, //图片的最小宽度
              //minImageHeight: 50,//图片的最小高度
              //maxImageWidth: 1000,//图片的最大宽度
              //maxImageHeight: 1000,//图片的最大高度
              //maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
              //minFileCount: 0,
              maxFileCount: 5, //表示允许同时上传的最大文件个数
              enctype: 'multipart/form-data',
              validateInitialCount:true,
              previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
              previewFileIconSettings: {
            	    'doc': '<i class="fa fa-file-word-o text-primary"></i>',
            	    'xls': '<i class="fa fa-file-excel-o text-success"></i>',
            	    'ppt': '<i class="fa fa-file-powerpoint-o text-danger"></i>',
            	    'jpg': '<i class="fa fa-file-photo-o text-warning"></i>',
            	    'pdf': '<i class="fa fa-file-pdf-o text-danger"></i>',
            	    'zip': '<i class="fa fa-file-archive-o text-muted"></i>',
            	},
              msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
          });
          $("#div_startimport").show();

          //导入文件上传完成之后的事件
      }
	function initDailyTarget(){
		var jsonMonth = {
				"shopId" : shopId,
				"y" : selected
			};
			var url = "<%=basePath%>/target/queryGoalMonthSale.do";
			getDate(url, jsonMonth);
			$('#calendar').fullCalendar("gotoDate",new Date("1990-02-21"));
			$('#calendar').fullCalendar("gotoDate",
					new Date(selected + "-01-01"));

			var jsonDaily = {
				"shopId" : shopId,
				"startDate" : selected + "-01-01",
				"endDate" : selected + "01-31",
			}
			getDailyDate(jsonDaily)
			$("#monthTarget").val($("#JanuaryTarget").val());
			$("#monthTarget").attr("month","JanuaryTarget");
			dailySummateAddChange();
			if(showSchedule){
				$(".addPopover").removeClass("hidden")
				}
				else{
					$(".addPopover").addClass("hidden");
				}
	}
	//月份点击效果
	function monthTargetClick(e) {
		var startDate = selected
				+ "-" + e.attr('value') + "-01";
		var endDate = selected
				+ "-" + e.attr('value') + "-31";
		if ($("#gmvTarget").css("display") != "none") {
			$('#calendar').fullCalendar("gotoDate",
					new Date(startDate));
			var json = {
				"shopId" : shopId,
				"startDate" : startDate,
				"endDate" : endDate,
				"storename" : ""
			}
			getDailyDate(json);
			//获取最后一个子节点传递差值计算
			$("#monthTarget").val(
					e
							.children("input:last-child")
							.val());
			$("#monthTarget").attr("month",e
					.children("input:last-child").attr("id"));
			//添加日计划点击改变事件
			dailySummateAddChange();
		}
	}
	/**是否是正整数
	 * @returns {String}
	 * 
	 */
	function isSignLessInteger(a){
			if(a==null||a=='')
				return true;
	var r = /^\+?[0-9][0-9]*$/;

		return flag=r.test(a);
	}
	 /* 计算所有的月目标都符合正整数 */
	 function allIsSingLessInteger(a){
		 var state=true;
		$("."+a).each(function(){
			 if(!isSignLessInteger($(this).val()))
				 state=false;
		 });
		 return state;
	 }
	 /*  便签相关*/
	 //添加事件
  function addSchedule(schedule){
		 if(schedule>=0){
	  		$.ajax({
          url: "<%=basePath%>dailySchedule/queryDailySchedule.do",
          type : "POST",
          dataType: 'JSON',
          data: {id:schedule},
          success: function(doc) {
        	  $("#schedoleId").val(schedule);
        	  $("#schedoleDesc").val(doc.data[0].scheduleDesc);
 			 $("#title").val(doc.data[0].title);
 			 $("#queryStartDate").val(doc.data[0].startDate);
          	}
      		});
		 }else{
			 $("#schedoleDesc").val("");
			 $("#title").val("");
			 $("#queryStartDate").val(""); 
		 } 
	  $("#myModal").modal().on("shown.bs.modal", function () {});
  }
  function addCheckSchedule(date){
	 	 $("#schedoleDesc").val("");
		 $("#title").val("");
		 $("#queryStartDate").val(""); 
		 $("#schedoleId").val("");
		 if(date!=null){
	  		$.ajax({
	       url: "<%=basePath%>dailySchedule/queryDailySchedule.do",
	       type : "POST",
	       dataType: 'JSON',
	       data: {startDate:date},
	       success: function(doc) {
	     	  $("#schedoleId").val(doc.data[0].id);
	     	  $("#schedoleDesc").val(doc.data[0].scheduleDesc);
				 $("#title").val(doc.data[0].title);
				 $("#queryStartDate").val(doc.data[0].startDate);
	       	}
   		});
		 } 
	  $("#myModal").modal().on("shown.bs.modal", function () {});
}
//保存便签
  function saveSchedule(){
	  $.ajax({
          url: "<%=basePath%>dailySchedule/insertDailySchedule.do",
          type : "POST",
          dataType: 'JSON',
          data: {
        	    id:$("#schedoleId").val(),
        	    scheduleDesc:$("#schedoleDesc").val(),
          		title: $("#title").val(),
          		startDate:$("#queryStartDate").val(),
          },
          success: function(doc) {
         	 toastr.success(doc.data);
         	var nowDate = $('#calendar').fullCalendar('getDate');
    		initDailyTarget();
    		$('#calendar').fullCalendar("gotoDate", new Date("1990-02-21"));
    		$('#calendar').fullCalendar("gotoDate", nowDate);
          }
      });
  }
  //删除便签
  function delectSchedule(scheduleId){
	  $.ajax({
          url: "<%=basePath%>dailySchedule/deleteDailySchedule.do",
			type : "POST",
			dataType : 'JSON',
			data : {
				id : scheduleId,
			},
			success : function(doc) {
				toastr.success(doc.data);
				var nowDate = $('#calendar').fullCalendar('getDate');
				initDailyTarget();
				$('#calendar').fullCalendar("gotoDate", new Date("1990-02-21"));
				$('#calendar').fullCalendar("gotoDate", nowDate);
			}
		});
	  
	}
	//显示便签
	function changeShow() {
		var nowDate = $('#calendar').fullCalendar('getDate');
		showSchedule = showSchedule ? false : true;
		initDailyTarget();

		$('#calendar').fullCalendar("gotoDate", new Date("1990-02-21"));
		$('#calendar').fullCalendar("gotoDate", nowDate);
	}
	//增加便签
	function changeShowSchedule() {
		showScheduleAdd = showScheduleAdd ? false : true;
	}
</script>

</html>
