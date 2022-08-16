import com.bdftool.parser.ConfigParser
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers

class ConfigParserTest extends AnyFlatSpecLike with Matchers{
  behavior of "fromSource"

  it should "parse Config" in {
    val conf = ConfigParser.fromSource(getClass.getResource( "testconf.json"))
    println(conf)
    conf.isRight should be(true)
  }

  behavior of "decodeField"

  it should "parse Field" in {
    val conf = ConfigParser.decodeField(
      """
        |{
        |      "name": "id",
        |      "fType": "uuid"
        |    }
        |""".stripMargin)
    println(conf)
    conf.isRight should be(true)
  }

}
