package src.kalelzar.edges.filter

object SobelOperator extends BidirectionalEdgeDetector(null, null) {

  private val horizontalMatrix = Seq(Seq(-1.0, -2.0, -1.0), Seq(0.0, 0.0, 0.0), Seq(1.0, 2.0, 1.0))
  private val horizontalConvolution = new Convolution(3, 3)
  horizontalConvolution.init(horizontalMatrix)

  private val verticalMatrix = Seq(Seq(-1.0, 0.0, 1.0), Seq(-2.0, 0.0, 2.0), Seq(-1.0, 0.0, 1.0))
  private val verticalConvolution = new Convolution(3, 3)
  verticalConvolution.init(verticalMatrix)

  reinit(new ConvolutionImageFilter(horizontalConvolution), new ConvolutionImageFilter(verticalConvolution))
}

object ReversedSobelOperator extends BidirectionalEdgeDetector(null, null) {
  private val horizontalMatrix = Seq(Seq(1.0, 2.0, 1.0), Seq(0.0, 0.0, 0.0), Seq(-1.0, -2.0, -1.0))
  private val horizontalConvolution = new Convolution(3, 3)
  horizontalConvolution.init(horizontalMatrix)

  private val verticalMatrix = Seq(Seq(1.0, 0.0, -1.0), Seq(2.0, 0.0, -2.0), Seq(1.0, 0.0, -1.0))
  private val verticalConvolution = new Convolution(3, 3)
  verticalConvolution.init(verticalMatrix)

  reinit(new ConvolutionImageFilter(horizontalConvolution), new ConvolutionImageFilter(verticalConvolution))
}

object BidirectionalSobelEdgeDetector extends BidirectionalEdgeDetector(SobelOperator, ReversedSobelOperator)