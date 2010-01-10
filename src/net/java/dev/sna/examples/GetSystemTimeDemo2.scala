package net.java.dev.sna.examples

import net.java.dev.sna.SNA

object GetSystemTimeDemo2 extends SNA {

	snaLibrary = "Kernel32"
	val GetSystemTime = SNA[GETSYSTEMTIME, Unit]

	def main(args : Array[String]) {
		val gst = new GETSYSTEMTIME
		GetSystemTime(gst)
		val shorts = gst.getPointer.getShortArray(0, 8)
		for (sh <- shorts)
			printf("%d ", sh)
		println
	}
}
