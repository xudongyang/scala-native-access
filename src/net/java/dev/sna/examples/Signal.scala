package net.java.dev.sna.examples

import net.java.dev.sna.SNA
import com.sun.jna.Callback
import com.sun.jna.Platform

  trait SignalType extends Callback {
    def invoke(signal: Int) {
      printf("invoke (%d) called on %s\n", signal, this.toString)
    }
  }

object Signal extends SNA {

  snaLibrary = if (Platform.isWindows) "msvcrt" else "c"
  val signal = SNA[Int, SignalType, SignalType]
  val raise = SNA[Int, Unit]

  def main(args : Array[String]) {
    val hdlr = new Object with SignalType
    for (i <- 0 to 32) {
    	val old_sig = signal(i, hdlr)
    	printf("%d -> old_sig: %s\n", i, old_sig)
    	raise(i)
    }
  }
}
