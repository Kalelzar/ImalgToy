package src.kalelzar.edges.filter

import java.awt.image.BufferedImage

object LaplaceFilter extends ImageFilter {
  private val laplaceMatrix = Seq(Seq(0.0, -1.0, 0.0), Seq(-1.0, 4.0, -1.0), Seq(0.0, -1.0, 0.0))
  private val laplaceConvolution = new Convolution(3, 3)
  laplaceConvolution.init(laplaceMatrix)

  override def filter(i: BufferedImage): BufferedImage = laplaceConvolution(i).orNull
}

object ReversedLaplaceFilter extends ImageFilter {
  private val laplaceMatrix = Seq(Seq(0.0, 1.0, 0.0), Seq(1.0, -4.0, 1.0), Seq(0.0, 1.0, 0.0))
  private val laplaceConvolution = new Convolution(3, 3)
  laplaceConvolution.init(laplaceMatrix)
  override def filter(i: BufferedImage): BufferedImage = laplaceConvolution(i).orNull
}

object BidirectionalLaplaceFilter extends BidirectionalEdgeDetector(LaplaceFilter, ReversedLaplaceFilter)
