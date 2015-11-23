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

package de.heikoseeberger.demoakkaremote.server

import akka.actor.ActorSystem
import akka.testkit.TestProbe
import de.heikoseeberger.demoakkaremote.server.protocol.EchoServerProtocol
import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{ BeforeAndAfterAll, Matchers, WordSpec }
import scala.concurrent.Await
import scala.concurrent.duration.Duration

class EchoServerSpec extends WordSpec with Matchers with BeforeAndAfterAll with GeneratorDrivenPropertyChecks {

  implicit val system = ActorSystem()

  "Sending Echo.Request to Echo" should {
    "result in sending Echo.Response to the given respondTo" in {
      val echoServer = system.actorOf(EchoServer.props)
      val respondTo = TestProbe()
      forAll(Gen.alphaStr -> "message") { message =>
        echoServer ! EchoServerProtocol.Request(message, respondTo.ref)
        respondTo.expectMsg(EchoServerProtocol.Response(message))
      }
    }
  }

  override protected def afterAll() = {
    Await.ready(system.terminate(), Duration.Inf)
    super.afterAll()
  }
}
