package gov.ca.cwds.inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import gov.ca.cwds.logging.AuditLogger;
import gov.ca.cwds.logging.AuditLoggerImpl;

/**
 * DI for Auditing classes
 * 
 * @author CWDS API Team
 */
public class AuditingModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(AuditLogger.class).to(AuditLoggerImpl.class);
  }

  @Provides
  @gov.ca.cwds.inject.AuditLogger
  public Logger auditLogger() {
    return LoggerFactory.getLogger("AUDIT");
  }


}
