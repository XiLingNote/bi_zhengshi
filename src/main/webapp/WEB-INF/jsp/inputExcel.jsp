<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>
<html>
<head>
<base href="<%=basePath%>">

</head>
<jsp:include page="/page/left.do"></jsp:include>
<link href="/css/bootstrap/bootstrap-editable.css" rel="stylesheet" />
<link rel="stylesheet" href="/css/bootstrap/bootstrap.min.css">
<link href="/css/bootstrap/bootstrap-table.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet"
	href="/css/bootstrap/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet"
	href="/css/fileinput.min.css">
	<style>

	</style>
</head>

<body>
	<div style="height: 50px"></div>
	<div style="float: right; height: 100px" hidden="true">
		<form method="POST" enctype="multipart/form-data" id="form1"
			action="uploadExcel/upload.do">
			<table>
				<tr>
					<td>上传日目标Excel文件</td>

				</tr>
				<tr>
					<td><input id="upfile" type="file" class="btn btn-default"
						name="upfile"></td>
				</tr>
				<tr>
					<td hidden="true"><input type="submit" value="提交"
						onclick="return checkData()"></td>
					<td><input type="button" class="btn btn-default"
						value="保存导入数据" id="btn" name="btn" onclick="ajaxClick()"></td>
				</tr>
			</table>
		</form>

		<form method="POST" enctype="multipart/form-data" id="form2"
			action="target/exportDailyTarget.do">
			<table>
				<tr>
					<td><input type="submit" class="btn btn-default"
						value="导出Excel" onclick="sub()"></td>
				</tr>
			</table>
		</form>
	</div>
<!--  bootsrtrap上传文件-->


	

	
	<div style="margin-left: 12%;margin-right:9%">
	<div style="height: 120px">
	<div>
		<div id="s1" style="position: absolute; top: 10%; left: 28%; width: 30%">
				<input id="queryStartDate" name="startDate" style="height: 40px" readonly="readonly" />
				<input id="queryEndDate" name="endDate" style="height: 40px"  readonly="readonly" />

		</div>
		<div class="storename">
			<span> Store name ：</span>
		</div>
		
		<input type="button" id="go" value="查询" />
		
		<input class="selectstore" id ="queryStorename"/>
		
		
		</div>
	
	</div>
	
	<form style="background-color: red">
    <div class="modal fade" id="myModalImoprt" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content ">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">请选择Excel文件</h4>
                </div>
                <div class="modal-body">
                    <a href="target/downloadDailyTargetTemplate.do" class="form-control" style="border:none;">下载导入模板</a>
                    <input type="file" name="txt_file" id="txt_file" multiple class="file-loading" />
                </div></div>
        </div>
    </div>
</form>
	<div id="datetimeStart" hidden="true">
		<input size="16" type="text"  name='date'  readonly="readonly"
			class="form_datetime"> 
	</div>
	
		<div id="toolbar" class="btn-group">
			<button id="btn_add" type="button" class="btn btn-default">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
			</button>
			<button id="btn_edit" type="button" class="btn btn-default">
				<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
			</button>
			<button id="btn_delete" type="button" class="btn btn-default">
				<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
			</button>
			<button id="btn_imput" type="button" class="btn btn-default">
				<span class="glyphicon  glyphicon-import" aria-hidden="true"></span>导入数据
			</button>
			<button id="btn_export" type="button" class="btn btn-default">
				<span class="glyphicon glyphicon-export" aria-hidden="true"></span>导出数据
			</button>
		</div>
		<table class="table table-hover" id="cusTable" data-pagination="true"
			data-showColumns="true" data-checkbox="true">
		</table>
		
	</div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">操作</h4>
				</div>
				<div class="modal-body">

					<div class="form-group">
						<label for="txt_departmentname">店铺名称</label> <input type="text"
							name="txt_departmentname" data-bind="value:storename"
							class="form-control selectstore2" id="storename" placeholder="店铺名称">
					</div>
					<div class="form-group">
						<label for="txt_departmentlevel">日期</label>
						
							<input size="16" type="text" id='date' name='date' data-bind="value:date"  readonly="readonly"
								class="form-control"> 
						
					</div>
					<div class="form-group">
						<label for="txt_des">目标</label> <input type="text" name="txt_des"
							data-bind="value:payment"  class="form-control" id="payment"
							placeholder="目标">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭
					</button>
					<button type="button" id="btn_submit" class="btn btn-primary"
						data-dismiss="modal">
						<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存
					</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script src="/js/jquery-1.8.3.min.js"></script>
<script src="/js/jquery.form-3.51.0.js"></script>
<script src="/js/bootstrap/bootstrap.min.js"></script>
<script src="/js/bootstrap/bootstrap-table.js"></script>
<script src="/js/bootstrap/bootstrap-table-zh-CN.js"></script>
<script src="/js/bootstrap/bootstrap-editable.js"></script>
<script src="/js/bootstrap/bootstrap-table-editable.js"></script>
<script src="/js/bootstrap/bootstrap-datetimepicker.js"></script>
<script src="/js/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="/js/fileinput.min.js"></script>
<script src="/js/fileinput_locale_zh.js"></script>
<script src="/js/knockout-3.4.2.js"></script>
<script src="/js/bootstart-table-knockout.js"></script>
<script src="/js/bootstrap/bootstrap-typeahead.js"></script>

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
        function initTable() {
            //先销毁表格
            $('#cusTable').bootstrapTable('destroy');
            //初始化表格,动态从服务器加载数据
            $("#cusTable").bootstrapTable({
                 method: "post",  //使用get请求到服务器获取数据
                contentType:"application/x-www-form-urlencoded; charset=UTF-8",
                url: "/target/queryDailyTarget.do", //获取数据的Servlet地址
                striped: true,  //表格显示条纹
                pagination: true, //启动分页
                pageSize: 10,  //每页显示的记录数
                pageNumber:1, //当前第几页
                pageList: [10, 25, 50, 100],  //记录数可选列表
                search: false,  //是否启用查询
                showColumns: false,  //显示下拉框勾选要显示的列
                showRefresh: false,  //显示刷新按钮
                showExport: false,
                toolbar:"#toolbar",
                sortable:true,
                sidePagination: "server", //表示服务端请求
                exportDataType: "all",
                height : 550, // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
                //设置为limit可以获取limit, offset, search, sort, order
                queryParamsType : "undefined",
                columns:[
                    {
                        title:'全选',
                        field:'select',
                        //复选框
                        checkbox:true,
                        width:25,
                        align:'center',
                        valign:'middle'
                    },
                    {
                        title:'ID',
                        field:'id',
                        
                    },
                    {
                        title:'店铺',
                        field:'storename',
                        hidden:true,
                    },
                    {
                        title:'日期',
                        field:'date',
                        width:'20%'
                    },
                    {
                        title:'日期',
                        field:'date',
                        width:'20%'
                    },
                    {
                        title:'计划数',
                        field:'payment',
                        formatter:function(value,row,index){
                        	return value*100+"%";
                        },
                        editable:{
                            type: 'text',
                            validate: function (v) {
                                if (!v) return '目标不能为空';
                            }
                        }
                    }
                ],
                onEditableSave: function (field, row, oldValue, $el) {
                    $.ajax({
                        type: "post",
                        url: "/target/updateDailyTarget.do",
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
                        page:this.pageNumber,
                        limit:this.pageSize,
                        sortName:this.sortName,
                        sortOrder:this.sortOrder
                    };
                     param.storename= $("#queryStorename").val();
                     param.startDat= $("#queryStartDate").val();
                     param.endDate=$("#queryEndDate").val();
                     param.payment= $("#queryPayment").val();
                alert(JSON.stringify(param));
                    return param;
                },
                onLoadError: function(){  //加载失败时执行
                    alert("加载数据失败");
                }
            });
        }
     function ajaxClick() {  
   		 
   		 if(checkData()){  
                $('#form1').ajaxSubmit({    
                    url:'target/uploadDailyTarget.do',  
                    dataType: 'text',  
                    success: resutlMsg,  
                    error: errorMsg  
                });   
                function resutlMsg(msg){  
                    alert(msg);     
                    $("#upfile").val("");  
                }  
                function errorMsg(){   
                    alert("导入excel出错！");      
                }  
            }  
        } 
      //JS校验form表单信息  
        function checkData(){  
           var fileDir = $("#upfile").val();  
           var suffix = fileDir.substr(fileDir.lastIndexOf("."));  
           if("" == fileDir){  
               alert("选择需要导入的Excel文件！");  
               return false;  
           }  
           if(".xls" != suffix && ".xlsx" != suffix ){  
               alert("选择Excel格式的文件导入！");  
               return false;  
           }  
           return true;  
        }  
      function sub(){
    		alert();
    	  $("#form2").submit();
      }
      var operate = {
          //初始化按钮事件
          operateInit: function () {
              this.operateAdd();
              this.operateUpdate();
              this.operateDelete();
              this.operateImput();
              this.operateExput();
              this.DepartmentModel = {
            		  id: ko.observable(),
            		  storename: ko.observable(),
            		  date: ko.observable(),
            		  payment: ko.observable()
            				  };
              ko.applyBindings(this.myViewModel, document.getElementById("tb_dept"));

          },
          //新增
          operateAdd: function(){
              $('#btn_add').on("click", function () {
                  $("#myModal").modal().on("shown.bs.modal", function () {
                	  $("#storename").val("");
                	  $("#date").val("");
                	  operate.DepartmentModel = {
                    		  id: null,
                    		  storename: $("#storename").val(),
                    		  date: $("#date").val(),
                    		  payment: $("payment").val()
                    		  }
                	  
                      ko.applyBindings(operate.DepartmentModel, document.getElementById("myModal"));
                      operate.operateSave();
                  }).on('hidden.bs.modal', function () {
               
                      ko.cleanNode(document.getElementById("myModal"));
                      initDate();
                  });
              });
          },
          //编辑
          operateUpdate: function () {
              $('#btn_edit').on("click", function () {

            	  var arrselectedData =  $('#cusTable').bootstrapTable("getAllSelections");
            	  if (!operate.operateCheck(arrselectedData)) { return; }
            	  operate.DepartmentModel = {
                		  id: arrselectedData[0].id,
                		  storename: arrselectedData[0].storename,
                		  date: arrselectedData[0].date,
                		  payment: arrselectedData[0].payment
                				  };
                  $("#myModal").modal().on("shown.bs.modal", function () {
                      //将选中该行数据有数据Model通过Mapping组件转换为viewmodel
                      ko.applyBindings(operate.DepartmentModel, document.getElementById("myModal"));
                      operate.operateSave();
                  }).on('hidden.bs.modal', function () {
                      //关闭弹出框的时候清除绑定(这个清空包括清空绑定和清空注册事件)
                      ko.cleanNode(document.getElementById("myModal"));
                      initDate();
                  });
              });
          },
          //删除
          operateDelete: function () {
              $('#btn_delete').on("click", function () {
            	  var arrselectedData =  $('#cusTable').bootstrapTable("getAllSelections");
            	  var idArray=new Array();
            	  jQuery.each(arrselectedData, function(i, val) {  

            		    idArray[i]=val.id;  

            		});  
            	var a=  {"idArray":idArray};
                  alert(JSON.stringify(a));
                  $.ajax({
                      url: "/target/deleteDailyTargetByBatch.do",
                      type: "post",
                      data:a,
                      success: function (data, status) {
                          alert(data);
                          initTable();
                          //tableInit.myViewModel.refresh();
                      }
                  });
              });
          },
        //导入
          operateImput: function () {
              $('#btn_imput').on("click", function () {
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
          //保存数据
          operateSave: function () {
              $('#btn_submit').on("click", function () {
                  //取到当前的viewmodel
                  var oViewModel = operate.DepartmentModel;
                  //将Viewmodel转换为数据model
                  var funcName = oViewModel.id?"updateDailyTarget.do":"insertDailyTarget.do";
      
                  $.ajax({
                      url: "/target/"+funcName,
                      type: "post",
                      data: oViewModel,
                      success: function (data, status) {
                    	  alert(status);
                    	  initTable();
                      }
                  });
              });
          },
          //数据校验
          operateCheck:function(arr){
              if (arr.length <= 0) {
                  alert("请至少选择一行数据");
                  return false;
              }
              if (arr.length > 1) {
                  alert("只能编辑一行数据");
                  return false;
              }
              return true;
          }
      }
    //日期插件初始化
    var options = {
			weekStart : 1,
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView : 2,
			format : 'yyyy-mm-dd',
			forceParse : 0,
			todayButton:true
		};
      function initDate(){
    	  $("#datetimeStart>input[name = date ]").datetimepicker(options);
    	  $("#date").datetimepicker(options);
    	  $("#queryStartDate").datetimepicker(options);
    	  $("#queryEndDate").datetimepicker(options);

    	     var  end = new Date();
    	  	var day = end.getDate();
    	  	var year = end.getFullYear();
    	  	var month = end.getMonth();
    	  	var viewmonth =(month>8)?(month+1):"0"+(month+1);
    	  	$('#datetimeStart>input[name = date ]').val(year + "-" + viewmonth+ "-" + day);
    	    }

      $("#datetimeStart>input[name = date ]").datetimepicker(options).on('changeDate',
  			function(ev) {
		});
      $("#date").datetimepicker(options).on('changeDate',
    			function(ev) {
  		});
      $("#queryStartDate").datetimepicker(options).on('changeDate',
  			function(ev) {
		});
      $("#queryEndDate").datetimepicker(options).on('changeDate',
  			function(ev) {
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
              msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
          });
          $("#div_startimport").show();

          //导入文件上传完成之后的事件
      }
      $("#txt_file").on("fileuploaded", function (event, data, previewId, index) {
          $("#myModalImoprt").modal("hide");
          var data = data.response.lstOrderImport;
          if (data == undefined) {
              toastr.error('文件格式类型不正确');
              return;
          }
        
      });
      function autoCompletion(store) {
  		$.post("/store/name.do", function(data) {
  			var storename = eval('(' + data + ')');
  			var storenames = [];
  			$.each(storename, function(i, value) {
  				var name = decodeURI(value);
  				storenames.push(name);
  			});
  			names = storenames;
  		});
  	}
      $(function() {
    	//初始化表格
      	initTable();
      	//日期初始化
      	initDate();
      	//初始化增删查改
      	operate.operateInit();
      	//初始化上传文件
           inportInit("txt_file", "target/uploadDailyTarget.do");
		
    
  		$(".selectstore").val('pandora潘多拉官方旗舰店');
  	//	pandora潘多拉官方旗舰店
  		var datetype = $("input[name='datetype']:checked").val();
  		$("input[name='datetype']:last").attr("checked", true);
  		//	json.series[1].tooltip.valueSuffix = 'k';
  		//		json.series[2].tooltip.valueSuffix = 'k';
  		
  	
  		// 加载店铺名称
  		autoCompletion();

  		$("#go").click(function() {
  			var type = $("input[name='datetype']:checked").val();
  			var store = $('.selectstore').val();//选中的值
  			
  			initTable();
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
    </script>
</html>
