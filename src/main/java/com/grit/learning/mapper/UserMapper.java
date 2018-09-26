package com.grit.learning.mapper;

import com.grit.learning.entity.UserEntity;
import com.grit.learning.enums.UserSexEnum;
import com.grit.learning.param.UserParam;
import java.util.List;
import org.apache.ibatis.annotations.*;

/**
 * 注解版最大的特点是具体的 SQL 文件需要写在 Mapper 中
 * 为了更接近生产，这里特地将 user_sex、nick_name 两个属性加了下划线，和实体类属性名不一致，
 * 另外 user_sex 使用了枚举.
 */
public interface UserMapper {

	/**
	 * @Select 是查询类的注解，所有的查询均使用这个
	 * @Result 修饰返回的结果集，关联实体类属性和数据库字段一一对应，如果实体类属性和数据库属性名保持一致，就不需要这个属性来修饰。
	 * @Insert 插入数据库使用，直接传入实体类会自动解析属性到对应的值
	 * @Update 负责修改，也可以直接传入对象
	 * @delete 负责删除
	 */

	/**
	 * 注意：使用井符号和“$”符号的不同：
	 * // This example creates a prepared statement, something like select * from teacher where name = ?;
	 * @Select("Select * from teacher where name = #{name}")
	 * Teacher selectTeachForGivenName(@Param("name") String name);
	 *
	 * // This example creates n inlined statement, something like select * from teacher where name = 'someName';
	 * @Select("Select * from teacher where name = '${name}'")
	 * Teacher selectTeachForGivenName(@Param("name") String name);
	 * 建议使用#，使用$有 SQL 注入的可能性。
	 */
	
	@Select("SELECT * FROM users")
	@Results({
		@Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
		@Result(property = "nickName", column = "nick_name")
	})
	List<UserEntity> getAll();

	/**
	 * type ：动态生成 SQL 的类
	 * method ： 类中具体的方法名
	 * @param userParam
	 * @return
	 */
	@SelectProvider(type = UserSql.class, method = "getList")
	List<UserEntity> getList(UserParam userParam);

	@SelectProvider(type = UserSql.class, method = "getCount")
	int getCount(UserParam userParam);
	
	@Select("SELECT * FROM users WHERE id = #{id}")
	@Results({
		@Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
		@Result(property = "nickName", column = "nick_name")
	})
	UserEntity getOne(Long id);

	@Insert("INSERT INTO users(userName,passWord,user_sex) VALUES(#{userName}, #{passWord}, #{userSex})")
	void insert(UserEntity user);

	@Update("UPDATE users SET userName=#{userName},nick_name=#{nickName} WHERE id =#{id}")
	void update(UserEntity user);

	@Delete("DELETE FROM users WHERE id =#{id}")
	void delete(Long id);

}