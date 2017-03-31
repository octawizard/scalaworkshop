package com.robertomanca.web

/**
 * @author ${user.name}
 */
object MyApp {
  
  def foo(x : Array[String]) = x.foldRight("")((a,b) => a + b)

  def main(args : Array[String]) {
    val args = Array("primo", "secondo")
    println( "Hello World!" )
    println("concat arguments = " + foo(args))

//    val user = User(1, "email")
    println()
  }

}
