package com.grit.learning.mapper;

import com.grit.learning.param.UserParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 动态 SQL
 * 如果我们需要写动态的 SQL，或者需要写复杂的 SQL，全部写在注解中会比较麻烦，MyBatis 还提供了另外的一种支持。还是以分页为例
 * 首先定义一个 UserSql 类，提供方法拼接需要执行的 SQL
 *
 */
public class UserSql {

    private static final Logger log = LoggerFactory.getLogger(UserSql.class);

    /**
     * 可以看出 UserSql 中有一个方法 getList，使用 StringBuffer 进行 SQL 拼接，最后返回需要执行的 SQL 语句。
     * 接下来只需要在 Mapper 中引入这个类和方法即可
     * @param userParam
     * @return sql dsl
     *
     */
    public String getList(UserParam userParam) {
        StringBuffer sql = new StringBuffer("select id, userName, passWord, user_sex as userSex, nick_name as nickName");
        sql.append(" from users where 1=1 ");
        if (userParam != null) {
            if (StringUtils.isNotBlank(userParam.getUserName())) {
                sql.append(" and userName = #{userName}");
            }
            if (StringUtils.isNotBlank(userParam.getUserSex())) {
                sql.append(" and user_sex = #{userSex}");
            }
        }
        sql.append(" order by id desc");
        sql.append(" limit " + userParam.getBeginLine() + "," + userParam.getPageSize());
        log.info("getList sql is :" +sql.toString());
        return sql.toString();
    }

    /**
     * 可能会觉得这样拼接 SQL 很麻烦，SQL 语句太复杂也比较乱，别着急！MyBatis 给我们提供了一种升级的方案：结构化 SQL。
     * SELECT：表示要查询的字段，可以写多行，多行的SELECT会智能的进行合并而不会重复，FROM和WHERE跟SELECT一样，可以写多个参数，
     * 也可以在多行重复使用，最终会智能合并而不会报错。这样语句适用于写很长的 SQL，且能够保证 SQL 结构清楚，便于维护，可读性高。
     * 更多结构化的 SQL 语法参考：SQL 语句构建器类
     * @param userParam
     * @return
     */
    public String getCount(UserParam userParam) {
       String sql= new SQL(){{
            SELECT("count(1)");
            FROM("users");
            if (StringUtils.isNotBlank(userParam.getUserName())) {
                WHERE("userName = #{userName}");
            }
            if (StringUtils.isNotBlank(userParam.getUserSex())) {
                WHERE("user_sex = #{userSex}");
            }
            //从这个toString可以看出，其内部使用高效的StringBuilder实现SQL拼接
        }}.toString();

        log.info("getCount sql is :" +sql);
        return sql;
    }
}
