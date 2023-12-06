val (x,y)=java.io.File("6").readLines().map{Regex("(\\d+)").findAll(it.split(":")[1]).map{it.value.toInt()}}
println(x.zip(y).map{(t,r)->(0..t).count{(t-it)*it>r}}.reduce{a,b->a*b})
