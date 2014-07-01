package controllers.rest

import models._
import play.api.Play.current
import play.api.db.slick._
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.TableQuery
import java.sql.{Timestamp, Date}
import org.joda.time.DateTime


/**
 * User: Michael Reitgruber
 * Date: 01.07.14
 * Time: 15:04
 */
trait TrackController extends Controller{

  implicit val trackWrites = new Writes[Track] {
    override def writes(o: Track): JsValue = {
      Json.obj(
      "id" -> o.id,
      "node_id" -> o.nodeId,
      "user_id" -> o.userId,
      "start" -> o.start,
      "end" -> o.end
      )
    }
  }

  implicit val trackReads: Reads[Track] = (
    (__ \ "id").read[Int] and
      (__ \ "node_id").read[Int] and
      (__ \ "user_id").read[String] and
      (__ \ "start").read[DateTime] and
      (__ \ "end").read[DateTime]
    )(Track.apply _)


  def track(id: Int) = Action{
    val trackq = TableQuery[Tracks]
    DB withSession { implicit session =>
      Ok(Json.toJson(trackq.filter(_.id === id).list.head))
    }
  }

  def tracks = Action {
    val trackq = TableQuery[Tracks]
    DB withSession { implicit session =>
      Ok(Json.toJson(trackq.list))
    }

  }

  def tracksNode(nodeId: Int) = Action {
    val trackq = TableQuery[Tracks]
    DB withSession { implicit session =>
      Ok(Json.toJson(trackq.filter(_.nodeId === nodeId).list))
    }
  }

  def tracksUser(userId: String) = Action {
    val trackq = TableQuery[Tracks]
    DB withSession { implicit session =>
      Ok(Json.toJson(trackq.filter(_.userId === userId).list))
    }
  }

  def deleteTrack(id: Int) = Action {
    val trackq = TableQuery[Tracks]
    DB withSession { implicit session =>
      trackq.filter(_.id === id).delete
    }
    NoContent
  }

  def createTrack = Action(parse.json) { request =>
    val result = request.body.validate[Track]
    val trackq = TableQuery[Tracks]
    result match {
      case s:JsSuccess[Track] => DB.withSession { implicit session =>
        trackq+=s.get
      }
      case e:JsError => println("Error: " + JsError.toFlatJson(e).toString())
    }
    Created
  }

  def updateTrack(id: Int) = Action(parse.json) { request =>
    val result = request.body.validate[Track]
    val trackq = TableQuery[Tracks]
    result match {
      case s:JsSuccess[Track] => DB.withSession { implicit session =>
        trackq.filter(_.id === id).update(s.get)
      }
      case e:JsError => println("Error: " + JsError.toFlatJson(e).toString())
    }
    NoContent
  }

}
