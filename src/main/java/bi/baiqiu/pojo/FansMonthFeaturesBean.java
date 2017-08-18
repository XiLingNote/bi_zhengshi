package bi.baiqiu.pojo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 粉丝月报表
 */
@Table(name = "bi_fans_month_features")
public class FansMonthFeaturesBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private String date;
	private String feature;
	private String property;
	private Float proportion;
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

	public FansMonthFeaturesBean(String month, String feature, String property, Float proportion, Integer shopId) {
		super();
		this.setDate(month);
		this.feature = feature;
		this.property = property;
		this.proportion = proportion;
		this.shopId = shopId;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Float getProportion() {
		return proportion;
	}

	public void setProportion(Float proportion) {
		this.proportion = proportion;
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

	/** default constructor */
	public FansMonthFeaturesBean() {
	}

	public Integer getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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