package src.kalelzar.edges.filter

import java.awt.image.BufferedImage

class ComposedImageFilter(filter1: ImageFilter, filter2: ImageFilter) extends ImageFilter {
  override def filter(i: BufferedImage): BufferedImage = {
    println(s"Applying ${filter1.getClass.getSimpleName} and then ${filter2.getClass.getSimpleName}")
    filter2(filter1(i))
  }
}
