package src.kalelzar.edges

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File

import javax.imageio.ImageIO

trait ImageFilter {
  protected def clamp(d: Double): Int = if(d < 0) 0 else if(d > 255) 255 else d.toInt

  def filter(i: BufferedImage): BufferedImage
  def apply(i: BufferedImage): BufferedImage = filter(i)

  def compose(other: ImageFilter): ImageFilter = new ComposedImageFilter(this, other)
}

class AverageOfFilter(filter1: ImageFilter, filter2: ImageFilter) extends ImageFilter {
  private def average(color: Color, color1: Color): Color = {
    val r = (color.getRed + color1.getRed)/2
    val g = (color.getGreen + color1.getGreen)/2
    val b = (color.getBlue + color1.getBlue )/2

    new Color(clamp(r), clamp(g), clamp(b))
  }

  override def filter(i: BufferedImage): BufferedImage = {
    val h = filter1(i)
    val v = filter2(i)
    if(h == null || v == null) return null

    ImageIO.write(h, "png", new File(this.getClass.getSimpleName+"-"+filter1.getClass.getSimpleName + ".png"))
    ImageIO.write(v, "png", new File(this.getClass.getSimpleName+"-"+filter2.getClass.getSimpleName + ".png"))

    val r = new BufferedImage(i.getWidth, i.getHeight, i.getType)

    (0 until r.getWidth).map{
      x=>
        (0 until r.getHeight).map{
          y =>
            val hc = new Color(h.getRGB(x,y))
            val vc = new Color(v.getRGB(x,y))
            val rc = average(hc, vc)
            r.setRGB(x, y, rc.getRGB)
        }
    }

    r
  }
}

object ImageFilters {

  val SobelEdgeDetector: ImageFilter = GaussianFilter.compose(src.kalelzar.edges.SobelEdgeDetector)
  val LaplaceEdgeDetector: ImageFilter = GaussianFilter.compose(LaplaceFilter)

  def ThresholdSobelEdgeDetector(threshold: Int): ImageFilter = {
    SobelEdgeDetector.compose(new ThresholdFilter(threshold))
  }

  def ThresholdLaplaceEdgeDetector(threshold: Int): ImageFilter = {
    LaplaceEdgeDetector.compose(new ThresholdFilter(threshold))
  }

}









