package daggerok

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier.VARARG
import sun.security.action.GetPropertyAction
import java.io.File
import java.security.AccessController.doPrivileged
import kotlin.text.Charsets.UTF_8

fun main(args: Array<String>) {

  val path = System::class.java.protectionDomain?.codeSource?.location?.toURI()?.path
      ?: File(doPrivileged(GetPropertyAction("java.io.tmpdir")), "kotlinpoet").path

  val directory = File(path)

  println("clean output directory: $directory")
  directory.deleteOnExit()

  println("create output directory: $directory")
  directory.mkdirs()

  val fileName = "Greeter"
  val file = File(path, "$fileName.kt")

  println("generate kotlin code into $file")
  FileSpec.builder("", fileName)
      .addFunction(FunSpec.builder("main")
          .addParameter("args", String::class, VARARG)
          .addStatement("println(*args.toList())")
          .build())
      .build()
      .writeTo(directory)

  println("\nresult:\n\n${file.readText(UTF_8)}")
}
