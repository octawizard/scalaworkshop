package com.robertomanca.flights

/**
 * @author ${user.name}
 */
object App {
  
  def foo(x : Array[String]) = x.foldRight("")((a,b) => a + b)
  
  def main(args : Array[String]) {
    val args = Array("primo", "secondo")
    println( "Hello World!" )
    println("concat arguments = " + foo(args))
  }

}
