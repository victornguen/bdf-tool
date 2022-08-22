import com.bdftool.generator.Generator
import com.bdftool.mapper.FakerTypesMapper
import com.bdftool.parser.{ConfigParser, ConfigTypes}
import io.circe
import org.apache.spark.sql.SparkSession
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers

class GeneratorTest extends AnyFlatSpecLike with Matchers {

  lazy val conf: Either[circe.Error, ConfigTypes.Config] = ConfigParser
    .fromSource("testdata/config/testconf.json")
  implicit lazy val spark: SparkSession = SparkSession.builder().master("local[*]").getOrCreate()

  behavior of "generateSchema"

  it should "return StructType" in {
    conf.map { conf =>
      val generatedSchema = Generator.generateSchema(FakerTypesMapper.configToFunctions(conf))
      println(generatedSchema)
      generatedSchema
    }.isRight should be(true)
  }

  behavior of "generateData"

  it should "return list of lists with random data" in {
    conf.map { conf =>
      val generatedData = Generator.generateData(FakerTypesMapper.configToFunctions(conf), 100)
      println(generatedData.mkString("\n"))
    }.isRight should be(true)
  }

  behavior of "generateData"

  it should "create Dataframe with fake data" in {
    conf.map { conf =>
      val df = Generator.createDataframe(FakerTypesMapper.configToFunctions(conf), 1000)
      df.show()
    }.isRight should be(true)
  }
}
