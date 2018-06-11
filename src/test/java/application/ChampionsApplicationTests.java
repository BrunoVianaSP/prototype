package application;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import application.backend.service.I18nService;

//@AutoConfigureMockMvc
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// @WebAppConfiguration
public class ChampionsApplicationTests {

	@Autowired
	I18nService i18nService;

	@Test
	public void testMessageByLocaleService() {
		String expected = "Bootstrap starter template";
		String messageId = "index.main.callout";
		String actual = i18nService.getMessage(messageId);
		assertEquals("The strings don't match", expected, actual);

	}

}
