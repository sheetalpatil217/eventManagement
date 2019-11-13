var mongoose = require('mongoose');
var Schema = mongoose.Schema;


var teamSchema = new Schema({

  teamName: {
    type:String
  },
  totalScore:{
     type:String
   },
    avgScore:{
     type:String
   },
     Q1:{
     type:String
   } ,
            Q2: {
     type:String
   },
            Q3: {
     type:String
   },
            Q4: {
     type:String
   },
            Q5: {
     type:String
   },
            Q6: {
     type:String
   },
            Q7: {
     type:String
   },
    started:{
        type:Boolean
    },
    progress:{
        type:String
    }
});

//const dataSchema = new mongoose.Schema({});
//const teams = mongoose.model('teams', dataSchema, 'teams');
var teams = mongoose.model('teams', teamSchema);
module.exports = teams;
