package cn.shop.entity;

import java.math.BigDecimal;

/**
 * @author NicholasLD
 * @createTime 2023/9/26 14:00
 */
public class ListHistory {
    private Integer id;
    private Integer shopHistoryId;
    private Integer goodId;
    private Integer count;
    private String goodName;
    private BigDecimal money;
    private BigDecimal totalPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopHistoryId() {
        return shopHistoryId;
    }

    public void setShopHistoryId(Integer shopHistoryId) {
        this.shopHistoryId = shopHistoryId;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getTotalPrice() {
        return this.money.multiply(new BigDecimal(this.count));
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return goodId + "\t\t" + goodName + "\t\t" + count + "个\t\t\t" + money + "\t\t\t" + this.getTotalPrice();
    }
}
