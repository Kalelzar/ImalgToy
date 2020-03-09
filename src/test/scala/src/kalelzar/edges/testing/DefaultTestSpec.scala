package src.kalelzar.edges.testing

import org.scalatest.flatspec.AnyFlatSpec
import src.kalelzar.macros.Metadata

class DefaultTestSpec extends AnyFlatSpec {
  implicit def getMetadata(implicit metadata: Metadata): Metadata = metadata
}
