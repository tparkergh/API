## JMeter Tests

JMeter Tests are part of the API project as jmx files eg. *API/src/test/resources/jmeter/CmsReferralLegacyNSComparison.jmx*

Some JMeter Tests are executed as part of Jenkins Continuous Integration after each build of the API project

The User Properties for JMeter are part of the api-deploy project

### CmsReferralLegacyNSComparison.jmx

This test compares a Referral created via the API New System to a Baseline Referral from the CMS Legacy System

The intent is that a referral created via the API New System should be identical to one with the same data created via the Legacy System.

### Tests for individual resources

These test REST calls against the resource

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

For this test The following properties are added to JMeter user.properties

load.test.number.of.users
load.test.loops
load.test.tabledata
load.test.db2.url
load.test.db2.user
load.test.db2.password

This test creates a load against the DB2 database. The data created as part of the test is cleared up when the test is complete. The tables covered include
CLIENT_T
REPTR_T
COLTRL_T
OTH_KIDT
EDPRVCNT
OTH_ADLT
ATTRNY_T

