package application.test.unit;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import application.backend.persistence.domain.backend.User;
import application.utils.UserUtils;
import application.web.controllers.ForgotMyPasswordController;
import application.web.domain.BasicAccountPayload;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class UserUtilsUnitTest {

	private MockHttpServletRequest mockHttpServletRequest;

	private PodamFactory podamFactory;

	@Before
	public void init() {
		mockHttpServletRequest = new MockHttpServletRequest();
		podamFactory = new PodamFactoryImpl();
	}

	@Test
	public void testPasswordResetEmailUrlConstruction() throws Exception {

		mockHttpServletRequest.setServerPort(8080); // Default is 80

		String token = UUID.randomUUID().toString();
		long userId = 123456;

		String expectedUrl = "http://localhost:8080" + ForgotMyPasswordController.CHANGE_PASSWORD_PATH + "?id=" + userId
				+ "&token=" + token;

		String actualUrl = UserUtils.createPasswordResetUrl(mockHttpServletRequest, userId, token);

		Assert.assertEquals(expectedUrl, actualUrl);

	}

	@Test
	public void mapWebUserToDomainUser() {

		BasicAccountPayload webUser = podamFactory.manufacturePojo(BasicAccountPayload.class);
		webUser.setEmail("me@example.com");

		User user = UserUtils.fromWebUserToDomainUser(webUser);
		Assert.assertNotNull(user);

		Assert.assertEquals(webUser.getUsername(), user.getUsername());
		Assert.assertEquals(webUser.getPassword(), user.getPassword());
		Assert.assertEquals(webUser.getFirstName(), user.getFirstName());
		Assert.assertEquals(webUser.getLastName(), user.getLastName());
		Assert.assertEquals(webUser.getEmail(), user.getEmail());
		Assert.assertEquals(webUser.getPhoneNumber(), user.getPhoneNumber());
		Assert.assertEquals(webUser.getCountry(), user.getCountry());
		Assert.assertEquals(webUser.getDescription(), user.getDescription());

	}
}