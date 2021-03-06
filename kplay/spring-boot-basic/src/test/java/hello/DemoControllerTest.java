package hello;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DemoControllerTest {

  private MockMvc mvc;

  @Autowired
  @InjectMocks private DemoController demoController;

  @Mock private ShaService shaService;

  @Mock private DatastoreService datastoreService;

  @Before
  public void setUpMock() {
    MockitoAnnotations.initMocks(this);

    mvc = MockMvcBuilders.standaloneSetup(demoController).build();
  }

  @Test
  public void testSlow_success() throws Exception {
    Mockito.when(shaService.getHashedTime()).thenReturn(DemoController.HASH_PASS + "junk");

    mvc.perform(MockMvcRequestBuilders.get("/slow").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(endsWith(DemoController.HASH_PASS + "junk")));
  }

  @Test
  public void testSlow_failure() throws Exception {
    Mockito.when(shaService.getHashedTime()).thenReturn(DemoController.HASH_FAIL + "junk");

    try {
      mvc.perform(MockMvcRequestBuilders.get("/slow").accept(MediaType.APPLICATION_JSON));
      Assert.fail(); // no exception thrown
    } catch (Exception e) {
      Assert.assertThat(e, Matchers.instanceOf(NestedServletException.class));
      Assert.assertThat(e.getCause(), Matchers.instanceOf(IllegalStateException.class));
    }
  }

  @Test
  public void testFib_withoutParam() throws Exception {

    mvc.perform(MockMvcRequestBuilders.get("/fib").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(404));

  }

  @Test
  public void testFib_withParam() throws Exception {

    mvc.perform(MockMvcRequestBuilders.get("/fib/35").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(equalTo("Fibonacci : 9227465")));
  }
}
