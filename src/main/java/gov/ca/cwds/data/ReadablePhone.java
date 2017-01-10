package gov.ca.cwds.data;

import java.io.Serializable;

/**
 * Simple, concrete implementation of interface {@link IPhoneAware}.
 * 
 * @author CWDS API Team
 */
public final class ReadablePhone implements IPhoneAware, Serializable {

  /**
   * Base serialization version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  private final String phoneNumber;
  private final String phoneNumberExtension;
  private final PhoneType phoneType;

  /**
   * Construct a readable phone from all required values.
   * 
   * @param phoneNumber concatenated phone number. not atomic.
   * @param phoneNumberExtension phone extension
   * @param phoneType phone type
   */
  public ReadablePhone(String phoneNumber, String phoneNumberExtension, PhoneType phoneType) {
    this.phoneNumber = phoneNumber;
    this.phoneNumberExtension = phoneNumberExtension;
    this.phoneType = phoneType;
  }

  @Override
  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  @Override
  public String getPhoneNumberExtension() {
    return this.phoneNumberExtension;
  }

  @Override
  public PhoneType getPhoneType() {
    return this.phoneType;
  }

}
