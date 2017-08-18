<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>monthSales</title>
<link rel="stylesheet" href="/css/bootstrap/bootstrap.css">
<link rel="stylesheet"
	href="/css/bootstrap/bootstrap-datetimepicker.min.css">
<jsp:include page="/page/left.do"></jsp:include>

<link rel="stylesheet" href="/css/bi.css">
<script src="/js/jquery-1.12.3.js"></script>
<script src="/js/highcharts2.js"></script>
<script src="/js/drilldown.js"></script>
<script src="/js/scientific.js"></script>
<script src="/js/bootstrap/bootstrap.min.js"></script>
<script src="/js/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="/js/bootstrap/bootstrap-datetimepicker.js"></script>
<script src="/js/Highchartstheme.js"></script>
<!-- <script src="/js/custom-scripts.js"></script> -->
<script>
	var amountType = "GMV";
	var department = "All";
	function getData(amountType, department) {
		//set arrayList
		$.post("/store/month.do", {
			"amountType" : amountType,
			"department" : department
		}, function(data) {
			json.series[0].data.length = 0;
			json.drilldown.series.length = 0;
			if (data) {
				var obj = eval('(' + data + ')');
				var set = obj.set;
				var arrayList = obj.arrayList;
				$.each(set, function(name, value) {
					var shop = decodeURI(value.shopName);
					var id = value.id;
					var options = {
						name : 2,
						y : 3,
						drilldown : 'Safari'
					}
					options.name = shop;
					options.drilldown = shop;
					var mount = value.amount.toFixed(0);
					var amount = eval('(' + mount + ')');
					options.y = amount;
					json.series[0].data.push(options);
				});

				json.yAxis.title.text = amountType;
				json.series[0].name = amountType;
				json.subtitle.text = amountType;
				$.each(arrayList, function(name, value) {
					var options = {
						name : 'Opera',
						id : 'Opera',
						data : []
					}
					var name = decodeURI(value.shopName);
					var id = value.id;
					options.id = name;
					options.name = name;
					var list = value.list;
					$.each(list, function(name, value) {
						var da = [];
						da.push(value.datetime);
						if (amountType == "Alipay") {
							var ali = value.alipay.toFixed(0);
							var alipay = eval('(' + ali + ')');
							da.push(alipay);
						} else {
							var gm = value.gmv.toFixed(0);
							var gmv = eval('(' + gm + ')');
							da.push(gmv);
						}
						da.push(value.amount);
						options.data.push(da);
					});
					json.drilldown.series.push(options);
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
			text : 'Month Sales Rank'
		},
		subtitle : {
			text : 'GMV'
		},
		xAxis : {
			type : 'category'
		},
		yAxis : {
			title : {
				text : 'GMV'
			}
		},
		legend : {
			enabled : false
		},
		plotOptions : {
			series : {
				color:'#ffffff',
				borderWidth : 0,
				dataLabels : {
					enabled : true,
					formatter : function() {
						var len = 1000000;
						var name = this.point.drilldown;
						if (!name) {
							len = 1000;
						}
						var vie = this.y;
						vie = (vie / len).toFixed(1);
						var b = formatNum(vie);
						return this.y;
					}
				}
			}
		},
		tooltip : {
			shared : true,
			pointFormatter : function() {
				var vie2 = this.y;
				var b2 = formatNum(vie2);
				return b2;
			},
			style : { //提示框内容的样式
				color : '#ffffff',
				padding : '10px', //内边距 (这个会常用到)
				fontSize : '9pt',
			},
			boderColor:'#ffffff'
		},
		series : [ {
			name : 'GMV',
			color:'#578EBE',
			//颜色
			//			colorByPoint : true,
			/*  {
					name : 'Firefox',
					y : 10.38,
					drilldown : 'Firefox'
				} */

			data : []
		} ],
		drilldown : {
			/* {
					name : 'Firefox',
					id : 'Firefox',
					data : [ [ 'v35', 2.76 ], [ 'v36', 2.32 ], [ 'v37', 2.31 ],
					[ 'v34', 1.27 ], [ 'v38', 1.02 ], [ 'v31', 0.33 ],
					[ 'v33', 0.22 ], [ 'v32', 0.15 ] ]
			}  */
			series : []
		}
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
	}

	$(function() {
		$("#ShopSales").attr("class", "nav nav-second-level in");
		$("#leftMonthlysales").css("background", "#578EBE");

		getData("GMV", "All");
		$(".amountType").change(function() {
			amountType = this.value;
			getData(amountType, department);
		});
		$(".department").change(function() {
			department = this.value;
			getData(amountType, department);
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

.body {
	color: #2a2a2b
}
</style>
<body>
	<div class="page">
		<!-- 查询列表 -->
		<form class="queryCriteria" action="" method="post">
			<div class="queryCriteriaDiv">
				<div class="departmentRemind">
					<span class="remind">department：</span>
				</div>
				<select class="department">
					<option value="All">All</option>
					<option value="Bag">Bag</option>
					<option value="Shoes">Shoes</option>
					<option value="Dress">Dress</option>
					<!-- <option value="TMhk">TMhk</option> -->
					<option value="ACC">ACC</option>
				</select>
				<div class="monthSaleAmount">
					<span class="remind">amounttype：</span>
				</div>
				<select class="amountType">
					<option value="GMV">GMV</option>
					<option value="Alipay">Alipay</option>
				</select>
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