# pi-taster

a tool which applys Bailey–Borwein–Plouffe formula to geneate pi sequence for analysis... 

## Overview
Is it fun? what is the inside story of this sequence?
<pre>
    3.14159265358979323846264338327950288419716939937510582097494459230781640628620899862803482534211706
    798214808651328230664709384460955058223172535940812848111745028410270193852110555964462294895493038196
    442881097566593344612847564823378678316527120190914564856692346034861045432664821339360726024914127...
</pre>

## Build
 <pre> lein uberjar </pre>

## API
- getStatAt("1000") will give status(in json) about the 3000th(3*1000) digit of pi, like the following: 

<pre>
[{"name":"0", "freq":{"f2":77, "f1":89, "f0":93}},
 {"name":"1", "freq":{"f2":96, "f1":96, "f0":116}},
 {"name":"2", "freq":{"f2":96, "f1":104, "f0":103}},
 {"name":"3", "freq":{"f2":77, "f1":86, "f0":103}},
 {"name":"4", "freq":{"f2":123, "f1":102, "f0":93}},
 {"name":"5", "freq":{"f2":110, "f1":108, "f0":97}},
 {"name":"6", "freq":{"f2":102, "f1":106, "f0":94}},
 {"name":"7", "freq":{"f2":90, "f1":102, "f0":95}},
 {"name":"8", "freq":{"f2":108, "f1":101, "f0":101}},
 {"name":"9", "freq":{"f2":121, "f1":106, "f0":105}}]
</pre>

## Docker
TODO

## License

Copyright © 2015 Zaza Deng

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
