# Scala Native Access #
Most Java APIs and libraries can be [used seamlessly from Scala](http://www.artima.com/pins1ed/combining-scala-and-java.html). But [JNA](https://github.com/twall/jna) is a little different. A [Scala](http://www.scala-lang.org/) class can not be used (as a Java class can) to emulate a [C struct](http://en.wikipedia.org/wiki/Struct_(C_programming_language)) passed to (or returned from) a native function. Scala Native Access (SNA) solves this problem by providing a modified version of JNA's [Structure](https://github.com/twall/jna/blob/master/src/com/sun/jna/Structure.java) class that works for client code written in Scala. The [modified Structure](https://code.google.com/p/scala-native-access/source/browse/trunk/sbt-ready/src/main/java/com/sun/jna/Structure.java) remains backward compatible, and can be used in Java projects as well.

Scala Native Access will be moving to github soon. Watch [JNA4Scala](https://github.com/sanjaydasgupta/JNA4Scala) and [sna](https://github.com/sanjaydasgupta/sna).

## SNA is Dynamic ##
JNA requires the user to supply a [specifically crafted Java interface](https://today.java.net/article/2009/11/11/simplify-native-code-access-jna#jnadev) (complete with method definitions) for each native library. But SNA has no such need. It abstracts all native libraries into instances of the [sna.Library](https://code.google.com/p/scala-native-access/source/browse/trunk/sbt-ready/src/main/scala/sna/Library.scala) class, and the native functions appear magically as methods of that class at run-time (see code below).

```
  val cLibrary: Library = Library("c")  // the standard C library
  cLibrary.printf("Hello world!\n")[Int]
```

This effect is achieved by leveraging [scala.Dynamic](http://www.scala-lang.org/api/current/index.html#scala.Dynamic) (available since [Scala 2.10](http://www.scala-lang.org/download/changelog.html#changes_in_version_2100)) in a technique detailed in [this](https://weblogs.java.net/blog/cayhorstmann/archive/2012/12/13/dynamic-types-scala-210) blog post. Note, however, that this is effectively [duck typing](http://en.wikipedia.org/wiki/Duck_typing), so the function names (and types) can only be checked at run-time. Another small cost of this convenience is that the function's return type must be _applied_ to the invocation as a postfix operation (the "`[Int]`" at the end of the second line; contrary to what most people believe and _know_, [printf returns an int](http://en.wikibooks.org/wiki/C%2B%2B_Programming/Code/Standard_C_Library/Functions/printf)).

This version of SNA is compatible with JNA 4.0.0, and depends on Scala 2.10.x (for the [scala.Dynamic](http://www.scala-lang.org/api/current/index.html#scala.Dynamic) feature).

## Using SNA ##
SNA is distributed entirely as source code ([modified Structure.java](https://code.google.com/p/scala-native-access/source/browse/trunk/sbt-ready/src/main/java/com/sun/jna/Structure.java) and [sna.Library.scala](https://code.google.com/p/scala-native-access/source/browse/trunk/sbt-ready/src/main/scala/sna/Library.scala)). The following description highlights where these two source files must be placed in relation to the rest of a project's source code.

The _sbt-ready_ directory in the source repository contains a set of simple Scala demo source files along with all other supporting elements required for building and running with [sbt](http://www.scala-sbt.org/). The main elements of the directory structure are:

  1. the _lib_ directory is for dependencies (contains _jna-4.0.0.jar_)
  1. the _src/main/java/com/sun/jna_ directory contains the modified _Structure.java_ file
  1. the _src/main/scala/sna_ directory contains SNA's _Library.scala_ file
  1. the _src/main/scala/demos_ directory contains the Scala demos

An sbt session with this directory is shown below:

```
sanjay:~/MyStuff/SNA-SVN$ sbt
[info] Set current project to sna-svn (in build file:/home/yajnas/MyStuff/SNA-SVN/)
> compile
[info] Updating {file:/home/yajnas/MyStuff/SNA-SVN/}sna-svn...
[info] Resolving org.fusesource.jansi#jansi;1.4 ...
[info] Done updating.
[info] Compiling 4 Scala sources and 1 Java source to /home/yajnas/MyStuff/SNA-SVN/target/scala-2.10/classes...
[warn] Note: /home/yajnas/MyStuff/SNA-SVN/src/main/java/com/sun/jna/Structure.java uses unchecked or unsafe operations.
[warn] Note: Recompile with -Xlint:unchecked for details.
[success] Total time: 10 s, completed 24 Jun, 2014 6:09:51 PM
> run

Multiple main classes detected, select one to run:

 [1] sna.Library
 [2] demos.Simple
 [3] demos.Signal
 [4] demos.WithStruct

Enter number:  
```

To adapt this structure for your use, just replace the _demos_ directory with the package containing your source code. And to add SNA to an existing sbt project proceed as follows:

  1. add [jna-4.0.0.jar](http://central.maven.org/maven2/net/java/dev/jna/jna/4.0.0/jna-4.0.0.jar) to the dependencies of the existing sbt project
  1. merge the 2 files _src/main/java/com/sun/jna/Structure.java_ and _src/main/scala/sna/Library.scala_ (from the _sbt-ready_ directory) into the _src_ directory of the existing sbt project
  1. import JNA classes normally into the client Scala code (as in the demos)
  1. use the `Library` class (above) to access native functions from client Scala code (as in the demos)

This version of SNA is compatible with JNA version 4.0.0. Later versions of JNA have not been tested.

## Sample Code ##
The following code is from the demo file _Simple.scala_, and illustrates the basics of using SNA. Two more demo files (_WithStruct.scala_ and _Signal.scala_) that illustrate the use of C structs and callbacks can be found in _sbt-ready_ alongside _Simple.scala_.

```
package demos
import sna.Library

object Simple extends App {

  val cLib = Library("c")  // Note-1

  val len = cLib.strcmp("Hello", "World")[Int]  // Note-2
  cLib.printf("strcmp: %d\n", len)[Int]  // Note-3

  val n = cLib.atol("0345")[Int]
  cLib.printf("atol: %ld\n", n)[Int]

  val timer = cLib.time()[Int]
  cLib.printf("timer: %ld\n", timer)[Int]

  cLib.exit(255)[Unit]
}
```

The first step (_Note-1_ above), is to get a reference to the native library by providing its name to the `sna.Library` constructor. The reference (`cLib` above) can then be used like an object with methods (_Note-2_ and _Note-3_ above). But there are two important things to note.

  1. the function return type must the _applied_ to the invocation in all cases -- even for trivial functions like `printf`. Failing to apply the return type will leave you with a JNA `Function` object. The forms `cLib.atol("0345").as[Int]` and `cLib.atol("0345").asInstanceOf[Int]` may also be used instead of a raw `apply`.
  1. although an object-oriented style of method invocation is used, the library reference does not actually have these methods (a mechanism based on [scala.Dynamic](http://www.scala-lang.org/api/current/index.html#scala.Dynamic) is used), so bad function names will not be caught by the compiler.

The other two demos (_WithStruct.scala_ and _Signal.scala_) illustrate the use of C structs and callbacks in native function invocations.