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
apiRoutes.get('/books/:id', function (req, res) {
    var book = {
        id: req.params.id,
        name: "test"
    };
    res.json(book);
});

apiRoutes.get('/authors/:id', function (req, res) {
    if (!req.headers['x-access-token']) {
        res.status(401);
        res.json({
            message: "Not authorized"
        });
    }
    else {
        res.json({
            name: "Author 1"
        });
    }
});

apiRoutes.get('/books', function (req, res) {
    var books = [];
    for (var index = 0; index < 1000; index++) {
        books.push({ id: index + 1, name: "Books " + index + 1 });
    }
    res.setHeader('Cache-Control', "private, max-age=" + 10000);
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
    res.json({ id: 1, name: "Books " + 2 });
});



app.use('/', apiRoutes);

var listeningPort = 3000;
var server = app.listen(listeningPort, function () {
    var port = server.address().port;
    console.log("Server is up " + port);
});