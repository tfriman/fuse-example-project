
package com.sun.mdm.index.webservice;

import javax.xml.bind.annotation.*;
import java.io.Serializable;


/**
 * <p>Java class for executeCustomLogicResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="executeCustomLogicResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="return" type="{http://webservice.index.mdm.sun.com/}customLogicResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "executeCustomLogicResponse", propOrder = {
    "_return"
})
@XmlRootElement(name = "executeCustomLogicResponse")
public class ExecuteCustomLogicResponse
    implements Serializable
{

    private final static long serialVersionUID = 12343L;
    @XmlElement(name = "return")
    protected CustomLogicResponse _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link CustomLogicResponse }
     *     
     */
    public CustomLogicResponse getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomLogicResponse }
     *     
     */
    public void setReturn(CustomLogicResponse value) {
        this._return = value;
    }

    public boolean isSetReturn() {
        return (this._return!= null);
    }

}