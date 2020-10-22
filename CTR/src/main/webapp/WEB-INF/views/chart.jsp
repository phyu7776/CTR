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
		<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
		
		<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
		<script src="http://code.highcharts.com/highcharts.js"></script>

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
		
		.parent_mini {
			float: left;
			margin: 1%;
			width:31%;
			text-align: center;
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

				var dps = [ [], [], [], [] ,[] ,[] ,[] ,[]];
		
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
				"LG" : [ {
					color : "#E7823A",
					name : "LG",
					type : "column",
					display : true,
					dataPoints : dps[4],
					
				} ],
				"KT" : [ {
					color : "#546BC1",
					name : "KT",
					type : "column",
					dataPoints : dps[5]
				} ],
				"SKT" : [ {
					color : "#546BC1",
					name : "SKT",
					type : "column",
					dataPoints : dps[6]
				} ],
				"UNKWON" : [ {
					color : "#546BC1",
					name : "UNKWON",
					type : "column",
					dataPoints : dps[7]
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
			chart2.options.data = visitorsData2[e.dataPoint.name];
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
							chart2.options.data = visitorsData2["New vs Returning Visitors2"];
							chart2.render();
						});

	}
			var ctx = document.getElementById('myChart'); 
			var ctx = document.getElementById('myChart').getContext('2d'); 
			var ctx = $('#myChart'); // jQuery 사용 var ctx = 'myChart';
			
		</script>
	</head>
	<body> 
	
	
		<div class="clearfix">
			<div>
				<div class="parent_mini">
					<div id="container" style="width: 500px; height: 350px; margin: 0"></div>
						<script language="JavaScript">
						$(document).ready(function() {  
						   var chart = {
						       plotBackgroundColor: null,
						       plotBorderWidth: 0,
						       plotShadow: false
						   };
						   var title = {
						      text: 'OS',
						      align: 'center',
						      verticalAlign: 'middle',
						      y: 50	  
						   };      
						   var tooltip = {
						      pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
						   };
						   var plotOptions = {
						      pie: {
						         dataLabels: {
						            enabled: true,
						            distance: -50,
						            style: {
						               fontWeight: 'bold',
						               color: 'white',
						               textShadow: '0px 1px 2px black'
						            }
						         },
						         startAngle: -90,
						         endAngle: 90,
						         center: ['50%', '75%']
						      }
						   };
						   var series= [{
						      type: 'pie',
						      name: 'Browser share',
						      innerSize: '50%',
						      data: [
						         ['IOS',   45.0],
						         ['ANDROID',       26.8],
						         {
						            name: 'Unkwon',
						            y: 45.7,
						            dataLabels: {
						               enabled: false
						            }
						         }
						      ]
						   }];     
						      
						   var json = {};   
						   json.chart = chart; 
						   json.title = title;     
						   json.tooltip = tooltip;  
						   json.series = series;
						   json.plotOptions = plotOptions;
						   $('#container').highcharts(json);  
						});
						</script>
				</div>
				<div class="parent_mini">
					<div id="container2" style="width: 500px; height: 350px; margin: 0"></div>
						<script language="JavaScript">
						$(document).ready(function() {  
						   var chart2 = {
						       plotBackgroundColor: null,
						       plotBorderWidth: 0,
						       plotShadow: false
						   };
						   var title2 = {
						      text: 'MAKE',
						      align: 'center',
						      verticalAlign: 'middle',
						      y: 50	  
						   };      
						   var tooltip2 = {
						      pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
						   };
						   var plotOptions2 = {
						      pie: {
						         dataLabels: {
						            enabled: true,
						            distance: -50,
						            style: {
						               fontWeight: 'bold',
						               color: 'white',
						               textShadow: '0px 1px 2px black'
						            }
						         },
						         startAngle: -90,
						         endAngle: 90,
						         center: ['50%', '75%']
						      }
						   };
						   var series2= [{
						      type: 'pie',
						      name: 'Browser share',
						      innerSize: '50%',
						      data: [
						         ['APPLE',   45.0],
						         ['SAMSUNG',       26.8],
						         ['LG', 12.8],
						         {
						            name: 'Unkwon',
						            y: 45.7,
						            dataLabels: {
						               enabled: false
						            }
						         }
						      ]
						   }];     
						      
						   var json2 = {};   
						   json2.chart = chart2; 
						   json2.title = title2;     
						   json2.tooltip = tooltip2;  
						   json2.series = series2;
						   json2.plotOptions = plotOptions2;
						   $('#container2').highcharts(json2);  
						});
						</script>
				</div>
				<div class="parent_mini">
					<div id="container3" style="width: 550px; height: 400px; margin: 0 auto"></div>
						<script language="JavaScript">
						$(document).ready(function() {  
						   var chart3 = {
						      type: 'column'
						   };
						   var title3 = {
						      text: '시간대별 접속'   
						   };    
						   var subtitle3 = {
						      text: 'Source: <a href="http://http://tpmn.co.kr/kr/"> <strong>TPMN</strong> </a>'
						   };
						   var xAxis3 = {
						      type: 'category',
						      labels: {
						         rotation: -45,
						         style: {
						            fontSize: '13px',
						            fontFamily: 'Verdana, sans-serif'
						         }
						      }
						   };
						   var yAxis3 ={
						      min: 0,
						      title: {
						        text: '접속자 수 (명)'
						      }
						   };  
						   var tooltip3 = {
						      pointFormat: '접속자: <b>{point.y:.1f} </b>'
						   };   
						   var credits3 = {
						      enabled: false
						   };
						   var series3= [{
						            name: '접속자',
						            data: [
						            	['02 시', 10.5],
						                ['04 시', 10.4],
						                ['06 시', 23.7],
						                ['08 시', 16.1],
						                ['10 시', 14.2],
						                ['12 시', 14.0],
						                ['14 시', 12.5],
						                ['16 시', 12.1],
						                ['18 시', 11.8],
						                ['20 시', 11.7],
						                ['22 시', 11.1],
						                ['24 시', 11.1]
						            ],
						            dataLabels3: {
						                enabled: true,
						                rotation: -90,
						                color: '#FFFFFF',
						                align: 'right',
						                format: '{point.y:.1f}', // one decimal
						                y: 10, // 10 pixels down from the top
						                style: {
						                    fontSize: '13px',
						                    fontFamily: 'Verdana, sans-serif'
						                }
						            }
						   }];     
						      
						   var json3 = {};   
						   json3.chart = chart3; 
						   json3.title = title3;   
						   json3.subtitle = subtitle3;
						   json3.xAxis = xAxis3;
						   json3.yAxis = yAxis3;   
						   json3.tooltip = tooltip3;   
						   json3.credits = credits3;
						   json3.series = series3;
						   $('#container3').highcharts(json3);
						  
						});
						</script>
				</div>
			</div>
		</div>
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
	</body>
</html>
