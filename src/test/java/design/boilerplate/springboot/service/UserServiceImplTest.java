package design.boilerplate.springboot.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static util.DtoHelper.mockUserRegistrationRequest;

import design.boilerplate.springboot.exceptions.RegistrationException;
import design.boilerplate.springboot.model.entities.User;
import design.boilerplate.springboot.repository.UserRepository;
import design.boilerplate.springboot.service.implementations.UserServiceImpl;
import design.boilerplate.springboot.service.validations.UserValidationService;
import design.boilerplate.springboot.utils.GeneralMessageAccessor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import util.BaseSicredTest;
import util.ModelHelper;

public class UserServiceImplTest extends BaseSicredTest {

  @InjectMocks
  private UserServiceImpl service;
  @Mock
  private UserRepository repository;
  @Mock
  private UserValidationService userValidationService;
  @Mock
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Mock
  private GeneralMessageAccessor generalMessageAccessor;

  @BeforeMethod()
  public void resetCount() {
    clearInvocations(repository);
  }

  @Test
  public void testFindByUsernameSuccess() {
    String userName = "UserName";

    User mockUser = mockDefaultUser(userName);
    when(repository.findByUsername(userName)).thenReturn(mockUser);

    var result = service.findByUsername(userName);
    assertEquals(result.getUsername(), mockUser.getUsername());
    verify(repository, times(1)).findByUsername(userName);
  }

  private User mockDefaultUser(String userName) {
    var mockUser = ModelHelper.mockUser(userName);
    when(repository.findByUsername(userName)).thenReturn(mockUser);
    return mockUser;
  }

  @Test
  public void testFindByUsernameFail() {
    String userName = "UserName";

    when(repository.findByUsername(userName)).thenReturn(null);

    var result = service.findByUsername(userName);
    assertNull(result);
    verify(repository, times(1)).findByUsername(userName);
  }

  @Test
  public void testCountUsers() {
    long expected = 33L;
    when(repository.count()).thenReturn(expected);

    var result = service.countUsers();

    assertEquals(result, expected);
    verify(repository, times(1)).count();
  }

  @Test
  public void testRegistration() {
    var req = mockUserRegistrationRequest();

    when(bCryptPasswordEncoder.encode(any())).thenReturn("encodedPassword");
    when(repository.save(any())).thenReturn(ModelHelper.mockUser(req.getUsername()));
    service.registration(req);

    verify(repository, times(1)).save(any());
  }

  @Test(expectedExceptions = RegistrationException.class)
  public void testRegistrationFail() {
    var req = mockUserRegistrationRequest();

    doThrow(new RegistrationException("Error")).when(userValidationService).validateUser(any());
    service.registration(req);

    verify(repository, times(0)).save(any());
  }

  @Test
  public void testFindAuthenticatedUserByUsername() {
    String username = "username";

    mockDefaultUser(username);

    var result = service.findByUsername(username);

    assertEquals(result.getUsername(), username);
    assertNotNull(result.getUserRole());
    assertNotNull(result.getName());
    assertNotNull(result.getUserRole());
    assertNotNull(result.getPassword());
  }
}