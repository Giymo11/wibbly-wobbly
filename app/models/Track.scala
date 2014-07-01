package models

import java.sql.{Timestamp, Date}
import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.{ProvenShape, Tag}
import org.joda.time.DateTime
import com.github.tototoshi.slick.PostgresJodaSupport._

/**
 * User: Michael Reitgruber
 * Date: 01.07.14
 * Time: 14:57
 */

case class Track(id: Int, nodeId: Int, userId: String, start: DateTime, end: DateTime)

class Tracks(tag: Tag) extends Table[Track](tag, "tracks") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def nodeId = column[Int]("node_id")
  def userId = column[String]("user_id")
  def start = column[DateTime]("start")
  def end = column[DateTime]("end")

  override def * = (id, nodeId, userId, start, end) <> (Track.tupled, Track.unapply)
}