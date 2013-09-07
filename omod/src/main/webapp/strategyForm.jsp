<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="View Patients, View Patient Cohorts" otherwise="/login.htm" redirect="/module/patientmatching/strategy.form" />

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="localHeader.jsp" %>

<openmrs:htmlInclude file="/dwr/interface/DWRStrategyUtilities.js" />
<openmrs:htmlInclude file="/dwr/engine.js" />
<openmrs:htmlInclude file="/dwr/util.js" />
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />

<h4>Fields for matching:</h4>
<thead>
<tr>
<th>Data</th>
</tr>
</thead>
<tbody id="fields">
</tbody>
</br>

<script type="text/javascript">
var v = "Hello";
document.write(v);
function handleCallback(fields)
{
	alert(fields);
	var cellFuncs = [
		  function(data) { return data; },
	];

	DWRUtil.addRows( "fields",fields, cellFuncs, {
		rowCreator:function(options) {
		var row = document.createElement("tr");
		var index = options.rowIndex;
		return row;
		},
		cellCreator:function(options) {
		var td = document.createElement("td");
		return td;
		},
		escapeHtml:false
	});
}
DWRStrategyUtilities.getAllMatchingFields(handleCallback); 
</script>


<%@ include file="/WEB-INF/template/footer.jsp" %>
