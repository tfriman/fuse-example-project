//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.03.08 at 07:23:44 PM EET 
//


package com.customer.app;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/**
 * A specialization of Code which contains properties necessary to support LOINC-coded answers to assessment questions.
 * 
 * <p>Java class for LonicAnswerCode complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LonicAnswerCode"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.app.customer.com}NullableCode"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="laccode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="laccodesystem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="laccodesystemname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="laccodesystemversion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="score" type="{http://www.app.customer.com}Real" minOccurs="0"/&gt;
 *         &lt;element name="sequencenumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="answer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LonicAnswerCode", propOrder = {
    "laccode",
    "laccodesystem",
    "laccodesystemname",
    "laccodesystemversion",
    "score",
    "sequencenumber",
    "answer"
})
@XmlRootElement(name = "LonicAnswerCode")
public class LonicAnswerCode
    extends NullableCode
    implements Serializable
{

    private final static long serialVersionUID = 12343L;
    protected String laccode;
    protected String laccodesystem;
    protected String laccodesystemname;
    protected String laccodesystemversion;
    protected Real score;
    protected Integer sequencenumber;
    protected String answer;

    /**
     * Gets the value of the laccode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLaccode() {
        return laccode;
    }

    /**
     * Sets the value of the laccode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLaccode(String value) {
        this.laccode = value;
    }

    /**
     * Gets the value of the laccodesystem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLaccodesystem() {
        return laccodesystem;
    }

    /**
     * Sets the value of the laccodesystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLaccodesystem(String value) {
        this.laccodesystem = value;
    }

    /**
     * Gets the value of the laccodesystemname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLaccodesystemname() {
        return laccodesystemname;
    }

    /**
     * Sets the value of the laccodesystemname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLaccodesystemname(String value) {
        this.laccodesystemname = value;
    }

    /**
     * Gets the value of the laccodesystemversion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLaccodesystemversion() {
        return laccodesystemversion;
    }

    /**
     * Sets the value of the laccodesystemversion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLaccodesystemversion(String value) {
        this.laccodesystemversion = value;
    }

    /**
     * Gets the value of the score property.
     * 
     * @return
     *     possible object is
     *     {@link Real }
     *     
     */
    public Real getScore() {
        return score;
    }

    /**
     * Sets the value of the score property.
     * 
     * @param value
     *     allowed object is
     *     {@link Real }
     *     
     */
    public void setScore(Real value) {
        this.score = value;
    }

    /**
     * Gets the value of the sequencenumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSequencenumber() {
        return sequencenumber;
    }

    /**
     * Sets the value of the sequencenumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSequencenumber(Integer value) {
        this.sequencenumber = value;
    }

    /**
     * Gets the value of the answer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Sets the value of the answer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnswer(String value) {
        this.answer = value;
    }

}
