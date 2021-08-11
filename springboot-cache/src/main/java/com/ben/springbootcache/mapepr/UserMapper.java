package com.ben.springbootcache.mapepr;

import com.ben.springbootcache.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
    @Select("select *from user")
    List<User> findAllUser();

    @Select("select *from user where id = #{id}")
    User findById(Integer id);

    @Update("update user set name = #{name}, age = #{age} where id = #{id}")
    void updateUser(User user);

    @Delete("delete from user where id = #{id}")
    void deleteUserById(Integer id);

    @Insert("insert into user(name,age) values(#{name},#{age})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    void addUser(User user);

}
