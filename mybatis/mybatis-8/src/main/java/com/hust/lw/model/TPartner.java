package com.hust.lw.model;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_partner
 *
 * @mbg.generated do_not_delete_during_merge
 */
public class TPartner {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_partner.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_partner.person_id
     *
     * @mbg.generated
     */
    private Long personId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_partner.place
     *
     * @mbg.generated
     */
    private String place;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_partner.id
     *
     * @return the value of t_partner.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_partner.id
     *
     * @param id the value for t_partner.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_partner.person_id
     *
     * @return the value of t_partner.person_id
     *
     * @mbg.generated
     */
    public Long getPersonId() {
        return personId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_partner.person_id
     *
     * @param personId the value for t_partner.person_id
     *
     * @mbg.generated
     */
    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_partner.place
     *
     * @return the value of t_partner.place
     *
     * @mbg.generated
     */
    public String getPlace() {
        return place;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_partner.place
     *
     * @param place the value for t_partner.place
     *
     * @mbg.generated
     */
    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }
}