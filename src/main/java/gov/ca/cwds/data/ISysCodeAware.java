package gov.ca.cwds.data;

public interface ISysCodeAware {

  int getSysId();

  String getDescription();

  ISysCodeAware lookupBySysId(int sysId);

}
