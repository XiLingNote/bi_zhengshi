<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>dailySales</title>
<link rel="stylesheet" href="/css/bootstrap/bootstrap.css">
<link rel="stylesheet" href="/css/bi.css">
<link rel="stylesheet"
	href="/css/bootstrap/bootstrap-datetimepicker.min.css">

<jsp:include page="/page/left.do"></jsp:include>

<script src="/js/jquery-1.12.3.js"></script>
<script src="/js/style.js"></script>
<script src="/js/scientific.js"></script>
<script src="/js/highcharts2.js"></script>
<script src="/js/bootstrap/bootstrap.min.js"></script>
<script src="/js/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="/js/bootstrap/bootstrap-datetimepicker.js"></script>
<script src="/js/Highchartstheme.js"></script>
<script>
	var endStr;
	var dateType = "DAY";
	var amountType = "GMV";
	var unitE = 'K';
	var unit = 1000;
	function getData(endStr, dateType, amountType) {
		if (dateType == "DAY") {
			unit = 1000;
			unitE = "K";
		} else {
			unit = 1000000;
			unitE = "M";
		}
		json.series[0].tooltip.valueSuffix = unitE;

		$.post("/store/daily.do", {
			"endStr" : endStr,
			"amountType" : amountType,
			"dateType" : dateType
		}, function(data) {
			if (data) {
				var obj = eval('(' + data + ')');
				var set = obj.set;
				var arrayList = obj.arrayList;
				json.series[0].data.length = 0;
				$.each(set, function(name, value) {
					var name = decodeURI(value.shopName);
					var da = [];

					da.push(name);
					var amoun = value.amount.toFixed(0);
					var aa = eval('(' + amoun + ')');
					da.push(aa);
					json.series[0].data.push(da);
				});
			} else {
				alert("查询失败");
			}

			$(".report").highcharts(json);
		});
	}

	var json = {
		chart : {
			type : 'column'
		},
		title : {
			text : 'daily sales'
		},
		subtitle : {
			text : ''
		},
		xAxis : {
			type : 'category',
			labels : {
				rotation : -45,
				style : {
					fontSize : '13px',
					fontFamily : 'Verdana, sans-serif'
				}
			}
		},
		yAxis : { // Primary yAxis
			labels : {
				formatter : function() {
					return this.value / unit + unitE;
				},
				style : {
					color : Highcharts.getOptions().colors[3]
				}
			},
			title : {
				text : 'Amount',
				style : {
					color : Highcharts.getOptions().colors[3]
				}
			}
		},
		legend : {
			enabled : false
		},
		plotOptions : {
			series : {
				borderWidth : 0,
				color:'#ffffff',
				dataLabels : {
					enabled : true,
					formatter : function() {
						var vie = this.y;
						vie = (vie / unit).toFixed(0);
						var b = formatNum(vie);
						return b + unitE;
					}
				}
			}
		},
		tooltip : {
			shared : true,
			boderColor:'#ffffff',
			style : { // 提示框内容的样式
				color : '#ffffff',
				padding : '10px', // 内边距 (这个会常用到)
				fontSize : '9pt'
			},
			formatter : function() {
				var money = this.y;
				var _money = formatNum(money);
				var s = '<span style="color:#ffffff ">'
						+ this.points[0].key
						+ '</span><br/>'
						+ '<span style="float:right;color: #ffffff ">'
						+ 'Amount : </span>'
						+ '<span style="color:#ffffff">'
						+ _money + '</span>';

				return s;

			}
		},
		series : [ {
			name : 'amount',
			color:'#578EBE',
			/* [ '上海', 24.25 ]  */
			data : [],
			tooltip : {
				valueSuffix : ''
			},
		/* 	dataLabels : {
				enabled : true,
				rotation : -90,
				color : '#FFFFFF',
				align : 'right',
				format : '{point.y:.1f}', // one decimal
				y : 10, // 10 pixels down from the top
				style : {
					fontSize : '13px',
					fontFamily : 'Verdana, sans-serif'
				}
			} */
		} ]
	};

	var options = {
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		language : 'zh-CN',
		format : 'dd/MM/yyyy',
		forceParse : 0
	};

	$(function() {
		
		$("#ShopSales").attr("class", "nav nav-second-level in");
		$("#leftDailysales").css("background", "#578EBE");
		
		// 天 ,月 ,年:日期准备
		var end = new Date();
		var day = end.getDate();
		var year = end.getFullYear();
		var month = end.getMonth();

		// 天 ,月 ,年：结束日期格式化
		var _dayEnd = (day > 9) ? day : "0" + day;
		var _monthEnd = (month > 8) ? (month + 1) : "0" + (month + 1);
		var _yearEnd = year;
		var _endYMD = _yearEnd + "/" + _monthEnd + "/" + _dayEnd;
		var _endYM = _yearEnd + "/" + _monthEnd;
		var _endY = _yearEnd;
		endStr = _endYMD;
		//年
		$('#s3>input[name = end ]').val(_endYMD);
		// 月
		$('#s2>input[name = end ]').val(_endYM);
		// 年
		$('#s1>input[name = end ]').val(_endY);

		options.minView = 4;
		options.startView = 4;
		options.format = "yyyy";
		$('#s1>input[name = end ]').datetimepicker(options);

		options.minView = 3;
		options.startView = 3;
		options.format = "yyyy/MM";
		$('#s2>input[name = end ]').datetimepicker(options);

		options.minView = 2;
		options.startView = 2;
		options.format = "yyyy/MM/dd";
		$('#s3>input[name = end ]').datetimepicker(options);

		$("input[name='datetype']:last").attr("checked", true);

		$('#s3>input[name = end ]').change(function() {
			endStr = this.value;
			getData(endStr, dateType, "GMV");
		});
		$('#s2>input[name = end ]').change(function() {
			endStr = this.value + "/1";
			getData(endStr, dateType, "GMV");
		});
		$('#s1>input[name = end ]').change(function() {
			endStr = this.value + "/1/1";
			getData(endStr, dateType, "GMV");
		});

		getData(endStr, dateType, "GMV");

		/* 		$(".query").click(function() {
		 var amountType = $('.amountType option:selected').val();//选中的值

		 if (amountType != "GMV" && amountType != "Alipay") {
		 alert("请选择查询的是GMV还是Alipay!");
		 return;
		 }
		 getData(endStr, dateType, amountType);
		 }); */
		$('.amountType').change(function() {
			dateType = $("input[name='datetype']").val();
			amountType = this.value;
			getData(endStr, dateType, amountType);
		});

		$("input[name='datetype']").change(function() {

			dateType = this.value;
			amountType = $('.amountType option:selected').val();

			if (this.value == "DAY") {
				endStr = $('#s3>input[name = end ]').val();
				$("#s3").removeAttr("hidden");
				$("#s2").attr("hidden", true);
				$("#s1").attr("hidden", true);
				getData(endStr, dateType, amountType);

			} else if (this.value == "MONTH") {
				endStr = $('#s2>input[name = end ]').val() + "/1";
				$("#s2").removeAttr("hidden");
				$("#s1").attr("hidden", true);
				$("#s3").attr("hidden", true);
				getData(endStr, dateType, "GMV");

			} else {
				endStr = $('#s2>input[name = end ]').val() + "/1/1";
				$("#s1").removeAttr("hidden");
				$("#s2").attr("hidden", true);
				$("#s3").attr("hidden", true);
				getData(endStr, dateType, "GMV");
			}
		});
	});
</script>
</head>
<style>
* {
	margin: 0;
	padding: 0;
}

a {
	color: #ffffff;
	text-decoration: none;
}
</style>
<body>
	<div class="page">
		<!-- 查询列表 -->
		<form class="queryCriteria" action="" method="post">

			<div class="queryCriteriaDiv">
				<div class="dateType">
					<div>
						<input type="radio" name="datetype" value="YEAR" />YEAR
						&nbsp;&nbsp;<input type="radio" name="datetype" value="MONTH" />
						MONTH&nbsp;&nbsp; <input type="radio" name="datetype" value="DAY" />
						DAY
					</div>
				</div>
				<div class="dateRemind">
					<span class="remind">date：</span>
				</div>
				<div class="dailySaleDate">
					<div class="dailySaleDateView" id="s1" hidden="true">

						<input class="end form-control" name="end" readonly="readonly" />
					</div>

					<div class="dailySaleDateView" id="s2" hidden="true">

						<input class="end form-control" name="end" readonly="readonly"/>
					</div>

					<div class="dailySaleDateView" id="s3">

						<input class="end form-control" name="end" readonly="readonly"/>
					</div>
				</div>
				<div class="amountRemind">
					<span class="remind">amounttype：</span>
				</div>
				<select class="amountType">
					<option value="GMV">GMV</option>
					<option value="Alipay">Alipay</option>
				</select>
				<!-- <input class="query form-control" type="button" value="search" /> -->
			</div>

		</form>

		<!-- view列表 -->
		<div class="view">
			<div class="report"
				style="position: relative; background-color: black; width: 100%; height: 100%; float: right"></div>
		</div>
	</div>
</body>
</html>