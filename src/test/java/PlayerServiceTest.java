import com.betbull.Application;
import com.betbull.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class PlayerServiceTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private PlayerService playerService;
}
