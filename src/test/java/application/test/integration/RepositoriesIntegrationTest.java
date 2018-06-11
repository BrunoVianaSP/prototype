package application.test.integration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import application.backend.persistence.domain.backend.Plan;
import application.backend.persistence.domain.backend.Role;
import application.backend.persistence.domain.backend.User;
import application.backend.persistence.domain.backend.UserRole;
import application.backend.persistence.repositories.PlanRepository;
import application.backend.persistence.repositories.RoleRepository;
import application.backend.persistence.repositories.UserRepository;
import application.enums.PlansEnum;
import application.enums.RolesEnum;
import application.utils.UserUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RepositoriesIntegrationTest {

	@Autowired
	private PlanRepository planRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Rule
	public TestName testName = new TestName();

	@Before
	public void init() {
		assertNotNull(planRepository);
		assertNotNull(roleRepository);
		assertNotNull(userRepository);
	}

	@Test
	public void testCreateNewPlan() throws Exception {
		Plan basicPlan = createPlan(PlansEnum.BASIC);
		planRepository.save(basicPlan);
		Optional<Plan> retrievedPlan = planRepository.findById(PlansEnum.BASIC.getId());
		assertNotNull(retrievedPlan);
	}

	@Test
	public void testCreateNewRole() throws Exception {

		Role userRole = createRole(RolesEnum.BASIC);
		roleRepository.save(userRole);

		Optional<Role> retrievedRole = roleRepository.findById(RolesEnum.BASIC.getId());
		assertNotNull(retrievedRole);
	}

	@Test
	public void createNewUser() throws Exception {

		String userName = testName.getMethodName();
		String email = testName.getMethodName() + "@email.com";

		User basicUser = createUser(userName, email);

		Optional<User> newlyCreatedUser = userRepository.findById(basicUser.getId());
		assertNotNull(newlyCreatedUser);
		assertTrue(newlyCreatedUser.get().getId() != 0);
		assertNotNull(newlyCreatedUser.get().getPlan());
		assertNotNull(newlyCreatedUser.get().getPlan().getId());
		Set<UserRole> newlyCreatedUserUserRoles = newlyCreatedUser.get().getUserRoles();
		for (UserRole ur : newlyCreatedUserUserRoles) {
			assertNotNull(ur.getRole());
			assertNotNull(ur.getRole().getId());
		}

	}

	@Test
	public void testDeleteUser() throws Exception {
		String userName = testName.getMethodName();
		String email = testName.getMethodName() + "@email.com";
		User basicUser = createUser(userName, email);
		userRepository.deleteById(basicUser.getId());
	}

	// -----------------> Private methods

	private Plan createPlan(PlansEnum plansEnum) {
		return new Plan(plansEnum);
	}

	private Role createRole(RolesEnum rolesEnum) {
		return new Role(rolesEnum);
	}

	private User createUser(String userName, String email) {
		Plan basicPlan = createPlan(PlansEnum.BASIC);
		planRepository.save(basicPlan);

		User basicUser = UserUtils.createBasicUser(userName, email);
		basicUser.setPlan(basicPlan);

		Role basicRole = createRole(RolesEnum.BASIC);
		roleRepository.save(basicRole);

		Set<UserRole> userRoles = new HashSet<>();
		UserRole userRole = new UserRole(basicUser, basicRole);
		userRoles.add(userRole);

		basicUser.getUserRoles().addAll(userRoles);
		basicUser = userRepository.save(basicUser);
		return basicUser;
	}

	// @Test
	// public void createNewUser() throws Exception {
	//
	// Plan basicPlan = createPlan(PlansEnum.BASIC);
	// planRepository.save(basicPlan);
	//
	// User basicUser = UsersUtils.createBasicUser();
	// basicUser.setPlan(basicPlan);
	//
	// Role basicRole = createRole(RolesEnum.BASIC);
	// Set<UserRole> userRoles = new HashSet<>();
	// UserRole userRole = new UserRole(basicUser, basicRole);
	// userRoles.add(userRole);
	//
	// basicUser.getUserRoles().addAll(userRoles);
	//
	// for (UserRole ur : userRoles) {
	// roleRepository.save(ur.getRole());
	// }
	//
	// basicUser = userRepository.save(basicUser);
	// Optional<User> newlyCreatedUser = userRepository.findById(basicUser.getId());
	//
	// assertNotNull(newlyCreatedUser);
	// assertTrue(newlyCreatedUser.get().getId() != 0);
	// assertNotNull(newlyCreatedUser.get().getPlan());
	// assertNotNull(newlyCreatedUser.get().getPlan().getId());
	// Set<UserRole> newlyCreatedUserUserRoles =
	// newlyCreatedUser.get().getUserRoles();
	// for (UserRole ur : newlyCreatedUserUserRoles) {
	// assertNotNull(ur.getRole());
	// assertNotNull(ur.getRole().getId());
	// }
	//
	// }
	//
	// //-----------------> Private methods
	// private Plan createPlan(PlansEnum plansEnum) {
	// return new Plan(plansEnum);
	// }
	//
	// private Role createRole(RolesEnum rolesEnum) {
	// return new Role(rolesEnum);
	// }
}
