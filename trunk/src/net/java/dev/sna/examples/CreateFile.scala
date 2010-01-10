package net.java.dev.sna.examples
import net.java.dev.sna.SNA

object CreateFile extends SNA {
	
  snaLibrary = "Kernel32"
  val CreateFileA = SNA[String, Int, Int, Object, Int, Int, Int]
	
  def main(args : Array[String]) {
    val chdl = CreateFileA("\\\\.\\C:", 
				0x10000000, // GENERIC_ALL
				3, // sharing - read, write 
				null, 
				3, // OPEN_EXISTING
				0x80)
    println(chdl)
    val dhdl = CreateFileA("\\\\.\\D:", 
				0x10000000, // GENERIC_ALL
				3, // sharing - read, write 
				null, 
				3, // OPEN_EXISTING
				0x80)
    println(dhdl)
  }
}
