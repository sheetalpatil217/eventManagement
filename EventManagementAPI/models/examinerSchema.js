var mongoose = require('mongoose');
var Schema = mongoose.Schema;


var examinerSchema = new Schema({

  name: {
    type:String
  },
  phone:{
     type:String
   },
    code:{
        type:String
    }
});


/*const dataSchema = new mongoose.Schema({});
const examiner = mongoose.model('examiner', dataSchema, 'examiner');*/

var examiner = mongoose.model('examiner', examinerSchema);
module.exports = examiner;
