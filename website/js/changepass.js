var user = null;


function resetInput() {
  $("#OldPassword").val("");
  $("#Password1").val("");
  $("#Password2").val("");
}

$('#profileForm').submit(function(e) {
  // get all the inputs into an array.
  var $inputs = $('#profileForm :input');

  // not sure if you wanted this, but I thought I'd add it.
  // get an associative array of just the values.
  var values = {};
  $inputs.each(function() {
    values[this.name] = $(this).val();
  });

  if(values["Password1"] !== values["Password2"]){
    addErrorMessage("Passwords do not match");
  }
  else{
    var registerData = {
      oldPassword: values["OldPassword"],
      newPassword: values["Password1"],
    };
    sendData(registerData);
  }


  e.preventDefault();
});


async function sendData(registerData){
  try{
          var success = false;
          //send to server
          await $.ajax({
            url: changePassURL+"/"+user.username,
            type: "PUT",
            data: JSON.stringify(registerData),
            dataType: "json",
            contentType: "application/json",
            success: function(data) {
              addSuccessMessage("Information succesfully updated!<br> You will be logged out shortly");
              console.log(data);
              //need to change token if user info was updated so better log out the user
              success = true;
            },
            error: function(err) {
                try{
                  if(err.responseJSON.message instanceof Array){
                        var str = "";
                        err.responseJSON.message.forEach(function(item) {
                          str += item +"</br>";
                        });
                      addErrorMessage(str);
                  }
                  else addErrorMessage(err.responseJSON.message);
              }
              catch(e){
                addErrorMessage("Cannot send your request! Please try again!");
              }
              console.log(err);
            }
          });

          if(success){
            await sleep(2000);
            logout();
          }
    }
catch(e){}
}


async function main(){
  user = await isLoggedIn();
  //var isSignedIn = true;
  if(user === null){
    showLoadingScreenMessage("You are not logged in",`
    <p style="color: black;">Please
     <button id="btn_SignUp" type="button" e,
      onclick="window.open('login.html', '_self', 'resizable=yes')"
     class="btn btn-primary btn-sm">Log In</button>
    `);
  }
  else{
    $("#profileNav").html("Welcome "+user.username+" ");
    $(".fadeMe").fadeOut();
    hideLoadingScreen();
  }
}

$(document).ready(function() {
      main();
});
