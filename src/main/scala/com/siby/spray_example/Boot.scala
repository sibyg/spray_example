package com.siby.spray_example

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import akka.util.Timeout
import spray.can.Http
import scala.concurrent.duration._
import akka.pattern.ask

object Boot extends App {
  implicit val system = ActorSystem("scalaexample")
  val service = system.actorOf(Props[SimpleRestServiceActor])
  // IO requires an implicit ActorSystem, and ? requires an implicit timeout

  // Bind HTTP to the specified service.

  implicit val timeout = Timeout(5.seconds)

  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)
}
