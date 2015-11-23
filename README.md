# demo-akka-remote #

Simple demo for [Akka Remoting](http://doc.akka.io/docs/akka/current/scala/remoting.html). There are two modules:
- demo-akka-remote-server: `EchoServer` responding with `EchoResponse`s to `EchoRequest`s
- demo-akka-remote-client: `EchoClient` looking up `EchoServer` on configured remote system, periodically sending `EchoRequest`s and logging the `EchoResponse`s

Either run both modules locally via separate sbt sessions or package them up as Docker images via `docker:publishLocal` and run them by giving some JVM arguments:

```
docker run --name demo-server -d \
  -p 2552:2552 \
  hseeberger/demo-akka-remote-server \
  -Dakka.remote.netty.tcp.hostname=192.168.99.100

docker run --name demo-client -d \
  -p 2550:2550 \
  hseeberger/demo-akka-remote-client \
  -Dakka.remote.netty.tcp.hostname=192.168.99.100 \
  -Decho-client.echo-server.host=192.168.99.100
```

## Contribution policy ##

Contributions via GitHub pull requests are gladly accepted from their original author. Along with any pull requests, please state that the contribution is your original work and that you license the work to the project under the project's open source license. Whether or not you state this explicitly, by submitting any copyrighted material via pull request, email, or other means you agree to license the material under the project's open source license and warrant that you have the legal authority to do so.

## License ##

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
