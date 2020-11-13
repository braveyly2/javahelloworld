package com.hust.lw.dao;

import com.hust.lw.model.TPerson;
import com.hust.lw.model.TPersonExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TPersonMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_person
     *
     * @mbg.generated
     */
    long countByExample(TPersonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_person
     *
     * @mbg.generated
     */
    int deleteByExample(TPersonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_person
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_person
     *
     * @mbg.generated
     */
    int insert(TPerson record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_person
     *
     * @mbg.generated
     */
    int insertSelective(TPerson record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_person
     *
     * @mbg.generated
     */
    List<TPerson> selectByExample(TPersonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_person
     *
     * @mbg.generated
     */
    TPerson selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_person
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TPerson record, @Param("example") TPersonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_person
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TPerson record, @Param("example") TPersonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_person
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TPerson record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_person
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TPerson record);

    TPerson selectAll(@Param("id") Long id, @Param("type") int type);
}