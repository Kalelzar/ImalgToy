package src.kalelzar.edges

import javax.imageio.ImageIO
import java.io.File
import java.awt.image.BufferedImage
import java.awt.Color
import java.awt.color.ColorSpace
import java.awt.image.ColorConvertOp

object Core extends App {


  val src = new File("tileset.png")
  val img = ImageIO.read(src)
  var ind = 0

  val laplaceImage = new BufferedImage(img.getWidth, img.getHeight, img.getType)
  val g = laplaceImage.getGraphics

  println(s"Processing image: ${src.getAbsolutePath}")
  (0 until 11).foreach{
    x=>
      val dx = (x+1)+x*16
      (0 until 11).foreach{
        y=>
          val dy = (y+1)+y*16
          val preimg = img.getSubimage(dx, dy, 16, 16)
          val op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null)
          op.filter(preimg, preimg)

//          val sobel = SobelEdgeDetector(preimg)
//
//          val sobelOut = new File(s"out/img-$ind-sobel.png")
//          ImageIO.write(sobel, "png", sobelOut)

          val laplace = ReversedLaplaceFilter(preimg)

//          val laplaceOut = new File(s"out/img-$ind-laplace.png")
//          ImageIO.write(laplace, "png", laplaceOut)
          ind+=1
          g.drawImage(laplace, dx, dy, null)
      }
  }

  val laplaceOut = new File(s"out/laplace.png")
  g.dispose();
  ImageIO.write(laplaceImage, "png", laplaceOut)



}
