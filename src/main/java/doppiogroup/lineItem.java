package doppiogroup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lineitem", catalog = "orderlist")
public class lineItem implements java.io.Serializable {
	
	private Integer LineSequenceNumber;
	private String  EAN;
	private String  OrderQtyx;
	private Order order;
	private static final long serialVersionUID = 1L;
	
	public lineItem(Integer lineNumber, String ean, String orderQtyx) {
		LineSequenceNumber=lineNumber;
		EAN=ean;
		OrderQtyx=orderQtyx;
	}
	@Id
	@Column(name = "LineSequenceNumber", nullable = false, length = 10)
	public Integer getLineSequenceNumber() {
		return LineSequenceNumber;
	}

	public void setLineSequenceNumber(Integer lineSequenceNumber) {
		LineSequenceNumber = lineSequenceNumber;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OrderId", nullable = false)
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	@Column(name = "EAN", nullable = false, length = 15)
	public String getEAN() {
		return EAN;
	}

	public void setEAN(String eAN) {
		EAN = eAN;
	}
	@Column(name = "OrderQtyx", nullable = false, length = 10)
	public String getOrderQtyx() {
		return OrderQtyx;
	}

	public void setOrderQtyx(String orderQtyx) {
		OrderQtyx = orderQtyx;
	}

}
