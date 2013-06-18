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

import akka.actor.{ ExtendedActorSystem, Extension, ExtensionKey }
import scala.concurrent.duration.{ Duration, FiniteDuration, MILLISECONDS }

object GabblerSettings extends ExtensionKey[GabblerSettings]

/**
 * The settings for Gabbler as an Akka extension:
 *   - `interface`: the network interface the service gets bound to, e.g. `"localhost"`.
 *   - `port`: the port the service gets bound to, e.g. `8080`.
 */
class GabblerSettings(system: ExtendedActorSystem) extends Extension {

  /**
   * The network interface the Gabbler service gets bound to, e.g. `"localhost"`.
   */
  val interface: String =
    system.settings.config getString "gabbler.interface"

  /**
   * The port the Gabbler service gets bound to, e.g. `8080`.
   */
  val port: Int =
    system.settings.config getInt "gabbler.port"

  val timeout: FiniteDuration =
    Duration(system.settings.config getMilliseconds "gabbler.timeout", MILLISECONDS)
}
