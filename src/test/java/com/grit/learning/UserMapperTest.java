package com.grit.learning;

import com.grit.learning.entity.UserEntity;
import com.grit.learning.enums.UserSexEnum;
import com.grit.learning.mapper.UserMapper;
import com.grit.learning.param.UserParam;
import com.grit.learning.result.Page;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

	@Autowired
	private UserMapper userMapper;

	@Test
	public void testInsert() throws Exception {
		userMapper.insert(new UserEntity("aa", "a123456", UserSexEnum.MAN));
		userMapper.insert(new UserEntity("bb", "b123456", UserSexEnum.WOMAN));
		userMapper.insert(new UserEntity("cc", "b123456", UserSexEnum.WOMAN));

		Assert.assertEquals(3, userMapper.getAll().size());
	}

	@Test
	public void testQuery() throws Exception {
		List<UserEntity> users = userMapper.getAll();
		System.out.println(users.toString());
	}
	
	
	@Test
	public void testUpdate() throws Exception {
		UserEntity user = userMapper.getOne(3l);
		System.out.println(user.toString());
		user.setNickName("neo");
		userMapper.update(user);
		Assert.assertTrue(("neo".equals(userMapper.getOne(3l).getNickName())));
	}


	@Test
	public void testPage() {
		UserParam userParam=new UserParam();
		userParam.setUserSex("WOMAN");
		userParam.setUserName("neo");
		userParam.setCurrentPage(0);
		List<UserEntity> users=userMapper.getList(userParam);
		long count=userMapper.getCount(userParam);
		Page page = new Page(userParam,count,users);
		System.out.println(page);
	}
}