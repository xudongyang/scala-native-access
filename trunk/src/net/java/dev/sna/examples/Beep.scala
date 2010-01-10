package net.java.dev.sna.examples

import net.java.dev.sna.SNA

object Beep extends SNA {
	
  snaLibrary = "Kernel32"
  val Beep = SNA[Int, Int, Unit]
	
  def main(args : Array[String]) {
	  Beep(800, 100)
  }
}
