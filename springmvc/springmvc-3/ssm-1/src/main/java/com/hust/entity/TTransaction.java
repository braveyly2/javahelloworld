package com.hust.entity;

import com.hust.validator.CaseMode;
import com.hust.validator.CheckCase;
import com.hust.validator.ValidatorGroup1;
import com.hust.validator.ValidatorGroup2;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

public class TTransaction {

    @NotNull
    private Long productId;

    @NotNull
    private Long userId;

    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @javax.validation.constraints.NotNull(message="{items.createtime.notnull}")
    private Date date;

    @NotNull
    @DecimalMin(value = "0.1")
    private Double price;

    @Min(value=1,groups={ValidatorGroup1.class})
    @Max(value=100,groups={ValidatorGroup1.class})
    @NotNull(groups={ValidatorGroup1.class})
    private Integer quantity;

    @NotNull(groups={ValidatorGroup2.class})
    @DecimalMax(value="500000.00",groups={ValidatorGroup2.class})
    @DecimalMin(value="1.00",groups={ValidatorGroup2.class})
    private Double amount;

    @Pattern(regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$"
            ,message = "邮箱必须是xxx@xxx.xxx格式")
    private String email;

    @Size(min=10,max=256,message="{items.name.size}",groups={ValidatorGroup2.class})
    @CheckCase(value = CaseMode.LOWER,message = "note必须是小写",groups={ValidatorGroup2.class})
    private String note;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}