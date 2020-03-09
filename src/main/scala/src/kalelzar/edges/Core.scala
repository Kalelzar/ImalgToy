package src.kalelzar.edges

import java.awt.color.ColorSpace
import java.awt.image.ColorConvertOp
import java.io.File

import javax.imageio.ImageIO
import src.kalelzar.edges.filter.ImageFilters

object Core extends App {


  val src = new File("src.png")
  val img = ImageIO.read(src)
  val op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null)
  op.filter(img, img)
  var ind = 0

  //val img = new ThresholdFilter(40)(ImageFilters.DoubleThinnedSobelEdgeDetector(img))


  //val g = laplaceImage.getGraphics

  println(s"Processing image: ${src.getAbsolutePath}")
  //  (0 until 11).foreach{
  //    x=>
  //      val dx = (x+1)+x*16
  //      (0 until 11).foreach{
  //        y=>
  //          val dy = (y+1)+y*16
  //          val preimg = img.getSubimage(dx, dy, 16, 16)
  //          val op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null)
  //          op.filter(preimg, preimg)
  //
  //          val laplace = ReversedLaplaceFilter(preimg)
  //
  //          ind+=1
  //          g.drawImage(laplace, dx, dy, null)
  //      }
  //  }

  //  val tsobelImage = ImageFilters.ThinnedSobelEdgeDetector(img)
  //
  //  val tsobelOut = new File(s"out/thinnedsobel.png")
  //  ImageIO.write(tsobelImage, "png", tsobelOut)

  val sobelImage = ImageFilters.ReversedSobelEdgeDetector(img)

  val sobelOut = new File(s"out/sobel.png")
  ImageIO.write(sobelImage, "png", sobelOut)

  //  val rtsobelImage = ImageFilters.ReversedThinnedSobelEdgeDetector(img)
  //
  //  val rtsobelOut = new File(s"out/reversedthinnedsobel.png")
  //  ImageIO.write(rtsobelImage, "png", rtsobelOut)
  //
  //  val rsobelImage = ImageFilters.ReversedSobelEdgeDetector(img)
  //
  //  val rsobelOut = new File(s"out/reversedsobel.png")
  //  ImageIO.write(rsobelImage, "png", rsobelOut)
  //
  //  val dtsobelImage = ImageFilters.DoubleThinnedSobelEdgeDetector(img)
  //
  //  val dtsobelOut = new File(s"out/doublethinnedsobel.png")
  //  ImageIO.write(dtsobelImage, "png", dtsobelOut)
  //
  //  val dsobelImage = ImageFilters.DoubleSobelEdgeDetector(img)
  //
  //  val dsobelOut = new File(s"out/doublesobel.png")
  //  ImageIO.write(dsobelImage, "png", dsobelOut)
  //
  //  val laplaceImage = ImageFilters.LaplaceEdgeDetector(img)
  //  val laplaceOut = new File(s"out/laplace.png")
  //  ImageIO.write(laplaceImage, "png", laplaceOut)
  //
  //  val rlaplaceImage = ImageFilters.ReversedLaplaceEdgeDetector(img)
  //  val rlaplaceOut = new File(s"out/rlaplace.png")
  //  ImageIO.write(rlaplaceImage, "png", rlaplaceOut)
  //
  //  val dlaplaceImage = ImageFilters.DoubleLaplaceEdgeDetector(img)
  //  val dlaplaceOut = new File(s"out/dlaplace.png")
  //  ImageIO.write(dlaplaceImage, "png", dlaplaceOut)
  //
  //  val dtlaplaceImage = ImageFilters.DoubleThinnedLaplaceEdgeDetector(img)
  //  val dtlaplaceOut = new File(s"out/dtlaplace.png")
  //  ImageIO.write(dtlaplaceImage, "png", dtlaplaceOut)

}
