<%@page import="org.apache.shiro.subject.Subject"%>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Subject sub=SecurityUtils.getSubject();
%>
<html>
<head>
<base href="<%=basePath%>">

</head>
<link href="<%=basePath%>/css/bootstrap/bootstrap-editable.css"
	rel="stylesheet" />

<link rel="stylesheet"
	href="<%=basePath%>/css/bootstrap/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="<%=basePath%>/css/fileinput.min.css">
<link rel="stylesheet"
	href="<%=basePath%>/css/bootstrap/bootstrap.min.css">
<link href="<%=basePath%>/css/bootstrap/bootstrap-table.css"
	rel="stylesheet" type="text/css">
	<jsp:include page="/page/left.do"></jsp:include>
	<link rel="stylesheet" href="<%=basePath%>/css/bi.css">
	<link rel="stylesheet" href="<%=basePath%>/css/localRightTable.css">
	<style>
	.table {
	table-layout: inherit
	}
	</style>
</head>

<body>
	<div class="page">
		<!-- 查询列表 -->
		<form class="queryCriteria" action="" method="post">
			<div class="queryCriteriaDiv">
				<div class="dateRemind">
					<span class="remind">date：</span>

				</div>
				<div class="date">

					<div class="dateView" id="s3">
						<input id="queryStartDate" class="begin form-control" name="begin" readonly="readonly"
							style="float: left" />
						<div 
							style="position: relative; width: 36px; top: 20%; float: left;  text-align: center">
							to</div>
						<input  id="queryEndDate" class="end form-control" name="end" readonly="readonly"
							style="float: left" />
					</div>
				</div>
				
			</div>
			<div class="btn-group" >
						<button id="query" type="button" class="queryBtn btn btn-primary">
							<span class="glyphicon glyphicon-search" aria-hidden="true"></span>Search
						</button>
						<button id="btn_delete" type="button" class="deleteBtn btn btn-primary">
							<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>DELETE
						</button>
						<button id="btn_imput" type="button" class="imputBtn btn btn-primary">
							<span class="glyphicon  glyphicon-import" aria-hidden="true"></span>IMPORT
							DATA
						</button>
					</div>
		</form>

		<!-- view列表 -->
		<div class="view" style="background: white;">
		<div class="tableBorder">
				<table class="table" id="cusTable" data-pagination="true" data-showColumns="true"
					data-checkbox="true">
				</table>
			</div>
	</div>






	<!-- <div class="localRightpage">
		<div class="localRightview">
			<div>
				<div class="queryCondition">
					Date: <input class=" form-control queryStartDate "
						id="queryStartDate" name=" startDate" readonly="readonly" />
					to
					<input class="form-control queryEndDate " id="queryEndDate"
						name="endDate" readonly="readonly" />
					<div class="btn-group" >
						<button id="query" type="button" class="queryBtn btn btn-primary">
							<span class="glyphicon glyphicon-search" aria-hidden="true"></span>Search
						</button>
						<button id="btn_delete" type="button" class="deleteBtn btn btn-primary">
							<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>DELETE
						</button>
						<button id="btn_imput" type="button" class="imputBtn btn btn-primary">
							<span class="glyphicon  glyphicon-import" aria-hidden="true"></span>IMPORT
							DATA
						</button>

					</div>
				</div>
			</div>
			<div class="tableBorder">
				<table class="table" id="cusTable" data-pagination="true" data-showColumns="true"
					data-checkbox="true">
				</table>
			</div>
		</div>
	</div> -->
	<!-- 信息删除确认 -->
	<div class="modal fade" id="delcfmModel">
		<div class="modal-dialog">
			<div class="modal-content message_align">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">Prompt Message</h4>
				</div>
				<div class="modal-body">
					<p>Are you sure you want to delete?</p>
				</div>
				<div class="modal-footer">
					<input type="hidden" id="url" />
					<button type="button" class="btn btn-primary" data-dismiss="modal">Cancel</button>
					<a onclick="deleteByArray()" class="btn btn-success"
						data-dismiss="modal">Confirm</a>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	</div>
	<!-- /.modal -->
	<form>
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
							href="<%=basePath%>download/downloadTemplate.do?fileName=pcFlow"
							class="form-control" style="border: none;">Download the
							import template <button id="btn_imput" type="button" class="imputBtn btn btn-primary" style="float: right;">
							<span class="glyphicon  glyphicon-import" aria-hidden="true" ></span>
						</button></a> <input type="file" name="txt_file" id="txt_file"
							multiple class="file-loading" />
					</div>
				</div>
			</div>
		</div>
	</form>

</body>
<script src="<%=basePath%>/js/jquery-1.8.3.min.js"></script>
<script src="<%=basePath%>/js/bootstrap/bootstrap.min.js"></script>
<script src="<%=basePath%>/js/bootstrap/bootstrap-table.js"></script>
<script src="<%=basePath%>/js/bootstrap/bootstrap-editable.js"></script>
<script src="<%=basePath%>/js/bootstrap/bootstrap-table-editable.js"></script>
<script src="<%=basePath%>/js/bootstrap/bootstrap-datetimepicker.js"></script>
<script src="<%=basePath%>/js/fileinput.min.js"></script>
<script src="<%=basePath%>/js/knockout-3.4.2.js"></script>
<script src="<%=basePath%>/js/bootstrap/bootstrap-typeahead.js"></script>
<script src="<%=basePath%>/js/localTableTitle.js"></script>
<script>
      /*   $(document).ready(function(){
        	//初始化表格
        	initTable();
        	//日期初始化
        	initDate();
        	//初始化增删查改
        	operate.operateInit();
        	//初始化上传文件 
             inportInit("txt_file", "target/uploadDailyTarget.do");
		
        	initSelect();

        }); */
        var tableTitleName="fansMonthFeatures";
        var role=<%=sub.isPermitted("all")||sub.isPermitted("delete_history")%>;
        function initTable(tableTitle) {
        	console.log(tableTitle);
            //先销毁表格
            $('#cusTable').bootstrapTable('destroy');
            //初始化表格,动态从服务器加载数据
            $("#cusTable").bootstrapTable({
                 method: "post",  //使用get请求到服务器获取数据
                contentType:"application/x-www-form-urlencoded; charset=UTF-8",
                url: "<%=basePath%>fansMonthFeature/queryFansMonthFeatureByPage.do", //获取数据的Servlet地址
                striped: true,  //表格显示条纹
                pagination: true, //启动分页
                pageSize: 15,  //每页显示的记录数
                pageNumber:1, //当前第几页
                pageList: [2,10, 25, 50, 100],  //记录数可选列表
                search: false,  //是否启用查询
                showColumns: false,  //显示下拉框勾选要显示的列
                showRefresh: false,  //显示刷新按钮
                showExport: false,
                paginationPreText:'PreText',
                paginationNextText:'NextText',
                paginationDetailHAlign:'left',
                sidePagination: "server", //表示服务端请求
                exportDataType: "all",
                //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
                //设置为limit可以获取limit, offset, search, sort, order
                queryParamsType : "undefined",
                columns:tableTitle,
                onEditableSave: function (field, row, oldValue, $el) {
                    $.ajax({
                        type: "post",
                        url: "<%=basePath%>fansDaily/updateFansDaily.do",
                        data: row,
                        dataType: 'JSON',
                        success: function (data, status) {
                            if (status == "success") {
                                alert('提交数据成功');
                                
                            }
                        },
                        error: function () {
                            alert('编辑失败');
                        },
                        complete: function () {

                        }
                    });
                    },
                queryParams: function queryParams(param) {   //设置查询参数
                     param = {
                        pageNum:this.pageNumber,
                        pageSize:this.pageSize,
                        sortName:this.sortName,
                        sortOrder:this.sortOrder,
                        startDate:$("#queryStartDate").val(),
                        endDate:$("#queryEndDate").val()
                    };
                    return param;
                    
                },
                onLoadError: function(){  //加载失败时执行
                	showTip("请稍候",500);
                },
                onLoadSuccess:function(){
                	$(".th-inner").each(function (index,element){
            			$(this).attr("data-toggle","tooltip");
            			$(this).attr("data-placement","top");
            			$(this).attr("title",$(this).text());
            			
            		});
                	$("[data-toggle='tooltip']").tooltip();
                }
            });
            $('#cusTable').bootstrapTable('hideColumn', 'id');
        }
      var operate = {
          //初始化按钮事件
          operateInit: function () {
              this.operateDelete();
              this.operateImput();
              this.operateExput();
              this.DepartmentModel = {
            				  };
              ko.applyBindings(this.myViewModel, document.getElementById("tb_dept"));

          },
          //删除
          operateDelete: function () {
              $('#btn_delete').on("click", function () {
            	  var arrselectedData =  $('#cusTable').bootstrapTable("getAllSelections");
            	
            	  if (arrselectedData.length <= 0) {
            		  showTip("unselected",1000);
                      return ;
                  }
            	  $("#delcfmModel").modal().on("shown.bs.modal", function () {});
       
              });
          },
        //导入
          operateImput: function () {
              $('#btn_imput').on("click", function () {
            	  $("#downloadTemplate").attr("href","<%=basePath%>download/downloadTemplate.do?fileName="+tableTitleName+"Template"); 
            	  $("#myModalImoprt").modal().on("shown.bs.modal", function () {
 
                  }).on('hidden.bs.modal', function () {
                      //关闭弹出框的时候清除绑定(这个清空包括清空绑定和清空注册事件)
                  });
              });
          },
        //导出
          operateExput: function () {
              $('#btn_export').on("click", function () {
            	  $("#form2").submit();
              });
          },

      }
    //日期插件初始化
    
    var options = {
    		  pickDate:true,
    		  weekStart : 1,
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView :3,
			format : 'yyyy-mm-dd',
			forceParse : 0,
			clearBtn:1,
		};
      function initDate(){
    	  $("#queryStartDate").datetimepicker(options);
    	  $("#queryEndDate").datetimepicker(options);
    	     var  end = new Date();
    	  	var day = end.getDate();
    	  	var year = end.getFullYear();
    	  	var month = end.getMonth();
    	    }
      /* 日期开始时间结束时间限制 */
      $("#queryStartDate").datetimepicker(options).on('changeDate',
  			function(ev) {
    	  $('#queryEndDate').datetimepicker('setStartDate',ev.date);
		});
      $("#queryEndDate").datetimepicker(options).on('changeDate',
  			function(ev) {
    	  $("#queryStartDate").datetimepicker('setEndDate',ev.date);
		});
    //初始化fileinput
 
      function inportInit(ctrlName, uploadUrl) {
          var control = $('#' + ctrlName);

          //初始化上传控件的样式
          control.fileinput({
              language: 'zh', //设置语言
              uploadUrl: uploadUrl, //上传的地址
              allowedFileExtensions: ['xlsx','xls'],//接收的文件后缀
              showPreview :true,//是否显示预览
              showUpload: true, //是否显示上传按钮
              showCaption: true,//是否显示标题
              dropZoneEnabled: false,//是否显示拖拽区域
              browseClass: "btn btn-primary", //按钮样式     
              //dropZoneEnabled: false,//是否显示拖拽区域
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
                msgFilesTooMany: "Select the number of files to upload ({n}) over the maximum allowed value {m}！",
          });
          $("#div_startimport").show();

          //导入文件上传完成之后的事件
      }
    
    	  $("#txt_file").on("fileuploaded", function (event, data, previewId, index) {
 			 $("#uploadRemark").append("<p>"+data.filenames.pop()+":"+data.response.data+"</p>");
 			getTitle(tableTitleName,role);
    	  });
      $(function() {
    	//初始化左侧菜单栏
    	   $("#importData").attr("class", "nav nav-second-level in"); 
    	  $("#leftfansData").attr("class", "nav nav-third-level in"); 
    	  $("#leftfansMonthFeatures").css("background","#578EBE");
    	getTitle(tableTitleName,role);
    	//初始化表格
      	//日期初始化
      	initDate();
      	//初始化增删查改
      	operate.operateInit();
      	//初始化上传文件
           inportInit("txt_file", "<%=basePath%>fansMonthFeature/uploadFansMonthFeatures.do");
  	//	pandora潘多拉官方旗舰店
  		var datetype = $("input[name='datetype']:checked").val();
  		$("input[name='datetype']:last").attr("checked", true);
  		//	json.series[1].tooltip.valueSuffix = 'k';
  		//		json.series[2].tooltip.valueSuffix = 'k';

  		$("#query").click(function() {
  		
  	    	getTitle(tableTitleName,role);
  		});

  		$.fn.typeahead.Constructor.prototype.blur = function() {
  			var that = this;
  			setTimeout(function() {
  				that.hide()
  			}, 250);

  		};

  		/* $(".selectstore").on('input', function() {
  			var name = this.value;
  			var storename = [];
  			storename.push(this.value);
  			names = storename;
  		}); */

  		$('.selectstore').typeahead({
  			source : function(query, process) {
  				return names;
  			}
  		});
  		$('.selectstore2').typeahead({
  			source : function(query, process) {
  				return names;
  			}
  		});

  	});
      function test(){
    	  $('#cusTable').bootstrapTable('selectPage',2);
      }
      function deleteByArray(){
    	  var idArray=new Array();
    	  var arrselectedData =  $('#cusTable').bootstrapTable("getAllSelections");
    	  jQuery.each(arrselectedData, function(i, val) {  

    		    idArray[i]=val.id;  

    		});
    	var a=  {"idArray":idArray};
          $.ajax({
              url: "<%=basePath%>fansMonthFeature/deleteFansMonthFeaturesByBatch.do",
              type: "post",
              data:a,
              success: function (data, status) {
            	  showTip(JSON.parse(data).data, 1000)
            	  $('#cusTable').bootstrapTable('remove', {field: 'id', values: idArray})
            		if($('#cusTable').bootstrapTable('getData').length==0)
            	    	getTitle(tableTitleName,role);
              }
          });
    	  
      }
 
</script>
</html>
