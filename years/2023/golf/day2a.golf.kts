println(java.io.File("2").readLines().withIndex().filter{(_,l)->l.split(": ")[1].split("; ").map{it.split(", ").map{it.split(" ")}}.all{mutableMapOf("red" to 12, "green" to 13, "blue" to 14).also{m->it.forEach{(n,c)->m[c]=m[c]!!-n.toInt()}}.values.all{it>=0}}}.sumOf{it.index+1})
