
package com.example.hust;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.hust.dao.StudentMapper;
import com.example.hust.dao.UserMapper;
import com.example.hust.entity.Student;
import com.example.hust.entity.Teacher;
import com.example.hust.entity.User;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@MapperScan("com.example.hust.dao")
@SpringBootTest
class HustApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Test
    void contextLoads() {
    }

    //BaseMapper中17种方法的一种
    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        for(User user:userList) {
            System.out.println(user);
        }
    }

    //注释方式，自定义mapper接口
    @Test
    public void testSelectBySql() {
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.like("name" , "%boss%").lt("age", 50).last("limit 4");
        List<User> userList = userMapper.selectBySql(wrapper);
        userList.forEach(System.out::println);
    }

    //xml方式，自定义mapper接口
    @Test
    public void testSelectByName() {
        List<User> userList = userMapper.selectByName("大boss");
        userList.forEach(System.out::println);
    }

    //BaseMapper中17种方法的一种
    @Test
    public void testSelectStudent() {
        System.out.println(("----- selectAll method test ------"));
        List<Student> studentList = studentMapper.selectList(null);
        for(Student student:studentList) {
            System.out.println(student);
        }
    }

    //测试四种querywrapper
    @Test
    public void testFourQueryWrapper() {
        System.out.println(("----- testThreeQueryWrapper method test ------"));

        System.out.println(("------------ QueryWrapper -----------"));
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name","大BOSS");
        List<Student> studentList = studentMapper.selectList(queryWrapper);
        for(Student student:studentList) {
            System.out.println(student);
        }

        System.out.println(("------------ LambdaQueryWrapper -----------"));
        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<Student>();
        lambdaQueryWrapper.eq(Student::getReal_name,"王天风");
        List<Student> studentList2 = studentMapper.selectList(lambdaQueryWrapper);
        for(Student student:studentList2) {
            System.out.println(student);
        }

        System.out.println(("------------ LambdaQueryChainWrapper -----------"));
        LambdaQueryWrapper<Student> lambdaQueryWrapper3 = new LambdaQueryWrapper<Student>();
        List<Student> studentList3 = new LambdaQueryChainWrapper<>(studentMapper).eq(Student::getReal_name,"李艺伟").list();
        for(Student student:studentList3) {
            System.out.println(student);
        }

        System.out.println(("------------ Wrappers.lambdaQuery() -----------"));
        LambdaQueryWrapper<Student> lambdaQueryWrapper4 =  Wrappers.lambdaQuery();
        lambdaQueryWrapper4.eq(Student::getReal_name,"张雨琪");
        List<Student> studentList4 = studentMapper.selectList(lambdaQueryWrapper4);
        for(Student student:studentList4) {
            System.out.println(student);
        }
    }

    //测试三种updatewrapper
    @Test
    public void testThreeUpdateWrapper() {
        System.out.println(("----- testThreeUpdateWrapper method test ------"));

        System.out.println(("------------ UpdateWrapper -----------"));
        UpdateWrapper<Student> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name","大BOSS");
        Student student = new Student();
        student.setAge(45);
        int affectRows = studentMapper.update(student,updateWrapper);
        if(affectRows>0){
            System.out.println("UpdateWrapper update succeed");
        }else{
            System.out.println("UpdateWrapper update failed");
        }


        System.out.println(("------------ lambdaUpdateWrapper -----------"));
        LambdaUpdateWrapper<Student> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Student::getReal_name,"大BOSS");
        Student student2 = new Student();
        student2.setEmail("123456@fenda.com");
        student2.setAge(55);
        int affectRows2 = studentMapper.update(student2,lambdaUpdateWrapper);
        if(affectRows>0){
            System.out.println("lambdaUpdateWrapper update succeed");
        }else{
            System.out.println("lambdaUpdateWrapper update failed");
        }

        System.out.println(("------------ lambdaUpdateWrapper -----------"));
        LambdaUpdateWrapper<Student> lambdaUpdateWrapper3 = Wrappers.lambdaUpdate();
        lambdaUpdateWrapper3.eq(Student::getReal_name,"大BOSS");
        Student student3 = new Student();
        student3.setEmail("123456@hust.com");
        student3.setAge(65);
        int affectRows3 = studentMapper.update(student3,lambdaUpdateWrapper3);
        if(affectRows3>0){
            System.out.println("Wrappers.lambdaUpdate() update succeed");
        }else{
            System.out.println("Wrappers.lambdaUpdate() update failed");
        }

        System.out.println(("------------ lambdaUpdateWrapper  仅更新一个字段 -----------"));
        LambdaUpdateWrapper<Student> lambdaUpdateWrapper4 = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper4.eq(Student::getReal_name,"大BOSS");
        lambdaUpdateWrapper4.set(Student::getAge,999);
        int affectRows4 = studentMapper.update(null,lambdaUpdateWrapper4);
        if(affectRows>0){
            System.out.println("lambdaUpdateWrapper4 update succeed");
        }else{
            System.out.println("lambdaUpdateWrapper4 update failed");
        }
    }

    //测试四种delete
    @Test
    public void testThreeDeleteWrapper() {
        System.out.println(("----- testThreeDeleteWrapper method test ------"));

        System.out.println(("------------ deleteById -----------"));
        int affectedRows = studentMapper.deleteById(1087982257332887553L);
        if(affectedRows>0){
            System.out.println("deletebyid succeed affectedRows=" + affectedRows);
        }else{
            System.out.println("deletebyid failed affectedRows=" + affectedRows);
        }


        System.out.println(("------------ deleteBatchIds -----------"));

        int affectedRows2 = studentMapper.deleteBatchIds(Arrays.asList(1087982257332887553L,1088248166370832385L));
        if(affectedRows2>0){
            System.out.println("deleteBatchIds succeed affectedRows2=" + affectedRows2);
        }else{
            System.out.println("deleteBatchIds failed affectedRows2=" + affectedRows2);
        }


        System.out.println(("------------ deleteByMap -----------"));
        //删除map中条件都满足的item
        HashMap<String,Object> colMap = new HashMap<>();
        colMap.put("name","张雨琪");
        colMap.put("age",31);
        int affectedRows3 = studentMapper.deleteByMap(colMap);
        if(affectedRows3>0){
            System.out.println("deleteByMap succeed affectedRows3="+affectedRows3);
        }else{
            System.out.println("deleteByMap failed affectedRows3="+affectedRows3);
        }

        System.out.println(("------------ delete -----------"));
        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<Student>();
        lambdaQueryWrapper.eq(Student::getReal_name,"刘红雨");
        int affectedRows4 = studentMapper.delete(lambdaQueryWrapper);
        if(affectedRows4>0){
            System.out.println("delete succeed affectedRows4="+affectedRows4);
        }else{
            System.out.println("delete failed affectedRows4="+affectedRows4);
        }
    }

    @Test
    public void testPage(){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
        lambdaQueryWrapper.between(User::getAge,20,35);

        IPage<User> userPage = new Page<>(2, 2);
        userPage = userMapper.selectPage(userPage,lambdaQueryWrapper);
        List<User> list = userPage.getRecords();
        for(User user:list){
            System.out.println(user);
        }
    }

    @Test
    public void testTeacher(){
        Teacher teacher = new Teacher();
        teacher.setId(1087982257332887553L);
        teacher.setAge(100);
        teacher.setEmail("nick@fenda.com");
        teacher.setSchool("hust");
        teacher.insert();
        //Teacher teacher1 = teacher.selectById();
        //System.out.println(teacher1);
        //teacher.selectAll().forEach(System.out::println);
    }

    @Test
    public void testPrimaryKey(){
        User user = new User();
        user.setAge(1002);
        user.setEmail("liwei2@fenda.com");
        user.setName("liwei2");
        int affectedRow = userMapper.insert(user);
        if(affectedRow>0){
            System.out.println("succeed testPrimaryKey affectedRow="+affectedRow);
        }else{
            System.out.println("failed testPrimaryKey affectedRow="+affectedRow);
        }
    }
}
