<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html>
<head>
<title>手机IMEI查询</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no">

<link rel="stylesheet" href="css/weui.min.css">
<link rel="stylesheet" href="css/jquery-weui.css">

</head>
<body ontouchstart>

	<!-- 帮助栏start  -->
	<div style="height: 40px; width:100%; background: #1A8FDE;">
		<div class="wyback" style="width:40px; height: 40px; float:left; margin-left:10px;" onclick="javascript:history.back(-1)">
			<span style="display: block;width: 20px; height: 20px; margin-top:12px; background: url(<%=request.getContextPath() %>/images/back.png) no-repeat;background-size: 20px;"></span>
		</div>
		
		<div class="wyflush" onclick="javascript:location.reload()" style="float:right">
			<span style="display: block;width: 20px; height: 20px; margin-top:12px; margin-right:10px; background: url(<%=request.getContextPath() %>/images/flush.png) no-repeat;background-size: 20px;"></span>
		</div>
		
		<div class="wyhelp" style="width:40px; height:40px; float:right;" onclick="javascript:location.href='MesHelp.html'">
			<span style="display: block;width: 20px; height: 20px; margin-top:12px; background: url(<%=request.getContextPath() %>/images/hhhh.png) no-repeat;background-size: 20px;"></span>
		</div>		
	</div>
	<!-- 帮助栏 end -->

	<div class="weui-tab">
		<!-- 导航栏 -->
		<div class="weui-navbar">
			<a id="cx" class="weui-navbar__item weui-bar__item--on" href="#tab1">查询</a>
			<a id="ls" class="weui-navbar__item" href="#tab2">历史</a>
		</div>

		<div class="weui-tab__bd">
			<!-- tab1-->
			<div id="tab1" class="weui-tab__bd-item weui-tab__bd-item--active">
				
				<!-- 通知信息 -->
				<div style="width:100%; height:25px; text-align:center">
					<span style="color:red">免费试用2条/天</span>
				</div>

				<!-- 多行文本框 -->
				<div class="weui-cell">
					<div class="weui-cell__bd">		
						<textarea id="imeis" class="weui-textarea" placeholder="最多输入2个IMEI，每条换行输入" maxlength="31" rows="5" onkeyup="this.value=this.value.replace(/[^\r\n0-9]/g,'');"></textarea>				
						<input hidden="hidden" id="wechatid" type="text" value="${requestScope.wxid}"/>
					</div>
					<div class="weui-cell__ft">		
					</div>
				</div>
      			<!-- 提交按钮 -->
				<div class="weui-cell">
					<div class="weui-cell__bd">		
						<a id="batchimei" class="weui-btn weui-btn_primary">查询</a>					
					</div>
					<div class="weui-cell__ft">		
					</div>
				</div>	
			</div>
			<!-- tab2-->
			<div id="tab2" class="weui-tab__bd-item">

				<div class="weui-cell">
					<div class="weui-cell__bd">
						<div class="demos-content-padded" id="tab2content">

						</div>
					</div>
				</div>		
			    <div class="weui-loadmore">
			    	<i class="weui-loading"></i>
			        <span class="weui-loadmore__tips">正在加载</span>
			    </div>
			</div>
	
		<!-- 隐藏框 -->
			<div id="xx" style="background: #EDEDED; border-radius: 5px; margin: 0 10px; display: none">
					<div class="weui-cell">
						<div class="weui-cell__bd" style="width:30%;">
							<b>IMEI</b>
						</div>
						<div class="weui-cell__ft" style="width:70%;">
							<p id="imeiid2"></p>
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__bd" style="width:30%;">
							<b>品牌</b>
						</div>
						<div class="weui-cell__ft" style="width:70%;">
							<p id="brand2"></p>
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__bd" style="width:30%;">
							<b>型号</b>
						</div>
						<div class="weui-cell__ft" style="width:70%;">
							<p id="model2"></p>
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__bd" style="width:30%;">
							<b>制造商</b>
						</div>
						<div class="weui-cell__ft" style="width:70%;">
							<p id="manufacturer2"></p>
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__bd">
							<b>频段</b>
						</div>
						<div class="weui-cell__ft" style="width:200px; overflow:hidden;">
							<p id="band2"></p>
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__bd" style="width:30%;">
							<b>分配机构</b>
						</div>
						<div class="weui-cell__ft" style="width:70%;">
							<p id="allocated2"></p>
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__bd" style="width:30%;">
							<b>序列号</b>
						</div>
						<div class="weui-cell__ft" style="width:70%;">
							<p id="snr2"></p>
						</div>
					</div>

				</div>
		</div>
	</div>




<script src="js/jquery-2.1.4.js"></script>

<script src="js/jquery-weui.js"></script>
<script>   
	//============================================================================================    
	//定义全局Ajax返回值，以便于打印历史信息，不必每次都访问后台数据
	var requestdata = null;
	var dataDB = null;//页面数据库
	var str= ${requestScope.returndata};
	if(str!=null && str!=""){
		requestdata = str;
	}
	var imeistr = "";//记录IMEI数，以逗号分割，用作请求参数
	
	//===============================滚动分页加载start======================================
	var num = 1;//定义数据分页的当前页
    var loading = false;
    $("body").infinite().on("infinite", function() {  	
        if(loading) return;
    	if($('.weui-loading').is(':hidden')){
    		return;
    	}
        loading = true;
        setTimeout(function() {  
        	num++;
        	var getwxid = $("#wechatid").val();
        	$.ajax({
				url : 'http://localhost:8080/IMEIAPI/imeipage',
				type : 'post',
				dataType : "json",
				data : {					
					"wxid" : getwxid,
					"page" : num,
					"num" : 10
				},
				success : function(data) {
					if(data.message=="Error"){				
						$(".weui-loading").hide();
						$(".weui-loadmore__tips").html("没有更多了");
						return;
					}
					//dataDB= Object.assign([], dataDB, data.datalist);//添加信息
					dataDB = dataDB.concat(data.datalist);
					console.log(dataDB);	
					//将查询出的历史信息循环显示在tab2中
					var op = "";
					
					//将查询出的历史信息循环显示在tab2中
					for (var i = 0; i < data.datalist.length; i++) {
						//添加历史信息到tab2
						op = op + "<a class='weui-cell weui-cell_access' onclick='fn2(this)'><div class='weui-cell__bd'><p>"
								+ data.datalist[i].imei
								+ "</p><p style='font-size: 12px; color: gray'>"
								+ timestampToTime(data.datalist[i].date)
								+"</p></div><div class='weui-cell__ft'>"
								+ data.datalist[i].brand
								+ "</div></a>";
					}
					$("#tab2content").append(op);
				},
				error:function(){
					$.alert("ajax请求历史记录失败！");
				}
        	})							
          	loading = false;
        }, 1000);
      });
	//===============================滚动分页加载end================================================== 
	
	$(function() {				
		//======================第一次访问本页面start=====================
		if(str.hasOwnProperty("datalist")){
			dataDB = requestdata.datalist;//将第一页机型信息存入数据库
			console.log(dataDB);
			for (var i = 0; i < requestdata.datalist.length; i++) {
				//添加历史信息到tab2
				var op = "<a class='weui-cell weui-cell_access' onclick='fn2(this)'><div class='weui-cell__bd'><p>"
						+ requestdata.datalist[i].imei
						+ "</p><p style='font-size: 12px; color: gray'>"
						+ timestampToTime(requestdata.datalist[i].date)
						+"</p></div><div class='weui-cell__ft'>"
						+ requestdata.datalist[i].brand
						+ "</div></a>";
												
				$("#tab2content").append(op);
			}
		}		
		//判断条数是否显示加载内容
		showandhide();
		//======================第一次访问本页面end=====================
		
		//提交按钮的单击事件
		$("#batchimei").click(function() {					
			
			//获取输入内容，并截取字符串
			var imeitext = $("#imeis").val();
			imeistr = "";//清空参数
			//非空校验
			if(imeitext=="" || imeitext==null){
				$.alert('IMEI不能为空')
				return;
			}
			//利用回车截取字符串
			var array = imeitext.split(/\r\n|[\r\n]/);
			var flag = true;//记录校验结果
			for (var i=0 ; i< array.length ; i++){	
				//空行
				if(array[i]!=null && array[i]!=""){
					varreg = /^[0-9]{15}$/;
					if (varreg.test(array[i]) && isImei(array[i])) {
						imeistr = imeistr + array[i] + ",";
					}else{
						$.alert('IMEI格式不正确:'+array[i]);
						flag = false;
						break;			
					}		
				}		
			}
			//如果校验失败就return，成功就发送ajax
			if(!flag){
				return;
			}else{
				//1.获取微信id和type
				var getwxid = $("#wechatid").val();				
				//3.发送AJAX请求
				$.ajax({
					url : 'http://localhost:8080/IMEIAPI/imeis',
					type : 'post',
					dataType : "json",
					data : {
						"imeis" : imeistr,
						"wxid": getwxid
					},
					success : function(data) {
						console.log(data);
						requestdata = data;

						//将历史数据清空，准备重新接收新数据
						 $("#xx").css("display","none");
						 $("#xx").insertAfter("#tab2");
						 $("#tab2content").empty();
						

						//将查询出的历史信息循环显示在tab2中
						for (var i = 0; i < data.datalist.length; i++) {
							//添加历史信息到tab2
							var op = "<a class='weui-cell weui-cell_access' onclick='fn2(this)'><div class='weui-cell__bd'><p>"
									+ data.datalist[i].imei
									+ "</p><p style='font-size: 12px; color: gray'>"
									+ timestampToTime(data.datalist[i].date)
									+"</p></div><div class='weui-cell__ft'>"
									+ data.datalist[i].brand
									+ "</div></a>";
															
							$("#tab2content").append(op);
						}
						//判断条数是否显示加载内容,将记录页面置为1
						showandhide();
						num = 1;
						dataDB = requestdata.datalist;//将DB修改为第一页数据
						//弹出提示信息
						$.alert(data.message);
						//转向历史记录
						$("#cx").removeClass("weui-bar__item--on");
						$("#ls").addClass("weui-bar__item--on");
						$("#tab1").removeClass("weui-tab__bd-item--active");
						$("#tab2").addClass("weui-tab__bd-item--active");
						
					},
					error : function() {
						$.alert('Ajax请求失败！')
					}
				});
			}
			
		})
	})
	

	//点击详情页
    function fn2(obj) {
		
		if($(obj).next("#xx").length == 0){
			  empty2();
			  var imei = $(obj).find("p").html();
			  $(obj).removeClass("weui-cell_access");
			  $(obj).siblings("a").addClass("weui-cell_access");
			  
			  $("#xx").insertAfter($(obj));
			  $("#xx").css("display","");
			  
			  for (var i = 0; i < dataDB.length; i++) {
					//将刚才发送的IMEI与传过来的历史数据进行对比，打印对应的数据在表格中
					if (dataDB[i].imei == imei) {
						//填充数据
						$("#imei2").val(dataDB[i].imei);
						$("#imeiid2").html(dataDB[i].imei);
						$("#brand2").html(dataDB[i].brand);
						$("#model2").html(dataDB[i].model);
						$("#manufacturer2").html(dataDB[i].manufacturer);
						
						
						
						$("#band2").html(dataDB[i].band);
						$("#allocated2").html(dataDB[i].allocated);
						$("#snr2").html(dataDB[i].snr);
					}
			  }	
		}else{

			 $(obj).addClass("weui-cell_access");
			 $(obj).siblings("a").addClass("weui-cell_access");
			  
			 $("#xx").insertAfter($("#tab2"));
			 $("#xx").css("display","none");
		}

	}
	
//==================================以下均为方法类=========================================
	//判断这个imei是否是正确的 正确返回ture 错误返回false
	function isImei(imeiString) {
		var i = 0;
		var vl_Sum1 = 0, vl_Sum2 = 0, vl_Total = 0;
		var vl_Temp = 0;
		
		for (i = 0; i < 14; i++) {
			/*(1)将奇数位数字相加(从1开始计数)*/
			if ((i % 2) == 0) {
				vl_Sum1 = vl_Sum1 + parseInt(imeiString[i]);
			} else {
				/*(2)将偶数位数字分别乘以2,分别计算个位数和十位数之和(从1开始计数)*/
				vl_Temp = (parseInt(imeiString[i])) * 2;
				if (vl_Temp < 10) {
					vl_Sum2 = vl_Sum2 + vl_Temp;
				} else {
					vl_Sum2 = vl_Sum2 + 1 + vl_Temp - 10;
				}
			}
		}
		/*(1)+(2)*/
		vl_Total = vl_Sum1 + vl_Sum2;
		/*如果得出的数个位是0则校验位为0,否则为10减去个位数 */
		if ((vl_Total % 10) == 0) {
			if(imeiString[14] == '0'){
				return true;
			}
		} else {
			if((10 - (vl_Total % 10)).toString() == imeiString[14] ){
				return true;
			}
		}
		return false;
	}
 
	//文本框清空-隐藏框
	function empty2() {
		 $("#imeiid2").html("");
		 $("#brand2").html("");
		 $("#model2").html("");
		 $("#manufacturer2").html("");
		 $("#band2").html("");
		 $("#allocated2").html("");
		 $("#snr2").html("");
	}
	//将时间戳转换成正常时间格式
    function timestampToTime(timestamp) {
        var date = new Date(timestamp);
       // return date.toLocaleString();
        var year = date.getFullYear();
        var month = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1);
        var day = ( date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate());     
        var hours = date.getHours();     
        var minutes = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes());
        var seconds = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());
  
        return  year + '/' + month + '/' + day+" "+hours+":"+minutes+":"+seconds;  
    }
	//分页查询显示状态
	function showandhide(){
		//如果总记录数不够10条，则隐藏加载图标
		//alert($("#tab2content").children('a').length);
		if($("#tab2content").children('a').length < 10){
			$(".weui-loading").hide();
			$(".weui-loadmore__tips").html("没有更多了");
		}else{
			$(".weui-loading").show();
			$(".weui-loadmore__tips").html("正在加载");
		}
	}
</script>
</body>
</html>