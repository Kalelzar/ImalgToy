package src.kalelzar.edges.filter

class DiscreteNonMaximumSuppressionFilter(bidirectionalEdgeDetector: BidirectionalEdgeDetector)
  extends NonMaximumSuppressionFilter(bidirectionalEdgeDetector) {

  override protected def edgeGradient(h: Double, v: Double): Double = {
    var _angle = Math.toDegrees(Math.atan2(h, v) + Math.PI)
    if (_angle > 180) {
      _angle -= 180
    }

    //map angle from 0 to 180 to 0, 45, 90, 135 and 180
    val angle = Math.round(_angle / 45) * 45


    if (angle == 180) 0 else angle
  }

  override protected def findValue(values: Seq[Seq[Int]], gradient: Double): Int = {

    //FIX: Lots of code duplication.
    gradient match {
      case 0 =>
        val left = values(0)(1)
        val right = values(2)(1)
        val center = values(1)(1)
        if (center >= left && center >= right) center else 0
      case 45 =>
        val bottomLeft = values(0)(2)
        val topRight = values(2)(0)
        val center = values(1)(1)
        if (center >= bottomLeft && center >= topRight) center else 0
      case 90 =>
        val top = values(1)(0)
        val bottom = values(1)(2)
        val center = values(1)(1)
        if (center >= top && center >= bottom) center else 0
      case 135 =>
        val topLeft = values(0)(0)
        val bottomRight = values(2)(2)
        val center = values(1)(1)
        if (center >= topLeft && center >= bottomRight) center else 0
      case _ =>
        Log.<>.error(s"The math doesn't check out! Recieved angle $gradient! Suppressing erroneous value!")
        0
    }
  }

}
