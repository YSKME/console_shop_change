package cn.shop.entity;

import java.math.BigDecimal;

/**
 * @author NicholasLD
 * @createTime 2023/9/26 13:58
 */
public class ShopHistory {
    private Integer id;
    private Integer customerId;
    private BigDecimal totalMoney;
    private String date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return customerId + "\t\t" + totalMoney + "\t\t" + date;
    }
}
