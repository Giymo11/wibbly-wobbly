package controllers

import play.api.mvc._

object Application extends Controller {

  def help = Action {
    Ok(views.html.help("Your new application is ready."))
  }

  def index = Action {
    Ok(views.html.index())
    //Ok(views.html.main("Hello World")(Html("<p>Hello World</p>")))
  }

  def home = Action {
    Ok(views.html.home("Wibbly Wobbly Startpage"))
  }

  def polymer_home = Action {
    Ok(views.html.polymer_home("Card 1", "Card 2", "Card 3"))
  }
}