package src.kalelzar.edges

import java.awt.image.BufferedImage

object SobelEdgeDetector extends ImageFilter {

  private val horizontalMatrix = Seq(Seq(-1.0, -2.0, -1.0), Seq(0.0, 0.0, 0.0), Seq(1.0, 2.0, 1.0))
  private val horizontalConvolution = new Convolution(3, 3)
  horizontalConvolution.init(horizontalMatrix)

  private val verticalMatrix = Seq(Seq(-1.0, 0.0, 1.0), Seq(-2.0, 0.0, 2.0), Seq(-1.0, 0.0, 1.0))
  private val verticalConvolution = new Convolution(3, 3)
  verticalConvolution.init(verticalMatrix)
  private val bidirectionalEdgeDetector = new BidirectionalEdgeDetector(new ConvolutionImageFilter(horizontalConvolution),
    new ConvolutionImageFilter(verticalConvolution))

  override def filter(i: BufferedImage): BufferedImage = bidirectionalEdgeDetector(i)
}

object ReversedSobelEdgeDetector extends ImageFilter{
  private val horizontalMatrix = Seq(Seq(1.0, 2.0, 1.0), Seq(0.0, 0.0, 0.0), Seq(-1.0, -2.0, -1.0))
  private val horizontalConvolution = new Convolution(3, 3)
  horizontalConvolution.init(horizontalMatrix)

  private val verticalMatrix = Seq(Seq(1.0, 0.0, -1.0), Seq(2.0, 0.0, -2.0), Seq(1.0, 0.0, -1.0))
  private val verticalConvolution = new Convolution(3, 3)
  verticalConvolution.init(verticalMatrix)
  private val bidirectionalEdgeDetector = new BidirectionalEdgeDetector(new ConvolutionImageFilter(horizontalConvolution),
    new ConvolutionImageFilter(verticalConvolution))

  override def filter(i: BufferedImage): BufferedImage = bidirectionalEdgeDetector(i)
}

object BidirectionalSobelEdgeDetector extends BidirectionalEdgeDetector(SobelEdgeDetector, ReversedSobelEdgeDetector)