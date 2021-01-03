package ru.ezhov.test.printer

import java.io.File
import java.io.FileInputStream
import javax.print.*
import javax.print.attribute.HashPrintRequestAttributeSet
import javax.print.attribute.PrintRequestAttributeSet
import javax.print.attribute.standard.*


fun main() {
    val attrSet: PrintRequestAttributeSet = HashPrintRequestAttributeSet()
    attrSet.add(MediaSizeName.ISO_A4)
    attrSet.add(Copies(1))
    attrSet.add(Sides.ONE_SIDED)
    attrSet.add(OrientationRequested.PORTRAIT)

    val defaultService = PrintServiceLookup.lookupDefaultPrintService()
    defaultService
            .supportedDocFlavors
            .forEach { f -> println(f.mediaType + ":" + f.mimeType + ":" + f.representationClassName) }

    val job = defaultService.createPrintJob()

    job.print(dataText().myDoc, attrSet)

    println("~ print ~")
}

fun dataText() =
        Data(myDoc = SimpleDoc(
                FileInputStream("D:\\branch_report.sh").readBytes(),
                DocFlavor.BYTE_ARRAY.AUTOSENSE,
                null)
        )

fun dataImage() =
        Data(myDoc = SimpleDoc(
                FileInputStream(File("D:\\avatar.jpg")),
                DocFlavor.INPUT_STREAM.PNG,
                null)
        )

data class Data(
        val myDoc: Doc
)