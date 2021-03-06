package com.siby.spray_example

import spray.json.DefaultJsonProtocol

object ApplicationJsonProtocol extends DefaultJsonProtocol {
  implicit val personFormat = jsonFormat3(Person)
}

case class Person(name: String, firstName: String, age: Long)