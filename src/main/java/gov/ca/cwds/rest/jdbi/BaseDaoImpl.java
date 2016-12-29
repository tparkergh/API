package gov.ca.cwds.rest.jdbi;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.google.common.collect.ImmutableList;

import gov.ca.cwds.rest.api.persistence.PersistentObject;

/**
 * Base class for DAO with some common methods.
 * 
 * @author CWDS API Team
 * @param <T> type of {@link PersistentObject}
 */
public abstract class BaseDaoImpl<T extends PersistentObject> extends CrudsDaoImpl<T>
    implements BaseDao<T> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  public BaseDaoImpl(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.jdbi.BaseDao#findAll()
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<T> findAll() {
    final String namedQueryName = constructNamedQueryName("findAll");
    Session session = getSessionFactory().getCurrentSession();

    Transaction txn = null;
    try {
      txn = session.beginTransaction();
      Query query = session.getNamedQuery(namedQueryName);
      ImmutableList.Builder<T> entities = new ImmutableList.Builder<>();
      entities.addAll(query.list());
      txn.commit();
      return entities.build();
    } catch (HibernateException h) {
      if (txn != null) {
        txn.rollback();
      }
      throw new DaoException(h);
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.jdbi.BaseDao#findAllUpdatedAfter(java.util.Date)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<T> findAllUpdatedAfter(Date datetime) {
    final String namedQueryName = constructNamedQueryName("findAllUpdatedAfter");
    Session session = getSessionFactory().getCurrentSession();

    Transaction txn = null;
    try {
      txn = session.beginTransaction();
      Query query = session.getNamedQuery(namedQueryName).setDate("after", datetime);
      ImmutableList.Builder<T> results = new ImmutableList.Builder<>();
      results.addAll(query.list());
      txn.commit();
      return results.build();
    } catch (HibernateException h) {
      if (txn != null) {
        txn.rollback();
      }
      throw new DaoException(h);
    }
  }

  /**
   * Builds named query by the naming convention of "entity class.suffix".
   * 
   * @param suffix suffix of the named query
   * @return named query for lookup
   */
  private String constructNamedQueryName(String suffix) {
    return getEntityClass().getName() + "." + suffix;
  }
}
