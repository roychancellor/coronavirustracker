<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="region" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="format" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
	<link rel="stylesheet" href="webjars/bootstrap-select/1.9.4/css/bootstrap-select.min.css">
	<spring:url value="/resources/css/style.css" var="mainCss" />
	<spring:url value="/resources/images/header.jpg" var="headerImg" />
	<spring:url value="/resources/images/footer.jpg" var="footerImg" />
	<meta charset="UTF-8">
	<title>Corona Home</title>
	<link rel="stylesheet" href="${mainCss}" />
</head>

<body>
	<div class="container">
		<%@ include file="common/header-common.jspf" %>
	</div>
	<div class="container">
		<section>
			<region:form id="region-form" modelAttribute="region" action="${pageContext.request.contextPath}/dashboard" method="POST">
			<!-- <div class="container"> -->
			<h4 style="color:#232f98;">Click a button to see the region dashboard (reference countries at bottom)</h4>
			<!-- </div> -->
		    <div class="row">
		      <div class="col-md-3"></div>
		      <div class="col-md-6"><button class="btn btn-primary btn-lg btn-block" name="region" value=${USA} type="submit">United States as a Whole Country</button></div>
		      <div class="col-md-3"></div>
		      <div class="col-md-4"></div>
		      <div class="col-md-4"><button class="btn btn-secondary btn-md btn-block" name="region" value=${USA_NO_NY} type="submit">United States without New York State</button></div>
		      <div class="col-md-4"></div>
		    </div>
		    <h4 style="color:#232f98;">Multi-Region Picker</h4>
		    <div class="row">
			    <div class="col-md-8">
			    	<select
			    		id="states-select" name="states[]" class="selectpicker show-tick" multiple
			    		title="Select (or search for) regions, then click MAKE DASHBOARD button"
			    		data-live-search="true" data-style="btn-warning" data-width="100%">
			    		<!-- Options get made in a script tag below -->
					</select>
			    </div>
				<div class="col-md-4">
					<button class="btn btn-warning btn-md btn-block" id="multi-region-button"
						form="region-form" name="region" type="submit">MAKE DASHBOARD</button>
				</div>
		    </div>
		    <h4 style="color:#232f98;">Individual States</h4>
		    <div class="row">
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${AL} type="submit">Alabama</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${AK} type="submit">Alaska</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${AZ} type="submit">Arizona</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${AR} type="submit">Arkansas</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${CA} type="submit">California</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${CO} type="submit">Colorado</button></div>
		    </div>
		    <div class="row">
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${CT} type="submit">Connecticut</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${DE} type="submit">Delaware</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${DC} type="submit">District of Columbia</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${FL} type="submit">Florida</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${GA} type="submit">Georgia</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${HI} type="submit">Hawaii</button></div>
		    </div>
		    <div class="row">
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${ID} type="submit">Idaho</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${IL} type="submit">Illinois</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${IN} type="submit">Indiana</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${IA} type="submit">Iowa</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${KS} type="submit">Kansas</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${KY} type="submit">Kentucky</button></div>
		    </div>
		    <div class="row">
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${LA} type="submit">Louisiana</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${ME} type="submit">Maine</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${MD} type="submit">Maryland</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${MA} type="submit">Massachusetts</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${MI} type="submit">Michigan</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${MN} type="submit">Minnesota</button></div>
		    </div>
		    <div class="row">
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${MS} type="submit">Mississippi</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${MO} type="submit">Missouri</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${MT} type="submit">Montana</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${NE} type="submit">Nebraska</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${NV} type="submit">Nevada</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${NH} type="submit">New Hampshire</button></div>
		    </div>
		    <div class="row">
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${NJ} type="submit">New Jersey</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${NM} type="submit">New Mexico</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${NY} type="submit">New York</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${NC} type="submit">North Carolina</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${ND} type="submit">North Dakota</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${OH} type="submit">Ohio</button></div>
		    </div>
		    <div class="row">
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${OK} type="submit">Oklahoma</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${OR} type="submit">Oregon</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${PA} type="submit">Pennsylvania</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${RI} type="submit">Rhode Island</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${SC} type="submit">South Carolina</button></div>
		      <div class="col-md-2"><button class="btn btn-danger btn-md" name="region" value=${SD} type="submit">South Dakota</button></div>
		    </div>
		    <div class="row">
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${TN} type="submit">Tennessee</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${TX} type="submit">Texas</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${UT} type="submit">Utah</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${VT} type="submit">Vermont</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${VA} type="submit">Virginia</button></div>
		      <div class="col-md-2"><button class="btn btn-outline-dark btn-md" name="region" value=${WA} type="submit">Washington</button></div>
		    </div>
		    <div class="row">
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${WV} type="submit">West Virginia</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${WI} type="submit">Wisconsin</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${WY} type="submit">Wyoming</button></div>
		      <div class="col-md-2"><button class="btn btn-primary btn-md" name="region" value=${PRI} type="submit">Puerto Rico</button></div>
		    </div>
		    <h4 style="color:#232f98;">Reference Countries</h4>
		    <div class="row">
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${AUS} type="submit">Australia</button></div>
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${CAN} type="submit">Canada</button></div>
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${FRA} type="submit">France</button></div>
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${DEU} type="submit">Germany</button></div>
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${GBR} type="submit">Great Britain</button></div>
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${IND} type="submit">India</button></div>
		    </div>
		    <div class="row">
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${ITA} type="submit">Italy</button></div>
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${JPN} type="submit">Japan</button></div>
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${MEX} type="submit">Mexico</button></div>
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${KOR} type="submit">South Korea</button></div>
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${ESP} type="submit">Spain</button></div>
		      <div class="col-md-2"><button class="btn btn-success btn-lg" name="region" value=${SWE} type="submit">Sweden</button></div>
		    </div>
			</region:form>
		</section>
	</div>
	<div>
		<%@ include file="common/footer-common.jspf" %>
	</div>
    <!-- Setup the states selection list -->    
    <script type="text/javascript">
		var select = document.getElementById("states-select");
		select.options[select.options.length] = new Option('Far West (WA, OR, CA, NV)', 'Far West');
		select.options[select.options.length] = new Option('Rocky Mountain (MT, ID, WY, UT, CO)', 'Rocky Mountain');
		select.options[select.options.length] = new Option('Southwest (AZ, NM, OK, TX)', 'Southwest');
		select.options[select.options.length] = new Option('Plains (ND, SD, NE, KS, MN, IA, MO)', 'Plains');
		select.options[select.options.length] = new Option('Great Lakes (WI, IL, MI, IN, OH)', 'Great Lakes');
		select.options[select.options.length] = new Option('Southeast (AR, LA, KY, TN, MS, AL, WV, VA, NC, SC, GA, FL)', 'Southeast');
		select.options[select.options.length] = new Option('Mideast (NY, PA, NJ, DE, MD, DC)', 'Mideast');
		select.options[select.options.length] = new Option('New England (ME, NH, VT, MA, CT, RI)', 'New England');
		select.options[select.options.length] = new Option('Alabama', '${AL}');
		select.options[select.options.length] = new Option('Alaska', '${AK}');
		select.options[select.options.length] = new Option('Arizona', '${AZ}');
		select.options[select.options.length] = new Option('Arkansas', '${AR}');
		select.options[select.options.length] = new Option('California', '${CA}');
		select.options[select.options.length] = new Option('Colorado', '${CO}');
		select.options[select.options.length] = new Option('Connecticut', '${CT}');
		select.options[select.options.length] = new Option().setAttribute('data-divider', "true");
		select.options[select.options.length] = new Option('Delaware', '${DE}');
		select.options[select.options.length] = new Option('District of Columbia', '${DC}');
		select.options[select.options.length] = new Option('Florida', '${FL}');
		select.options[select.options.length] = new Option('Georgia', '${GA}');
		select.options[select.options.length] = new Option('Hawaii', '${HI}');
		select.options[select.options.length] = new Option('Idaho', '${ID}');
		select.options[select.options.length] = new Option('Illinois', '${IL}');
		select.options[select.options.length] = new Option('Indiana', '${IN}');
		select.options[select.options.length] = new Option('Iowa', '${IA}');
		select.options[select.options.length] = new Option('Kansas', '${KS}');
		select.options[select.options.length] = new Option('Kentucky', '${KY}');
		select.options[select.options.length] = new Option('Louisiana', '${LA}');
		select.options[select.options.length] = new Option('Maine', '${ME}');
		select.options[select.options.length] = new Option('Maryland', '${MD}');
		select.options[select.options.length] = new Option('Massachusetts', '${MA}');
		select.options[select.options.length] = new Option('Michigan', '${MI}');
		select.options[select.options.length] = new Option('Minnesota', '${MN}');
		select.options[select.options.length] = new Option('Mississippi', '${MS}');
		select.options[select.options.length] = new Option('Missouri', '${MO}');
		select.options[select.options.length] = new Option('Montana', '${MT}');
		select.options[select.options.length] = new Option('Nebraska', '${NE}');
		select.options[select.options.length] = new Option('Nevada', '${NV}');
		select.options[select.options.length] = new Option('New Hampshire', '${NH}');
		select.options[select.options.length] = new Option('New Jersey', '${NJ}');
		select.options[select.options.length] = new Option('New Mexico', '${NM}');
		select.options[select.options.length] = new Option('New York', '${NY}');
		select.options[select.options.length] = new Option('North Carolina', '${NC}');
		select.options[select.options.length] = new Option('North Dakota', '${ND}');
		select.options[select.options.length] = new Option('Ohio', '${OH}');
		select.options[select.options.length] = new Option('Oklahoma', '${OK}');
		select.options[select.options.length] = new Option('Oregon', '${OR}');
		select.options[select.options.length] = new Option('Pennsylvania', '${PA}');
		select.options[select.options.length] = new Option('Rhode Island', '${RI}');
		select.options[select.options.length] = new Option('South Carolina', '${SC}');
		select.options[select.options.length] = new Option('South Dakota', '${SD}');
		select.options[select.options.length] = new Option('Tennessee', '${TN}');
		select.options[select.options.length] = new Option('Texas', '${TX}');
		select.options[select.options.length] = new Option('Utah', '${UT}');
		select.options[select.options.length] = new Option('Vermont', '${VT}');
		select.options[select.options.length] = new Option('Virginia', '${VA}');
		select.options[select.options.length] = new Option('Washington', '${WA}');
		select.options[select.options.length] = new Option('West Virginia', '${WV}');
		select.options[select.options.length] = new Option('Wisconsin', '${WI}');
		select.options[select.options.length] = new Option('Wyoming', '${WY}');
    </script>
	
	<!-- Process the multi-region selection -->
	<script type="text/javascript">
	document.getElementById('region-form').onsubmit = function(e) {
	    //console.log("The MAKE DASHBOARD button was clicked!");
	    
	    var optionsSelected = getSelectedOptions( this.elements['states[]'] );
	    var optionStr = "MULTI:";
	    var numItems = optionsSelected.length;
	    
	    for(var i = 0; i < numItems; i++) {
	    	optionStr += optionsSelected[i].value;
	    	if (i < numItems - 1) {
	    		optionStr += ",";
	    	}
	    }
	    
	    //console.log("Setting the value of the multi-region-button to: " + optionStr)
	    document.getElementById("multi-region-button").value = optionStr;
	    //console.log("The value of the multi-region-button is: " + document.getElementById("multi-region-button").value);
	    //alert( "Options selected: " + optionStr);
	    
	    return optionStr;
	};
	
	// arguments: reference to select list, callback function (optional)
	function getSelectedOptions(sel) {
	    //console.log("In getSelectedOptions...")
		var optionsSelected = [];
	    var opt;
	    
	    // loop through options in select list
	    for (var i = 0; i < sel.options.length; i++) {
	        opt = sel.options[i];
	        
	        // check if selected
	        if ( opt.selected ) {
	            // add to array of option elements to return from this function
	            optionsSelected.push(opt);
	            //console.log("Selected: " + opt);
	        }
	    }
	    
	    // return array containing references to selected option elements
	    return optionsSelected;
	}	
	</script>
	<script src="webjars/jquery/3.1.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
    <script src="webjars/bootstrap-select/1.9.4/js/bootstrap-select.min.js"></script>
</body>
</html>