package com.hust.lw.dao;

import com.hust.lw.model.TFemaleHealthReport;
import com.hust.lw.model.TFemaleHealthReportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFemaleHealthReportMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_female_health_report
     *
     * @mbg.generated
     */
    long countByExample(TFemaleHealthReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_female_health_report
     *
     * @mbg.generated
     */
    int deleteByExample(TFemaleHealthReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_female_health_report
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_female_health_report
     *
     * @mbg.generated
     */
    int insert(TFemaleHealthReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_female_health_report
     *
     * @mbg.generated
     */
    int insertSelective(TFemaleHealthReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_female_health_report
     *
     * @mbg.generated
     */
    List<TFemaleHealthReport> selectByExample(TFemaleHealthReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_female_health_report
     *
     * @mbg.generated
     */
    TFemaleHealthReport selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_female_health_report
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TFemaleHealthReport record, @Param("example") TFemaleHealthReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_female_health_report
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TFemaleHealthReport record, @Param("example") TFemaleHealthReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_female_health_report
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TFemaleHealthReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_female_health_report
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TFemaleHealthReport record);

    TFemaleHealthReport selectByEmployeeId(Long id);
}