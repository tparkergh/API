package gov.ca.cwds.data.persistence.cms;

import javax.persistence.MappedSuperclass;

import gov.ca.cwds.data.std.ApiMultiplePhonesAware;
import gov.ca.cwds.data.std.ApiPersonAware;

@MappedSuperclass
public abstract class BaseAttorney extends CmsPersistentObject
    implements ApiPersonAware, ApiMultiplePhonesAware {

  public BaseAttorney() {
    super();
  }

  public BaseAttorney(String lastUpdatedId) {
    super(lastUpdatedId);
  }

}
