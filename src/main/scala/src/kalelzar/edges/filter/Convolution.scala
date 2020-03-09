package src.kalelzar.edges.filter

import java.awt.Color
import java.awt.image.BufferedImage

class Convolution(x: Int, y: Int) {
  type Pixels = Seq[Seq[Color]]

  if (x == 0 || y == 0) throw new IndexOutOfBoundsException(s"Cannot have a zero-length convolution matrix!")
  if (x % 2 == 0 || y % 2 == 0) throw new IndexOutOfBoundsException(s"ConvolutionMatrix must have an odd amount of columns and rows!")

  private var initialized = false
  private var matrix: Seq[Seq[Double]] = _

  private def clamp(n: Double, min: Double, max: Double): Double = if(n < min) min else if(n > max ) max else n

  def init(matrix: Seq[Seq[Double]]): Unit = {
    if(initialized) return //Maybe throw an exception
    if(matrix.length != x) throw new IndexOutOfBoundsException(s"Expected a Seq with x=$x elements")

    if(!matrix.forall(x=>x.length == y)) throw new IndexOutOfBoundsException(s"Expected a seq with y=$y elements")

    this.matrix = matrix
    initialized = true
  }

  def apply(img: BufferedImage): Option[BufferedImage] = {
    if(!initialized) return None
    val pixels = (0 until img.getWidth).map{
      x=>
      (0 until img.getHeight).map{
        y =>
        new Color(img.getRGB(x,y))
      }
    }

    val newImage = new BufferedImage(img.getWidth, img.getHeight, img.getType)

    val halfX = x/2
    val halfY = y/2

    (0 until img.getWidth).foreach{
      tx =>
      (0 until img.getHeight).foreach{
        ty =>
        var bsum = 0.0
        var gsum = 0.0
        var rsum = 0.0
        (-halfX to halfX).foreach{
          dx =>
          if(tx + dx >= 0 && tx + dx < img.getWidth){
            (-halfY to halfY).foreach{
              dy=>
              if(ty + dy >= 0 && ty + dy < img.getHeight) {
                bsum += matrix(halfX - dx)(halfY - dy) * pixels(tx + dx)(ty + dy).getBlue
                gsum += matrix(halfX - dx)(halfY - dy) * pixels(tx + dx)(ty + dy).getGreen
                rsum += matrix(halfX - dx)(halfY - dy) * pixels(tx + dx)(ty + dy).getRed
              }
            }
          }
        }


        newImage.setRGB(tx, ty, new Color(clamp(Math.round(rsum), 0, 255).toInt, clamp(Math.round(gsum), 0, 255).toInt, clamp(Math.round(bsum), 0, 255).toInt).getRGB)
      }
    }

    Some(newImage)
  }

}
