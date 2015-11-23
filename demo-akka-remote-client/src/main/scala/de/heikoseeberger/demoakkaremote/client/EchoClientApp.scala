/*
 * Copyright 2015 Heiko Seeberger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.heikoseeberger.demoakkaremote.client

import akka.actor.{ ActorSystem, Address }
import scala.concurrent.Await
import scala.concurrent.duration.{ Duration, MILLISECONDS }

object EchoClientApp {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("echo-client-system")

    val echoClientConfig = system.settings.config.getConfig("echo-client")
    val echoServerAddress = {
      val protocol = echoClientConfig.getString("echo-server.protocol")
      val systemName = echoClientConfig.getString("echo-server.system-name")
      val host = echoClientConfig.getString("echo-server.host")
      val port = echoClientConfig.getInt("echo-server.port")
      Address(protocol, systemName, host, port)
    }
    val echoServerPath = {
      val stripLeadingSlash = "/?(.*)".r
      val stripLeadingSlash(path) = echoClientConfig.getString("echo-server.path")
      path.split("/").to[List]
    }
    val echoServerIdentifyTimeout = Duration(echoClientConfig.getDuration("echo-server.identify-timeout").toMillis, MILLISECONDS)
    val requestInterval = Duration(echoClientConfig.getDuration("request-interval").toMillis, MILLISECONDS)

    system.actorOf(
      EchoClient.props(echoServerAddress, echoServerPath, echoServerIdentifyTimeout, requestInterval),
      EchoClient.Name
    )
    Await.ready(system.whenTerminated, Duration.Inf)
  }
}
