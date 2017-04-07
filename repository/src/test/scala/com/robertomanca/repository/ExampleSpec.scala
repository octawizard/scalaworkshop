package com.robertomanca.repository

import collection.mutable.{ListBuffer, Stack}
import org.scalatest._

/**
  * Created by Roberto Manca (roberto.manca@edreamsodigeo.com) on 07/04/2017.
  */

class ExampleSpec extends FlatSpec with Matchers {

  "A Stack" should "pop values in last-in-first-out order" in {
    val list = new ListBuffer[Int]
    list.append(1)
    list.append(2)
    list.lift(0).get should be (1)
    list.lift(1).get should be (2)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyList = List.empty
    a [NoSuchElementException] should be thrownBy {
      emptyList.take(0)
    }
  }
}
