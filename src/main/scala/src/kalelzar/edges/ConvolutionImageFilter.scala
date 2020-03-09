package src.kalelzar.edges

import java.awt.image.BufferedImage

class ConvolutionImageFilter(convolution: Convolution) extends ImageFilter {
  override def filter(i: BufferedImage): BufferedImage = convolution(i).orNull
}
