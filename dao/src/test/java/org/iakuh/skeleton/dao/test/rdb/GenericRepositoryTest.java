package org.iakuh.skeleton.dao.test.rdb;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.iakuh.skeleton.dao.rdb.entity.User;
import org.iakuh.skeleton.dao.rdb.mapper.GenericMapper;
import org.iakuh.skeleton.dao.rdb.repository.GenericRepository;
import org.iakuh.skeleton.test.BaseTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GenericRepositoryTest extends BaseTest {

  @Mock
  private GenericMapper<User, Long> mapper;

  @InjectMocks
  private GenericRepository<User, Long> repository = new GenericRepository<User, Long>() {
    @Override
    protected GenericMapper<User, Long> getGenericMapper() {
      return mapper;
    }
  };

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testInsertSuccess() {
    when(mapper.insert(any())).thenReturn(1);
    User user = repository.insert(new User());
    assertNotNull(user);
  }

  @Test
  public void testInsertFail() {
    when(mapper.insert(any())).thenReturn(0);
    User user = repository.insert(new User());
    assertNull(user);
  }

  @Test
  public void testInsertSelectiveSuccess() {
    when(mapper.insertSelective(any())).thenReturn(1);
    User user = repository.insertSelective(new User());
    assertNotNull(user);
  }

  @Test
  public void testInsertSelectiveFail() {
    when(mapper.insertSelective(any())).thenReturn(0);
    User user = repository.insertSelective(new User());
    assertNull(user);
  }
}
