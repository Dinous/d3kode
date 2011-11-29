<%@ taglib prefix ="s" uri="/struts-tags" %>
<s:property value="html" escapeHtml="false"  />
<script type="text/javascript">
var id = document.getElementById('id').value;

var tableSrc = document.getElementById(id);
/* alert(document.getElementsByTagName("body")[0].childNodes);
for (var i = 0; i < collEnfants.length; i++){
 
	 alert(collEnfants[i].id);
 
}
if(tableSrc.parentNode.nodeValue != 'body'){
 */
var clone = document.getElementById(id).cloneNode(true);
//clone.style.top = parseInt(document.getElementById('y').value,0)+parseInt(50,0)+'px';
clone.style.top = parseInt(document.getElementById('y').value,0) +'px';
clone.style.left = parseInt(document.getElementById('x').value,0) +'px';
clone.style.position = 'absolute'; 
clone.style.display = "block";
add(clone);
tableSrc.id = 'delete';
$("#delete").remove();
/* } */
</script>