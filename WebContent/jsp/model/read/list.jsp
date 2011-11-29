<%@ taglib prefix ="s" uri="/struts-tags" %>
<%@ taglib prefix ="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix ="sjt" uri="/struts-jquery-tree-tags" %>

<table width="100%">
	<tbody>
		<tr>
			<td width="65%">
			
			<s:url id="urlTree" action="model_update_item"/>
			<s:set id="contextPath"  value="#request.get('javax.servlet.forward.context_path')" />
		   	<sjt:tree 
		   		id="treeDynamicAjax" 
		   		jstreetheme="default"
		   		rootNode="nodes"
		   		childCollectionProperty="children"
		   		nodeTitleProperty="title"
		   		nodeIdProperty="id"
		   		nodeTypeProperty="type"
		   		nodeHref="%{urlTree}"
		   		nodeHrefParamName="uri"
		   		nodeTargets="result"
		   		types="{
					'valid_children' : [ 'root' ],
					'types' : {
						'root' : {
							'icon' : { 
								'image' : '%{contextPath}/icone/root.png' 
							},
							'valid_children' : [ 'folder', 'file' ],
						},
						'folder' : {
							'icon' : { 
								'image' : '%{contextPath}/icone/folder.png' 
							},
							'valid_children' : [ 'folder', 'file' ],
						},
						'file' : {
							'icon' : { 
								'image' : '%{contextPath}/icone/file.png' 
							},
							'valid_children' : [ 'none' ],
						}
				}
				}"
		   	/>
			
			<%-- <sjt:tree 
    		id="treeTypes" 
    		jstreetheme="default" 
    		openAllOnLoad="true" 
    		types="{
					'valid_children' : [ 'root' ],
					'types' : {
						'root' : {
							'icon' : { 
								'image' : '%{contextPath}/icone/root.png' 
							},
							'valid_children' : [ 'folder', 'file' ],
						},
						'folder' : {
							'icon' : { 
								'image' : '%{contextPath}/icone/folder.png' 
							},
							'valid_children' : [ 'folder', 'file' ],
						},
						'file' : {
							'icon' : { 
								'image' : '%{contextPath}/icone/file.png' 
							},
							'valid_children' : [ 'none' ],
						}
				}
		}">
    		<sjt:treeItem title="%{nodes.title}" >
    		<s:iterator value="nodes.children" status="base">
	    		<sjt:treeItem id="%{id}" title="%{title}"  type="root">
	    		<s:iterator value="children" status="traceModel">
	    		<s:url var="urlTraceModel" action="model_update_item">
	    			<s:param name="uri" value="id" />
	    		</s:url>
	    			<sjt:treeItem  id="%{id}" title="%{title}"  type="folder"
	    				 href="%{urlTraceModel}"
	    				 targets="result"> 
	    				<s:iterator value="children" status="obselType">
	    					<s:url var="urlObselType" action="model_update_item">
	    						<s:param name="uri" value="id" />
	    					</s:url>
							<sjt:treeItem id="%{id}" title="%{title}"  type="file"
								href="%{urlObselType}"
								targets="result"> 
							<s:iterator value="children" status="attributeType">
								<s:url var="urlAttributeType" action="model_update_item" >
		    						<s:param name="uri" value="id" />
		    					</s:url>
								<sjt:treeItem id="%{id}" title="%{title}"  type="file"
									href="%{urlAttributeType}"
									targets="result"
	    				 			/> 
							</s:iterator>
							</sjt:treeItem>     				
	    				</s:iterator>
	    			</sjt:treeItem> 	
	    		</s:iterator>		
	    		</sjt:treeItem> 
    		</s:iterator>
   			</sjt:treeItem> 
    	</sjt:tree> --%>
    	
    	
    	<%-- <s:iterator value="nodes.children" status="base">
    		<s:property value="title" />
	    		
	    		<s:iterator var="traceModel" value="children" status="traceModel">
	    		
	    		<s:url var="urlTraceModel" value="model_update_item">
	    			<s:param name="uri" value="id" />
	    		</s:url>
	    				<s:iterator var="obselType" value="children" status="obselType">
	    				<s:set var="idObselType" value="id" />
	    		aaaaa<s:property value="#idObselType" />
	    					<s:url var="urlObselType" value="model_update_item">
	    						<s:param name="uri" value="id" />
	    					</s:url>
							<s:iterator var="attributeType" value="children" status="attributeType">
							zzzzzz<s:property value="id" />
								<s:url var="urlAttributeType" value="model_update_item" >
		    						<s:param name="uri" value="id" />
		    					</s:url>
							</s:iterator>			
	    				</s:iterator>	    				
	    		</s:iterator>		
    		</s:iterator> --%>
    	
			
			</td>
			<td width="35%">
				<div id="result" align="right" >
				<s:text name="tree.info" />
				</div>
			</td>
		</tr>
	</tbody>
</table>