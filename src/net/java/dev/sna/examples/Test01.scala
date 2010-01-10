package net.java.dev.sna.examples

import net.java.dev.sna.SNA

class ST (
		var wYear: Short = 0,
		var wMonth: Short = 0,
		var wDayOfWeek: Short = 0,
		var wDay: Short = 0,
		var wHour: Short = 0,
		var wMinute: Short = 0,
		var wSecond: Short = 0,
		var wMilliseconds: Short = 0) extends com.sun.jna.Structure

object Test01 extends SNA {

	snaLibrary = "Kernel32"
	val GetSystemTime = SNA[ST, Unit]
	val Beep = SNA[Int, Int, Unit]
	val GetLogicalDrives = SNA[Int]

	def main(args : Array[String]) {
		Beep(500, 100)
		println(GetLogicalDrives())
		val st = new ST
		GetSystemTime(st)
		printf("Date: %d-%d-%d Time: %d:%d:%d.%d GMT%n", st.wYear, st.wMonth, 
				st.wDay, st.wHour, st.wMinute, st.wSecond, st.wMilliseconds)
	}
}
