var express = require('express');
var app = express();
var bodyParser = require("body-parser");
var compress = require('compression');

app.use(compress());
app.disable('etag');

app.use(bodyParser.json()); // to support JSON-encoded bodies
app.use(bodyParser.urlencoded({ // to support URL-encoded bodies
    extended: true
}));

var apiRoutes = express.Router();

var currentId = 1;

apiRoutes.get('/books', function (req, res) {
    var books = [];
    for (var index = 0; index < 1000; index++) {
        books.push({ id: index + 1, name: "Books " + index + 1 });
    }
    res.json(books);
});

apiRoutes.post('/books', function (req, res) {
    var book = {
        id: currentId,
        name: req.body.name
    };
    res.status(201);
    currentId++;
    res.json(book);
});

apiRoutes.put('/books/:id', function (req, res) {
    var book = {
        id: req.params.id,
        name: req.body.name
    };
    res.json(book);
});

apiRoutes.delete('/books/:id', function (req, res) {
    res.status(204);
    res.json({ "message": "deleted" });
});



app.use('/', apiRoutes);

var listeningPort = 3000;
var server = app.listen(listeningPort, function () {
    var port = server.address().port;
    console.log("Server is up " + port);
});