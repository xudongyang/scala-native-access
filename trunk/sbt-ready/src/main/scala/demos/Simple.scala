/* Copyright (c) 20010-2014 Sanjay Dasgupta, All Rights Reserved
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 */

package demos

import sna.Library

object Simple extends App {

  val cLib = Library("c")

  val len = cLib.strcmp("Hello", "World")[Int]
  cLib.printf("strcmp: %d\n", len)[Int]

  val n = cLib.atol("0345")[Int]
  cLib.printf("atol: %ld\n", n)[Int]

  val timer = cLib.time()[Int]
  cLib.printf("timer: %ld\n", timer)[Int]

  cLib.exit(255)[Unit]
}
