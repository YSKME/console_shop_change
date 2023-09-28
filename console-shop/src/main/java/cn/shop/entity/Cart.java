package cn.shop.entity;

import java.math.BigDecimal;

/**
 * @author NicholasLD
 * @createTime 2023/9/26 11:54
 */
public class Cart {
    private Integer id;
    private Integer customerId;
    private Integer goodId;
    private String goodName;
    private Integer count;
    //单价
    private BigDecimal price;
    private BigDecimal totalPrice;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return this.price.multiply(new BigDecimal(this.count));
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    @Override
    public String toString() {
        return goodId+ "\t\t" + goodName + "\t\t" + count + "\t\t\t" + price + "\t\t\t" + this.getTotalPrice();
    }
}
