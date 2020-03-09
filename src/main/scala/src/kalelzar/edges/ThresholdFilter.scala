package src.kalelzar.edges

import java.awt.Color
import java.awt.image.BufferedImage

class ThresholdFilter(threshold: Int) extends ImageFilter {

  private def thresholdChannel(i: Int): Int ={
    if(i >= threshold) 255 else 0
  }

  protected def thresholdColor(color: Color): Color = {
    val r = thresholdChannel(color.getRed)
    val g = thresholdChannel(color.getGreen)
    val b = thresholdChannel(color.getBlue)
    new Color(r, g, b)
  }

  override def filter(i: BufferedImage): BufferedImage = {
    val r = new BufferedImage(i.getWidth, i.getHeight, i.getType)

    (0 until r.getWidth).map{
      x=>
        (0 until r.getHeight).map{
          y =>
            val rc = thresholdColor(new Color(i.getRGB(x, y))).getRGB
            r.setRGB(x, y, rc)
        }
    }

    r
  }
}
