package org.liris.mTrace.tools;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.liris.ktbs.domain.UriResource;
import org.liris.ktbs.domain.interfaces.IAttributeType;
import org.liris.ktbs.domain.interfaces.IKtbsResource;
import org.liris.ktbs.domain.interfaces.IObselType;
import org.liris.ktbs.domain.interfaces.IPropertyStatement;
import org.liris.ktbs.domain.interfaces.IUriResource;

public class DefaultAttributeType implements IUriResource,IAttributeType {

	public static String BASE_PREFIX =":";
	public static String KTBS_PREFIX ="ktbs:";
	public static String RDFS_PREFIX ="rdfs:";
	
	public static String KTBS_NAMESPACE ="http://liris.cnrs.fr/silex/2009/ktbs#";
	public static String RDF_NAMESPACE ="http://www.w3.org/2000/01/rdf-schema#";
	
	public static String hasBegin = "http://liris.cnrs.fr/silex/2009/ktbs#hasBegin";
	public static String hasEnd = "http://liris.cnrs.fr/silex/2009/ktbs#hasEnd";
	public static String hasLabel = "http://www.w3.org/2000/01/rdf-schema#label";	
	
	private String uri;
	private String label;
	private Set<IUriResource> ranges;
	
	private DefaultAttributeType() {
	}
	
	public DefaultAttributeType(String uri) {
		this();
		Set<IUriResource> linkedHashSet = new LinkedHashSet<IUriResource>();
		//if(!uri.startsWith("http")){
			if(hasBegin.endsWith(uri)){
				setUri(hasBegin);
				linkedHashSet.add(new UriResource("http://www.w3.org/2001/XMLSchema#datetime"));
			}else if (hasEnd.endsWith(uri)) {
				setUri(hasEnd);
				linkedHashSet.add(new UriResource("http://www.w3.org/2001/XMLSchema#datetime"));
			}else if (hasLabel.endsWith(uri)) {
				setUri(hasLabel);
				linkedHashSet.add(new UriResource("http://www.w3.org/2001/XMLSchema#string"));
			}else{
				setUri("#"+uri);
				linkedHashSet.add(new UriResource("http://www.w3.org/2001/XMLSchema#string"));
			}
//		}else{
//			setUri(uri);
//		}
		setRanges(linkedHashSet);
	}
	
	public String getPrefix(){
		if(uri.startsWith("http://liris.cnrs.fr/silex/2009/ktbs")){
			return "ktbs:";
		}else if(uri.startsWith("http://www.w3.org/2000/01/rdf-schema")){
			return "rdf:";
		}else{
			return ":";
		}
	}
	
	public String getNamespace(){
		return uri.substring(0, uri.indexOf("#"));
	}
	
	@Override
	public int compareTo(IUriResource o) {
		return 0;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	@Override
	public String getUri() {
		return uri;
	}

	@Override
	public String getLocalName() {
		return uri.substring(uri.indexOf("#") + 1);
	}

	@Override
	public String getParentUri() {
		return "";
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	/* (non-Javadoc)
	 * @see org.liris.ktbs.domain.interfaces.IKtbsResource#getLabels()
	 */
	@Override
	public Set<String> getLabels() {
	    // TODO Auto-generated method stub
	    return null;
	}

	/* (non-Javadoc)
	 * @see org.liris.ktbs.domain.interfaces.IKtbsResource#setLabels(java.util.Set)
	 */
	@Override
	public void setLabels(Set<String> labels) {
	    // TODO Auto-generated method stub
	    
	}

	/* (non-Javadoc)
	 * @see org.liris.ktbs.domain.interfaces.IKtbsResource#getProperties()
	 */
	@Override
	public Set<IPropertyStatement> getProperties() {
	    // TODO Auto-generated method stub
	    return null;
	}

	/* (non-Javadoc)
	 * @see org.liris.ktbs.domain.interfaces.IKtbsResource#setProperties(java.util.Set)
	 */
	@Override
	public void setProperties(Set<IPropertyStatement> properties) {
	    // TODO Auto-generated method stub
	    
	}

	/* (non-Javadoc)
	 * @see org.liris.ktbs.domain.interfaces.IKtbsResource#addLabel(java.lang.String)
	 */
	@Override
	public void addLabel(String label) {
	    // TODO Auto-generated method stub
	    
	}

	/* (non-Javadoc)
	 * @see org.liris.ktbs.domain.interfaces.IKtbsResource#getPropertyValues(java.lang.String)
	 */
	@Override
	public Collection<Object> getPropertyValues(String propertyName) {
	    // TODO Auto-generated method stub
	    return null;
	}

	/* (non-Javadoc)
	 * @see org.liris.ktbs.domain.interfaces.IKtbsResource#getPropertyValue(java.lang.String)
	 */
	@Override
	public Object getPropertyValue(String propertyName) {
	    // TODO Auto-generated method stub
	    return null;
	}

	/* (non-Javadoc)
	 * @see org.liris.ktbs.domain.interfaces.IKtbsResource#addProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public void addProperty(String propertyName, Object value) {
	    // TODO Auto-generated method stub
	    
	}

	/* (non-Javadoc)
	 * @see org.liris.ktbs.domain.interfaces.IKtbsResource#removeProperty(java.lang.String)
	 */
	@Override
	public void removeProperty(String propertyName) {
	    // TODO Auto-generated method stub
	    
	}

	/* (non-Javadoc)
	 * @see org.liris.ktbs.domain.interfaces.IKtbsResource#getParentResource()
	 */
	@Override
	public IKtbsResource getParentResource() {
	    // TODO Auto-generated method stub
	    return null;
	}

	/* (non-Javadoc)
	 * @see org.liris.ktbs.domain.interfaces.IKtbsResource#getTypeUri()
	 */
	@Override
	public String getTypeUri() {
	    // TODO Auto-generated method stub
	    return null;
	}

	/* (non-Javadoc)
	 * @see org.liris.ktbs.domain.interfaces.WithDomain#getDomains()
	 */
	@Override
	public Set<IObselType> getDomains() {
	    // TODO Auto-generated method stub
	    return null;
	}

	/* (non-Javadoc)
	 * @see org.liris.ktbs.domain.interfaces.WithDomain#setDomains(java.util.Set)
	 */
	@Override
	public void setDomains(Set<IObselType> domains) {
	    // TODO Auto-generated method stub
	    
	}

	/* (non-Javadoc)
	 * @see org.liris.ktbs.domain.interfaces.WithRange#getRanges()
	 */
	@Override
	public Set<IUriResource> getRanges() {
	    return ranges;
	}

	/* (non-Javadoc)
	 * @see org.liris.ktbs.domain.interfaces.WithRange#setRanges(java.util.Set)
	 */
	@Override
	public void setRanges(Set<IUriResource> ranges) {
	    this.ranges = ranges;
	}

	
}
