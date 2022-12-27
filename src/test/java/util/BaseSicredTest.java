package util;

import org.mockito.MockitoAnnotations;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;


public class BaseSicredTest extends AbstractTestNGSpringContextTests {

  @BeforeClass()
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }
}
