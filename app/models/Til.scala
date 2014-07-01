package models

import java.sql.{Timestamp, Date}
import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.Tag
import org.joda.time.DateTime
import com.github.tototoshi.slick.PostgresJodaSupport._

/**
 * User: Michael Reitgruber
 * Date: 01.07.14
 * Time: 13:27
 */

case class Til(id: Int, nodeId: Int, userId: String, date: DateTime, title: String, desc: String)

class Tils(tag: Tag) extends Table[Til](tag, "tils") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def nodeId = column[Int]("node_id")
  def userId = column[String]("user_id")
  def date = column[DateTime]("date")
  def title = column[String]("title")
  def desc = column[String]("desc")

  override def * = (id, nodeId, userId, date, title, desc) <> (Til.tupled, Til.unapply)

}
