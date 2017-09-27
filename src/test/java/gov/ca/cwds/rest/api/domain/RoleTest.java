package gov.ca.cwds.rest.api.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class RoleTest {
  @Test
    public void shouldContainPerpetratorRole(){
      assertTrue(Role.hasRole("Perpetrator"));
    }

  @Test
  public void shouldContainVictimRole(){
    assertTrue(Role.hasRole("Victim"));
  }

  @Test
  public void shouldContainMandatedReporterRole(){
    assertTrue(Role.hasRole("Mandated Reporter"));
  }

  @Test public void shouldContainNonMandatedReporterRole(){
    assertTrue(Role.hasRole("Non-mandated Reporter"));
  }

  @Test public void shouldContainAnonymousReporter(){
    assertTrue(Role.hasRole("Anonymous Reporter"));
  }

  @Test public void shouldContainSelfReporter(){
    assertTrue(Role.hasRole("Self Reported"));
  }

  @Test public void shouldNotContainUnknownRoles(){
    assertFalse(Role.hasRole("Foo"));
  }

  @Test public void shouldNotContainNullRoles(){
    assertFalse(Role.hasRole(null));
  }
}