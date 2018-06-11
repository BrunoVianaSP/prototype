package application.test.integration;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import application.ChampionsApplication;
import application.backend.persistence.domain.backend.Plan;
import application.backend.persistence.domain.backend.Role;
import application.backend.persistence.domain.backend.User;
import application.backend.persistence.domain.backend.UserRole;
import application.enums.PlansEnum;
import application.enums.RolesEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ChampionsApplication.class)
public class UserRepositoryIntegrationTest extends AbstractIntegrationTest {

	@Rule
	public TestName testName = new TestName();

	@Before
	public void init() {
		Assert.assertNotNull(planRepository);
		Assert.assertNotNull(roleRepository);
		Assert.assertNotNull(userRepository);
	}

	@Test
	public void testCreateNewPlan() throws Exception {
		Plan basicPlan = createPlan(PlansEnum.BASIC);
		planRepository.save(basicPlan);
		Optional<Plan> retrievedPlan = planRepository.findById(PlansEnum.BASIC.getId());
		Assert.assertNotNull(retrievedPlan.get());
	}

	@Test
	public void testCreateNewRole() throws Exception {

		Role userRole = createRole(RolesEnum.BASIC);
		roleRepository.save(userRole);

		Optional<Role> retrievedRole = roleRepository.findById(RolesEnum.BASIC.getId());
		Assert.assertNotNull(retrievedRole);
	}

	@Test
	public void createNewUser() throws Exception {

		String username = testName.getMethodName();
		String email = testName.getMethodName() + "@gmail.com";

		User basicUser = createUser(username, email);

		Optional<User> newlyCreatedUser = userRepository.findById(basicUser.getId());
		Assert.assertNotNull(newlyCreatedUser);
		Assert.assertTrue(newlyCreatedUser.get().getId() != 0);
		Assert.assertNotNull(newlyCreatedUser.get().getPlan());
		Assert.assertNotNull(newlyCreatedUser.get().getPlan().getId());
		Set<UserRole> newlyCreatedUserUserRoles = newlyCreatedUser.get().getUserRoles();
		for (UserRole ur : newlyCreatedUserUserRoles) {
			Assert.assertNotNull(ur.getRole());
			Assert.assertNotNull(ur.getRole().getId());
		}

	}

	@Test
	public void testDeleteUser() throws Exception {

		String username = testName.getMethodName();
		String email = testName.getMethodName() + "@gmail.com";

		User basicUser = createUser(username, email);
		userRepository.deleteById(basicUser.getId());
	}

	@Test
	public void testGetUserByEmail() throws Exception {
		User user = createUser(testName);

		User newlyFoundUser = userRepository.findByEmail(user.getEmail());
		Assert.assertNotNull(newlyFoundUser);
		Assert.assertNotNull(newlyFoundUser.getId());
	}

	@Test
	public void testUpdateUserPassword() throws Exception {
		User user = createUser(testName);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());

		String newPassword = UUID.randomUUID().toString();

		userRepository.updateUserPassword(user.getId(), newPassword);

		Optional<User> foundUser = userRepository.findById(user.getId());
		Assert.assertEquals(newPassword, foundUser.get().getPassword());

	}

}
