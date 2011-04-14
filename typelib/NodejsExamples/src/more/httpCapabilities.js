var tcp = require("tcp");

var server = tcp.createServer(function (socket) {
  socket.setEncoding("utf8");
  socket.addListener("connect", function () {
    socket.write("hello\r\n");
  });
  socket.addListener("data", function (data) {
    socket.write(data);
  });
  socket.addListener("end", function () {
    socket.send("goodbye\r\n");
    socket.close();
  });
});
server.listen(7000, "localhost");

