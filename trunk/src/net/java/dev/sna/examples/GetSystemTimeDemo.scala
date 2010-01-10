package net.java.dev.sna.examples

import net.java.dev.sna.SNA

object GetSystemTimeDemo extends SNA {

	snaLibrary = "Kernel32"
	val GetSystemTime = SNA[GETSYSTEMTIME, Unit]

	def main(args : Array[String]) {
		val gst = new GETSYSTEMTIME
		GetSystemTime(gst)
		printf("Date: %d-%02d-%02d Time: %d:%02d:%02d.%03d GMT%n", gst.wYear, gst.wMonth, 
				gst.wDay, gst.wHour, gst.wMinute, gst.wSecond, gst.wMilliseconds)
	}
}
