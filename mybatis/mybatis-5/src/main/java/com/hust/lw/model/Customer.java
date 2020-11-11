package com.hust.lw.model;

import com.hust.lw.enums.Gender;
import com.hust.lw.enums.Hobby;
import com.hust.lw.enums.Member;

import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table customer
 *
 * @mbg.generated do_not_delete_during_merge
 */
public class Customer {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.userName
     *
     * @mbg.generated
     */
    private String username;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.gender
     *
     * @mbg.generated
     */
    private Gender gender;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.hobby
     *
     * @mbg.generated
     */
    private Hobby hobby;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.member
     *
     * @mbg.generated
     */
    private Member member;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.userAge
     *
     * @mbg.generated
     */
    private Integer userage;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.userAddress
     *
     * @mbg.generated
     */
    private String useraddress;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column customer.reg_time
     *
     * @mbg.generated
     */
    private Date regTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.id
     *
     * @return the value of customer.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.id
     *
     * @param id the value for customer.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.userName
     *
     * @return the value of customer.userName
     *
     * @mbg.generated
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.userName
     *
     * @param username the value for customer.userName
     *
     * @mbg.generated
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.gender
     *
     * @return the value of customer.gender
     *
     * @mbg.generated
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.gender
     *
     * @param gender the value for customer.gender
     *
     * @mbg.generated
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.hobby
     *
     * @return the value of customer.hobby
     *
     * @mbg.generated
     */
    public Hobby getHobby() {
        return hobby;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.hobby
     *
     * @param hobby the value for customer.hobby
     *
     * @mbg.generated
     */
    public void setHobby(Hobby hobby) {
        this.hobby = hobby == null ? null : hobby;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.member
     *
     * @return the value of customer.member
     *
     * @mbg.generated
     */
    public Member getMember() {
        return member;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.member
     *
     * @param member the value for customer.member
     *
     * @mbg.generated
     */
    public void setMember(Member member) {
        this.member = member;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.userAge
     *
     * @return the value of customer.userAge
     *
     * @mbg.generated
     */
    public Integer getUserage() {
        return userage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.userAge
     *
     * @param userage the value for customer.userAge
     *
     * @mbg.generated
     */
    public void setUserage(Integer userage) {
        this.userage = userage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.userAddress
     *
     * @return the value of customer.userAddress
     *
     * @mbg.generated
     */
    public String getUseraddress() {
        return useraddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.userAddress
     *
     * @param useraddress the value for customer.userAddress
     *
     * @mbg.generated
     */
    public void setUseraddress(String useraddress) {
        this.useraddress = useraddress == null ? null : useraddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column customer.reg_time
     *
     * @return the value of customer.reg_time
     *
     * @mbg.generated
     */
    public Date getRegTime() {
        return regTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column customer.reg_time
     *
     * @param regTime the value for customer.reg_time
     *
     * @mbg.generated
     */
    public void setRegTime(Date regTime) {
        this.regTime = regTime == null ? null : regTime;
    }
}