package ru.ezhov.test.tree.task;

import org.codehaus.jackson.map.ObjectMapper
import java.util.*
import kotlin.collections.HashMap

fun main(args: Array<String>) {
    val binaryObjects: List<BinaryObjectK> = listOf(
            BinaryObjectK(103, "Дополнительная выкладка", null),
            BinaryObjectK(100, "Лояльность Хаммер", 103),
            BinaryObjectK(101, "Хаммер", 103),
            BinaryObjectK(111, "Хаммер child", 101),
            BinaryObjectK(123, "ЮХУ", null)
    )

    val treePresenterMap: MutableMap<Int, TreePresenterK> = HashMap()
    val parentPresenters: MutableList<TreePresenterK> = ArrayList()
    for (binaryObject in binaryObjects) {
        val prnId: Int? = binaryObject.prnId
        if (prnId == null) {
            val treePresenterParent = TreePresenterK(binaryObject.id, binaryObject.name)
            treePresenterMap[binaryObject.id] = treePresenterParent
            parentPresenters.add(treePresenterParent)
        } else {
            val treePresenterParent = treePresenterMap[prnId]
            if (treePresenterParent != null) {
                val presenter = TreePresenterK(binaryObject.id, binaryObject.name)
                treePresenterParent.add(presenter)
                treePresenterMap[binaryObject.id] = presenter
            } else {
                val treePresenterParentNew = TreePresenterK(binaryObject.id, binaryObject.name)
                treePresenterMap[binaryObject.id] = treePresenterParentNew
                parentPresenters.add(treePresenterParentNew)
            }
        }
    }

    val objectMapper = ObjectMapper()
    println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(parentPresenters))
}

class BinaryObjectK(val id: Int, val name: String, val prnId: Int?)

class TreePresenterK(
        val id: Int,
        val text: String,
        var checked: Boolean = false,
        var expanded: Boolean = false,
        var leaf: Boolean = true
) {
    val data: MutableList<TreePresenterK> = ArrayList()

    fun add(child: TreePresenterK) {
        data.add(child)
        this.leaf = data.isEmpty()
    }
}