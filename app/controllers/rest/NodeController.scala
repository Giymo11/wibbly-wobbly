package controllers.rest


import models._
import play.api.Play.current
import play.api.db.slick._
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.TableQuery

/**
 * User: Michael Reitgruber
 * Date: 01.07.14
 * Time: 16:47
 */
trait NodeController extends Controller{

  implicit val nodeWrites = new Writes[Node] {
    override def writes(o: Node): JsValue = {
      Json.obj(
        "id" -> o.id,
        "parent_id" -> o.parentId,
        "type" -> o.nodeType,
        "title" -> o.title,
        "desc" -> o.desc
      )
    }
  }

  implicit val nodeReads: Reads[Node] = (
    (__ \ "id").read[Int] and
      (__ \ "parent_id").read[Int] and
      (__ \ "type").read[String] and
      (__ \ "title").read[String] and
      (__ \ "desc").read[String]
  )(Node.apply _)

  def node(id: Int) = Action {
    val nodeq = TableQuery[Nodes]
    DB withSession { implicit session =>
      Ok(Json.toJson(nodeq.filter(_.id === id).list.head))
    }
  }

  def nodes = Action {
    val nodeq = TableQuery[Nodes]
    DB withSession { implicit session =>
      Ok(Json.toJson(nodeq.list))
    }
  }

  def nodesType(typeOf: String) = Action {
    val nodeq = TableQuery[Nodes]
    DB withSession { implicit session =>
      Ok(Json.toJson(nodeq.filter(_.nodeType === typeOf).list))
    }
  }

  def childrenOf(parentId: Int) = Action {
    val nodeq = TableQuery[Nodes]
    DB withSession { implicit session =>
      Ok(Json.toJson(nodeq.filter(_.parentId === parentId).list))
    }
  }

  def createNode = Action(parse.json) { request =>
    val result = request.body.validate[Node]
    val nodeq = TableQuery[Nodes]
    result match {
      case s: JsSuccess[Node] => DB withSession { implicit session =>
        nodeq += s.get
      }
      case e: JsError => println("Error: " + JsError.toFlatJson(e).toString)
    }
    Created
  }

  def deleteNode(id: Int) = Action {
    val nodeq = TableQuery[Nodes]
    DB withSession{ implicit session =>
      nodeq.filter(_.id === id).delete
    }
    NoContent
  }

  def updateNode(id: Int) = Action(parse.json) { request =>
    val result = request.body.validate[Node]
    val nodeq = TableQuery[Nodes]
    result match {
      case s: JsSuccess[Node] => DB withSession { implicit session =>
        nodeq.filter(_.id === id).update(s.get)
      }
      case e: JsError => println("Error: " + JsError.toFlatJson(e).toString)
    }
    NoContent
  }
}
