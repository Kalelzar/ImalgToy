package src.kalelzar.edges.filter

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File

import javax.imageio.ImageIO

class BidirectionalEdgeDetector(_horizontal: ImageFilter, _vertical: ImageFilter) extends ImageFilter {

  private var horizontal = _horizontal
  private var vertical = _vertical

  def reinit(horizontal: ImageFilter, vertical: ImageFilter): Unit = {
    this.horizontal = horizontal
    this.vertical = vertical
  }

  private def clampSqrt(color: Color, color1: Color): Color = {
    val r = Math.sqrt(color.getRed * color.getRed + color1.getRed * color1.getRed)
    val g = Math.sqrt(color.getGreen * color.getGreen + color1.getGreen * color1.getGreen)
    val b = Math.sqrt(color.getBlue * color.getBlue + color1.getBlue * color1.getBlue)

    new Color(clamp(r), clamp(g), clamp(b))
  }

  override def filter(i: BufferedImage): BufferedImage = {
    filter(partial(i))
  }

  def filter(hv: (BufferedImage, BufferedImage)): BufferedImage = {
    val (h, v) = hv
    if (h == null || v == null) return null

    ImageIO.write(h, "png", new File(this.getClass.getSimpleName + "-" + horizontal.getClass.getSimpleName + "-hor.png"))
    ImageIO.write(v, "png", new File(this.getClass.getSimpleName + "-" + vertical.getClass.getSimpleName + "-ver.png"))

    val r = new BufferedImage(h.getWidth, h.getHeight, h.getType)

    (0 until r.getWidth).map {
      x =>
        (0 until r.getHeight).map {
          y =>
            val hc = new Color(h.getRGB(x, y))
            val vc = new Color(v.getRGB(x, y))
            val rc = clampSqrt(hc, vc)
            r.setRGB(x, y, rc.getRGB)
        }
    }

    r
  }

  def partial(i: BufferedImage): (BufferedImage, BufferedImage) = {
    if (horizontal == null || vertical == null) throw new NullPointerException("Attempt to apply an initialized filter.")
    val h = horizontal(i)
    val v = vertical(i)
    (h, v)
  }
}
