package com.evolution.tetris.db

import scalikejdbc.{AutoSession, ConnectionPool, DB}

trait Database {
  val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"

  val dbURL = "jdbc:derby:myDB;create=true;"
  // initialize JDBC driver & connection pool
 // Class.forName(derbyDriverClassname)

  ConnectionPool.singleton(dbURL, "me", "mine")

  // ad-hoc session provider on the REPL
  implicit val session = AutoSession
}

object Database extends Database{
  def setupDB() = {
    if (!hasDBInitialize)
      Player.initializeTable()
  }
  def hasDBInitialize : Boolean = {
    //check whether the the table is initialized
    DB getTable "Player" match {
      case Some(_) => true
      case None => false
    }
  }
}
