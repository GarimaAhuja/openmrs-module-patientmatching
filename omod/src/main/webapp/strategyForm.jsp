<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="View Patients, View Patient Cohorts" otherwise="/login.htm" redirect="/module/patientmatching/strategy.form" />

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="localHeader.jsp" %>

<openmrs:htmlInclude file="/dwr/interface/DWRStrategyUtilities.js" />
<openmrs:htmlInclude file="/dwr/engine.js" />
<openmrs:htmlInclude file="/dwr/util.js" />
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />

<h4>Fields for matching:</h4>
<table id="fields">
</table>
</br>

<script type="text/javascript">
function handleCallback(fields)
{
	for(var i=0;i<fields.length;i++)
	{
		var table=document.getElementById("fields");
		var row=table.insertRow(i);
		var cell1=row.insertCell(0);
		cell1.innerHTML=fields[i];
	}
}
DWRStrategyUtilities.getAllMatchingFields(handleCallback); 
</script>


<%@ include file="/WEB-INF/template/footer.jsp" %>
