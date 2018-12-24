var registerURL = serverUrl + "/api/signup";


function resetInput() {
  $("#UserName").val("");
  $("#EmailAddress").val("");
  $("#password").val("");
}

$('#registerForm').submit(function(e) {
  // get all the inputs into an array.
  var $inputs = $('#registerForm :input');

  // not sure if you wanted this, but I thought I'd add it.
  // get an associative array of just the values.
  var values = {};
  $inputs.each(function() {
    values[this.name] = $(this).val();
  });

  var registerData = {
    username: values["UserName"],
    password: values["password"],
    email: values["EmailAddress"],
  };
  //send to server
  $.ajax({
    url: registerURL,
    type: "POST",
    data: JSON.stringify(registerData),
    dataType: "json",
    contentType: "application/json",
    success: function(data) {
      window.open('verify.html', '_self', 'resizable=yes')
      console.log(data);
    },
    error: function(err) {
        try{
        addErrorMessage(err.responseJSON.message);
        resetInput();
      }
      catch(e){
        addErrorMessage("Cannot send your request! Please try again!");
      }
      console.log(err);
    }
  });

  e.preventDefault();
});

async function main(){
  var isSignedIn = await isLoggedIn();
  if(isSignedIn){
    window.open('index.html', '_self', 'resizable=yes');
  }
  else{
    $(".fadeMe").fadeOut();
  }
}

$(document).ready(function() {
      main();
});
