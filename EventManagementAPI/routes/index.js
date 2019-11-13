var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var taskQ = require('../models/questionSchema');
var taskT = require('../models/teamSchema');
var taskE = require('../models/examinerSchema');
var questions = mongoose.model('questions');
var teams = mongoose.model('teams');
const Nexmo = require('nexmo');
var examiner = mongoose.model('examiner');


const nexmo = new Nexmo({
    apiKey: 'eab4d994',
    apiSecret: 'H9h2zaPhpCLsbSGI',
});





router.post('/sendcode', function (req, res) {
     console.log("send code is hit");
    console.log("req body",req.body);
    console.log("number", req.body.phone);

    examiner.find({
        phone: req.body.phone
    }, function (err, item) {
        if (err) {
            res.send("cannot find number");
        }
        console.log("",item);
        const from = '17329781084';
        const to = item.phone;
        const text = '<#> Your ExampleApp code is: ' + item.code + ' \n p0+mHOjEJQd';
        nexmo.message.sendSms(from, to, text);

        res.send("sucess");
    });
});


router.post('/signin', function (req, res) {
    examiner.find({
        phone: req.body.phone
    }, function (error, comments) {
        const response = {};
        if (comments.length) {
                    var response = {};
                    console.log("inside find", )
                        jwt.sign({
                            phone: req.body.phone,
                        }, "secretkey", (err, token) => {
                            response.status = "Success";
                            response.message = "Sucessfully logged In";
                            response.token = token;
                            console.log(response);
                            res.send(response);
                        });
        } else {
            response.status = "Failure";
            response.message = "user not found";
            res.send(response)
        }
    });
});


router.get('/questions', function (req, res) {
    var response = {};
     
       const bearerHeader = req.headers['authorization'];
       console.log("bearerHeader: " + bearerHeader);

       if (typeof bearerHeader !== "undefined") {
           const bearer = bearerHeader.split(" ");
           const bearer_token = bearer[1];
           req.token = bearer_token;
           console.log("Inside Bearer");

           jwt.verify(req.token, 'secretkey', (err, authData) => {
               if (err) {
                   res.send(403);
                   console.log("forbidden from validate");
               } else {
    var pageNo = parseInt(req.query.pageNo);
    var size = parseInt(req.query.size);
    var query = {}
    if (pageNo < 0 || pageNo === 0) {
        response = {
            "error": true,
            "message": "invalid page number, should start with 1"
        };
        return res.json(response)
    }
    query.skip = size * (pageNo - 1)
    query.limit = size
    questions.find({}, {}, query, function (err, items) {
        if (items.length > 0) {
            response.status = 200;
            response.message = "questions sent successfully";
            response.data = items;
            res.send(response);

        } else {
            response.status = 402;
            response.message = "product list empty";
            res.send(response);
        }
    });
            }
          })
      } else {
          console.log("Failed here");
          res.sendStatus(403);
      }
});

router.get('/teams', function (req, res) {
      const bearerHeader = req.headers['authorization'];

     if (typeof bearerHeader !== "undefined") {
         const bearer = bearerHeader.split(" ");
         const bearer_token = bearer[1];
         req.token = bearer_token;
         console.log("inside bearer");

         jwt.verify(req.token, 'secretkey', (err, authData) => {
             if (err) {
                 res.send(403);
                 console.log("forbidden from validate");
             } 
                 var response = {};
    teams.find({}, function (err, items) {
        //console.log(items);
        // productList.push(items);

        if (items.length > 0) {
            response.status = 200;
            response.message = "team sent successfully";
            response.data = items;
            res.send(response);

        } else {
            response.status = 402;
            response.message = "team list empty";
            res.send(response);
        }
    });     
         });
    } else {
        res.sendStatus(403);
    }
    
});


router.post('/rate', function (req, res) {
    console.log("rate profile");

  
     const bearerHeader = req.headers['authorization'];

     if (typeof bearerHeader !== "undefined") {
         const bearer = bearerHeader.split(" ");
         const bearer_token = bearer[1];
         req.token = bearer_token;
         console.log("inside bearer");

         jwt.verify(req.token, 'secretkey', (err, authData) => {
             if (err) {
                 res.send(403);
                 console.log("forbidden from validate");
             } else {

    console.log("req body", req.body);
    updateTeamScore(req, res)
     }
        });
    } else {
        res.sendStatus(403);
    }
});

function updateTeamScore(req, res) {
    console.log("in the update team score");

    teams.findOneAndUpdate({
        teamName: req.body.teamName
    }, {
        $set: {
            Q1: req.body.question1,
            Q2: req.body.question2,
            Q3: req.body.question3,
            Q4: req.body.question4,
            Q5: req.body.question5,
            Q6: req.body.question6,
            Q7: req.body.question7,
            started: true,
            progress: "1"
        }
    }, {
        new: true
    }, function (error, comments) {
        if (error) {
            console.log(error);
            var response = {};
            response.status = 402;
            response.message = "Not  Updated";
            res.send(response);
        }
        console.log("comments", comments);

        var response = {};
        response.status = 200;
        response.message = "User Updated Successfully";
        res.send(response);

    }, function (err, res) {
        if (err) {
            console.log(err);
        }
    });

}


router.post('/submit', function (req, res) {
    console.log("rate profile");


     const bearerHeader = req.headers['authorization'];

     if (typeof bearerHeader !== "undefined") {
         const bearer = bearerHeader.split(" ");
         const bearer_token = bearer[1];
         req.token = bearer_token;
         console.log("inside bearer");

         jwt.verify(req.token, 'secretkey', (err, authData) => {
             if (err) {
                 res.send(403);
                 console.log("forbidden from validate");
             } else {
   
    console.log("req body", req.body);
    calculateScore(req, res)

     }
        });
    } else {
        res.sendStatus(403);
    }
});


function calculateScore(req, res) {
    console.log("in the update team score");

    var total_score = calTotalScore(req.body.question1, req.body.question2, req.body.question3, req.body.question4, req.body.question5, req.body.question6, req.body.question7);
    console.log("total_score", total_score);
    var avg_score = calAvgScore(total_score);
    console.log("avg_score", avg_score);
    teams.findOneAndUpdate({
        teamName: req.body.teamName
    }, {
        $set: {
            Q1: req.body.question1,
            Q2: req.body.question2,
            Q3: req.body.question3,
            Q4: req.body.question4,
            Q5: req.body.question5,
            Q6: req.body.question6,
            Q7: req.body.question7,
            totalScore: total_score.toFixed(2),
            avgScore: avg_score.toFixed(2),
            started: true,
            progress: "100"
        }
    }, {
        new: true
    }, function (error, comments) {
        if (error) {
            console.log(error);
            var response = {};
            response.status = 402;
            response.message = "Not  Updated";
            res.send(response);
        }
        console.log("comments", comments);

        var response = {};
        response.status = 200;
        response.message = "User Updated Successfully";
        res.send(response);

    }, function (err, res) {
        if (err) {
            console.log(err);
        }
    });

}

function calTotalScore(q1, q2, q3, q4, q5, q6, q7) {
    console.log("first number", parseInt(q1));
    var add = parseInt(q1) + parseInt(q2) + parseInt(q3) + parseInt(q4) + parseInt(q5) + parseInt(q6) + parseInt(q7);

    console.log("addition is", add);
    return add;
}

function calAvgScore(totalscore) {
    var avg = totalscore / 7;
    return avg;
}


module.exports = router