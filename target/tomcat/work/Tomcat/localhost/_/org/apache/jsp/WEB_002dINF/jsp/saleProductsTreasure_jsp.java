/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.37
 * Generated at: 2017-08-11 08:21:03 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.SecurityUtils;
import bi.baiqiu.pojo.User;
import java.util.*;

public final class saleProductsTreasure_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Subject sub=SecurityUtils.getSubject();


      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<base href=\"");
      out.print(basePath);
      out.write("\">\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<link href=\"");
      out.print(basePath);
      out.write("/css/bootstrap/bootstrap-editable.css\"\r\n");
      out.write("\trel=\"stylesheet\" />\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(basePath);
      out.write("/css/fileinput.min.css\">\r\n");
      out.write("<link rel=\"stylesheet\"\r\n");
      out.write("\thref=\"");
      out.print(basePath);
      out.write("/css/bootstrap/bootstrap.css\">\r\n");
      out.write("<link href=\"");
      out.print(basePath);
      out.write("/css/bootstrap/bootstrap-table.css\"\r\n");
      out.write("\trel=\"stylesheet\" type=\"text/css\">\r\n");
      out.write("\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/page/left.do", out, false);
      out.write("\r\n");
      out.write("\t\t<link rel=\"stylesheet\" href=\"");
      out.print(basePath);
      out.write("/css/bi.css\">\r\n");
      out.write("\t\r\n");
      out.write("\t<link rel=\"stylesheet\" href=\"");
      out.print(basePath);
      out.write("/css/localRightTable.css\">\r\n");
      out.write("\t\r\n");
      out.write("<link rel=\"stylesheet\"\r\n");
      out.write("\thref=\"");
      out.print(basePath);
      out.write("/css/bootstrap/bootstrap-datetimepicker.min.css\">\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("\t<div class=\"page\">\r\n");
      out.write("\t\t<!-- 查询列表 -->\r\n");
      out.write("\t\t<form class=\"queryCriteria\" action=\"\" method=\"post\">\r\n");
      out.write("\t\t\t<div class=\"queryCriteriaDiv\">\r\n");
      out.write("\t\t\t\t<div class=\"dateRemind\">\r\n");
      out.write("\t\t\t\t\t<span class=\"remind\">date：</span>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"date\">\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t<div class=\"dateView\" id=\"s3\">\r\n");
      out.write("\t\t\t\t\t\t<input id=\"queryStartDate\" class=\"begin form-control\" name=\"begin\" readonly=\"readonly\"\r\n");
      out.write("\t\t\t\t\t\t\tstyle=\"float: left\" />\r\n");
      out.write("\t\t\t\t\t\t<div \r\n");
      out.write("\t\t\t\t\t\t\tstyle=\"position: relative; width: 36px; top: 20%; float: left;  text-align: center\">\r\n");
      out.write("\t\t\t\t\t\t\tto</div>\r\n");
      out.write("\t\t\t\t\t\t<input  id=\"queryEndDate\" class=\"end form-control\" name=\"end\" readonly=\"readonly\"\r\n");
      out.write("\t\t\t\t\t\t\tstyle=\"float: left\" />\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"btn-group\" >\r\n");
      out.write("\t\t\t\t\t\t<button id=\"query\" type=\"button\" class=\"queryBtn btn btn-primary\">\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"glyphicon glyphicon-search\" aria-hidden=\"true\"></span>Search\r\n");
      out.write("\t\t\t\t\t\t</button>\r\n");
      out.write("\t\t\t\t\t\t<button id=\"btn_delete\" type=\"button\" class=\"deleteBtn btn btn-primary\">\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span>DELETE\r\n");
      out.write("\t\t\t\t\t\t</button>\r\n");
      out.write("\t\t\t\t\t\t<button id=\"btn_imput\" type=\"button\" class=\"imputBtn btn btn-primary\">\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"glyphicon  glyphicon-import\" aria-hidden=\"true\"></span>IMPORT\r\n");
      out.write("\t\t\t\t\t\t\tDATA\r\n");
      out.write("\t\t\t\t\t\t</button>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t</form>\r\n");
      out.write("\r\n");
      out.write("\t\t<!-- view列表 -->\r\n");
      out.write("\t\t<div class=\"view\" style=\"background: white;\">\r\n");
      out.write("\t\t<div class=\"tableBorder\">\r\n");
      out.write("\t\t\t\t<table class=\"table\" id=\"cusTable\" data-pagination=\"true\" data-showColumns=\"true\"\r\n");
      out.write("\t\t\t\t\tdata-checkbox=\"true\">\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<!-- 信息删除确认 -->\r\n");
      out.write("\t<div class=\"modal fade\" id=\"delcfmModel\">\r\n");
      out.write("\t\t<div class=\"modal-dialog\">\r\n");
      out.write("\t\t\t<div class=\"modal-content message_align\">\r\n");
      out.write("\t\t\t\t<div class=\"modal-header\">\r\n");
      out.write("\t\t\t\t\t<button type=\"button\" class=\"close\" data-dismiss=\"modal\"\r\n");
      out.write("\t\t\t\t\t\taria-label=\"Close\">\r\n");
      out.write("\t\t\t\t\t\t<span aria-hidden=\"true\">×</span>\r\n");
      out.write("\t\t\t\t\t</button>\r\n");
      out.write("\t\t\t\t\t<h4 class=\"modal-title\">Prompt Message</h4>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"modal-body\">\r\n");
      out.write("\t\t\t\t\t<p>Are you sure you want to delete?</p>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"modal-footer\">\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" id=\"url\" />\r\n");
      out.write("\t\t\t\t\t<button type=\"button\" class=\"btn btn-primary\" data-dismiss=\"modal\">Cancel</button>\r\n");
      out.write("\t\t\t\t\t<a onclick=\"deleteByArray()\" class=\"btn btn-success\"\r\n");
      out.write("\t\t\t\t\t\tdata-dismiss=\"modal\">Confirm</a>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<!-- /.modal-content -->\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<!-- /.modal-dialog -->\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<!-- /.modal -->\r\n");
      out.write("\t<form>\r\n");
      out.write("\t\t<div class=\"modal fade\" id=\"myModalImoprt\" tabindex=\"-1\" role=\"dialog\"\r\n");
      out.write("\t\t\taria-labelledby=\"myModalLabel\">\r\n");
      out.write("\t\t\t<div class=\"modal-dialog modal-lg\" role=\"document\">\r\n");
      out.write("\t\t\t\t<div class=\"modal-content \">\r\n");
      out.write("\t\t\t\t\t<div class=\"modal-header\">\r\n");
      out.write("\t\t\t\t\t\t<button type=\"button\" class=\"close\" data-dismiss=\"modal\"\r\n");
      out.write("\t\t\t\t\t\t\taria-label=\"Close\">\r\n");
      out.write("\t\t\t\t\t\t\t<span aria-hidden=\"true\">&times;</span>\r\n");
      out.write("\t\t\t\t\t\t</button>\r\n");
      out.write("\t\t\t\t\t\t<h4 class=\"modal-title\" id=\"myModalLabel\">Please select a\r\n");
      out.write("\t\t\t\t\t\t\tfile(Excel)</h4>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div id=\"uploadRemark\"></div>\r\n");
      out.write("\t\t\t\t\t<div class=\"modal-body\">\r\n");
      out.write("\t\t\t\t\t\t<a id=\"downloadTemplate\"\r\n");
      out.write("\t\t\t\t\t\t\thref=\"");
      out.print(basePath);
      out.write("download/downloadTemplate.do?fileName=pcFlow\"\r\n");
      out.write("\t\t\t\t\t\t\tclass=\"form-control\" style=\"border: none;\">Download the\r\n");
      out.write("\t\t\t\t\t\t\timport template <button id=\"btn_imput\" type=\"button\" class=\"imputBtn btn btn-primary\" style=\"float: right;\">\r\n");
      out.write("\t\t\t\t\t\t\t<span class=\"glyphicon  glyphicon-import\" aria-hidden=\"true\" ></span>\r\n");
      out.write("\t\t\t\t\t\t</button></a> <input type=\"file\" name=\"txt_file\" id=\"txt_file\"\r\n");
      out.write("\t\t\t\t\t\t\tmultiple class=\"file-loading\" />\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</form>\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("<script src=\"");
      out.print(basePath);
      out.write("/js/jquery-1.8.3.min.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(basePath);
      out.write("/js/bootstrap/bootstrap.min.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script src=\"");
      out.print(basePath);
      out.write("/js/bootstrap/bootstrap-table.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(basePath);
      out.write("/js/bootstrap/bootstrap-editable.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(basePath);
      out.write("/js/bootstrap/bootstrap-table-editable.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(basePath);
      out.write("/js/bootstrap/bootstrap-datetimepicker.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(basePath);
      out.write("/js/fileinput.min.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(basePath);
      out.write("/js/knockout-3.4.2.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(basePath);
      out.write("/js/bootstrap/bootstrap-typeahead.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(basePath);
      out.write("/js/localTableTitle.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script>\r\n");
      out.write("      /*   $(document).ready(function(){\r\n");
      out.write("        \t//初始化表格\r\n");
      out.write("        \tinitTable();\r\n");
      out.write("        \t//日期初始化\r\n");
      out.write("        \tinitDate();\r\n");
      out.write("        \t//初始化增删查改\r\n");
      out.write("        \toperate.operateInit();\r\n");
      out.write("        \t//初始化上传文件\r\n");
      out.write("             inportInit(\"txt_file\", \"target/uploadDailyTarget.do\");\r\n");
      out.write("\t\t\r\n");
      out.write("        \tinitSelect();\r\n");
      out.write("\r\n");
      out.write("        }); */\r\n");
      out.write("        var tableTitleName=\"saleProductsTreasure\";\r\n");
      out.write("        var role=");
      out.print(sub.isPermitted("all")||sub.isPermitted("delete_history"));
      out.write(";\r\n");
      out.write("\r\n");
      out.write("        function initTable(tableTitle) {\r\n");
      out.write("            //先销毁表格\r\n");
      out.write("            $('#cusTable').bootstrapTable('destroy');\r\n");
      out.write("            //初始化表格,动态从服务器加载数据\r\n");
      out.write("            $(\"#cusTable\").bootstrapTable({\r\n");
      out.write("                 method: \"post\",  //使用get请求到服务器获取数据\r\n");
      out.write("                contentType:\"application/x-www-form-urlencoded; charset=UTF-8\",\r\n");
      out.write("                url: \"");
      out.print(basePath);
      out.write("saleProductsTreasure/querySaleProductsTreasureByPage.do\", //获取数据的Servlet地址\r\n");
      out.write("                striped: true,  //表格显示条纹\r\n");
      out.write("                pagination: true, //启动分页\r\n");
      out.write("                pageSize: 15,  //每页显示的记录数\r\n");
      out.write("                pageNumber:1, //当前第几页\r\n");
      out.write("                pageList: [2,10, 25, 50, 100],  //记录数可选列表\r\n");
      out.write("                search: false,  //是否启用查询\r\n");
      out.write("                showColumns: false,  //显示下拉框勾选要显示的列\r\n");
      out.write("                showRefresh: false,  //显示刷新按钮\r\n");
      out.write("                showExport: false,\r\n");
      out.write("                paginationPreText:'PreText',\r\n");
      out.write("                paginationNextText:'NextText',\r\n");
      out.write("                paginationDetailHAlign:'left',\r\n");
      out.write("                sidePagination: \"server\", //表示服务端请求\r\n");
      out.write("                exportDataType: \"all\",\r\n");
      out.write("                //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder\r\n");
      out.write("                //设置为limit可以获取limit, offset, search, sort, order\r\n");
      out.write("                queryParamsType : \"undefined\",\r\n");
      out.write("                columns:tableTitle,\r\n");
      out.write("                onEditableSave: function (field, row, oldValue, $el) {\r\n");
      out.write("                    $.ajax({\r\n");
      out.write("                        type: \"post\",\r\n");
      out.write("                        url: \"");
      out.print(basePath);
      out.write("saleProductsTreasure/updateSaleProductsTreasure.do\",\r\n");
      out.write("                        data: row,\r\n");
      out.write("                        dataType: 'JSON',\r\n");
      out.write("                        success: function (data, status) {\r\n");
      out.write("                            if (status == \"success\") {\r\n");
      out.write("                                \r\n");
      out.write("                            }\r\n");
      out.write("                        },\r\n");
      out.write("                        error: function () {\r\n");
      out.write("                        },\r\n");
      out.write("                        complete: function () {\r\n");
      out.write("\r\n");
      out.write("                        }\r\n");
      out.write("                    });\r\n");
      out.write("                    },\r\n");
      out.write("                queryParams: function queryParams(param) {   //设置查询参数\r\n");
      out.write("                     param = {\r\n");
      out.write("                        pageNum:this.pageNumber,\r\n");
      out.write("                        pageSize:this.pageSize,\r\n");
      out.write("                        sortOrder:this.sortName?this.sortOrder:\"desc\",\r\n");
      out.write("                        sortName:this.sortName?this.sortName:\"date\",\r\n");
      out.write("                        startDate:$(\"#queryStartDate\").val(),\r\n");
      out.write("                        endDate:$(\"#queryEndDate\").val()\r\n");
      out.write("                    };\r\n");
      out.write("                    return param;\r\n");
      out.write("                    \r\n");
      out.write("                },\r\n");
      out.write("                onLoadError: function(){  //加载失败时执行\r\n");
      out.write("                \tshowTip(\"wait\",500);\r\n");
      out.write("                },\r\n");
      out.write("                onLoadSuccess:function(){\r\n");
      out.write("                \t$(\".th-inner\").each(function (index,element){\r\n");
      out.write("            \t\t\t$(this).attr(\"data-toggle\",\"tooltip\");\r\n");
      out.write("            \t\t\t$(this).attr(\"data-placement\",\"top\");\r\n");
      out.write("            \t\t\t$(this).attr(\"title\",$(this).text());\r\n");
      out.write("            \t\t\t\r\n");
      out.write("            \t\t});\r\n");
      out.write("                \t$(\"[data-toggle='tooltip']\").tooltip();\r\n");
      out.write("                }\r\n");
      out.write("            });\r\n");
      out.write("            $('#cusTable').bootstrapTable('hideColumn', 'id');\r\n");
      out.write("        }\r\n");
      out.write("      var operate = {\r\n");
      out.write("          //初始化按钮事件\r\n");
      out.write("          operateInit: function () {\r\n");
      out.write("              this.operateDelete();\r\n");
      out.write("              this.operateImput();\r\n");
      out.write("              this.operateExput();\r\n");
      out.write("              this.DepartmentModel = {\r\n");
      out.write("            \t\t\t\t  };\r\n");
      out.write("              ko.applyBindings(this.myViewModel, document.getElementById(\"tb_dept\"));\r\n");
      out.write("\r\n");
      out.write("          },\r\n");
      out.write("          //删除\r\n");
      out.write("          operateDelete: function () {\r\n");
      out.write("              $('#btn_delete').on(\"click\", function () {\r\n");
      out.write("            \t  var arrselectedData =  $('#cusTable').bootstrapTable(\"getAllSelections\");\r\n");
      out.write("            \t  if (arrselectedData.length <= 0) {\r\n");
      out.write("            \t\t  showTip(\"unselected\",1000);\r\n");
      out.write("                      return ;\r\n");
      out.write("                  }\r\n");
      out.write("            \t  $(\"#delcfmModel\").modal().on(\"shown.bs.modal\", function () {});\r\n");
      out.write("       \r\n");
      out.write("              });\r\n");
      out.write("          },\r\n");
      out.write("        //导入\r\n");
      out.write("          operateImput: function () {\r\n");
      out.write("              $('#btn_imput').on(\"click\", function () {\r\n");
      out.write("            \t  $(\"#downloadTemplate\").attr(\"href\",\"");
      out.print(basePath);
      out.write("download/downloadTemplate.do?fileName=\"+tableTitleName+\"Template\"); \r\n");
      out.write("            \t  $(\"#myModalImoprt\").modal().on(\"shown.bs.modal\", function () {\r\n");
      out.write(" \r\n");
      out.write("                  }).on('hidden.bs.modal', function () {\r\n");
      out.write("                      //关闭弹出框的时候清除绑定(这个清空包括清空绑定和清空注册事件)\r\n");
      out.write("                  });\r\n");
      out.write("              });\r\n");
      out.write("          },\r\n");
      out.write("        //导出\r\n");
      out.write("          operateExput: function () {\r\n");
      out.write("              $('#btn_export').on(\"click\", function () {\r\n");
      out.write("            \t  $(\"#form2\").submit();\r\n");
      out.write("              });\r\n");
      out.write("          },\r\n");
      out.write("\r\n");
      out.write("      }\r\n");
      out.write("    //日期插件初始化\r\n");
      out.write("    var options = {\r\n");
      out.write("\t\t\tweekStart : 1,\r\n");
      out.write("\t\t\ttodayBtn : 1,\r\n");
      out.write("\t\t\tautoclose : 1,\r\n");
      out.write("\t\t\tclearBtn:1,\r\n");
      out.write("\t\t\ttodayHighlight : 1,\r\n");
      out.write("\t\t\tstartView : 2,\r\n");
      out.write("\t\t\tminView : 2,\r\n");
      out.write("\t\t\tformat : 'yyyy-mm-dd',\r\n");
      out.write("\t\t\tforceParse : 0,\r\n");
      out.write("\t\t};\r\n");
      out.write("      function initDate(){\r\n");
      out.write("    \t  $(\"#queryStartDate\").datetimepicker(options);\r\n");
      out.write("    \t  $(\"#queryEndDate\").datetimepicker(options);\r\n");
      out.write("    \t     var  end = new Date();\r\n");
      out.write("    \t  \tvar day = end.getDate();\r\n");
      out.write("    \t  \tvar year = end.getFullYear();\r\n");
      out.write("    \t  \tvar month = end.getMonth();\r\n");
      out.write("    \t    }\r\n");
      out.write("      /* 日期开始时间结束时间限制 */\r\n");
      out.write("      $(\"#queryStartDate\").datetimepicker(options).on('changeDate',\r\n");
      out.write("  \t\t\tfunction(ev) {\r\n");
      out.write("    \t  $('#queryEndDate').datetimepicker('setStartDate',ev.date);\r\n");
      out.write("\t\t});\r\n");
      out.write("      $(\"#queryEndDate\").datetimepicker(options).on('changeDate',\r\n");
      out.write("  \t\t\tfunction(ev) {\r\n");
      out.write("    \t  $(\"#queryStartDate\").datetimepicker('setEndDate',ev.date);\r\n");
      out.write("\t\t});\r\n");
      out.write("    //初始化fileinput\r\n");
      out.write(" \r\n");
      out.write("      function inportInit(ctrlName, uploadUrl) {\r\n");
      out.write("          var control = $('#' + ctrlName);\r\n");
      out.write("\r\n");
      out.write("          //初始化上传控件的样式\r\n");
      out.write("          control.fileinput({\r\n");
      out.write("              language: 'zh', //设置语言\r\n");
      out.write("              uploadUrl: uploadUrl, //上传的地址\r\n");
      out.write("              allowedFileExtensions: ['xlsx','xls'],//接收的文件后缀\r\n");
      out.write("              showPreview :true,//是否显示预览\r\n");
      out.write("              showUpload: true, //是否显示上传按钮\r\n");
      out.write("              showCaption: true,//是否显示标题\r\n");
      out.write("              dropZoneEnabled: false,//是否显示拖拽区域\r\n");
      out.write("              browseClass: \"btn btn-primary\", //按钮样式     \r\n");
      out.write("              //dropZoneEnabled: false,//是否显示拖拽区域\r\n");
      out.write("              //minImageWidth: 50, //图片的最小宽度\r\n");
      out.write("              //minImageHeight: 50,//图片的最小高度\r\n");
      out.write("              //maxImageWidth: 1000,//图片的最大宽度\r\n");
      out.write("              //maxImageHeight: 1000,//图片的最大高度\r\n");
      out.write("              //maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小\r\n");
      out.write("              //minFileCount: 0,\r\n");
      out.write("              maxFileCount: 5, //表示允许同时上传的最大文件个数\r\n");
      out.write("              enctype: 'multipart/form-data',\r\n");
      out.write("              validateInitialCount:true,\r\n");
      out.write("              previewFileIcon: \"<i class='glyphicon glyphicon-king'></i>\",\r\n");
      out.write("              previewFileIconSettings: {\r\n");
      out.write("            \t    'doc': '<i class=\"fa fa-file-word-o text-primary\"></i>',\r\n");
      out.write("            \t    'xls': '<i class=\"fa fa-file-excel-o text-success\"></i>',\r\n");
      out.write("            \t    'ppt': '<i class=\"fa fa-file-powerpoint-o text-danger\"></i>',\r\n");
      out.write("            \t    'jpg': '<i class=\"fa fa-file-photo-o text-warning\"></i>',\r\n");
      out.write("            \t    'pdf': '<i class=\"fa fa-file-pdf-o text-danger\"></i>',\r\n");
      out.write("            \t    'zip': '<i class=\"fa fa-file-archive-o text-muted\"></i>',\r\n");
      out.write("            \t},\r\n");
      out.write("                msgFilesTooMany: \"Select the number of files to upload ({n}) over the maximum allowed value {m}！\",\r\n");
      out.write("          });\r\n");
      out.write("          $(\"#div_startimport\").show();\r\n");
      out.write("\r\n");
      out.write("          //导入文件上传完成之后的事件\r\n");
      out.write("      }\r\n");
      out.write("    \r\n");
      out.write("    \t  $(\"#txt_file\").on(\"fileuploaded\", function (event, data, previewId, index) {\r\n");
      out.write(" \t\t\t $(\"#uploadRemark\").append(\"<p>\"+data.filenames.pop()+\":\"+data.response.data+\"</p>\");\r\n");
      out.write(" \t\t\t getTitle(tableTitleName,role);\r\n");
      out.write("      });\r\n");
      out.write("      $(function() {\r\n");
      out.write("    \t  $(\"#importData\").attr(\"class\", \"nav nav-second-level in\"); \r\n");
      out.write("    \t  $(\"#leftPayData\").attr(\"class\", \"nav nav-third-level in\"); \r\n");
      out.write("    \t  $(\"#leftSaleProductsTreasure\").css(\"background\",\"#578EBE\");\r\n");
      out.write("    \tgetTitle(tableTitleName,role);\r\n");
      out.write("    \t//初始化表格\r\n");
      out.write("      \t//日期初始化\r\n");
      out.write("      \tinitDate();\r\n");
      out.write("      \t//初始化增删查改\r\n");
      out.write("      \toperate.operateInit();\r\n");
      out.write("      \t//初始化上传文件\r\n");
      out.write("           inportInit(\"txt_file\", \"");
      out.print(basePath);
      out.write("saleProductsTreasure/uploadSaleProductsTreasure.do\");\r\n");
      out.write("  \t//\tpandora潘多拉官方旗舰店\r\n");
      out.write("  \t\tvar datetype = $(\"input[name='datetype']:checked\").val();\r\n");
      out.write("  \t\t$(\"input[name='datetype']:last\").attr(\"checked\", true);\r\n");
      out.write("  \t\t//\tjson.series[1].tooltip.valueSuffix = 'k';\r\n");
      out.write("  \t\t//\t\tjson.series[2].tooltip.valueSuffix = 'k';\r\n");
      out.write("\r\n");
      out.write("  \t\t$(\"#query\").click(function() {\r\n");
      out.write("  \t\t\r\n");
      out.write("  \t    \tgetTitle(tableTitleName,role);\r\n");
      out.write("  \t\t});\r\n");
      out.write("\r\n");
      out.write("  \t\t$.fn.typeahead.Constructor.prototype.blur = function() {\r\n");
      out.write("  \t\t\tvar that = this;\r\n");
      out.write("  \t\t\tsetTimeout(function() {\r\n");
      out.write("  \t\t\t\tthat.hide()\r\n");
      out.write("  \t\t\t}, 250);\r\n");
      out.write("\r\n");
      out.write("  \t\t};\r\n");
      out.write("\r\n");
      out.write("  \t\t/* $(\".selectstore\").on('input', function() {\r\n");
      out.write("  \t\t\tvar name = this.value;\r\n");
      out.write("  \t\t\tvar storename = [];\r\n");
      out.write("  \t\t\tstorename.push(this.value);\r\n");
      out.write("  \t\t\tnames = storename;\r\n");
      out.write("  \t\t}); */\r\n");
      out.write("\r\n");
      out.write("  \t\t$('.selectstore').typeahead({\r\n");
      out.write("  \t\t\tsource : function(query, process) {\r\n");
      out.write("  \t\t\t\treturn names;\r\n");
      out.write("  \t\t\t}\r\n");
      out.write("  \t\t});\r\n");
      out.write("  \t\t$('.selectstore2').typeahead({\r\n");
      out.write("  \t\t\tsource : function(query, process) {\r\n");
      out.write("  \t\t\t\treturn names;\r\n");
      out.write("  \t\t\t}\r\n");
      out.write("  \t\t});\r\n");
      out.write("  \t});\r\n");
      out.write("      function test(){\r\n");
      out.write("    \t  $('#cusTable').bootstrapTable('selectPage',2);\r\n");
      out.write("      }\r\n");
      out.write("      function deleteByArray(){\r\n");
      out.write("    \t  var idArray=new Array();\r\n");
      out.write("    \t  var arrselectedData =  $('#cusTable').bootstrapTable(\"getAllSelections\");\r\n");
      out.write("    \t  jQuery.each(arrselectedData, function(i, val) {  \r\n");
      out.write("\r\n");
      out.write("    \t\t    idArray[i]=val.id;  \r\n");
      out.write("\r\n");
      out.write("    \t\t});\r\n");
      out.write("    \tvar a=  {\"idArray\":idArray};\r\n");
      out.write("          $.ajax({\r\n");
      out.write("              url: \"");
      out.print(basePath);
      out.write("saleProductsTreasure/deleteSaleProductsTreasureByBatch.do\",\r\n");
      out.write("              type: \"post\",\r\n");
      out.write("              data:a,\r\n");
      out.write("              success: function (data, status) {\r\n");
      out.write("            \t  toastr.success(JSON.parse(data).data);\r\n");
      out.write("            \t  $('#cusTable').bootstrapTable('remove', {field: 'id', values: idArray})\r\n");
      out.write("            \t  if($('#cusTable').bootstrapTable('getData').length==0)\r\n");
      out.write("            \t    \tgetTitle(tableTitleName,role);\r\n");
      out.write("              }\r\n");
      out.write("          });\r\n");
      out.write("    \t  \r\n");
      out.write("      }\r\n");
      out.write(" \r\n");
      out.write("</script>\r\n");
      out.write("</html>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
