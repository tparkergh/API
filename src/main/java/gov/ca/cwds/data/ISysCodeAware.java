package gov.ca.cwds.data;

/**
 * Interface declares a Java bean or component as aware of CMS system codes.
 * 
 * @author CWDS API Team
 */
public interface ISysCodeAware {

  /**
   * Getter for the unique SYS_ID code.
   * 
   * @return the sys id
   */
  int getSysId();

  /**
   * Getter for sys code description.
   * 
   * @return brief description of the system code
   */
  String getDescription();

  /**
   * A system code aware bean can look up related types by sys id
   * 
   * @param sysId the system id code
   * @return another or same {@link ISysCodeAware}
   */
  ISysCodeAware lookupBySysId(int sysId);

}
