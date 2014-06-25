package controllers

import play.api.mvc._
import play.twirl.api.Html

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
}