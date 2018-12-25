

  function resetInput() {
    $("#UserName").val("");
    $("#EmailAddress").val("");
    $("#password").val("");
  }

  $('#loginForm').submit(function(e) {
    // get all the inputs into an array.
    var $inputs = $('#loginForm :input');

    // not sure if you wanted this, but I thought I'd add it.
    // get an associative array of just the values.
    var values = {};
    $inputs.each(function() {
      if(this.name == "RememberMe")
        values[this.name] = this.checked;
      else
       values[this.name] = $(this).val();
    });

    var registerData = {
      username: values["UserName"],
      password: values["password"],
      rememberMe: values["RememberMe"],
    };
    //send to server
    $.ajax({
      url: loginURL,
      type: "POST",
      data: JSON.stringify(registerData),
      dataType: "json",
      contentType: "application/json",
      success: function(data) {
        //store login token in local storage
        //must prevent XSS attack!!!
        //if rememberMe option then store into localStorage
        //else sessionStorage
        if(registerData.rememberMe)
          localStorage.setItem('token', data.token);
        else sessionStorage.setItem('token', data.token);
        window.open('index.html', '_self', 'resizable=yes');
      },
      error: function(err) {
        try {
          addErrorMessage(err.responseJSON.message);
          resetInput();
        } catch (err) {
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
