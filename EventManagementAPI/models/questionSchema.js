var mongoose = require('mongoose');
var Schema = mongoose.Schema;


var questionSchema = new Schema({

  questionNo: {
    type:String
  },
  question:{
     type:String
   },
    answer:{
        type:String
    }
});


//const dataSchema = new mongoose.Schema({});
//const questions = mongoose.model('questions', dataSchema, 'questions');

var questions = mongoose.model('questions', questionSchema);
module.exports = questions;
