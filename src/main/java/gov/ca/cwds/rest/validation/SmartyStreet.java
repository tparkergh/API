package gov.ca.cwds.rest.validation;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_street.Candidate;
import com.smartystreets.api.us_street.Client;
import com.smartystreets.api.us_street.ClientBuilder;
import com.smartystreets.api.us_street.Lookup;

import gov.ca.cwds.rest.api.domain.ValidatedAddress;

public class SmartyStreet {
  private static final Logger LOGGER = LoggerFactory.getLogger(SmartyStreet.class);
  Client client =
      new ClientBuilder("e14528e5-6ce5-1ada-e46b-e496dc5b9333", "e7QaGKlGV3L2Jlla4s2d").build();
  String streetAddress;
  String cityName;
  String stateName;
  Integer zip;
  boolean delPoint;
  Double latitude;
  Double longitude;

  public ValidatedAddress[] UsStreetSingleAddress(String street, String city, String state,
      Integer zipCode) {
    Lookup lookup = new Lookup();
    lookup.setStreet(street);
    lookup.setCity(city);
    lookup.setState(state);
    lookup.setZipCode(Integer.toString(zipCode));
    lookup.setMaxCandidates(10);

    try {
      client.send(lookup);
    } catch (SmartyException ex) {
      LOGGER.error("Unable to validate the address", ex.getMessage());
    } catch (IOException ex) {
      LOGGER.error("Unable to validate the address", ex.getMessage());
    }

    ArrayList<Candidate> results = lookup.getResult();

    ArrayList<ValidatedAddress> returnValidatedAddresses = new ArrayList<>();

    if (results.isEmpty()) {
      delPoint = false;
      longitude = null;
      latitude = null;
      ValidatedAddress address = new ValidatedAddress(streetAddress, cityName, stateName, zip,
          longitude, latitude, delPoint);
      returnValidatedAddresses.add(address);
      return returnValidatedAddresses.toArray(new ValidatedAddress[0]);
    }

    for (int i = 0; i < results.size(); i++) {

      Candidate candidate = results.get(i);

      if (("Y").equals(candidate.getAnalysis().getDpvMatchCode())
          || ("S").equals(candidate.getAnalysis().getDpvMatchCode())
          || ("D").equals(candidate.getAnalysis().getDpvMatchCode())) {
        delPoint = true;
      } else {
        delPoint = false;
      }
      longitude = candidate.getMetadata().getLongitude();
      latitude = candidate.getMetadata().getLatitude();
      streetAddress = candidate.getDeliveryLine1();
      cityName = candidate.getComponents().getCityName();
      stateName = candidate.getComponents().getState();
      zip = Integer.parseInt(candidate.getComponents().getZipCode());

      ValidatedAddress address = new ValidatedAddress(streetAddress, cityName, stateName, zip,
          longitude, latitude, delPoint);
      returnValidatedAddresses.add(address);

    }
    return returnValidatedAddresses.toArray(new ValidatedAddress[returnValidatedAddresses.size()]);
  }

}

