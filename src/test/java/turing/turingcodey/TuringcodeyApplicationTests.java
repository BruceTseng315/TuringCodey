package turing.turingcodey;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import turing.turingcodey.data.dao.custom.UserMapper;
import turing.turingcodey.data.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class TuringcodeyApplicationTests {
	@Autowired
	UserMapper userMapper;


	@Test
	public void usermapperTest()throws Exception{
		User user = new User();
		user.setUserName("杜甫");
		user.setPassword("123456");
		userMapper.insertSelective(user);
		if(user!=null){
			System.out.println(user.getUserName()+" : "+user.getId());
		}
	}


}
