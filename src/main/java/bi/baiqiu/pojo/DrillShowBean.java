package bi.baiqiu.pojo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.shiro.subject.Subject;

import bi.baiqiu.utils.UtilTool;
@Table(name="bi_drill_show")
public class DrillShowBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private String date;
	private String showAmount;
	private String clickQuantity;
	private String cost;
	private String clicktRate;
	private String clickUnit;
	private String costPresentingThousand;
	private String visitorNum;
	private String depthPenetrationNum;
	private String accessTime;
	private String accessPagesNum;
	private String goodsCollection;
	private String storeCollection;
	private String goodsShoppingCartNum;
	private String orderNum;
	private String orderAmount;
	private String orderTransactionNum;
	private String orderTransactionAmount;
	private String clickConversionRate;
	private String investmentReturnRate;
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
	private int[] idArray;

	public DrillShowBean() {
	}

	public DrillShowBean(String date, String showAmount, String clickQuantity, String cost, String clicktRate,
			String clickUnit, String costPresentingThousand, String visitorNum, String depthPenetrationNum,
			String accessTime, String accessPagesNum, String goodsCollection, String storeCollection,
			String goodsShoppingCartNum, String orderNum, String orderAmount, String orderTransactionNum,
			String orderTransactionAmount, String clickConversionRate, String investmentReturnRate, Integer shopId) {
		super();
		this.date = date;
		this.showAmount = showAmount;
		this.clickQuantity = clickQuantity;
		this.cost = cost;
		this.clicktRate = UtilTool.convertToDecimal(clicktRate);
		this.clickUnit = clickUnit;
		this.costPresentingThousand = costPresentingThousand;
		this.visitorNum = visitorNum;
		this.depthPenetrationNum = depthPenetrationNum;
		this.accessTime = accessTime;
		this.accessPagesNum = accessPagesNum;
		this.goodsCollection = goodsCollection;
		this.storeCollection = storeCollection;
		this.goodsShoppingCartNum = goodsShoppingCartNum;
		this.orderNum = orderNum;
		this.orderAmount = orderAmount;
		this.orderTransactionNum = orderTransactionNum;
		this.orderTransactionAmount = orderTransactionAmount;
		this.clickConversionRate = UtilTool.convertToDecimal(clickConversionRate);
		this.investmentReturnRate = UtilTool.convertToDecimal(investmentReturnRate);
		this.shopId = shopId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getGoodsShoppingCartNum() {
		return goodsShoppingCartNum;
	}

	public void setGoodsShoppingCartNum(String goodsShoppingCartNum) {
		this.goodsShoppingCartNum = goodsShoppingCartNum;
	}

	public String getClickConversionRate() {
		return clickConversionRate;
	}

	public void setClickConversionRate(String clickConversionRate) {
		this.clickConversionRate = clickConversionRate;
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

	public String getVisitorNum() {
		return visitorNum;
	}

	public void setVisitorNum(String visitorNum) {
		this.visitorNum = visitorNum;
	}

	public String getDepthPenetrationNum() {
		return depthPenetrationNum;
	}

	public void setDepthPenetrationNum(String depthPenetrationNum) {
		this.depthPenetrationNum = depthPenetrationNum;
	}

	public String getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}

	public String getAccessPagesNum() {
		return accessPagesNum;
	}

	public void setAccessPagesNum(String accessPagesNum) {
		this.accessPagesNum = accessPagesNum;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getOrderTransactionNum() {
		return orderTransactionNum;
	}

	public void setOrderTransactionNum(String orderTransactionNum) {
		this.orderTransactionNum = orderTransactionNum;
	}

	public String getOrderTransactionAmount() {
		return orderTransactionAmount;
	}

	public void setOrderTransactionAmount(String orderTransactionAmount) {
		this.orderTransactionAmount = orderTransactionAmount;
	}

	public String getInvestmentReturnRate() {
		return investmentReturnRate;
	}

	public void setInvestmentReturnRate(String investmentReturnRate) {
		this.investmentReturnRate = investmentReturnRate;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
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

	/**根据是否具有删除历史数据权限判定是否能删除
	 * @param sub
	 */
	public boolean hasDeleteHistoryAuthority(Subject sub) {
		if (sub.isPermitted("all") || sub.isPermitted("delete_history")) {
			this.deleteHistory = true;
		}else{
			this.deleteHistory = false;
		}
		return this.deleteHistory;
	}
}