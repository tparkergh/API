package gov.ca.cwds.data.cms;

import java.util.Set;

import gov.ca.cwds.rest.api.domain.SystemCodeCategoryId;
import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import gov.ca.cwds.rest.api.domain.cms.SystemMeta;

@SuppressWarnings("serial")
public class TestSystemCodeCache implements SystemCodeCache {

  public TestSystemCodeCache() {
    register();
  }

  @Override
  public Set<SystemMeta> getAllSystemMetas() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<SystemCode> getAllSystemCodes() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public SystemCode getSystemCode(Number systemCodeId) {
    if (1828 == systemCodeId.intValue()) {
      return new SystemCode(systemCodeId.shortValue(), null, null, null, "California", "CA", null,
          null, null);
    }
    return null;
  }

  @Override
  public Set<SystemCode> getSystemCodesForMeta(String metaId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getSystemCodeShortDescription(Number systemCodeId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean verifyActiveSystemCodeIdForMeta(Number systemCodeId, String metaId) {
    if (456 == systemCodeId.intValue()) {
      return false;
    } else if (6404 == systemCodeId.intValue()) {
      return false;
    } else if (19 == systemCodeId.intValue()) {
      return true;
    }

    if (SystemCodeCategoryId.CROSS_REPORT_METHOD.equals(metaId)) {
      if (2095 == systemCodeId.intValue() || 0 == systemCodeId.intValue()
          || 123 == systemCodeId.intValue() || 1234 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.INJURY_HARM_TYPE.equals(metaId)) {
      if (2179 == systemCodeId.intValue()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean verifyActiveSystemCodeDescriptionForMeta(String shortDesc, String metaId) {
    if ("djdjskshahfdsa".equals(shortDesc)) {
      return false;
    } else if ("".equals(shortDesc)) {
      return false;
    } else if ("Breasts".equals(shortDesc)) {
      return true;
    }
    return false;
  }
}
