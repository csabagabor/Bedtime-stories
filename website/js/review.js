
async function deletePosted(elem){
  var id = $(elem).attr('id');
  var taleId = id.replace("tale-id2-","");
  var success = false;
  await $.ajax({
   url: deleteTaleByIdURL+taleId,
   type: 'DELETE',
   data : "",
   dataType: "json",
   contentType: "application/json",
   success: function(data){
        //success
        addSuccessMessage("Tale successfully deleted!");
        success = true;
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
  if(success){
    await getTopTales();
  }
}

async function acceptPosted(elem){
  var id = $(elem).attr('id');
  var taleId = id.replace("tale-id-","");
  var success = false;
  var registerData = {
    date: $("#Dates"+taleId).find("option:selected").text()
  };

  await  $.ajax({
   url: updateTaleByIdURL+taleId,
   type: 'PUT',
   data :  JSON.stringify(registerData),
   dataType: "json",
   contentType: "application/json",
   success: function(data){
        //success
        addSuccessMessage("Tale successfully accepted!");
      success = true;
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
  if(success){
    await getTopTales();
  }

}

function appendDates(){
  var str="";
  $.get(availableDatesUrl, function(data, status){
    data.forEach(function(item){
        str +="<option>" + item+ "</option>";
    });
    $(".Dates").each(function(i) {
            $(this).append(str);
    });
  });

}


async function getTopTales(){
  await $.ajax({
      url: postedTalesURL
  }).then(function(data) {
        $("#my-content").html("");
        for(let i=0;i<data.length ; i++){
          if(data !== null){
           $("#my-content").append(  '<div class="card content" style="width: 18rem;margin-top:40px">' +
               '<div class="card-body">' +

               "<p>ID " + data[i].id + "</p>" +
               "<p class='card-text'>Author: " +  data[i].author.name + '</p>' +
               "<p class='card-text'>Genre: " +  data[i].genre.type + '</p>' +
               "<p class='card-text'>Title: " +  data[i].title + '</p><hr>' +
               "<p class='card-text'>" +  data[i].description + '</p><hr>' +
               '<a onclick=acceptPosted(this) id="tale-id-' + data[i].id +'" class="li-modal btn btn-info">Accept</a>' +
               '<a onclick=deletePosted(this) id="tale-id2-' + data[i].id +'" class="li-modal btn btn-danger">Deny</a>' +
              '</br></br><label for="Dates"><span>*</span> Date when story will appear</label>'+
              "<select class='custom-select form-control Dates' name='Dates' id='Dates" +data[i].id +  "'>"+
                "<option selected>Choose...</option>" +
              "</select>"+
             '</div></div>');
         }
       }
       appendDates();
  });
}

async function main_top(){
  await getTopTales();
  //appendItemsToArchiveList();
  hideLoadingScreen();
}

async function main(){

  var user = await isLoggedIn();
  //var isSignedIn = true;
  if(user === null){
    showLoadingScreenMessage("You are not logged in",`
    <p style="color: black;">Please
     <button id="btn_SignUp" type="button" e,
      onclick="window.open('login.html', '_self', 'resizable=yes')"
     class="btn btn-primary btn-sm">Log In</button>
    `);
  }
  else if(!isAdmin(user)){
    showLoadingScreenMessage("You are not an ADMIN!",`
    <p style="color: black;">Go back to
     <button id="btn_SignUp" type="button" e,
      onclick="window.open('index.html', '_self', 'resizable=yes')"
     class="btn btn-primary btn-sm">Home</button>
    `);
  }
  else{
    $("#profileNav").html("Welcome "+user.username+" ");
    $(".fadeMe").fadeOut();
    main_top()
    hideLoadingScreen();
  }
}

$(document).ready(function() {
    main();
});
