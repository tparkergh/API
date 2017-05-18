package gov.ca.cwds.rest.services.cms;

import gov.ca.cwds.data.Dao;
import gov.ca.cwds.data.cms.SystemCodeDao;
import gov.ca.cwds.data.cms.SystemMetaDao;
import gov.ca.cwds.data.persistence.cms.SystemMeta;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeListResponse;
import gov.ca.cwds.rest.api.domain.cms.SystemMetaListResponse;
import gov.ca.cwds.rest.services.CrudsService;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.jadira.usertype.spi.utils.lang.StringUtils;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;

/**
 * Business layer object to work on {@link SystemCode}
 * 
 * @author CWDS API Team
 */
public class SystemCodeService implements CrudsService {

  private SystemCodeDao systemCodeDao;
  private SystemMetaDao systemMetaDao;

  /**
   * 
   * @param systemCodeDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.SystemCode} objects.
   * @param systemMetaDao The {@link Dao} handling
   *        {@link gov.ca.cwds.data.persistence.cms.SystemMeta} objects.
   */
  @Inject
  public SystemCodeService(SystemCodeDao systemCodeDao, SystemMetaDao systemMetaDao) {
    this.systemCodeDao = systemCodeDao;
    this.systemMetaDao = systemMetaDao;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#find(java.io.Serializable)
   */
  @Override
  public Response find(Serializable primaryKey) {
    assert primaryKey instanceof String;
    if (("").equals(primaryKey)) {
      SystemMeta[] sysMeta = systemMetaDao.findAll();
      ImmutableSet.Builder<gov.ca.cwds.rest.api.domain.cms.SystemMeta> builder =
          ImmutableSet.builder();
      for (SystemMeta s : sysMeta) {
        if (s != null) {
          builder.add(new gov.ca.cwds.rest.api.domain.cms.SystemMeta(s));
        }
      }
      return new SystemMetaListResponse(builder.build());
    } else {
      gov.ca.cwds.data.persistence.cms.SystemCode[] systemCodes = findByCriteria(primaryKey);
      ImmutableSet.Builder<SystemCode> builder = ImmutableSet.builder();
      for (gov.ca.cwds.data.persistence.cms.SystemCode systemCode : systemCodes) {
        if (systemCode != null) {
          builder.add(new gov.ca.cwds.rest.api.domain.cms.SystemCode(systemCode));
        }

      }
      Set<SystemCode> sysCodes = builder.build();

      return new SystemCodeListResponse(sysCodes);
    }

  }

  @SuppressWarnings("unchecked")
  public gov.ca.cwds.data.persistence.cms.SystemCode[] findByCriteria(Serializable id) {
    String foreignKeyMetaTable = id.toString();

    if (StringUtils.isNotEmpty(foreignKeyMetaTable)) {
      return systemCodeDao.findByForeignKeyMetaTable(foreignKeyMetaTable);
    }
    return new gov.ca.cwds.data.persistence.cms.SystemCode[1];
  }



  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#delete(java.io.Serializable)
   */
  @Override
  public Response delete(Serializable primaryKey) {
    assert primaryKey instanceof Long;
    throw new NotImplementedException("Delete is not implemented");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#create(gov.ca.cwds.rest.api.Request)
   */
  @Override
  public SystemCode create(Request request) {
    assert request instanceof SystemCode;

    throw new NotImplementedException("Create is not implemented");
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.services.CrudsService#update(java.io.Serializable,
   *      gov.ca.cwds.rest.api.Request)
   */
  @Override
  public SystemCode update(Serializable primaryKey, Request request) {
    assert primaryKey instanceof Long;
    assert request instanceof SystemCode;

    throw new NotImplementedException("Update is not implemented");
  }

}
