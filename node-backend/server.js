const helmet = require("helmet");
const { RateLimiterMemory } = require('rate-limiter-flexible');
const cors = require('cors');
const { query } = require('express-validator');
const bodyParser = require('body-parser');
const express = require('express');
const app = express();
const port = 3000;

const rateLimiter = new RateLimiterMemory({
  points: 10, // maximum number of requests allowed
  duration: 1, // time frame in seconds
});

const rateLimiterMiddleware = (req, res, next) => {
  rateLimiter.consume(req.ip)
      .then(() => {
        // request allowed,
        // proceed with handling the request
        next();
      })
      .catch(() => {
        // request limit exceeded,
        // respond with an appropriate error message
        res.status(429).send('Too Many Requests');
      });
};

app.use(rateLimiterMiddleware);
app.use(express.static('./public/'));
app.use(express.json());
app.use(bodyParser.json({ limit: '1mb' }));
app.use(cors());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(helmet());

app.get('/api/users', (req, res) => {
  res.send('Hello World from Node.js server!');
});

app.listen(port, () => {
  console.log(`Server listening at http://localhost:${port}`);
});
