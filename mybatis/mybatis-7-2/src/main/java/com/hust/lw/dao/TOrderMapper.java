package com.hust.lw.dao;

import com.hust.lw.model.TOrder;
import com.hust.lw.model.TOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TOrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order
     *
     * @mbg.generated
     */
    long countByExample(TOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order
     *
     * @mbg.generated
     */
    int deleteByExample(TOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order
     *
     * @mbg.generated
     */
    int insert(TOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order
     *
     * @mbg.generated
     */
    int insertSelective(TOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order
     *
     * @mbg.generated
     */
    List<TOrder> selectByExample(TOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order
     *
     * @mbg.generated
     */
    TOrder selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TOrder record, @Param("example") TOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TOrder record, @Param("example") TOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TOrder record);

    List<TOrder> selectOrderByUserId(Long id);
}