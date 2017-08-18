package bi.baiqiu.pojo;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Id;
import javax.persistence.Transient;

import bi.baiqiu.utils.UtilTool;

/**
 *直通车
 */

public class ThrougTrainBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private BigInteger id;
	private String date;
	private String cost;
	private String clickQuantity;
	private String clickUnit;
	private String clicktRate;
	private String transactionAmount;
	private String inputOutputRatio;
	private String goodsCollection;
	private String storeCollection;
	private String transactionsNum;
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

	public ThrougTrainBean() {
	}

	
	public ThrougTrainBean(String date, String cost, String clickQuantity, String clickUnit, String clicktRate,
			String transactionAmount, String inputOutputRatio, String goodsCollection, String storeCollection,
			String transactionsNum, String clickConversionRate, Integer shopId) {
		super();
		
		this.date = date;
		this.cost = cost;
		this.clickQuantity = clickQuantity;
		this.clickUnit = clickUnit;
		this.clicktRate = UtilTool.convertToDecimal(clicktRate);
		this.transactionAmount = transactionAmount;
		this.inputOutputRatio = UtilTool.convertToDecimal(inputOutputRatio);
		this.goodsCollection = goodsCollection;
		this.storeCollection = storeCollection;
		this.transactionsNum = transactionsNum;
		this.clickConversionRate = (clickConversionRate);
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

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getClickQuantity() {
		return clickQuantity;
	}

	public void setClickQuantity(String clickQuantity) {
		this.clickQuantity = clickQuantity;
	}

	public String getClickUnit() {
		return clickUnit;
	}

	public void setClickUnit(String clickUnit) {
		this.clickUnit = clickUnit;
	}

	public String getClicktRate() {
		return clicktRate;
	}

	public void setClicktRate(String clicktRate) {
		this.clicktRate = clicktRate;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getInputOutputRatio() {
		return inputOutputRatio;
	}

	public void setInputOutputRatio(String inputOutputRatio) {
		this.inputOutputRatio = inputOutputRatio;
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

	public String getTransactionsNum() {
		return transactionsNum;
	}

	public void setTransactionsNum(String transactionsNum) {
		this.transactionsNum = transactionsNum;
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
		// 通过逆向驼峰转换进而排序
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