package gov.ca.cwds.data.cms;

import java.util.HashSet;
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

    return null;
  }

  @Override
  public Set<SystemCode> getAllSystemCodes() {

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
    Set codes = new HashSet();
    short sysCode = 1101;
    if ("GVR_ENTC" == metaId) {
      SystemCode sacramento =
          new SystemCode(sysCode, null, "N", null, "Sacramento", "34", null, null, null);
      codes.add(sacramento);
    }

    return codes;
  }

  @Override
  public String getSystemCodeShortDescription(Number systemCodeId) {

    return null;
  }

  @Override
  public boolean verifyActiveSystemCodeIdForMeta(Number systemCodeId, String metaId,
      boolean checkCategoryIdValueIsZero) {
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

    if (SystemCodeCategoryId.REFERRAL_RESPONSE.equals(metaId)) {
      if (1520 == systemCodeId.intValue()) {
        return true;
      }
      if (1516 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.COMMUNICATION_METHOD.equals(metaId)) {
      if (409 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.INJURY_HARM_TYPE.equals(metaId)) {
      if (2179 == systemCodeId.intValue()) {
        return true;
      }
      if (2178 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.ADDRESS_TYPE.equals(metaId)) {
      if (32 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.STATE_CODE.equals(metaId)) {
      if (1828 == systemCodeId.intValue() || 1877 == systemCodeId.intValue()
          || 1823 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.COUNTY_CODE.equals(metaId)) {
      if (5542 == systemCodeId.intValue() || 5548 == systemCodeId.intValue()
          || 5550 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.LANGUAGE_CODE.equals(metaId)) {
      if (1253 == systemCodeId.intValue() || 1271 == systemCodeId.intValue()
          || 1274 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.CONTACT_LOCATION.equals(metaId)) {
      if (415 == systemCodeId.intValue() || 5524 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.APPROVAL_STATUS_TYPE.equals(metaId)) {
      if (118 == systemCodeId.intValue()) {
        return true;
      }
    }

    if (SystemCodeCategoryId.ETHNICITY.equals(metaId)) {
      if (839 == systemCodeId.intValue() || 840 == systemCodeId.intValue()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean verifyActiveLogicalIdForMeta(String logicalId, String metaId) {
    if (SystemCodeCategoryId.COUNTY_CODE.equals(metaId)) {
      if ("23".equals(logicalId) || "34".equals(logicalId) || "01".equals(logicalId) || "58".equals
          (logicalId)
          || "99".equals(logicalId)) {
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
