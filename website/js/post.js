var user = null;

function resetInput() {
  $("#Title").val("");
}


function appendGenres(){
  $.get(getGenres, function(data, status){
    data.forEach(function(item){
        $("#Genre").append("<option>" + item.type+ "</option>");
    });
  });
}

function appendAuthors(){
  $.get(getAuthors, function(data, status){
    data.forEach(function(item){
        $("#Author").append("<option>" + item.name+ "</option>");
    });
  });
}


$('#postForm').submit(function(e) {
  // get all the inputs into an array.
  var $inputs = $('#postForm :input');

  // not sure if you wanted this, but I thought I'd add it.
  // get an associative array of just the values.
  var values = {};
  $inputs.each(function() {
    if(this.name == "Genre" || this.name == "Author"){
      values[this.name] = $(this).find("option:selected").text();
    }
    else{
      values[this.name] = $(this).val();
    }
  });

  var registerData = {
    title: values["Title"],
    dateAdded: values["DateAdded"],
    genre:values["Genre"],
    author:values["Author"],
    description:values["Description"]
  };




  //send to server
  sendData(registerData);

  e.preventDefault();
});

async function sendData(registerData){
  await $.ajax({
    url: postTale,
    type: "POST",
    data: JSON.stringify(registerData),
    dataType: "json",
    contentType: "application/json",
    success: function(data) {
      //window.open('verify.html', '_self', 'resizable=yes')
      addSuccessMessage("Tale posted succesfully!");
      console.log(data);
      //need to change token if user info was updated so better log out the user
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
    appendGenres();
    appendAuthors();
    $("#profileNav").html("Welcome "+user.username+" ");
    $(".fadeMe").fadeOut();
    hideLoadingScreen();
  }
}

$(document).ready(function() {
      main();
});
