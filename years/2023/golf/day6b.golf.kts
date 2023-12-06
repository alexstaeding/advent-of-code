val (x,y)=java.io.File("6").readLines().map{it.split(":")[1].replace(Regex("\\s+"),"").toLong()}
println((0..x).count{(x-it)*it>y})
