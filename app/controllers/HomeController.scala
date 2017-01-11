package controllers

import javax.inject._
import akka.actor.{Actor, ActorRef}
import akka.actor.ActorSystem
import akka.actor.Props
import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Akka
import play.api.Play.current

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
object HomeController extends Controller {

  val messageActor = Akka.system.actorOf(Props[MessageServer], name = "message")
  val device = Play.current.configuration.getString("serial.device").getOrElse("/dev/ttyACM0")
  val serialActor = Akka.system.actorOf(
  	SerialServer.props( device, messageActor), "SerialServer")
  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index("Test akka serial I/O"))
  }

  def message( m: String) = Action {
    serialActor ! m
    Ok(views.html.index("Test akka serial I/O"))
  }

}
