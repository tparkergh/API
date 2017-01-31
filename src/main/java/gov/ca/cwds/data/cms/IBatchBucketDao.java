package gov.ca.cwds.data.cms;

import java.util.List;

import gov.ca.cwds.data.persistence.PersistentObject;


/**
 * Uniform interface to pull records for batch processing.
 *
 * @param <T> persistence type
 * 
 * @author CWDS API Team
 */
public interface IBatchBucketDao<T extends PersistentObject> {

  /**
   * Result set sort order is determined by named query.
   * 
   * @param bucketNum current bucket for this batch
   * @param totalBuckets total buckets in batch run
   * @return ordered list of referral/client document records
   */
  List<T> bucketList(long bucketNum, long totalBuckets);

}
