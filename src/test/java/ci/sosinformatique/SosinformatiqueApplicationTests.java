package ci.sosinformatique;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import ci.sosinformatique.service.TicketController;
@RunWith(SpringRunner.class)
@WebMvcTest(value = TicketController.class)
@WithMockUser
@SpringBootTest
class SosinformatiqueApplicationTests {
	@Test
	void contextLoads() {
		MockMvc mockMvc;
	}

}
