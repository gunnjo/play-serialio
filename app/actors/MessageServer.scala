package controllers

import java.net._

import akka.actor.{Actor, ActorRef, Props, ReceiveTimeout}
import akka.io.{IO}
import akka.util.ByteString

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import play.api.Logger
import play.api.Play.current

import scala.concurrent.duration._
import scala.reflect._

/**
  * 
  *
  */

class MessageServer extends Actor {

  override def receive = {

    case other =>   Logger.info(s"${classTag[this.type].toString.replace("$", "")} Unknown message: $other")
  }

}
