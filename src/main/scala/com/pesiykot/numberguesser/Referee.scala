package com.pesiykot.numberguesser

import akka.actor.{Actor, ActorLogging, Props}
import com.pesiykot.numberguesser.Referee.GuessNumberRequest

object Referee {
  def props: Props = Props[Referee]

  final case class GuessNumberRequest(playId: Int, number: Int)
  final case class GuessNumberResponse()
}

class Referee extends Actor with ActorLogging {
  override def receive: Receive = {
    case GuessNumberRequest(playId, number) => {}
  }
}
