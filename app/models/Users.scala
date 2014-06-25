package models

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.Tag

/**
 * Created by Gizmo on 2014-06-25.
 */
case class User(id: Int, email: String, username: String, password: String)

class Users(tag: Tag) extends Table[User](tag, "users") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def email = column[String]("email")
  def username = column[String]("username")
  def password = column[String]("password")

  override def * = (id, email, username, password) <> (User.tupled, User.unapply)
}

