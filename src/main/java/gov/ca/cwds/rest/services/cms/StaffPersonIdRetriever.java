package gov.ca.cwds.rest.services.cms;


public class StaffPersonIdRetriever {


  StaffPersonIdRetriever() {}

  /**
   * Retrieves the Staff Person Id of the current user
   * 
   * @return the last updated id for persistence, this is the Staff Person Id of the current user
   */
  public String getStaffPersonId() {
    String staffPersonId = "0X5";
    // Subject currentUser = SecurityUtils.getSubject();
    // if (currentUser != null && currentUser.getPrincipal() != null) {
    // String user = ((PerryAccount) currentUser.getPrincipal()).getUser();
    // from here we should be able to retrieve Staff Person Id from Perry
    // staffPersonId = getStaffPersonId(user);
    // for now hard-coding to "0X5" per #145709743 Save a referral with a valid hard-coded Staff ID
    // }
    return staffPersonId;
  }

}
