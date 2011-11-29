package org.liris.mTrace.tools;


public interface Criteria {
	public boolean isEmpty();

	public String getFirstOperandeTypeLocalName();
	
	public Integer getIdCriteria() ;

	public void setIdCriteria(Integer idCriteria);

	public String getFirstOperandeType();

	public void setFirstOperandeType(String obselType);

	public String getFirstAttribute();

	public void setFirstAttribute(String firstAttribute);

	public String getSecondOperandeType();

	public void setSecondOperandeType(String operandeType);

	public String getSecondOperandeText();

	public void setSecondOperandeText(String texteOperande);
}
