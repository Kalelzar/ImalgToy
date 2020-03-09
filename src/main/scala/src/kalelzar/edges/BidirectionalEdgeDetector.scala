package src.kalelzar.edges

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File

import javax.imageio.ImageIO

class BidirectionalEdgeDetector(horizontal: ImageFilter, vertical: ImageFilter) extends ImageFilter {

  private def clampSqrt(color: Color, color1: Color): Color = {
    val r = Math.sqrt(color.getRed * color.getRed + color1.getRed * color1.getRed)
    val g = Math.sqrt(color.getGreen * color.getGreen + color1.getGreen * color1.getGreen)
    val b = Math.sqrt(color.getBlue * color.getBlue + color1.getBlue * color1.getBlue)

    new Color(clamp(r), clamp(g), clamp(b))
  }

  override def filter(i: BufferedImage): BufferedImage = {
    val h = horizontal(i)
    val v = vertical(i)
    if(h == null || v == null) return null

    //ImageIO.write(h, "png", new File(this.getClass.getSimpleName+"-"+horizontal.getClass.getSimpleName + ".png"))
    //ImageIO.write(v, "png", new File(this.getClass.getSimpleName+"-"+vertical.getClass.getSimpleName + ".png"))

    val r = new BufferedImage(i.getWidth, i.getHeight, i.getType)

    (0 until r.getWidth).map{
      x=>
        (0 until r.getHeight).map{
          y =>
            val hc = new Color(h.getRGB(x,y))
            val vc = new Color(v.getRGB(x,y))
            val rc = clampSqrt(hc, vc)
            r.setRGB(x, y, rc.getRGB)
        }
    }

    r
  }
}
