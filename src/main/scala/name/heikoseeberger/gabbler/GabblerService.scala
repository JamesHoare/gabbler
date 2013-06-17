/*
 * Copyright 2013 Heiko Seeberger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package name.heikoseeberger.gabbler

import akka.actor.{ ActorLogging, ActorRef, Props }
import akka.io.IO
import spray.can.Http
import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport
import spray.json.DefaultJsonProtocol
import spray.routing.{ HttpServiceActor, Route }
import spray.routing.authentication.BasicAuth

/**
 * Messages and `akka.actor.Props` factories for the [[GabblerService]] actor.
 */
object GabblerService {

  /**
   * Defines JSON support for [[Message]].
   */
  object Message extends DefaultJsonProtocol {
    implicit val format = jsonFormat2(apply)
  }

  /**
   * A message contains username and text.
   */
  case class Message(username: String, text: String)

  /**
   * Factory for `akka.actor.Props` for [[GabblerService]].
   */
  def props(interface: String, port: Int): Props =
    Props(new GabblerService(interface, port))
}

/**
 * A service providing
 *   - static resources from the `web` directory
 *   - a REST-ful API under `api/messages/`
 */
class GabblerService(interface: String, port: Int) extends HttpServiceActor with ActorLogging {

  import GabblerService._
  import SprayJsonSupport._
  import context.dispatcher

  IO(Http)(context.system) ! Http.Bind(self, interface, port)

  override def receive: Receive =
    runRoute(apiRoute ~ staticRoute)

  def apiRoute: Route =
  // format: OFF
    authenticate(BasicAuth(UsernameEqualsPasswordAuthenticator, "Gabbler"))(user =>
      path("api" / "messages")(
        get(context =>
          log.debug("User '{}' is asking for messages ...", user.username)
          // TODO Complete this user's request later, when messages are available!
        ) ~
        post(
          entity(as[Message]) { message =>
            complete {
              log.debug("User '{}' has posted '{}'", user.username, message.text)
              // TODO Dispatch message to all users!
              StatusCodes.NoContent
            }
          }
        )
      )
    )
  // format: ON

  def staticRoute: Route =
    path("")(getFromResource("web/index.html")) ~ getFromResourceDirectory("web")
}
