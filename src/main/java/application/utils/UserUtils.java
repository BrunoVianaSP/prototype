package application.utils;

import javax.servlet.http.HttpServletRequest;

import application.backend.persistence.domain.backend.User;
import application.web.controllers.ForgotMyPasswordController;
import application.web.domain.BasicAccountPayload;

public class UserUtils {

	/**
	 * Non instantiable.
	 */
	private UserUtils() {
		throw new AssertionError("Non instantiable");
	}

	/**
	 * Creates a user with basic attributes set.
	 * 
	 * @return A User entity
	 */
	public static User createBasicUser(String userName, String email) {

		User user = new User();
		user.setUsername(userName);
		user.setPassword("secret");
		// user.setPassword("{noop}secret");
		user.setEmail(email);
		user.setFirstName("firstName");
		user.setLastName("lastName");
		user.setPhoneNumber("123456789123");
		user.setCountry("GB");
		user.setEnabled(true);
		user.setDescription("A basic user");
		user.setProfileImageUrl("https://blabla.images.com/basicuser");

		return user;
	}

	public static String createPasswordResetUrl(HttpServletRequest request, long userId, String token) {
		String passwordResetUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + ForgotMyPasswordController.CHANGE_PASSWORD_PATH + "?id=" + userId
				+ "&token=" + token;

		return passwordResetUrl;
	}

	public static <T extends BasicAccountPayload> User fromWebUserToDomainUser(T frontendPayload) {
		User user = new User();
		user.setUsername(frontendPayload.getUsername());
		user.setPassword(frontendPayload.getPassword());
		user.setFirstName(frontendPayload.getFirstName());
		user.setLastName(frontendPayload.getLastName());
		user.setEmail(frontendPayload.getEmail());
		user.setPhoneNumber(frontendPayload.getPhoneNumber());
		user.setCountry(frontendPayload.getCountry());
		user.setEnabled(true);
		user.setDescription(frontendPayload.getDescription());

		return user;
	}

}
