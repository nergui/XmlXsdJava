package doppiogroup;



import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "order", catalog = "orderlist")
public class Order implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer OrderId;
	

	private String TradingPartnerId;
	private String  PurchaseOrderNumber;
	private String  PurchaseOrderDate;
	private Set<lineItem> LineItems = new HashSet<lineItem>(0);

	public Order(String Pid, String purchaseOrderNumber, String pDate) {
		TradingPartnerId =Pid;
		PurchaseOrderNumber=purchaseOrderNumber;
		PurchaseOrderDate=pDate;
	}
	@Id
	@GeneratedValue
	@Column(name = "OrderId", unique = true, nullable = false)
	public Integer getOrderId() {
		return OrderId;
	}
	public void setOrderId(Integer orderId) {
		OrderId = orderId;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	public Set<lineItem> getLineItems() {
		return LineItems;
	}

	public void setLineItems(Set<lineItem> lineItems) {
		LineItems = lineItems;
	}
	
	@Column(name = "TradingPartnerId", nullable = false)
	public String getTradingPartnerId() {
		return TradingPartnerId;
	}

	public void setTradingPartnerId(String tradingPartnerId) {
		TradingPartnerId = tradingPartnerId;
	}
	@Column(name = "PurchaseOrderNumber", nullable = false)
	public String getPurchaseOrderNumber() {
		return PurchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		PurchaseOrderNumber = purchaseOrderNumber;
	}
	@Column(name = "PurchaseOrderDate", nullable = false)
	public String getPurchaseOrderDate() {
		return PurchaseOrderDate;
	}

	public void setPurchaseOrderDate(String purchaseOrderDate) {
		PurchaseOrderDate = purchaseOrderDate;
	}

}
