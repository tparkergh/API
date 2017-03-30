package gov.ca.cwds.rest.util.jni;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;
import javax.naming.spi.NamingManager;
import javax.sql.XADataSource;

import com.ibm.db2.jcc.DB2XADataSource;

public class XASample {

  javax.sql.XADataSource xaDS1;
  javax.sql.XADataSource xaDS2;
  javax.sql.XAConnection xaconn1;
  javax.sql.XAConnection xaconn2;
  javax.transaction.xa.XAResource xares1;
  javax.transaction.xa.XAResource xares2;
  java.sql.Connection conn1;
  java.sql.Connection conn2;

  public static void main(String args[]) throws java.sql.SQLException {
    XASample xat = new XASample();
    xat.runThis(args);
  }

  private static void setupInitialContext() {
    try {
      NamingManager.setInitialContextFactoryBuilder(new InitialContextFactoryBuilder() {

        @Override
        public InitialContextFactory createInitialContextFactory(Hashtable<?, ?> environment)
            throws NamingException {
          return new InitialContextFactory() {

            @Override
            public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
              return new InitialContext() {

                private Hashtable<String, XADataSource> dataSources = new Hashtable<>();

                @Override
                public Object lookup(String name) throws NamingException {

                  if (dataSources.isEmpty()) { // init datasources

                    // MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();

                    // DOCKER:
                    // final String urlDocker = "jdbc:db2://localhost:50000/DB0TDEV";
                    final String userDocker = System.getenv("DB2_DOCKER_USER");
                    final String passwordDocker = System.getenv("DB2_DOCKER_PASSWORD");

                    // MAINFRAME:
                    // final String urlFrame =
                    // "jdbc:db2://localhost:9000/DB0TSOC:retrieveMessagesFromServerOnGetMessage=true";
                    final String userFrame = System.getenv("DB2_FRAME_USER");
                    final String passwordFrame = System.getenv("DB2_FRAME_PASSWORD");

                    DB2XADataSource ds1 = new DB2XADataSource();
                    ds1.setUser(userFrame);
                    ds1.setPassword(passwordFrame);
                    ds1.setServerName("localhost");
                    ds1.setPortNumber(9000);
                    ds1.setDatabaseName("DB0TSOC");
                    ds1.setDriverType(4);
                    dataSources.put("jdbc/ns1", ds1);

                    DB2XADataSource ds2 = new DB2XADataSource();
                    ds2.setUser(userDocker);
                    ds2.setPassword(passwordDocker);
                    ds1.setServerName("localhost");
                    ds2.setPortNumber(50000);
                    ds2.setDatabaseName("DB0TDEV");
                    ds1.setDriverType(4);
                    dataSources.put("jdbc/ns2", ds2);
                  }

                  if (dataSources.containsKey(name)) {
                    return dataSources.get(name);
                  }

                  throw new NamingException("Unable to find datasource: " + name);
                }
              };
            }

          };
        }

      });
    } catch (NamingException ne) {
      ne.printStackTrace();
    }
  }

  /**
   * As the transaction manager, this program supplies the global transaction ID and the branch
   * qualifier. The global transaction ID and the branch qualifier must not be equal to each other,
   * and the combination must be unique for this transaction manager.
   * 
   * @param args dunno
   */
  public void runThis(String[] args) {

    byte[] gtrid = new byte[] {0x44, 0x11, 0x55, 0x66};
    byte[] bqual = new byte[] {0x00, 0x22, 0x00};
    int rc1 = 0;
    int rc2 = 0;

    try {
      Class.forName("com.ibm.db2.jcc.DB2Driver");
      setupInitialContext();

      // DOCKER:
      // final String urlDocker = "jdbc:db2://localhost:50000/DB0TDEV";
      final String userDocker = System.getenv("DB2_DOCKER_USER");
      final String passwordDocker = System.getenv("DB2_DOCKER_PASSWORD");

      // Note that javax.sql.XADataSource is used instead of a specific driver implementation such
      // as com.ibm.db2.jcc.DB2XADataSource.
      xaDS1 = (javax.sql.XADataSource) InitialContext.doLookup("jdbc/ns1");
      xaDS2 = (javax.sql.XADataSource) InitialContext.doLookup("jdbc/ns2");

      // The XADatasource contains the user ID and password.
      // Get the XAConnection object from each XADataSource
      xaconn1 = xaDS1.getXAConnection();
      xaconn2 = xaDS2.getXAConnection();

      // Get the java.sql.Connection object from each XAConnection
      conn1 = xaconn1.getConnection();
      conn2 = xaconn2.getConnection();

      // Get the XAResource object from each XAConnection
      xares1 = xaconn1.getXAResource();
      xares2 = xaconn2.getXAResource();

      // Create the Xid object for this distributed transaction.
      // This example uses the com.ibm.db2.jcc.DB2Xid implementation of the Xid interface. This Xid
      // can be used with any JDBC driver that supports JTA.
      javax.transaction.xa.Xid xid1 = new com.ibm.db2.jcc.DB2Xid(100, gtrid, bqual);

      // Start the distributed transaction on the two connections.
      // The two connections do NOT need to be started and ended together.
      // They might be done in different threads, along with their SQL operations.
      xares1.start(xid1, javax.transaction.xa.XAResource.TMNOFLAGS);
      xares2.start(xid1, javax.transaction.xa.XAResource.TMNOFLAGS);

      // TODO: ADD SQL HERE!
      // Run DML on connection 1.
      // Run DML on connection 2.

      // Now end the distributed transaction on the two connections.
      xares1.end(xid1, javax.transaction.xa.XAResource.TMSUCCESS);
      xares2.end(xid1, javax.transaction.xa.XAResource.TMSUCCESS);

      // If connection 2 work had been done in another thread,
      // a thread.join() call would be needed here to wait until the
      // connection 2 work is done.

      try { // Now prepare both branches of the distributed transaction.
            // Both branches must prepare successfully before changes can be committed.
            // If the distributed transaction fails, an XAException is thrown.
        rc1 = xares1.prepare(xid1);
        if (rc1 == javax.transaction.xa.XAResource.XA_OK) { // Prepare was successful. Prepare the
                                                            // second connection.
          rc2 = xares2.prepare(xid1);
          if (rc2 == javax.transaction.xa.XAResource.XA_OK) { // Both connections prepared
                                                              // successfully and neither was
                                                              // read-only.
            xares1.commit(xid1, false);
            xares2.commit(xid1, false);
          } else if (rc2 == javax.transaction.xa.XAException.XA_RDONLY) { // The second connection
                                                                          // is read-only, so just
                                                                          // commit the
                                                                          // first connection.
            xares1.commit(xid1, false);
          }
        } else if (rc1 == javax.transaction.xa.XAException.XA_RDONLY) { // SQL for the first
                                                                        // connection is read-only
                                                                        // (such as a SELECT).
                                                                        // The prepare committed it.
                                                                        // Prepare the second
                                                                        // connection.
          rc2 = xares2.prepare(xid1);
          if (rc2 == javax.transaction.xa.XAResource.XA_OK) { // The first connection is read-only
                                                              // but the second is not.
                                                              // Commit the second connection.
            xares2.commit(xid1, false);
          } else if (rc2 == javax.transaction.xa.XAException.XA_RDONLY) { // Both connections are
                                                                          // read-only, and both
                                                                          // already committed,
                                                                          // so there is nothing
                                                                          // more to do.
          }
        }
      } catch (javax.transaction.xa.XAException xae) { // Distributed transaction failed, so roll it
                                                       // back.
                                                       // Report XAException on prepare/commit.
        System.out.println("Distributed transaction prepare/commit failed. " + "Rolling it back.");
        System.out.println("XAException error code = " + xae.errorCode);
        System.out.println("XAException message = " + xae.getMessage());
        xae.printStackTrace();

        try {
          xares1.rollback(xid1);
        } catch (javax.transaction.xa.XAException xae1) { // Report failure of rollback.
          System.out.println("distributed Transaction rollback xares1 failed");
          System.out.println("XAException error code = " + xae1.errorCode);
          System.out.println("XAException message = " + xae1.getMessage());
        }

        try {
          xares2.rollback(xid1);
        } catch (javax.transaction.xa.XAException xae2) { // Report failure of rollback.
          System.out.println("distributed Transaction rollback xares2 failed");
          System.out.println("XAException error code = " + xae2.errorCode);
          System.out.println("XAException message = " + xae2.getMessage());
        }
      }

      try {
        conn1.close();
        xaconn1.close();
      } catch (Exception e) {
        System.out.println("Failed to close connection 1: " + e.toString());
        e.printStackTrace();
      }

      try {
        conn2.close();
        xaconn2.close();
      } catch (Exception e) {
        System.out.println("Failed to close connection 2: " + e.toString());
        e.printStackTrace();
      }

    } catch (java.sql.SQLException sqe) {
      System.out.println("SQLException caught: " + sqe.getMessage());
      sqe.printStackTrace();
    } catch (javax.transaction.xa.XAException xae) {
      System.out.println("XA error is " + xae.getMessage());
      xae.printStackTrace();
    } catch (javax.naming.NamingException nme) {
      System.out.println(" Naming Exception: " + nme.getMessage());
      nme.printStackTrace();
    } catch (ClassNotFoundException cnfe) {
      System.out.println(" Class Not Found Exception: " + cnfe.getMessage());
      cnfe.printStackTrace();
    }

  }

}

