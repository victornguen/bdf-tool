import com.bdftool.parser.TypeMapper.mapType
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers

class TypeMaperTest extends AnyFlatSpecLike with Matchers{
behavior of "mapType"

  it  should "return different values"   in{
    val r = mapType("sentence")
    val t = mapType("sentences")
    val e = mapType("email")
    println(r())
    println(r())
    println(t())
    println(t())
    println(e())
    println(e())
    println(e())
    println(e())
    true
  }
}
