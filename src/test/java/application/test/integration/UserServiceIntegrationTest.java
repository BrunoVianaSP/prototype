package application.test.integration;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import application.backend.persistence.domain.backend.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserServiceIntegrationTest extends AbstractServiceIntegrationTest {

	@Rule
	public TestName testName = new TestName();

	@Test
	public void testCreateNewUser() throws Exception {

		User user = createUser(testName);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());

	}

}
