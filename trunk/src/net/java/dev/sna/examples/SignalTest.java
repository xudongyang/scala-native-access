package net.java.dev.sna.examples;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Callback;
//import com.sun.jna.win32.StdCallLibrary.StdCallCallback;

interface sig_type extends Callback {
	public void callback(int s);
}

interface CLib extends Library {
	void raise(int s);
	sig_type signal(int s, sig_type f);
}

public class SignalTest {
	public static void main(String[] args) {
		sig_type st = new sig_type() {
			public void callback(int s) {
				System.out.printf("callback(%d) called\n", s);
			}
		};
		CLib lib = (CLib)Native.loadLibrary("msvcrt", CLib.class);
		lib.signal(8, st);
		lib.raise(8);
	}
}
