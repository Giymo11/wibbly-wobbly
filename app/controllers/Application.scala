package controllers

import models._
import play.api.Play.current
import play.api.db.slick._
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.TableQuery
import controllers.rest._

object Application extends Controller with UserController with TilController with TrackController with NodeController{

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



}