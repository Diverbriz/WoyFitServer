package com.example.woyfitserver;

import com.example.woyfitserver.auth.JwtUtil;
import com.example.woyfitserver.auth.Role;
import com.example.woyfitserver.user.User;
import com.example.woyfitserver.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class WoyFitServerApplicationTests {
	@Autowired
	private  UserService userService;
	@Autowired
	private JwtUtil jwtUtil;

	@Test
	void contextLoads() {

	}

	@Test
	public void saveUserWithRole()
			throws Exception {

		userService.saveUser(new User("test", "12345"));

	}

	@Test
	public void JwtRefresh()
			throws Exception {

	}


	@Test
	public void JwtAuth()
			throws Exception {
		Set<Role> roles = new HashSet<>();
		roles.add(new Role(1L, "ROLE_USER"));
		String token = jwtUtil.createToken(new User(1, "test", "test", true, roles));
		assert !jwtUtil.isTokenExpired(token);
	}
}
