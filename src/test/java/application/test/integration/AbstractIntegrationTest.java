package application.test.integration;

import java.util.HashSet;
import java.util.Set;

import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;

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

public abstract class AbstractIntegrationTest {

	@Autowired
	protected PlanRepository planRepository;

	@Autowired
	protected RoleRepository roleRepository;

	@Autowired
	protected UserRepository userRepository;

	protected Plan createPlan(PlansEnum plansEnum) {
		return new Plan(plansEnum);
	}

	protected Role createRole(RolesEnum rolesEnum) {
		return new Role(rolesEnum);
	}

	protected User createUser(String username, String email) {
		Plan basicPlan = createPlan(PlansEnum.BASIC);
		planRepository.save(basicPlan);

		User basicUser = UserUtils.createBasicUser(username, email);
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

	protected User createUser(TestName testName) {
		return createUser(testName.getMethodName(), testName.getMethodName() + "@devopsbuddy.com");
	}
}
