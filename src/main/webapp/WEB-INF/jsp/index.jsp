﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
<head>
<title>index</title>
<link rel="stylesheet" href="/css/bootstrap/bootstrap.css">
<link rel="stylesheet"
	href="/css/bootstrap/bootstrap-datetimepicker.min.css">
<jsp:include page="/page/left.do"></jsp:include>
<link rel="stylesheet" href="/css/bi.css">
<script src="/js/jquery-1.8.3.min.js"></script>
<script src="/js/highcharts.js"></script>
<script src="/js/scientific.js"></script>
<script src="/js/bootstrap/bootstrap.min.js"></script>
<script src="/js/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="/js/bootstrap/bootstrap-datetimepicker.js"></script>
<script src="/js/Highchartstheme.js"></script>
<script src="/js/page/index.js"></script>
</head>
<body>
	<div class="page">
		<!-- 查询列表 -->
		<form class="queryCriteria" action="" method="post">
			<div class="queryCriteriaDiv">
				<!--  -->
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
							style="position: relative; width: 36px; top: 20%; float: left; text-align: center">
							to</div>
						<input class="end form-control" name="end" readonly="readonly"
							style="float: left" />
					</div>

					<div class="dateView" id="s2" hidden="true">
						<input class="begin form-control" name="begin" readonly="readonly"
							style="float: left" />
						<div
							style="position: relative; width: 36px; top: 20%; float: left; ; text-align: center">
							to</div>
						<input class="end form-control" name="end" readonly="readonly"
							style="float: left" />
					</div>

					<div class="dateView" id="s3">
						<input class="begin form-control" name="begin" readonly="readonly"
							style="float: left" />
						<div
							style="position: relative; width: 36px; top: 20%; float: left;  text-align: center">
							to</div>
						<input class="end form-control" name="end" readonly="readonly"
							style="float: left" />
					</div>
				</div>
				<div class="departmentRemind">
					<span class="remind">department:</span>
				</div>
				<select class="department">
					<option value="All">All</option>
					<option value="Bag">Bag</option>
					<option value="Shoes">Shoes</option>
					<option value="Dress">Dress</option>
					<!-- <option value="TMhk">TMhk</option> -->
					<option value="ACC">ACC</option>
				</select>
				<!-- <button id="query" type="button" class=" query  btn btn-default">
					Search</button> -->
			</div>

		</form>

		<!-- view列表 -->
		<div class="view">
			<div class="left">
				<div class="leftGmv">
					<table id="gmv" class="select" border="1">
						<tr style="text-align: center">
							<td style="width: 100%; font-size: 20px">GMV</td>
						</tr>
						<tr style="text-align: center; height: 20%">
							<td rowspan="1" style="width: 100%; font-size: 15px"><span
								style="float: left">&nbsp;&nbsp;&nbsp;Completeness:</span><span
								style="float: right">&nbsp;&nbsp;&nbsp;</span><span id="gc"
								style="float: right"></span></td>
						</tr>
						<tr style="text-align: center">
							<td rowspan="1" style="width: 100%; font-size: 15px"><span
								style="float: left">&nbsp;&nbsp;&nbsp;Target:</span><span
								style="float: right">&nbsp;&nbsp;&nbsp;</span><span id="gt"
								style="float: right"></span></td>
						</tr>
						<tr style="text-align: center">

							<td rowspan="1" style="width: 100%; font-size: 15px"><span
								style="float: left">&nbsp;&nbsp;&nbsp;Completion rate:</span><span
								style="float: right">&nbsp;&nbsp;&nbsp;</span><span id="grate"
								style="float: right"></span>
						</tr>
						<tr style="text-align: center; height: 60px">
							<td rowspan="1" style="width: 100%; font-size: 15px"><span
								style="float: left">&nbsp;&nbsp;&nbsp;YOY:</span> <span
								style="float: right">&nbsp;&nbsp;&nbsp;</span><span id="gy1"
								hidden="true" rowspan="1"
								style="color: green; float: right; font-size: 15px"> </span> <span
								id="gy2" rowspan="1"
								style="color: red; float: right; font-size: 15px"></span></td>
						</tr>
					</table>
				</div>
				<div class="leftAlipay">
					<table id="alipay" class="unselect" border="1">
						<tr style="text-align: center">
							<td style="width: 100%; font-size: 20px">Alipay</td>
						</tr>
						<tr style="text-align: center; height: 20%">
							<td rowspan="1" style="width: 100%; font-size: 15px"><span
								style="float: left; left: 5px">&nbsp;&nbsp;&nbsp;Completeness:</span><span
								style="float: right">&nbsp;&nbsp;&nbsp;</span><span id="ac"
								style="float: right"></span></td>
						</tr>
						<tr style="text-align: center">
							<td rowspan="1" style="width: 100%; font-size: 15px"><span
								style="float: left">&nbsp;&nbsp;&nbsp;Target:</span><span
								style="float: right">&nbsp;&nbsp;&nbsp;</span><span id="at"
								style="float: right"></span></td>
						</tr>
						<tr style="text-align: center">

							<td rowspan="1" style="width: 100%; font-size: 15px"><span
								style="float: left">&nbsp;&nbsp;&nbsp;Completion rate:</span><span
								style="float: right">&nbsp;&nbsp;&nbsp;</span><span id="arate"
								style="float: right"></span>
						</tr>
						<tr style="text-align: center; height: 60px">
							<td rowspan="1" style="width: 100%; font-size: 15px"><span
								style="float: left">&nbsp;&nbsp;&nbsp;YOY:</span> <span
								style="float: right">&nbsp;&nbsp;&nbsp;</span> <span id="ay1"
								hidden="true" rowspan="1"
								style="color: green; float: right; font-size: 15px"> </span> <span
								id="ay2" rowspan="1"
								style="color: red; float: right; font-size: 15px"></span></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="report"
				style="position: relative; background-color: black; width: 80%; height: 100%; float: right"></div>
		</div>
	</div>
</body>
</html>