package com.pesiykot.numberguesser

import akka.actor.{Actor, ActorLogging, ActorRef, Cancellable, Props, Timers}
import com.pesiykot.numberguesser.Referee.{AskPlayersRequest, GuessNumberRequest, LetsPlayRequest, ListGamesRequest}

import scala.collection.mutable
import scala.concurrent.duration._

object Referee {
  def props: Props = Props[Referee]

  final case class AskPlayersRequest(playId: Int)
  final case class GuessNumberRequest(playId: Int)

  final case class GuessNumberResponse()
  final case class LetsPlayRequest()
  final case class ListGamesRequest()
}

class Referee extends Actor with Timers with ActorLogging{
  val plays = mutable.HashMap[Int, (ActorRef, ActorRef)]()
  var counter = 0

  override def receive: Receive = {
    case LetsPlayRequest => {
      log.info("play da game!")
      val player1 = context.actorOf(Player.props("PlayerJohn"))
      val player2 = context.actorOf(Player.props("PlayerSteve"))
      counter += 1
      timers.startPeriodicTimer(s"play#$counter", AskPlayersRequest(counter), 2 seconds)
      plays += counter -> (player1, player2)
    }

    case AskPlayersRequest(playId) => {
      Option(plays(playId)) match {
        case Some(p) => {
          p._1 ! GuessNumberRequest(playId)
          p._2 ! GuessNumberRequest(playId)
        }
        case None => log.warning(s"can't find play with id: $playId")
      }
    }

    case ListGamesRequest => plays.keySet.foreach(x => log.info(x.toString))
  }
}
