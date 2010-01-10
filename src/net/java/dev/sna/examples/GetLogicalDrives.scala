package net.java.dev.sna.examples

import net.java.dev.sna.SNA

object GetLogicalDrives extends SNA {
	
  snaLibrary = "Kernel32"
  val GetLogicalDrives = SNA[Int]
	
  def main(args : Array[String]) {
    printf("GetLogicalDrives: %d\n", GetLogicalDrives())
  }
}
