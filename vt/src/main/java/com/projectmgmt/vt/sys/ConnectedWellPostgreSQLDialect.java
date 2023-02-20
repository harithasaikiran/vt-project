package com.projectmgmt.vt.sys;

import org.hibernate.dialect.PostgreSQL94Dialect;

import java.sql.Types;

/**
 * @author Mounir MESSAOUDI This class is a custom sql dialect form postgresql to prevent (java - No
 * Dialect mapping for JDBC type: 1111) when using native sql query & uuid columns.
 */
public class ConnectedWellPostgreSQLDialect extends PostgreSQL94Dialect {

  public ConnectedWellPostgreSQLDialect() {
    super();
//    registerHibernateType(Types.OTHER, "pg-uuid");
  }
}
