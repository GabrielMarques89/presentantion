package design.boilerplate.springboot.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static util.MockValuesConstants.DEFAULT_USERNAME;

import design.boilerplate.springboot.model.enums.UserRole;
import design.boilerplate.springboot.model.mapper.UserMapper;
import design.boilerplate.springboot.repository.UserRepository;
import design.boilerplate.springboot.service.implementations.UserDetailsServiceImpl;
import design.boilerplate.springboot.service.implementations.UserServiceImpl;
import design.boilerplate.springboot.utils.GeneralMessageAccessor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import util.BaseSicredTest;
import util.ModelHelper;

public class UserDetailsServiceImplTest extends BaseSicredTest {

  @InjectMocks
  private UserDetailsServiceImpl service;

  @Mock
  private UserRepository repository;
  @Mock
  private UserServiceImpl userService;
  @Mock
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  @Mock
  private GeneralMessageAccessor generalMessageAccessor;

  @AfterMethod()
  public void resetMocks() {
    reset(userService);
  }

  @Test(priority = 1)
  public void testLoadUserByUsername() {
    var authUser = UserMapper.INSTANCE.convertToAuthenticatedUserDto(
        ModelHelper.mockUser(DEFAULT_USERNAME));

    when(userService.findByUsername(any())).thenReturn(authUser);
    var result = service.loadUserByUsername(DEFAULT_USERNAME);



    Assert.assertEquals(result.getUsername(), DEFAULT_USERNAME);
    Assert.assertTrue(result.getAuthorities().size() > 0);
    testAuthority(result, UserRole.USER);
  }

  @Test(priority = 0)
  public void testLoadAdminUserByUsername() {
    var authUser = UserMapper.INSTANCE.convertToAuthenticatedUserDto(
        ModelHelper.mockAdminUser());

    when(userService.findByUsername(any())).thenReturn(authUser);
    var result = service.loadUserByUsername(DEFAULT_USERNAME);

    testAuthority(result, UserRole.ADMIN);
  }

  private static void testAuthority(UserDetails result, UserRole admin) {
    SimpleGrantedAuthority authority = (SimpleGrantedAuthority) result.getAuthorities().stream()
        .findFirst().get();
    Assert.assertEquals(admin.name(), authority.getAuthority());
  }
}