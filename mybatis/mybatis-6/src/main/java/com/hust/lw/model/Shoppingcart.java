package com.hust.lw.model;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table shoppingcart
 *
 * @mbg.generated do_not_delete_during_merge
 */
public class Shoppingcart {
    public Shoppingcart(){}

    public Shoppingcart(Long productId,String productName,Integer number,Double price,Double totalAmount){
        this.productId = productId;
        this.productName = productName;
        this.number = number;
        this.price = price;
        this.totalAmount = totalAmount;
    }

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shoppingcart.product_id
     *
     * @mbg.generated
     */
    private Long productId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shoppingcart.product_name
     *
     * @mbg.generated
     */
    private String productName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shoppingcart.number
     *
     * @mbg.generated
     */
    private Integer number;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shoppingcart.price
     *
     * @mbg.generated
     */
    private Double price;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column shoppingcart.total_amount
     *
     * @mbg.generated
     */
    private Double totalAmount;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shoppingcart.product_id
     *
     * @return the value of shoppingcart.product_id
     *
     * @mbg.generated
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shoppingcart.product_id
     *
     * @param productId the value for shoppingcart.product_id
     *
     * @mbg.generated
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shoppingcart.product_name
     *
     * @return the value of shoppingcart.product_name
     *
     * @mbg.generated
     */
    public String getProductName() {
        return productName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shoppingcart.product_name
     *
     * @param productName the value for shoppingcart.product_name
     *
     * @mbg.generated
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shoppingcart.number
     *
     * @return the value of shoppingcart.number
     *
     * @mbg.generated
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shoppingcart.number
     *
     * @param number the value for shoppingcart.number
     *
     * @mbg.generated
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shoppingcart.price
     *
     * @return the value of shoppingcart.price
     *
     * @mbg.generated
     */
    public Double getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shoppingcart.price
     *
     * @param price the value for shoppingcart.price
     *
     * @mbg.generated
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column shoppingcart.total_amount
     *
     * @return the value of shoppingcart.total_amount
     *
     * @mbg.generated
     */
    public Double getTotalAmount() {
        return totalAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column shoppingcart.total_amount
     *
     * @param totalAmount the value for shoppingcart.total_amount
     *
     * @mbg.generated
     */
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void init(){
        this.totalAmount = this.price * this.number;
    }
}