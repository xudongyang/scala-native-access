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

import com.sun.jna.Structure
import sna.Library

object WithStruct extends App {

  val cLib = Library("c")

  object tm extends Structure {
    var tm_sec: Int = _ /* seconds after the minute (0 to 61) */
    var tm_min: Int = _  /* minutes after the hour (0 to 59) */
    var tm_hour: Int = _  /* hours since midnight (0 to 23) */
    var tm_mday: Int = _  /* day of the month (1 to 31) */
    var tm_mon: Int = _  /* months since January (0 to 11) */
    var tm_year: Int = _  /* years since 1900 */
    var tm_wday: Int = _  /* days since Sunday (0 to 6 Sunday=0) */
    var tm_yday: Int = _  /* days since January 1 (0 to 365) */
    var tm_isdst: Int = _  /* Daylight Savings Time */
  }

  tm.tm_year = 2014 - 1900
  tm.tm_mon = 6
  tm.tm_mday = 1
  cLib.printf(cLib.asctime(tm)[String])[Int]

  cLib.exit(123)[Unit]
}
