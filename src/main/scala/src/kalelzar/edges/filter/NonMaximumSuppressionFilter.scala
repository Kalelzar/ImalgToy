package src.kalelzar.edges.filter

import java.awt.Color
import java.awt.image.BufferedImage

abstract class NonMaximumSuppressionFilter(bidirectionalEdgeDetector: BidirectionalEdgeDetector) extends ImageFilter {

  override def filter(i: BufferedImage): BufferedImage = {
    val (h, v) = bidirectionalEdgeDetector.partial(i)
    val edgeDetected = bidirectionalEdgeDetector.filter((h, v))

    val r = new BufferedImage(i.getWidth, i.getHeight, i.getType)

    (0 until edgeDetected.getWidth).foreach {
      x =>
        (0 until edgeDetected.getHeight).foreach {
          y =>
            val values3x3 = (-1 to 1).map {
              dx =>
                (-1 to 1).map {
                  dy =>
                    try {
                      if (dx + x >= 0 && dx + x < edgeDetected.getWidth() && dy + y >= 0 && dy + y < edgeDetected.getHeight())
                        new Color(edgeDetected.getRGB(x + dx, y + dy))
                      else new Color(0)
                    } catch {
                      case _: Exception =>
                        Log.<>.error((dx + x, dy + y))
                        new Color(0)
                    }
                }
            }
            val gradient = edgeGradient(new Color(h.getRGB(x, y)).getRed, new Color(v.getRGB(x, y)).getRed)
            val value = findValue(values3x3.map(_.map(_.getRed)), gradient)


            //if(rvalue == 0) edgeDetected.setRGB(x, y, 0)

            r.setRGB(x, y, new Color(value, value, value).getRGB)
        }
    }


    r
  }

  protected def edgeGradient(h: Double, v: Double): Double

  protected def findValue(values: Seq[Seq[Int]], gradient: Double): Int


}

