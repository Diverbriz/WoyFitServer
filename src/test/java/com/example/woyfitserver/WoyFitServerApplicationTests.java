package com.example.woyfitserver;

import com.example.woyfitserver.auth.JwtUtil;
import com.example.woyfitserver.auth.Role;
import com.example.woyfitserver.controller.AuthController;
import com.example.woyfitserver.user.User;
import com.example.woyfitserver.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class WoyFitServerApplicationTests {
	@Autowired
	private  UserService userService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private AuthController controller;

	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

	@Test
	void contextJwtUtil() throws Exception {
		assertThat(jwtUtil).isNotNull();
	}

	@Test
	void contextUserService() throws Exception {
		assertThat(userService).isNotNull();
	}


	@Test
	public void saveUserWithRole()
			throws Exception {

		userService.saveUser(new User("test", "12345"));

	}

	@Test
	public void ExtractUsername()
			throws Exception {
		Set<Role> roles = new HashSet<>();
		roles.add(new Role(1L, "ROLE_USER"));
		String token = jwtUtil.createToken(new User(1, "test", "test", true, roles));
		assert jwtUtil.extractUsername(token).equals("test");
	}


	@Test
	public void JwtExpired()
			throws Exception {
		Set<Role> roles = new HashSet<>();
		roles.add(new Role(1L, "ROLE_USER"));
		String token = jwtUtil.createToken(new User(1, "test", "test", true, roles));
		assert !jwtUtil.isTokenExpired(token);
	}

	@Test
	public void JwtExtractToken()
			throws Exception {
		Set<Role> roles = new HashSet<>();
		roles.add(new Role(1L, "ROLE_USER"));
		String token = jwtUtil.createToken(new User(1, "test", "test", true, roles));
		jwtUtil.extractExpiration(token);
	}
}
