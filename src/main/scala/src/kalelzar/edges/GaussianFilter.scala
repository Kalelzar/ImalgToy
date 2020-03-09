package src.kalelzar.edges

import java.awt.image.BufferedImage

object GaussianFilter extends ImageFilter {
  private val gaussianMatrix = Seq(Seq(2, 4, 5, 4, 2), Seq(4, 9, 12, 9, 4), Seq(5, 12, 15, 12, 5),
    Seq(4, 9, 12, 9, 4), Seq(2, 4, 5, 4, 2)).map( _.map(_/159.0) )
  private val gaussianConvolution = new Convolution(5, 5)
  gaussianConvolution.init(gaussianMatrix)

  override def filter(i: BufferedImage): BufferedImage = gaussianConvolution(i).orNull
}
