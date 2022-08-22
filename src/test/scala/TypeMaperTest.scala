import com.bdftool.mapper.TypeMapper.mapType
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers

class TypeMaperTest extends AnyFlatSpecLike with Matchers{
  behavior of "mapType"

  it should "return different values" in {
    val s  = mapType("sentence")
    val ss = mapType("sentences")
    val e  = mapType("email")
    (s() != s() && ss() != ss() && e() != e()) should be(true)
  }
}
