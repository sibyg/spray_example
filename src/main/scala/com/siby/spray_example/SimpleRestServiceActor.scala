package com.siby.spray_example

import akka.actor.{Actor, ActorRefFactory}
import spray.http.MediaTypes._
import spray.http.{ContentType, HttpHeaders, HttpResponse, MediaType}
import spray.routing.HttpService
import ApplicationJsonProtocol._
import spray.httpx.SprayJsonSupport._

class SimpleRestServiceActor extends Actor with HttpService {

  implicit val system = context.system

  override implicit def actorRefFactory: ActorRefFactory = context

  override def receive: Receive = runRoute(aRoute ~ bRoute)

  val aRoute = {

    path("path1") {
      get {
        headerValue({
          case x@HttpHeaders.`Content-Type`(value) => Some(value)
          case default => None
        }) {
          header => header match {
            case ContentType(MediaType("application/vnd.type.a"), _) => {
              respondWithMediaType(`application/json`) {
                complete {
                  Person("Bob", "Type A", System.currentTimeMillis())
                }
              }
            }

            // if we habe another content-type we return a different type.

            case ContentType(MediaType("application/vnd.type.b"), _) => {

              respondWithMediaType(`application/json`) {

                complete {

                  Person("Bob", "Type B", System.currentTimeMillis())

                }
              }

            }



            // if content-types do not match, return an error code

            case default => {

              complete {

                HttpResponse(406)

              }

            }
          }
        }
      }
    }
  }

  val bRoute = {
    path("path2") {

      get {

        // respond with text/html.

        respondWithMediaType(`text/html`) {

          complete {

            // respond with a set of HTML elements

            <html>

              <body>

                <h1>Path 2</h1>

              </body>

            </html>

          }

        }

      }

    }
  }
}
