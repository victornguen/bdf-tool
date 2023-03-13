package com.bdftool.generator

import com.bdftool.implicits._
import com.github.mjakubowski84.parquet4s.{ParquetReader, ParquetWriter, Path, RowParquetRecord}
import org.apache.parquet.schema.MessageType
import com.github.mjakubowski84.parquet4s._
import org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName
import org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.{BINARY, INT32, INT64}
import org.apache.parquet.schema.Type.Repetition.{OPTIONAL, REQUIRED}
import org.apache.parquet.schema.{LogicalTypeAnnotation, MessageType, Types}

import java.nio.file.Files
import java.time.LocalDate
import scala.collection.immutable.ListMap

object Generator {
  def generateData(fields: ListMap[String, () => String], nRows: Int): List[Seq[String]] = {
    val data = (1 to nRows).toList.map { _ =>
      fields.map { case (_, func) => func() }
    }
    data.map(_.toSeq)
  }

    def generateSchema(fields: ListMap[String, () => String], schemaName: String = "generatedSchema"): MessageType = {
        val messageBuilder = Types
            .buildMessage()
        fields.foreach{ case (name, _) =>
            messageBuilder.addField(Types.primitive(BINARY, OPTIONAL).as(LogicalTypeAnnotation.stringType()).named(name))
        }
        val schema: MessageType = messageBuilder.named(schemaName)
        schema
    }

  def createDataframe(fields: ListMap[String, () => String], nRows: Int) = {
    val schema: MessageType = generateSchema(fields)
    val data: List[Seq[String]] = generateData(fields, nRows)
      val rowParquetRecords = for {
          rowWithFiledNames <- data.map(fields.keys.zip(_))
      } yield RowParquetRecord(rowWithFiledNames.map{ case (name, v) => (name, BinaryValue(v))})
//      data.map(row => row.map(field =>  RowParquetRecord.emptyWithSchema(fields.keys).updated))
      ParquetWriter.generic(schema).writeAndClose(Path("file.parquet"), rowParquetRecords)
  }


  def generateAndWriteData(fields: ListMap[String, () => String],
                           batchSize: Int,
                           steps: Int,
                           rowNum: Long,
                           writeOptions: Map[String, String],
                           format: String,
                           outputPath: String,
                           inOneFile: Boolean
  ): Unit = {
    val remainder = (rowNum % batchSize).toInt
    // generating and writing data
    (1 to steps).foreach { _ =>
      Generator
        .createDataframe(fields, batchSize)
        .writeWithOptions(writeOptions, format, SaveMode.Append, outputPath)
    }
    if (remainder > 0)
      Generator
        .createDataframe(fields, (rowNum % batchSize).toInt)
        .writeWithOptions(writeOptions, format, SaveMode.Append, outputPath)
  }
}
