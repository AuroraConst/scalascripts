//> using file project.scala

import pprint.*
class Custom



// implicit val  customTP: pprint.TPrint[Custom] = {
//     pprint.TPrint.render(_ => "**Custom()**")
// }
val data = (1 to 100).toList
pprint.pprintln(data,width=4, height = 1000)
pprint.pprintln(data,width=6,height=10)  