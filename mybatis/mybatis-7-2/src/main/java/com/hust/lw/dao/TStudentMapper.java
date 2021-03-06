package com.hust.lw.dao;

import com.hust.lw.model.TStudent;
import com.hust.lw.model.TStudentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TStudentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student
     *
     * @mbg.generated
     */
    long countByExample(TStudentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student
     *
     * @mbg.generated
     */
    int deleteByExample(TStudentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student
     *
     * @mbg.generated
     */
    int insert(TStudent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student
     *
     * @mbg.generated
     */
    int insertSelective(TStudent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student
     *
     * @mbg.generated
     */
    List<TStudent> selectByExample(TStudentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student
     *
     * @mbg.generated
     */
    TStudent selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TStudent record, @Param("example") TStudentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TStudent record, @Param("example") TStudentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TStudent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TStudent record);

    List<TStudent> selectStudentByTeacherId(Long id);
}