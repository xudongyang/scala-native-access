package net.java.dev.sna.examples

import com.sun.jna.ptr.IntByReference
import com.sun.jna.win32.StdCallLibrary.StdCallCallback
import com.sun.jna.Pointer
import net.java.dev.sna.SNA

class EnumCallback extends StdCallCallback {
  def callback(hWnd: Pointer, ud: Pointer) = {
    printf("%s(%d, ...)\n", getClass.getName, hWnd.hashCode/*, ud.hashCode*/)
    true;
  }
}

object EnumWindows extends SNA {

  snaLibrary = "User32"
  val EnumWindows = SNA[EnumCallback, Pointer, Boolean]

  def main(args : Array[String]) {
	  EnumWindows(new EnumCallback, new Pointer(0))
  }
}
