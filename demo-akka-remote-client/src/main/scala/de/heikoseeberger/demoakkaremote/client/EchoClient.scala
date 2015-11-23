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

import akka.actor.{ Actor, ActorIdentity, ActorLogging, ActorRef, Address, Identify, Props, ReceiveTimeout, RootActorPath, Terminated }
import de.heikoseeberger.demoakkaremote.server.protocol.EchoServerProtocol
import java.time.LocalTime
import scala.concurrent.duration.FiniteDuration

object EchoClient {

  final val Name = "echo-client"

  def props(echoServer: Address, echoServerPath: List[String], identifyEchoServerTimeout: FiniteDuration, requestInterval: FiniteDuration): Props =
    Props(new EchoClient(echoServer, echoServerPath, identifyEchoServerTimeout, requestInterval))
}

class EchoClient(echoServerAddress: Address, echoServerPath: List[String], identifyEchoServerTimeout: FiniteDuration, requestInterval: FiniteDuration)
    extends Actor with ActorLogging {

  private val echoServerSelection =
    context.actorSelection(echoServerPath.foldLeft(RootActorPath(echoServerAddress) / "user")(_ / _))

  becomeIdentifying(0)

  override def receive = identifying(0) // The actual value doesn't matter because of the above call to `becomeIdentifying`

  // Identifying state

  private def becomeIdentifying(seqNr: Int) = {
    echoServerSelection ! Identify(seqNr)
    context.setReceiveTimeout(identifyEchoServerTimeout)
    context.become(identifying(seqNr))
  }

  private def identifying(seqNr: Int): Receive = {
    case ActorIdentity(`seqNr`, Some(echoServer)) => becomeIdentified(echoServer, seqNr)
    case ReceiveTimeout                           => becomeIdentifying(seqNr + 1) // This also deals with ignoring ActorIdentity(`seqNr`, None) the correct way!
  }

  // Identified state

  private def becomeIdentified(echoServer: ActorRef, seqNr: Int) = {
    context.watch(echoServer)
    requestEcho(echoServer)
    context.setReceiveTimeout(requestInterval)
    context.become(identified(echoServer, seqNr))
  }

  private def identified(echoServer: ActorRef, seqNr: Int): Receive = {
    case EchoServerProtocol.Response(message) => log.info("Received response from echo-server: {}", message)
    case ReceiveTimeout                       => requestEcho(echoServer)
    case Terminated(`echoServer`)             => becomeIdentifying(seqNr + 1)
  }

  private def requestEcho(echoServer: ActorRef) =
    echoServer ! EchoServerProtocol.Request(f"Hello at ${LocalTime.now()}", self)
}
