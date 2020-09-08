<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">

<title>CTR Result</title>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script src="https://canvasjs.com/assets/script/jquery-1.11.1.min.js"></script>
	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>

<style>
#backButton {
	border-radius: 4px;
	padding: 8px;
	border: none;
	font-size: 16px;
	background-color: #2eacd1;
	color: white;
	position: absolute;
	top: 10px;
	right: 10px;
	cursor: pointer;
}

.parent {
	float: left;
	margin: 1%;
	width:48%;
}

.clearfix:after {
	display:block;
	content:"";
	clear:both;
}

.invisible {
	display: none;
}
</style>
<script type="text/javascript">

	window.onload = function() {

		var dps = [ [], [], [], [] ];

		var yValue;
		var label;
		var name;
		var color;
		var totalVisitors = ${total}

		<c:forEach items="${dataPointsList}" var="dataPoints" varStatus="loop">
			<c:forEach items="${dataPoints}" var="dataPoint">
				yValue = parseFloat("${dataPoint.y}");
				label = "${dataPoint.label}";
				name = "${dataPoint.name}";
				color = "${dataPoint.color}";
				dps[parseInt("${loop.index}")].push({
					label : label,
					y : yValue,
					name : name,
					color : color,
				});
			</c:forEach>
		</c:forEach>
		
 
		
		var visitorsData = {
			"New vs Returning Visitors" : [ {
				click : visitorsChartDrilldownHandler,
				cursor : "pointer",
				explodeOnClick : false,
				innerRadius : "75%",
				legendMarkerType : "square",
				name : "New vs Returning Visitors",
				radius : "100%",
				showInLegend : true,
				startAngle : 90,
				type : "doughnut",
				dataPoints : dps[0]
			} ],
			"Click" : [ {
				color : "#E7823A",
				name : "New Visitors",
				type : "column",
				display : true,
				dataPoints : dps[1],
				
			} ],
			"Non_Click" : [ {
				color : "#546BC1",
				name : "Returning Visitors",
				type : "column",
				dataPoints : dps[2]
			} ],
		};
		
		var visitorsData2 = {
				"New vs Returning Visitors2" : [ {
					click : visitorsChartDrilldownHandler2,
					cursor : "pointer",
					explodeOnClick : false,
					innerRadius : "75%",
					legendMarkerType : "square",
					name : "New vs Returning Visitors",
					radius : "100%",
					showInLegend : true,
					startAngle : 90,
					type : "doughnut",
					dataPoints : dps[3]
				} ],
				"Click" : [ {
					color : "#E7823A",
					name : "New Visitors",
					type : "column",
					display : true,
					dataPoints : dps[1],
					
				} ],
				"Non_Click" : [ {
					color : "#546BC1",
					name : "Returning Visitors",
					type : "column",
					dataPoints : dps[2]
				} ],
			};
		
		console.log(visitorsData["New vs Returning Visitors"])

		
		var newVSReturningVisitorsOptions = {
			animationEnabled : true,
			theme : "light2",
			title : {
				text : "클릭 비율"
			},
			subtitles : [ {
				backgroundColor : "#2eacd1",
				fontSize : 16,
				fontColor : "white",
				padding : 5
			} ],
			legend : {
				fontFamily : "calibri",
				fontSize : 14,
				itemTextFormatter : function(e) {
					return e.dataPoint.name + ": " + Math.round(e.dataPoint.y / totalVisitors * 100) + "%";
				}
			},
			data : []
		};
		
		var newVSReturningVisitorsOptions2 = {
				animationEnabled : true,
				theme : "light2",
				title : {
					text : "각 통신사 비율"     
				},
				subtitles : [ {
					backgroundColor : "#2eacd1",
					fontSize : 16,
					fontColor : "white",
					padding : 5
				} ],
				legend : {
					fontFamily : "calibri",
					fontSize : 14,
					itemTextFormatter : function(e) {
						return e.dataPoint.name + ": " + Math.round(e.dataPoint.y / totalVisitors * 100) + "%";
					}
				},
				data : []
			};


		
		var visitorsDrilldownedChartOptions = {
			animationEnabled : true,
			theme : "light2",
			axisX: {
				labelFontColor: "#717171",
				lineColor: "#a2a2a2",
				tickColor: "#a2a2a2"
			},
			axisY : {
				gridThickness : 0,
				includeZero : false,
				labelFontColor : "#717171",
				lineColor : "#a2a2a2",
				tickColor : "#a2a2a2",
				lineThickness : 1
			},
			data : []
		};
		
		var visitorsDrilldownedChartOptions2 = {
				animationEnabled : true,
				theme : "light2",
				axisX: {
					labelFontColor: "#717171",
					lineColor: "#a2a2a2",
					tickColor: "#a2a2a2"
				},
				axisY : {
					gridThickness : 0,
					includeZero : false,
					labelFontColor : "#717171",
					lineColor : "#a2a2a2",
					tickColor : "#a2a2a2",
					lineThickness : 1
				},
				data : []
			};


		
		var chart = new CanvasJS.Chart("chartContainer", newVSReturningVisitorsOptions);
		chart.options.data = visitorsData["New vs Returning Visitors"];
		chart.render();
		
		var chart2 = new CanvasJS.Chart("chartContainer2", newVSReturningVisitorsOptions2);
		chart2.options.data = visitorsData2["New vs Returning Visitors2"];
		chart2.render();



		function visitorsChartDrilldownHandler(e) {
			chart = new CanvasJS.Chart("chartContainer", visitorsDrilldownedChartOptions);
			chart.options.data = visitorsData[e.dataPoint.name];
			chart.options.title = { text : e.dataPoint.name }
			chart.options.label = { label: e.dataPoint.label}
			chart.render();
			$("#backButton").toggleClass("invisible");
		}
		
		$("#backButton")
		.click(
				function() {
					$(this).toggleClass("invisible");
					chart = new CanvasJS.Chart("chartContainer",
							newVSReturningVisitorsOptions);
					chart.options.data = visitorsData["New vs Returning Visitors"];
					chart.render();
				});
		
		function visitorsChartDrilldownHandler2(e) {
			chart2 = new CanvasJS.Chart("chartContainer2", visitorsDrilldownedChartOptions2);
			chart2.options.data = visitorsData[e.dataPoint.name];
			chart2.options.title = { text : e.dataPoint.name }
			chart2.options.label = { label: e.dataPoint.label}
			chart2.render();
			$("#backButton2").toggleClass("invisible");
		}
		

		$("#backButton2 ")
				.click(
						function() {
							$(this).toggleClass("invisible");
							chart2 = new CanvasJS.Chart("chartContainer2",newVSReturningVisitorsOptions2);
							chart2.options.data = visitorsData["New vs Returning Visitors2"];
							chart2.render();
						});

	}
</script>
</head>
<body> 
	<div class="clearfix">
		<div class="parent">
			<div id="chartContainer" style="height: 370px; width:100%;"></div>
			<button class="btn invisible" id="backButton">&lt; Back</button>
		</div>
		<div class="parent">
			<div id="chartContainer2" style="height: 370px; width:100%;"></div>
			<button class="btn invisible" id="backButton2">&lt; Back</button>
		</div>
	</div>
</html>
