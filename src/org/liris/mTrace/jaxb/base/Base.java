//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.09.08 at 10:30:17 AM CEST 
//


package org.liris.mTrace.jaxb.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}label"/>
 *         &lt;element ref="{}traces"/>
 *         &lt;element ref="{}traceModelFigures"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "label",
    "traces",
    "traceModelFigures"
})
@XmlRootElement(name = "base")
public class Base {

    @XmlElement(required = true)
    protected String label;
    @XmlElement(required = true)
    protected Traces traces;
    @XmlElement(required = true)
    protected TraceModelFigures traceModelFigures;

    /**
     * Gets the value of the label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabel(String value) {
        this.label = value;
    }

    /**
     * Gets the value of the traces property.
     * 
     * @return
     *     possible object is
     *     {@link Traces }
     *     
     */
    public Traces getTraces() {
        return traces;
    }

    /**
     * Sets the value of the traces property.
     * 
     * @param value
     *     allowed object is
     *     {@link Traces }
     *     
     */
    public void setTraces(Traces value) {
        this.traces = value;
    }

    /**
     * Gets the value of the traceModelFigures property.
     * 
     * @return
     *     possible object is
     *     {@link TraceModelFigures }
     *     
     */
    public TraceModelFigures getTraceModelFigures() {
        return traceModelFigures;
    }

    /**
     * Sets the value of the traceModelFigures property.
     * 
     * @param value
     *     allowed object is
     *     {@link TraceModelFigures }
     *     
     */
    public void setTraceModelFigures(TraceModelFigures value) {
        this.traceModelFigures = value;
    }

}