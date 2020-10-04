const express = require("express");
const http = require("http");
const socketIo = require("socket.io");
const axios = require("axios");
const port = process.env.PORT || 4001;
const index = require("./routes/index");
const app = express();
app.use(index);
const server = http.createServer(app);
const io = socketIo(server); // < Interesting!

function getRandomInt(max) {
  return Math.floor(Math.random() * Math.floor(max));
}

const getApiAndEmit = async socket => {
  try {
    let res;
    const type = getRandomInt(2)
    if (type === 0) {
      res = await axios.post(
        'http://localhost:8080/orderbook/random/buy'
      );
    } else {
      res = await axios.post(
        'http://localhost:8080/orderbook/random/sell'
      )
    }
    console.log(res.data);
    socket.emit("FromAPI", res.data.id)
  } catch (error) {
    console.error(`Error: ${error.code}`)
  }
}

let interval;
io.on("connection", socket => {
  console.log("New client connected");
  if (interval) {
    clearInterval(interval);
  }
  interval = setInterval(() => getApiAndEmit(socket), 1000);
  socket.on("disconnect", () => {
    console.log("Client disconnected");
  });
});

server.listen(port, () => console.log(`Listening on port ${port}`));