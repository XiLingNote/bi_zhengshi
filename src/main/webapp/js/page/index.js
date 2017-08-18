var unit = 1000;
var unitE = 'K';
var beginStr;
var endStr;
var datetype = "DAY";
var department = 'All';
var amountType = "GMV";
// "datetype":YEAR、MONTH、DAY, "begin" : begin, "end" : end, "type" : type,
// "department" : department
function getDate(datetype, beginStr, endStr, department, type) {
	$("#gc").text("");
	$("#gt").text("");
	$("#grate").text("");
	$("#gy1").text("");
	$("#gy2").text("");
	$("#ac").text("");
	$("#at").text("");
	$("#arate").text("");
	$("#ay1").text("");
	$("#ay2").text("");

	if (datetype == "DAY") {
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

	json.title.text = type;

	$
			.post(
					"/overall/sale.do",
					{
						"datetype" : datetype,
						"beginStr" : beginStr,
						"endStr" : endStr,
						"department" : department
					},
					function(data) {
						var amounts = [];
						var targets = [];
						var categories = [];
						var completion = [];
						var gc = 0.0;
						var gt = 0.0;
						var ac = 0.0;
						var at = 0.0;

						var lastgrate = 0.0;
						var lastarate = 0.0;
						if (data) {
							var obj = eval('(' + data + ')');
							var pojo = obj.treeSet;
							var last = obj.last;
							if (last) {
								lastgrate = ((last.gmv / last.target) * 100)
										.toFixed(1);
								lastarate = ((last.alipay / last.alipayTarget) * 100)
										.toFixed(1);
							}
							$.each(pojo, function(name, value) {
								if (type == "Alipay") {
									gc = gc + value.gmv;
									gt = gt + value.target;
									ac = ac + value.alipay;
									at = at + value.alipayTarget;
									var amount = eval('('
											+ (value.alipay).toFixed(0) + ')');
									amounts.push(amount);
									categories.push(value.datetime);
									var target = eval('('
											+ (value.alipayTarget).toFixed(0)
											+ ')');
									targets.push(target);
									var com = value.alipayCompleteness;
									var comp = eval('('
											+ (com * 100).toFixed(0) + ')');
									completion.push(comp + '  ');
								} else {
									gc = gc + value.gmv;
									ac = ac + value.alipay;
									gt = gt + value.target;
									at = at + value.alipayTarget;
									var amoun = (value.gmv);
									var amount = eval('(' + (amoun).toFixed(0)
											+ ')');
									amounts.push(amount);
									categories.push(value.datetime);
									var target = eval('('
											+ (value.target).toFixed(0) + ')');
									targets.push(target);
									var com = value.completeness;
									var comp = eval('('
											+ (com * 100).toFixed(1) + ')');
									completion.push(comp);
								}
							});
							if (categories.length >= 60) {
								json.xAxis[0].tickInterval = 10;
							} else if (categories.length > 30) {
								json.xAxis[0].tickInterval = 5;
							} else if (categories.length > 15) {
								json.xAxis[0].tickInterval = 2;
							}

							json.xAxis[0].categories = categories;
							// 完成率 ，目标 完成
							json.series[0].data = targets;
							json.series[1].data = amounts;
							json.series[2].data = completion;
							gc = eval('(' + (gc).toFixed(0) + ')');
							var showgc = formatNum(gc);
							// gc = eval('(' + (gc / unit).toFixed(0) + ')');
							$("#gc").text(showgc + '  ');
							var showgt1 = gt.toFixed(0);
							var showgt = formatNum(showgt1);
							// gt = eval('(' + (gt / unit).toFixed(0) + ')');
							$("#gt").text(showgt);
							var grate;
							if (gt > 0) {
								grate = (gc / gt * 100).toFixed(1);
								if (grate) {
									$("#grate").text(grate + '%');
								}
							} else {
								$("#grate").text('0.0%');
							}
							// 上绿，下红
							if (lastgrate && lastgrate != Infinity) {
								if (lastgrate / grate > 1) {
									$("#gy2").removeAttr("hidden");
									$("#gy1").attr("hidden", "true");
									$("#gy2").text(
											"↓（"
													+ (lastgrate - grate)
															.toFixed(0) + "％)");
								}
								if (lastgrate / grate < 1) {
									$("#gy1").removeAttr("hidden");
									$("#gy2").attr("hidden", "true");
									$("#gy1").text(
											"↑"
													+ (grate - lastgrate)
															.toFixed(0) + "％");
								}
							} else {
								$("#gy2").removeAttr("hidden");
								$("#gy1").attr("hidden", "true");
								$("#gy2").text("（" + 0.0 + "％)");
							}
							ac = eval('(' + (ac).toFixed(0) + ')');
							var showac = formatNum(ac);
							$("#ac").text(showac);

							var showat1 = at.toFixed(0);
							var showat = formatNum(showat1);
							$("#at").text(showat);
							var arate;
							if (at > 0) {
								arate = (ac / at * 100).toFixed(1);
								if (arate) {
									$("#arate").text(arate + '%');
								}
							} else {
								$("#arate").text('0.0%');
							}

							if (lastarate && lastarate != Infinity) {
								if (lastarate / arate > 1) {
									$("#ay2").removeAttr("hidden");
									$("#ay1").attr("hidden", "true");
									$("#ay2").text(
											"↓（"
													+ (lastarate - arate)
															.toFixed(1) + "％)");
								}

								if (lastarate / arate < 1) {
									$("#ay1").removeAttr("hidden");
									$("#ay2").attr("hidden", "true");
									$("#ay1").text(
											"↑"
													+ (arate - lastarate)
															.toFixed(1) + "％");
								}
							} else {
								$("#ay2").removeAttr("hidden");
								$("#ay1").attr("hidden", "true");
								$("#ay2").text("（" + 0 + "％)");
							}
						} else {
							alert("参数错误");
						}
						$(".report").highcharts(json);
					});
}
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

var json = {
	chart : {
		zoomType : 'xy'
	},
	title : {
		text : 'ＧMV'
	},
	subtitle : {
		text : ''
	},
	xAxis : [ { // Ajax后台数据 X轴数据
		categories : [],
		crosshair : true,
		// X轴每隔几个显示一个
		tickInterval : 0,
		floor : 3,
		tickWidth : 0
	/*
	 * labels: { rotation: -30 }
	 */
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
	}, {
		title : {
			text : 'Completion rate',
			style : {
				color : Highcharts.getOptions().colors[4]
			}
		},
		labels : {
			format : '{value} ％',
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
			$.each(this.points, function(i, value) {
				var _value = value.y;
				if (i != 2) {
					if (datetype == "MONTH") {
						_value = _value / unit;
						_value = _value.toFixed(1);
						_value = formatNum(_value);
					} else {
						_value = _value / unit;
						_value = _value.toFixed(0);
						_value = formatNum(_value);
					}
				}
				s += '<br/>' + '<span style="float:right;color:'
						+ value.series.color + '">' + value.series.name
						+ ' : </span>' + '<span style="float:left;color:'
						+ value.series.color + '">' + _value
						+ value.series.tooltipOptions.valueSuffix + '</span>';
			});
			return s;
		}
	},
	/*
	 * legend : { layout : 'vertical', align : 'left', x : 800, verticalAlign :
	 * 'top', floating : true, y : 0, floating : true },
	 */
	// Ajax后台数据
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
		name : 'target',
		type : 'areaspline',
		color : '#66cdaa',
		data : [],
		tooltip : {
			valueSuffix : ''
		}
	}, {
		name : 'complete',
		color : '#7F7876',
		type : 'areaspline',
		data : [],
		tooltip : {
			valueSuffix : ''
		}

	}, { // 内部有个List集合（）
		name : 'completion rate',
		type : 'spline',
		color : Highcharts.getOptions().colors[4],
		yAxis : 1,
		data : [],
		tooltip : {
			valueSuffix : '％'
		}
	} ]
};

$(function() {
	/* 左侧菜单选中效果 */
	$("#leftSalesOverall").css("background", "#578EBE");

	/* 年日期选项 */
	options.minView = 4;
	options.startView = 4;
	options.format = "yyyy";
	$('#s1>input[name = begin ]').datetimepicker(options);
	$('#s1>input[name = end ]').datetimepicker(options);
	/* 月日期选项 */
	options.minView = 3;
	options.startView = 3;
	options.format = "yyyy/MM";
	$('#s2>input[name = begin ]').datetimepicker(options);
	$('#s2>input[name = end ]').datetimepicker(options);
	/* 日日期选项 */
	options.minView = 2;
	options.startView = 2;
	options.format = "yyyy/MM/dd";
	$('#s3>input[name = begin ]').datetimepicker(options);
	$('#s3>input[name = end ]').datetimepicker(options);
	// day默认选中
	$("input[name='datetype']:last").attr("checked", true);
	/*
	 * json.series[1].tooltip.valueSuffix = 'k';
	 * json.series[2].tooltip.valueSuffix = 'k';
	 */

	// 天 ,月 ,年:日期准备
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
	var _beginYMD = _dayBeginYear + "/" + _dayMonthBegin + "/" + _dayDayBegin;

	// 月:开始日期格式化
	var monthBeginMonth = monthBegin.getMonth();
	var _monthBeginMonth = (monthBeginMonth > 8) ? (monthBeginMonth + 1) : "0"
			+ (monthBeginMonth + 1);
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
	getDate(datetype, beginStr, endStr, "All", "GMV");
	// 左侧GMV Alipay求换
	$(".leftGmv").mouseenter(function() {
		amountType = "GMV";
		$("#gmv").removeAttr("class");
		$("#alipay").removeAttr("class");
		$("#gmv").attr('class', 'select');
		$("#alipay").attr('class', 'unselect');
		getDate(datetype, beginStr, endStr, department, "GMV");
	});

	$(".leftAlipay").mouseenter(function() {
		amountType = "Alipay";
		$("#gmv").removeAttr("class");
		$("#alipay").removeAttr("class");
		$("#gmv").attr('class', 'unselect');
		$("#alipay").attr('class', 'select');
		getDate(datetype, beginStr, endStr, department, "Alipay");
	});

	// 后台数据
	$('#s3>input[name = begin ]').change(function() {
		beginStr = this.value;
		getDate(datetype, beginStr, endStr, department, amountType);
	});
	$('#s3>input[name = end ]').change(function() {
		endStr = this.value;
		getDate(datetype, beginStr, endStr, department, amountType);
	});

	$('#s2>input[name = begin ]').change(function() {
		beginStr = this.value + "/01";
		getDate(datetype, beginStr, endStr, department, amountType);
	});
	$('#s2>input[name = end ]').change(function() {
		endStr = this.value + "/01";
		getDate(datetype, beginStr, endStr, department, amountType);
	});

	$('#s1>input[name = begin ]').change(function() {
		beginStr = this.value + '/01/01';
		getDate(datetype, beginStr, endStr, department, amountType);
	});

	$('#s1>input[name = end ]').change(function() {
		endStr = this.value + '/01/01';
		getDate(datetype, beginStr, endStr, department, amountType);
	});

	/*
	 * $('#s1>input[name = begin ]').datetimepicker().on('changeDate',
	 * function(ev) { begin = ev.date; });
	 */

	$("input[name='datetype']").change(function() {
		var type = $("input[name='datetype']:checked").val();
		datetype = type;
		if (type == "YEAR") {
			beginStr = $('#s1>input[name = begin ]').val() + "/01/01";
			endStr = $('#s1>input[name = end ]').val() + "/01/01";
			$("#s1").removeAttr("hidden");
			$("#s2").attr("hidden", true);
			$("#s3").attr("hidden", true);
			getDate(datetype, beginStr, endStr, department, amountType);
		}

		if (type == "MONTH") {
			$("#s2").removeAttr("hidden");
			$("#s1").attr("hidden", true);
			$("#s3").attr("hidden", true);
			beginStr = $('#s2>input[name = begin ]').val() + "/01";
			endStr = $('#s2>input[name = end ]').val() + "/01";
			getDate(datetype, beginStr, endStr, department, amountType);
		}

		if (type == "DAY") {
			$("#s3").removeAttr("hidden");
			$("#s2").attr("hidden", true);
			$("#s1").attr("hidden", true);
			beginStr = $('#s3>input[name = begin ]').val();
			endStr = $('#s3>input[name = end ]').val();
			getDate(datetype, beginStr, endStr, department, amountType);
		}
	});

	$(".query").click(function() {
		department = $('.department option:selected').val();// 选中的值
		var type = $('.amountType option:selected').val();// 选中的值
		datetype = $("input[name='datetype']:checked").val();
		getDate(datetype, beginStr, endStr, department, "GMV");

	});
	
	$(".department").change(function(){
		department = this.value;
		getDate(datetype, beginStr, endStr, department, amountType);
	});

});