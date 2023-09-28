package cn.shop.entity;

import java.math.BigDecimal;

/**
 * @author NicholasLD
 * @createTime 2023/9/26 10:05
 */
public class Good {
    private Integer id;
    private String name;
    private String factory;
    private String date;
    private String model;
    private BigDecimal purchasePrice;
    private BigDecimal sellPrice;
    private Integer count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return id + "\t\t" + name + "\t\t\t" + factory + "\t\t" + date + "\t\t\t\t" + model + "\t\t\t\t" + purchasePrice + "\t\t\t\t" + sellPrice + "\t\t\t" + count;
    }
}
