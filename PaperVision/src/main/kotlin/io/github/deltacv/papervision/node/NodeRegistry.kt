package io.github.deltacv.papervision.node

import io.github.deltacv.papervision.gui.CategorizedNodes

@Suppress("UNCHECKED_CAST")
object NodeRegistry {

    val nodes: CategorizedNodes = mutableMapOf()

    init {
        // Register nodes from metadata generated by KSP - NodeAnnotationProcessor
        fromMetadataClasslist(PaperNodeClassesMetadata.classList)
    }

    fun fromMetadataClasslist(classList: List<String>) {
        for (className in classList) {
            val clazz = NodeRegistry::class.java.classLoader.loadClass(className)
            val regAnnotation = clazz.getDeclaredAnnotation(PaperNode::class.java)

            if (hasSuperclass(clazz, Node::class.java)) {
                val nodeClazz = clazz as Class<out Node<*>>
                registerNode(nodeClazz, regAnnotation.category)
            }
        }
    }

    fun registerNode(nodeClass: Class<out Node<*>>, category: Category) {
        val list = nodes[category]

        val mutableNodes = nodes as MutableMap

        if (list == null) {
            mutableNodes[category] = mutableListOf(nodeClass)
        } else {
            list.add(nodeClass)
        }
    }

}

fun hasSuperclass(clazz: Class<*>, superclass: Class<*>): Boolean {
    return if (clazz.superclass == null) {
        false
    } else if (clazz.superclass == superclass) {
        true
    } else {
        hasSuperclass(clazz.superclass, superclass)
    }
}