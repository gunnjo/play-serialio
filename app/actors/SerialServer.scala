package controllers

import java.net._

import akka.actor.{Actor, ActorRef, Props, ReceiveTimeout}
import akka.io.{IO}
import akka.util.ByteString
import com.github.akileev.akka.serial.io.Serial._
import com.github.akileev.akka.serial.io.Serial

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import play.api.Logger
import play.api.Play.current

import scala.concurrent.duration._
import scala.reflect._

/**
  * Represents a connection to a Honeywell 3310g scanner
  *
  */

object SerialServer {
  def props(device: String, transactor: ActorRef): Props = {
    Props(new SerialServer(device, transactor))
  }
}

class SerialServer(device: String, transactor: ActorRef) extends Actor {
  import context.system

  override def preStart = {
    IO(Serial) ! Open(self, device, 115200)
  }

  override def postStop = {
      Logger.info(s"${classTag[this.type].toString.replace("$", "")}  stopped")
      self ! "close"
  }

  override def receive = {
    case Opened(p) =>
      Logger.info(s"${classTag[this.type].toString.replace("$", "")} Connected to device $p")
      context become open(sender())

    case CommandFailed(_, cause) =>
      Logger.info(s"${classTag[this.type].toString.replace("$", "")} Could not connect to device: $cause")
      context stop self

    case other =>   Logger.info(s"${classTag[this.type].toString.replace("$", "")} Unknown message: $other")
  }

  def open(connection: ActorRef): Receive = {
    case "close" =>
        Logger.info(s"${classTag[this.type].toString.replace("$", "")} Closing")
      connection ! Close

    case s: String =>
        Logger.info(s"${classTag[this.type].toString.replace("$", "")} Sending to the device: $s")
      connection ! Write(ByteString(s))

    case Received(data) =>
        Logger.info(s"${classTag[this.type].toString.replace("$", "")} Received data from serial device: ${data.decodeString("UTF-8")}")
        connection ! data

    case Closed =>
        Logger.info(s"${classTag[this.type].toString.replace("$", "")} Serial device closed")
      context stop self
  }
}
