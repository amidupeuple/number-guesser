package com.pesiykot.numberguesser.test

import akka.actor.ActorSystem
import akka.testkit.{TestKit, TestProbe}
import com.pesiykot.numberguesser.Referee.{LetsPlayRequest, ListGamesRequest}
import com.pesiykot.numberguesser._
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

class RefereeSpec(_system: ActorSystem)
  extends TestKit(_system)
    with Matchers
    with WordSpecLike
    with BeforeAndAfterAll {
  def this() = this(ActorSystem("RefereeSpec"))

  override def afterAll: Unit = {
    shutdown(system)
  }

  "ask referee to print active games" in {
    val probe = TestProbe()
    val referee = system.actorOf(Referee.props)
    referee.tell(LetsPlayRequest, probe.ref)
    referee.tell(LetsPlayRequest, probe.ref)
    referee.tell(ListGamesRequest, probe.ref)
  }
}
