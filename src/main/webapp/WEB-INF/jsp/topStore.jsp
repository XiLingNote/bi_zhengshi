<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="/page/left.do"></jsp:include>
<html lang="en">
<head>
<title>topStore</title>
<link rel="stylesheet" href="/css/bootstrap/bootstrap.css">
<link rel="stylesheet"
	href="/css/bootstrap/bootstrap-datetimepicker.min.css">
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
	function getData(datetype, begin, end, stores) {
		json.series.length = 0;
		$.post("/industry/top.do", {
			"datetype" : datetype,
			"begin" : begin,
			"end" : end,
			"brandOrStore" : "store",
			"names" : stores
		}, function(data) {
			//	topList

			if (data) {
				try {
					var obj = eval('(' + data + ')');
				} catch (e) {
					var response = decodeURI(data);
					alert(response);
					return;
				}

				var obj = eval('(' + data + ')');
				var topList = obj.topList;
				var i = 0;
				$.each(topList, function(i, value) {
					i++;
					var totleGmv = 0;
					var _name = decodeURI(value.name);
					var _categories = [];
					var serie = {
						name : 'GMV',
						type : 'areaspline',
						data : [ 18, 16, 14, 32, 19 ],
						tooltip : {
							valueSuffix : ''
						}
					}
					serie.name = _name;
					var _data = [];
					var list = value.list;
					$.each(list, function(i, am) {
						_categories.push(am.date);
						var gmv = (am.amount).toFixed(0);
						totleGmv = totleGmv + am.amount;
						var _gmv = eval('(' + gmv + ')');
						_data.push(_gmv);
					});
					var _viewgmv = formatNum(totleGmv);
					$("#gmv" + i).text(_viewgmv);

					serie.data = _data;
					json.series.push(serie);
					json.xAxis[0].categories = _categories;

					if (_categories.length >= 60) {
						json.xAxis[0].tickInterval = 10;
					} else if (_categories.length > 30) {
						json.xAxis[0].tickInterval = 5;
					} else if (_categories.length > 15) {
						json.xAxis[0].tickInterval = 2;
					}
				});
				$(".report").highcharts(json);
			} else {
				alert("查询参数无效");
			}
		});
	}
	function value() {
		$('#s1>input[name = begin ]').val(null);
		$('#s1>input[name = end ]').val(null);
		$('#s2>input[name = begin ]').val(null);
		$('#s2>input[name = end ]').val(null);
		$('#s3>input[name = begin ]').val(null);
		$('#s3>input[name = end ]').val(null);
	}
	function autoCompletion(store) {
		$.get("/industry/storeName.do", function(data) {
			var storename = eval('(' + data + ')');
			var storenames = [];

			$.each(storename,
					function(i, value) {
						var name = decodeURI(value);
						name = decodeURI(name);
						var _options = "<option value='"+name+"'>" + name
								+ "</option>";
						$(_options).appendTo($(".topCompareSelect"));
						$(_options).appendTo($(".topSelectSelect"));
						storenames.push(name);
					});
			names = storenames;
		});

		$.fn.typeahead.Constructor.prototype.blur = function() {
			var that = this;
			setTimeout(function() {
				that.hide()
			}, 250);

		};
		//	topSelectInput topCompareInput
		$(".topSelectInput").typeahead({
			source : function(query, process) {
				return names;
			}
		});
		$(".topCompareInput").typeahead({
			source : function(query, process) {
				return names;
			}
		});
	}

	var json = {
		chart : {
			zoomType : 'xy'
		},
		title : {
			text : 'topstore'
		},
		subtitle : {
			text : 'gmv'
		},
		xAxis : [ {
			categories : [],
			crosshair : true,
			tickInterval : 1
		} ],
		yAxis : [ { // Primary yAxis
			labels : {
				format : '{value}',
				style : {
					color : Highcharts.getOptions().colors[1]
				}
			},
			title : {
				text : '金额',
				style : {
					color : Highcharts.getOptions().colors[1]
				}
			}
		} ],
		tooltip : {
			shared : true
		},
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
		series : []
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
	}

	var names = [ "aa", "bb" ];

	$(function() {
		var begin;
		var end;
		end = new Date();
		var day = end.getDate();
		var year = end.getFullYear();
		var month = end.getMonth();
		begin = new Date(year, month, 1);
		var viewmonth = (month > 8) ? (month + 1) : "0" + (month + 1);
		var _viewday = (day > 9) ? day : "0" + day;
		$('#s3>input[name = begin ]').val(year + "/" + viewmonth + "/" + "01");
		$('#s3>input[name = end ]')
				.val(year + "/" + viewmonth + "/" + _viewday);

		$("input[name='datetype']:last").attr("checked", true);
		//	json.series[1].tooltip.valueSuffix = 'k';
		//		json.series[2].tooltip.valueSuffix = 'k';
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

		$('#s1>input[name = begin ]').datetimepicker().on('changeDate',
				function(ev) {
					begin = ev.date;
				});
		$('#s1>input[name = end ]').datetimepicker().on('changeDate',
				function(ev) {
					end = ev.date;
				});
		$('#s2>input[name = begin ]').datetimepicker().on('changeDate',
				function(ev) {
					begin = ev.date;
				});
		$('#s2>input[name = end ]').datetimepicker().on('changeDate',
				function(ev) {
					end = ev.date;
				});
		$('#s3>input[name = begin ]').datetimepicker().on('changeDate',
				function(ev) {
					begin = ev.date;
				});
		$('#s3>input[name = end ]').datetimepicker().on('changeDate',
				function(ev) {
					end = ev.date;
				});

		$("input[name='datetype']").change(function() {
			begin = null;
			end = null;
			// 清空日期选择框的值
			value();

			var datetype = $("input[name='datetype']:checked").val();

			if (datetype == "YEAR") {
				$("#s1").removeAttr("hidden");
				$("#s2").attr("hidden", true);
				$("#s3").attr("hidden", true);
			}
			if (datetype == "MONTH") {
				$("#s2").removeAttr("hidden");
				$("#s1").attr("hidden", true);
				$("#s3").attr("hidden", true);
			}
			if (datetype == "DAY") {
				$("#s3").removeAttr("hidden");
				$("#s2").attr("hidden", true);
				$("#s1").attr("hidden", true);
			}
		});
		//	getData(datetype, begin, end, store);
		// 加载店铺名称
		autoCompletion();

		$(".query").click(function() {
			var type = $("input[name='datetype']:checked").val();
			var _this = $(".topSelectInput").val();
			if (!_this) {
				alert("请选择查询店铺的名称!");
				return;
			}
			if (!type) {
				alert("请选择日期类型!");
				return;
			}

			if (!begin) {
				alert("请选择开始日期!");
				return;
			}
			if (!end) {
				alert("请选择结束!");
				return;
			}
			//	topSelectInput topCompareInput
			var _topCompareInput = $(".topCompareInput").val();
			getData(type, begin, end, _this + "，，，" + _topCompareInput);
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
					&nbsp; <input type="radio" name="datetype" value="YEAR" />&nbsp;YEAR
					&nbsp;&nbsp;<input type="radio" name="datetype" value="MONTH" />
					MONTH&nbsp;&nbsp; <input type="radio" name="datetype" value="DAY" />
					DAY
				</div>
				<div class="dateRemind">
					<span class="dateView">date：</span>
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
						<input class="end form-control" name="end" readonly="readonly"
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
					<span>store:</span>
				</div>
				<div class="topSelect">
					<span class="topSpan"> <select
						class="topSelectSelect form-control"
						style="width: 100%; height: 100%"
						onchange="$('.topSelectInput').val(this.value)">
					</select>
					</span> <input class="topSelectInput form-control"
						style="position: relative; width: 90%; height: 100%">
				</div>
				<div class="compare">
					<span>&nbsp;&nbsp;&nbsp;compare store：</span>
				</div>

				<div class="topCompare">
					<span class="topSpan"> <select
						class="topCompareSelect form-control"
						style="width: 100%; height: 100%"
						onchange="$('.topCompareInput').val(this.value)">
					</select>
					</span> <input class="topCompareInput form-control"
						style="position: absolute; width: 90%; height: 100%">
				</div>

				<input class="query form-control" type="button" value="query" />
			</div>
		</form>
		<!-- view列表 -->
		<div class="view">
			<div class="topView">
				<table border="1px" bordercolor="black"
					style="width: 100%; height: 100%">
					<tr>
						<td style="width: 50%"><span class="span"
							style="color: black">GMV:</span> <span id="gmv1" class="span"
							style="color: black; float: right;"></span></td>
						<td><span class="span" style="color: black">Compare:</span> <span
							id="gmv2" class="span" style="color: black; float: right;"></span></td>
					</tr>
				</table>
			</div>
			<div class="report"
				style="position: relative; background-color: black; width: 100%; height: 90%"></div>
		</div>
	</div>
</body>
</html>