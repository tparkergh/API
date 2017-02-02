## JMeter Tests

JMeter Tests are part of the API project as jmx files eg. *API/src/test/resources/jmeter/CmsReferralLegacyNSComparison.jmx*

The JMeter Tests are executed as part of Jenkins Continuous Integration after each build of the API project

The User Properties for JMeter are part of the api-deploy project

### CmsReferralLegacyNSComparison.jmx

This test compares a Referral created via the API New System to a Baseline Referral from the CMS Legacy System

The intent is that a referral created via the API New System should be identical to one with the same data created via the Legacy System.

### Tests for individual resources

These test the REST calls against the resource

_allegations.jmx
_cmsreferrals.jmx
_referralClients.jmx
_referrals.jmx
_reporters.jmx
_staffpersons.jmx
address_validation.jmx
addresses.jmx
autocomplete.jmx
people_search.jmx

### CmsDb2LoadTestIncludingSetUpAndTearDown.jmx

This test creates a load against the DB2 database. The tables covered include
CLIENT_T
REPTR_T
COLTRL_T


