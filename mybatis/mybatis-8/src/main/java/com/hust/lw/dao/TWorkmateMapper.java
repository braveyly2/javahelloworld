package com.hust.lw.dao;

import com.hust.lw.model.TWorkmate;
import com.hust.lw.model.TWorkmateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TWorkmateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_workmate
     *
     * @mbg.generated
     */
    long countByExample(TWorkmateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_workmate
     *
     * @mbg.generated
     */
    int deleteByExample(TWorkmateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_workmate
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_workmate
     *
     * @mbg.generated
     */
    int insert(TWorkmate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_workmate
     *
     * @mbg.generated
     */
    int insertSelective(TWorkmate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_workmate
     *
     * @mbg.generated
     */
    List<TWorkmate> selectByExample(TWorkmateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_workmate
     *
     * @mbg.generated
     */
    TWorkmate selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_workmate
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TWorkmate record, @Param("example") TWorkmateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_workmate
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TWorkmate record, @Param("example") TWorkmateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_workmate
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TWorkmate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_workmate
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TWorkmate record);

    List<TWorkmate> selectByPersonId(Long id);
}