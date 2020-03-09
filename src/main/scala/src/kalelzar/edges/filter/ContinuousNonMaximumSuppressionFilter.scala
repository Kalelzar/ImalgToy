package src.kalelzar.edges.filter

class ContinuousNonMaximumSuppressionFilter(bidirectionalEdgeDetector: BidirectionalEdgeDetector)
  extends NonMaximumSuppressionFilter(bidirectionalEdgeDetector) {

  override protected def edgeGradient(h: Double, v: Double): Double = {
    var angle = Math.toDegrees(Math.atan2(h, v) + Math.PI)
    if (angle > 180) {
      angle -= 180
    }

    angle
  }

  override protected def findValue(values: Seq[Seq[Int]], gradient: Double): Int = {

    val center = values(1)(1)

    var pos = 0.0
    var neg = 0.0

    if (gradient >= 90 && gradient < 135) {
      val ratio = Math.abs(center)
      pos = interpolate(ratio, values(0)(1), values(0)(2))
      neg = interpolate(ratio, values(2)(1), values(2)(0))
    } else if (gradient >= 135 && gradient <= 180) {
      val ratio = (gradient - 135) / 45d
      pos = interpolate(ratio, values(0)(2), values(1)(2))
      neg = interpolate(ratio, values(2)(0), values(1)(0))
    } else if (gradient >= 0 && gradient < 45) {
      val ratio = (gradient - 0) / 45d
      pos = interpolate(ratio, values(1)(2), values(2)(2))
      neg = interpolate(ratio, values(1)(0), values(0)(0))
    } else if (gradient >= 45 && gradient < 90) {
      val ratio = (gradient - 45) / 45d
      pos = interpolate(ratio, values(2)(2), values(2)(1))
      neg = interpolate(ratio, values(0)(0), values(0)(1))
    }

    if (center > pos && center > neg) center else 0
  }

  private def interpolate(ratio: Double, a: Double, b: Double): Double = ratio * (a - b) + b
}
