package com.verbose.potato

import eu.timepit.refined.types.numeric.PosInt

object VerbosePotato {

  def ordinalIndicatorSuffix(n: PosInt): String =
    n.value % 10 match {
      case 1 => s"${n}st"
      case 2 => s"${n}nd"
      case 3 => s"${n}rd"
      case _ => s"${n}th"
    }

}
