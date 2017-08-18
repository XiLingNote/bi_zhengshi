<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>shopSale</title>
<link rel="stylesheet" href="/css/bootstrap/bootstrap.css">

<link rel="stylesheet"
	href="/css/bootstrap/bootstrap-datetimepicker.min.css">
<jsp:include page="/page/left.do"></jsp:include>
<link rel="stylesheet" href="/css/bi.css">
<script src="/js/jquery-1.12.3.js"></script>
<script src="/js/highcharts.js"></script>
<script src="/js/style.js"></script>
<script src="/js/scientific.js"></script>
<script src="/js/bootstrap/bootstrap.min.js"></script>
<script src="/js/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="/js/bootstrap/bootstrap-datetimepicker.js"></script>
<script src="/js/bootstrap/bootstrap-typeahead.js"></script>
<script src="/js/Highchartstheme.js"></script>
<script>
	var unit = 1000;
	var unitE = 'K';
	var beginStr;
	var endStr;
	var dateType;
	var store;
	function getData(dateType, beginStr, endStr, store) {
		if (dateType == "DAY") {
			unit = 1000;
			unitE = 'K';
			json.series[0].tooltip.valueSuffix = 'K';
			json.series[1].tooltip.valueSuffix = 'K';
		} else {
			unit = 1000000;
			unitE = 'M';
			json.series[0].tooltip.valueSuffix = 'M';
			json.series[1].tooltip.valueSuffix = 'M';
		}
		$("#gmv").html(null);
		$("#alipay").html(null);
		$("#gmv").html(null);
		$("#cpl").html(null);
		$.post("/store/salesTrend.do", {
			"dateType" : dateType,
			"beginStr" : beginStr,
			"endStr" : endStr,
			"store" : store
		}, function(data) {
			if (data) {
				try {
					var obj = eval('(' + data + ')');
				} catch (e) {
					var response = decodeURI(data);
					alert(response);
					return;
				}

				var obj = eval('(' + data + ')');
				var treeSet = obj.treeSet;

				var categories = [];
				var rates = [];

				var gmvs = [];
				var targets = [];
				var alipays = [];
				var totalGmv = 0.00;
				var totalTarget = 0.00;
				var totalAlipay = 0.00;
				var totalCplR;
				$.each(treeSet,
						function(i, value) {
							totalGmv = totalGmv + value.gmv;
							totalTarget = totalTarget + value.target;
							totalAlipay = totalAlipay + value.alipay;
							var gmv = value.gmv;
							var viewGmv = eval('(' + (value.gmv).toFixed(0)
									+ ')');
							gmvs.push(viewGmv);
							var viewTarget = eval('('
									+ (value.target).toFixed(0) + ')');
							targets.push(viewTarget);
							//if (target) {
							//	var ra = gmv / target;
							var rate = eval('('
									+ (value.completeness * 100).toFixed(2)
									+ ')');
							rates.push(rate);
							//} else {
							//}
							var viewAlipay = eval('('
									+ (value.alipay).toFixed(0) + ')');
							alipays.push(viewAlipay);
							categories.push(value.datetime);
						});

				if (categories.length > 60) {
					json.xAxis[0].tickInterval = 6;
				} else if (categories.length > 30) {
					json.xAxis[0].tickInterval = 3;
				} else if (categories.length > 15) {
					json.xAxis[0].tickInterval = 2;
				}

				json.xAxis[0].categories = categories;
				json.series[0].data = gmvs;
				json.series[1].data = targets;
				json.series[2].data = alipays;
				json.series[3].data = rates;
				$(".report").highcharts(json);
				if (totalTarget) {

					totalCplR = (totalGmv / totalTarget * 100).toFixed(2);
				} else {
					totalCplR = 0;
				}
				//gmv alipay target cpl
				totalGmv = totalGmv.toFixed(0);
				totalAlipay = totalAlipay.toFixed(0);
				totalTarget = totalTarget.toFixed(0);
				var _totalGmv = formatNum(totalGmv);
				var _totalAlipay = formatNum(totalAlipay);
				var _totalTarget = formatNum(totalTarget);
				$("#gmv").html(_totalGmv);
				$("#alipay").html(_totalAlipay);
				$("#target").html(_totalTarget);
				$("#cpl").html(totalCplR + "%");
			} else {
				alert("查询失败");
			}
		});
	}

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

	var b = true;
	var time = 10000;

	var json = {
		chart : {
			zoomType : 'xy'
		},
		title : {
			text : ''
		},
		subtitle : {
			text : ''
		},
		xAxis : [ {
			categories : [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul',
					'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ],
			crosshair : true,
			tickInterval : 0
		} ],
		yAxis : [ { // Primary yAxis
			labels : {
				formatter : function() {
					return this.value / unit + unitE;
				},
				style : {
					color : Highcharts.getOptions().colors[6]
				}
			},
			title : {
				text : 'Amount',
				style : {
					color : Highcharts.getOptions().colors[6]
				}
			}
		}, { // Secondary yAxis
			title : {
				text : '完成率',
				floor : 10,
				style : {
					color : Highcharts.getOptions().colors[4]
				}
			},
			labels : {
				format : '{value} %',
				style : {
					color : Highcharts.getOptions().colors[4]
				}
			},
			min : 0,
			opposite : true
		} ],
		tooltip : {
			shared : true,
			style : { // 提示框内容的样式
				color : 'pink',
				padding : '10px', // 内边距 (这个会常用到)
				fontSize : '9pt'
			},
			formatter : function() {
				var s = '<b>' + this.x + '</b>';
				// "<span style="color:{series.color}">{series.name}</span>:
				// <b>{point.y}</b><br/>"
				$
						.each(
								this.points,
								function(i, value) {
									var _value = value.y;
									if (i != 4) {
										if (dateType == "MONTH") {
											_value = _value / unit;
											_value = _value.toFixed(1);
											_value = formatNum(_value);
										} else {
											_value = _value / unit;
											_value = _value.toFixed(0);
											_value = formatNum(_value);
										}
									}
									s += '<br/>'
											+ '<span style="float:right;color:'
							+ value.series.color + '">'
											+ value.series.name
											+ ' : </span>'
											+ '<span style="float:left;color:'
							+ value.series.color + '">'
											+ _value
											+ value.series.tooltipOptions.valueSuffix
											+ '</span>';
								});
				return s;
			}
		},
		/* legend : {
			layout : 'vertical',
			align : 'left',
			x : 0,
			verticalAlign : 'top',
			y : 0,
			floating : true,
			backgroundColor : (Highcharts.theme && Highcharts.theme.legendBackgroundColor)
					|| '#FFFFFF'
		}, */
		plotOptions : {
			areaspline : {
				marker : {
					enabled : false,
					symbol : 'circle',
					radius : 2,
					states : {
						hover : {
							enabled : true
						}
					}
				}
			},
			spline : {
				marker : {
					enabled : false,
					symbol : 'circle',
					radius : 2,
					states : {
						hover : {
							enabled : true
						}
					}
				}
			}
		},
		series : [ {
			name : 'GMV',
			type : 'areaspline',
			color : '#888888',
			data : [],
			tooltip : {
				valueSuffix : ''
			}
		}, {
			name : 'target',
			type : 'areaspline',
			color : '#66cdaa',
			data : [],
			tooltip : {
				valueSuffix : ''
			}
		}, {
			name : 'alipay',
			type : 'areaspline',
			color : '#555555',
			data : [],
			tooltip : {
				valueSuffix : ''
			}
		}, {
			name : 'GMV cpl R',
			type : 'spline',
			yAxis : 1,
			color : Highcharts.getOptions().colors[4],
			data : [],
			tooltip : {
				valueSuffix : ' %'
			}
		} ]
	};

	var options = {
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		defaultDate : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		language : 'zh-CN',
		format : 'dd/MM/yyyy',
		forceParse : 0
	}

	var names = [ "aa", "bb" ];

	$(function() {
		
		$("#ShopSales").attr("class", "nav nav-second-level in");
		$("#leftStoresales").css("background", "#578EBE");
		
		$(".selectStore").val('pandora潘多拉官方旗舰店');
		//	pandora潘多拉官方旗舰店
		var datetype = $("input[name='datetype']:checked").val();
		$("input[name='datetype']:last").attr("checked", true);
		//	json.series[1].tooltip.valueSuffix = 'k';
		//	json.series[2].tooltip.valueSuffix = 'k';
		var end = new Date();
		var day = end.getDate();
		var year = end.getFullYear();
		var month = end.getMonth();

		// 天 ,月 ,年:开始日期
		var dayBegin = new Date(year, month, day - 29);
		var monthBegin = new Date(year, month - 5);
		var yearBegin = new Date(year - 2, month);

		// 天 ,月 ,年：结束日期格式化
		var _dayEnd = (day > 9) ? day : "0" + day;
		var _monthEnd = (month > 8) ? (month + 1) : "0" + (month + 1);
		var _yearEnd = year;
		var _endYMD = _yearEnd + "/" + _monthEnd + "/" + _dayEnd;
		var _endYM = _yearEnd + "/" + _monthEnd;
		var _endY = _yearEnd;

		// 天：开始日期格式化
		var dayBeginDay = dayBegin.getDate();
		var dayBeginMonth = dayBegin.getMonth();
		var _dayDayBegin = (dayBeginDay > 9) ? dayBeginDay : "0" + dayBeginDay;
		var _dayMonthBegin = (dayBeginMonth > 8) ? (dayBeginMonth + 1) : "0"
				+ (dayBeginMonth + 1);
		var _dayBeginYear = dayBegin.getFullYear();
		var _beginYMD = _dayBeginYear + "/" + _dayMonthBegin + "/"
				+ _dayDayBegin;

		// 月:开始日期格式化
		var monthBeginMonth = monthBegin.getMonth();
		var _monthBeginMonth = (monthBeginMonth > 8) ? (monthBeginMonth + 1)
				: "0" + (monthBeginMonth + 1);
		var _monthBeginYear = monthBegin.getFullYear();
		var _beginYM = _monthBeginYear + "/" + _monthBeginMonth;
		// 年：开始日期格式化
		var _beginY = yearBegin.getFullYear();
		// 默认查询时间
		beginStr = _beginYMD;
		endStr = _endYMD;

		// 日
		$('#s3>input[name = begin ]').val(_beginYMD);
		$('#s3>input[name = end ]').val(_endYMD);
		// 月
		$('#s2>input[name = begin ]').val(_beginYM);
		$('#s2>input[name = end ]').val(_endYM);
		// 年
		$('#s1>input[name = begin ]').val(_beginY);
		$('#s1>input[name = end ]').val(_endY);
		getData("DAY", beginStr, endStr, 'pandora潘多拉官方旗舰店');
		/*年日期选项   */
		options.minView = 4;
		options.startView = 4;
		options.format = "yyyy";
		$('#s1>input[name = begin ]').datetimepicker(options);
		$('#s1>input[name = end ]').datetimepicker(options);
		/*月日期选项   */
		options.minView = 3;
		options.startView = 3;
		options.format = "yyyy/MM";
		$('#s2>input[name = begin ]').datetimepicker(options);
		$('#s2>input[name = end ]').datetimepicker(options);
		/*日日期选项   */
		options.minView = 2;
		options.startView = 2;
		options.format = "yyyy/MM/dd";
		$('#s3>input[name = begin ]').datetimepicker(options);
		$('#s3>input[name = end ]').datetimepicker(options);

		// 后台数据
		$('#s3>input[name = begin ]').change(function() {
			beginStr = this.value;
		});
		$('#s3>input[name = end ]').change(function() {
			endStr = this.value;
		});

		$('#s2>input[name = begin ]').change(function() {
			beginStr = this.value + "/01";
		});
		$('#s2>input[name = end ]').change(function() {
			endStr = this.value + "/01";
		});

		$('#s1>input[name = begin ]').change(function() {
			beginStr = this.value + '/01/01';
		});

		$('#s1>input[name = end ]').change(function() {
			endStr = this.value + '/01/01';
		});

		$("input[name='datetype']").change(function() {
			var dateType = $("input[name='datetype']:checked").val();
			if (dateType == "YEAR") {
				$("#s1").removeAttr("hidden");
				$("#s2").attr("hidden", true);
				$("#s3").attr("hidden", true);
				beginStr = $('#s1>input[name = begin ]').val() + "/01/01";
				endStr = $('#s1>input[name = end ]').val() + "/01/01";
			}

			if (dateType == "MONTH") {
				$("#s2").removeAttr("hidden");
				$("#s1").attr("hidden", true);
				$("#s3").attr("hidden", true);
				beginStr = $('#s2>input[name = begin ]').val() + "/01";
				endStr = $('#s2>input[name = end ]').val() + "/01";
			}

			if (dateType == "DAY") {
				$("#s3").removeAttr("hidden");
				$("#s2").attr("hidden", true);
				$("#s1").attr("hidden", true);
				beginStr = $('#s3>input[name = begin ]').val();
				endStr = $('#s3>input[name = end ]').val();
			}
		});
		// 加载店铺名称
		autoCompletion();

		/* $(".selectStore").change(function() {
			alert(this.value);
		}); */
		$(".query").click(function() {
			var type = $("input[name='datetype']:checked").val();
			var store = $('.selectStore').val();//选中的值

			if (!store) {
				alert("请选择查询的店铺名称!");
				return;
			}

			if (!type) {
				alert("请选择日期类型!");
				return;
			}

			getData(type, beginStr, endStr, store);
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

		$('.selectStore').typeahead({
			source : function(query, process) {
				return names;
			}
		});

	});
</script>
</head>
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
				<div class="date">
					<div class="dateView" id="s1" hidden="true">
						<input class="begin form-control" name="begin" readonly="readonly"
							style="float: left" />
						<div
							style="position: relative; width: 12%; top: 20%; float: left; height: 50%; text-align: center">
							to</div>
						<input class="end form-control" name="end" readonly="readonly"
							style="float: left" />
					</div>

					<div class="dateView" id="s2" hidden="true">
						<input class="begin form-control" name="begin" readonly="readonly"
							style="float: left" />
						<div
							style="position: relative; width: 12%; top: 20%; float: left; height: 50%; text-align: center">
							to</div>
						<input class="end form-control"  name="end" readonly="readonly"
							style="float: left" />
					</div>

					<div class="dateView" id="s3">
						<input class="begin form-control" name="begin" readonly="readonly"
							style="float: left" />
						<div
							style="position: relative; width: 12%; top: 20%; float: left; height: 50%; text-align: center">
							to</div>
						<input class="end form-control" name="end" readonly="readonly"
							style="float: left" />
					</div>
				</div>
				<div class="storeDiv">
					<span class="remind">store：</span>
				</div>
				<input class="selectStore"> <button id="" type="button" class=" query  btn btn-primary">
							<span class="glyphicon glyphicon-search"></span>Search
						</button>
			</div>

		</form>

		<!-- view列表 -->
		<div class="view">
			<div class="saleTrendTop">
				<table class="dataDisplay" boder="1" border-color="#8e8686">
					<tr>
						<td><span class="span">GMV:</span> <span id="gmv"
							class="span"></span></td>
						<td><span class="span">Alipay:</span> <span id="alipay"
							class="span"></span></td>
						<td><span class="span">GMV Target:</span> <span id="target"
							class="span"></span></td>
						<td><span class="span">GMV cpl R:</span> <span id="cpl"
							class="span"></span></td>
					</tr>
				</table>
			</div>
			<div class="report"
				style="position: relative; background-color: black; width: 100%; height: 90%"></div>
		</div>
	</div>
</body>
</html>