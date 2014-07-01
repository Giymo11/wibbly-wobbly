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
 * Time: 13:32
 */
trait TilController extends Controller {


  implicit val tilWrites = new Writes[Til] {
    override def writes(til: Til): JsValue = {
      Json.obj(
        "id" -> til.id,
        "node_id" -> til.nodeId,
        "user_id" -> til.userId,
        "date" -> til.date,
        "title" -> til.title,
        "desc" -> til.desc
      )
    }
  }

  implicit val tilReads: Reads[Til]= (
    (__ \ "id").read[Int] and
      (__ \ "node_id").read[Int] and
      (__ \ "user_id").read[String] and
      (__ \ "date").read[DateTime] and
      (__ \ "title").read[String] and
      (__ \ "desc").read[String]
    )(Til.apply _)

  def til(id: Int) = Action {
    val tilq = TableQuery[Tils]
    DB withSession {
      implicit session =>
        Ok(Json.toJson(tilq.filter(_.id === id).list.head))
    }
  }

  def tils = Action {
    val tilq = TableQuery[Tils]
    DB withSession {
      implicit session =>
        Ok(Json.toJson(tilq.list))
    }
  }

  def tilsNode(nodeId: Int) = Action {
    val tilq = TableQuery[Tils]
    DB withSession {
      implicit session =>
        Ok(Json.toJson(tilq.filter(_.nodeId === nodeId).list))
    }
  }

  def tilsUser(userId: String) = Action {
    val tilq = TableQuery[Tils]
    DB withSession {
      implicit session =>
        Ok(Json.toJson(tilq.filter(_.userId === userId).list))
    }
  }

  def deleteTil(id: Int) = Action {
    val tilq = TableQuery[Tils]
    DB withSession {
      implicit session =>
        tilq.filter(_.id === id).delete
    }
    NoContent
  }

  def createTil = Action(parse.json) { request =>
    val result = request.body.validate[Til]
    val tilq = TableQuery[Tils]
    result match {
      case s: JsSuccess[Til] => DB withSession { implicit session =>
         tilq += s.get
      }
      case e: JsError => println("Errors: " + JsError.toFlatJson(e).toString())
    }
    Created
  }

  def updateTil(id: Int) = Action(parse.json) { request =>
    val result = request.body.validate[Til]
    val tilq = TableQuery[Tils]
    result match {
      case s: JsSuccess[Til] => DB withSession { implicit session =>
        tilq.filter(_.id === id).update(s.get)
      }
      case e: JsError => println("Errors: " + JsError.toFlatJson(e).toString())
    }
   NoContent
  }

}
