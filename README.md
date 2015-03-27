# pi-taster

a tool which applys Bailey–Borwein–Plouffe formula to geneate pi sequence for analysis... 

## Overview
Is it fun? what is the inside story of this sequence? Wanna use D3 to see what is happening.
<pre>
 3.141592653589793238462643383279502884197169399375105820974944592307816406286208
 99862803482534211706798214808651328230664709384460955058223172535940812848111745
 02841027019385211055596446229489549303819644288109756659334461284756482337867...
</pre>

## Build
 <pre> lein uberjar </pre>

## API
- getStatAt("1000") will give us some view (my view) from 0th to the 3000th (3*1000) digit of pi, like the following: 

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
What will be the answer after 100 billion? Let's turn to the cloud ...

###Build
<pre>
docker build -t zaza/pi .
</pre>

##Run
<pre>
docker run -t -i zaza/pi java -jar app-standalone.jar 100
</pre>
this will give us the dicimal place to 3 * 100.

## License

Copyright © 2015 Zeyu Deng

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
