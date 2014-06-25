package controllers

import models.Users
import play.api.db.slick.DBAction
import play.api.mvc._
import play.twirl.api.Html

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.TableQuery

object Application extends Controller {

  def help = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def index = Action {
    Ok(views.html.main("Hello World")(Html("<p>Hello World</p>")))
  }

  def home = Action {
    Ok(views.html.home("Wibbly Wobbly Startpage"))
  }

  def showDDL = DBAction { implicit session =>
    val users = TableQuery[Users]
    val statements = users.ddl.createStatements.foldRight("")(_ + ", " + _)
    Ok(views.html.main("Your DDL would be: ")(Html("<p>" + statements + "</p>")))
  }
}