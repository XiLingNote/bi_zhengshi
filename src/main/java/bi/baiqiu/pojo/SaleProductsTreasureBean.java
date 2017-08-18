package bi.baiqiu.pojo;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import bi.baiqiu.utils.UtilTool;

/**
 * 品销宝
 */
@Table(name = "bi_sale_products_treasure")
public class SaleProductsTreasureBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private BigInteger id;
	private String date;
	private String showAmount;
	private String clickQuantity;
	private String clicktRate;
	private String cost;
	private String costPresentingThousand;
	private String clickUnit;
	private String showTransactions;
	private String transactionAmount;
	private String goodsCollection;
	private String storeCollection;
	private String goodsShoppingCartNum;
	private String displayReturnRate;
	private String clickReturnRate;
	private String clickTransactions;
	private String clickTransactionAmount;
	private String visitorNum;
	private String newVisitorsNum;
	private String clickVisitorsNum;
	private String showConversionRate;
	private String clickConversionRate;
	private Integer shopId;
	private String createTime;
	private String remark;
	@Transient
	private String startDate;
	@Transient
	private String endDate;
	@Transient
	private String sortName;
	@Transient
	private String sortOrder;
	@Transient
	private Boolean deleteHistory;
	@Transient
	private int[]idArray;

	// Constructors

	/** default constructor */
	public SaleProductsTreasureBean() {
	}

	public SaleProductsTreasureBean(String date, String showAmount, String clickQuantity, String clicktRate,
			String cost, String costPresentingThousand, String clickUnit, String showTransactions,
			String transactionAmount, String goodsCollection, String storeCollection, String goodsShoppingCartNum,
			String displayReturnRate, String clickReturnRate, String clickTransactions, String clickTransactionAmount,
			String visitorNum, String newVisitorsNum, String clickVisitorsNum, String showConversionRate,
			String clickConversionRate, Integer shopId) {
		super();
		
		
		
		
		
		this.date = date;
		this.showAmount = showAmount;
		this.clickQuantity = clickQuantity;
		this.clicktRate = UtilTool.convertToDecimal(clicktRate);
		this.cost = cost;
		this.costPresentingThousand = costPresentingThousand;
		this.clickUnit = clickUnit;
		this.showTransactions = showTransactions;
		this.transactionAmount = transactionAmount;
		this.goodsCollection = goodsCollection;
		this.storeCollection = storeCollection;
		this.goodsShoppingCartNum = goodsShoppingCartNum;
		this.displayReturnRate = UtilTool.convertToDecimal(displayReturnRate);
		this.clickReturnRate = UtilTool.convertToDecimal(clickReturnRate);
		this.clickTransactions = clickTransactions;
		this.clickTransactionAmount = clickTransactionAmount;
		this.visitorNum = visitorNum;
		this.newVisitorsNum = newVisitorsNum;
		this.clickVisitorsNum = clickVisitorsNum;
		this.showConversionRate = UtilTool.convertToDecimal(showConversionRate);
		this.clickConversionRate = UtilTool.convertToDecimal(clickConversionRate);
		this.shopId = shopId;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getShowAmount() {
		return showAmount;
	}

	public void setShowAmount(String showAmount) {
		this.showAmount = showAmount;
	}

	public String getClickQuantity() {
		return clickQuantity;
	}

	public void setClickQuantity(String clickQuantity) {
		this.clickQuantity = clickQuantity;
	}

	public String getClicktRate() {
		return clicktRate;
	}

	public void setClicktRate(String clicktRate) {
		this.clicktRate = clicktRate;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getCostPresentingThousand() {
		return costPresentingThousand;
	}

	public void setCostPresentingThousand(String costPresentingThousand) {
		this.costPresentingThousand = costPresentingThousand;
	}

	public String getClickUnit() {
		return clickUnit;
	}

	public void setClickUnit(String clickUnit) {
		this.clickUnit = clickUnit;
	}

	public String getShowTransactions() {
		return showTransactions;
	}

	public void setShowTransactions(String showTransactions) {
		this.showTransactions = showTransactions;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getGoodsCollection() {
		return goodsCollection;
	}

	public void setGoodsCollection(String goodsCollection) {
		this.goodsCollection = goodsCollection;
	}

	public String getStoreCollection() {
		return storeCollection;
	}

	public void setStoreCollection(String storeCollection) {
		this.storeCollection = storeCollection;
	}

	public String getGoodsShoppingCartNum() {
		return goodsShoppingCartNum;
	}

	public void setGoodsShoppingCartNum(String goodsShoppingCartNum) {
		this.goodsShoppingCartNum = goodsShoppingCartNum;
	}

	public String getDisplayReturnRate() {
		return displayReturnRate;
	}

	public void setDisplayReturnRate(String displayReturnRate) {
		this.displayReturnRate = displayReturnRate;
	}

	public String getClickReturnRate() {
		return clickReturnRate;
	}

	public void setClickReturnRate(String clickReturnRate) {
		this.clickReturnRate = clickReturnRate;
	}

	public String getClickTransactions() {
		return clickTransactions;
	}

	public void setClickTransactions(String clickTransactions) {
		this.clickTransactions = clickTransactions;
	}

	public String getClickTransactionAmount() {
		return clickTransactionAmount;
	}

	public void setClickTransactionAmount(String clickTransactionAmount) {
		this.clickTransactionAmount = clickTransactionAmount;
	}

	public String getVisitorNum() {
		return visitorNum;
	}

	public void setVisitorNum(String visitorNum) {
		this.visitorNum = visitorNum;
	}

	public String getNewVisitorsNum() {
		return newVisitorsNum;
	}

	public void setNewVisitorsNum(String newVisitorsNum) {
		this.newVisitorsNum = newVisitorsNum;
	}

	public String getClickVisitorsNum() {
		return clickVisitorsNum;
	}

	public void setClickVisitorsNum(String clickVisitorsNum) {
		this.clickVisitorsNum = clickVisitorsNum;
	}

	public String getShowConversionRate() {
		return showConversionRate;
	}

	public void setShowConversionRate(String showConversionRate) {
		this.showConversionRate = showConversionRate;
	}

	public String getClickConversionRate() {
		return clickConversionRate;
	}

	public void setClickConversionRate(String clickConversionRate) {
		this.clickConversionRate = clickConversionRate;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		//通过逆向驼峰转换进而排序
		Matcher matcher = Pattern.compile("[A-Z]").matcher(sortName);
	    StringBuilder builder = new StringBuilder(sortName);
	    for (int i = 0; matcher.find(); i++) {
	        builder.replace(matcher.start() + i, matcher.end() + i, "_" + matcher.group().toLowerCase());
	    }
	    if (builder.charAt(0) == '_') {
	        builder.deleteCharAt(0);
	    }
	
		this.sortName = builder.toString();
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Boolean getDeleteHistory() {
		return deleteHistory;
	}

	public void setDeleteHistory(Boolean deleteHistory) {
		this.deleteHistory = deleteHistory;
	}

	public int[] getIdArray() {
		return idArray;
	}

	public void setIdArray(int[] idArray) {
		this.idArray = idArray;
	}
}