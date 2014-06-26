package controllers

import play.api.mvc._
import scala.slick.driver.PostgresDriver.simple._

import models._
import play.api.Play.current
import play.api.db.slick._
import play.api.libs.json.{JsValue, Writes, Json}
import play.api.libs.json._
import play.api.libs.functional.syntax._
object Application extends Controller {


  implicit val userReads: Reads[User]= (
    (__ \ "id").read[Int] and
      (__ \ "email").read[String] and
      (__ \ "username").read[String] and
      (__ \ "password").read[String]
   )(User.apply _)


  implicit val userWrites = new Writes[User] {
    override def writes(user: User): JsValue = {
      Json.obj(
        "id" -> user.id,
        "email" -> user.email,
        "username" -> user.username,
        "password" -> user.password
      )
    }
  }

  def help = Action {
    Ok(views.html.help("Your new application is ready."))
  }

  def index = Action {
    Ok(views.html.index())
    //Ok(views.html.main("Hello World")(Html("<p>Hello World</p>")))
  }

  def home = Action {
    val users = TableQuery[Users]
   DB withSession { implicit session =>
      users.delete
    }
    Ok(views.html.home("Wibbly Wobbly Startpage"))
  }

  def polymer_home = Action {
    Ok(views.html.polymer_home("Card 1", "Card 2", "Card 3"))
  }

  def user(id:Int) = Action{
    val users = TableQuery[Users]
    DB withSession { implicit session =>
       Ok(Json.toJson(users.filter(_.id === id).list.head))
    }
  }

  def users() = Action{
    val users = TableQuery[Users]
    DB withSession { implicit session =>
      Ok(Json.toJson(users.list))
    }
  }

  def updateUser(id:Int) = Action (parse.json) { request =>
    val userResult = request.body.validate[User]
    val users = TableQuery[Users]
    userResult match {
      case s: JsSuccess[User] => DB withSession { implicit session =>
        users.filter(_.id === id).update(s.get)
      }
      case c: JsError => println("Errors: " + JsError.toFlatJson(c).toString())
    }
    NoContent

  }

  def createUser = Action (parse.json) { request =>
    val userResult = request.body.validate[User]
    val users = TableQuery[Users]
    userResult match {
      case s: JsSuccess[User] => DB withSession { implicit session =>
        users+=s.get
      }
      case c: JsError => println("Errors: " + JsError.toFlatJson(c).toString())
    }
    Created
  }

  def deleteUser(id:Int) = Action {
    val users = TableQuery[Users]
    DB withSession {implicit session =>
      users.filter(_.id === id).delete
    }
    NoContent
  }

}