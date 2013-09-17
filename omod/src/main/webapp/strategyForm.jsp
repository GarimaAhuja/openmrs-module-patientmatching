<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="View Patients, View Patient Cohorts" otherwise="/login.htm" redirect="/module/patientmatching/strategy.form" />

<%@ include file="configForm.jsp" %>

<openmrs:htmlInclude file="/dwr/interface/DWRStrategyUtilities.js" />
<openmrs:htmlInclude file="/dwr/engine.js" />
<openmrs:htmlInclude file="/dwr/util.js" />
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />

<script type="text/javascript">
function handleCallback(fields)
{
	for(var i=0;i<fields.length;i++)
	{
		var ig = 'IG-'
		var checkMatch = ig.concat(fields[i]);
		
		k=0;
		do
		{
			var fieldIds = document.getElementsByName('configurationEntries['+k+'].inclusion');
			for(var j=0;j<fieldIds.length;j++)
			{
				if(fieldIds[j].id=='fieldName'&&fieldIds[j+1].id==checkMatch)
				{
					fieldIds[j].style.fontWeight="bold";
					fieldIds[j+2].checked=true;
				}
			}
			k++;
		} while (fieldIds.length>0)
	}
	checkConfiguration();
}
DWRStrategyUtilities.getAllSuggestedFields(handleCallback); 
</script>


<%@ include file="/WEB-INF/template/footer.jsp" %>
