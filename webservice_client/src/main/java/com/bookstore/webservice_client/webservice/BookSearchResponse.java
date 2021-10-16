//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.3.2 生成的
// 请访问 <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a>
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2021.10.16 时间 06:05:06 PM CST
//


package com.bookstore.webservice_client.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="booklist" type="{bookSearch}bookInfo" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "booklist"
})
@XmlRootElement(name = "bookSearchResponse")
public class BookSearchResponse {

    @XmlElement(required = true)
    protected List<BookInfo> booklist;

    /**
     * Gets the value of the booklist property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the booklist property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBooklist().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BookInfo }
     *
     *
     */
    public List<BookInfo> getBooklist() {
        if (booklist == null) {
            booklist = new ArrayList<BookInfo>();
        }
        return this.booklist;
    }

}
