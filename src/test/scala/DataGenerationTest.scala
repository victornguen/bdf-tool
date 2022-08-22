import com.bdftool.functions.DataGeneration
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers

class DataGenerationTest extends AnyFlatSpecLike with Matchers{

  behavior of "generateUUID"

  it should "return random 50-length UUID like string" in {
   val uid = DataGeneration.genUUID(50)
    println(uid)
    uid.length should be(50)
  }

  behavior of "generateDateBetween"

  it should "return random date between 01.01.2001 and 31.12.2021" in {
    val date = DataGeneration.genDateBetween("01.01.2001", "31.12.2021")
    println(date)
    assert(date.getYear >= 2000 && date.getYear <= 2021)
  }
}
