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


$('#searchForm').submit(function(e) {
  // get all the inputs into an array.
  var $inputs = $('#searchForm :input');

  // not sure if you wanted this, but I thought I'd add it.
  // get an associative array of just the values.
  var values = {};
  $inputs.each(function() {
    if(this.name == "Genre" || this.name == "Author" || this.name == "Rating"){
      values[this.name] = $(this).find("option:selected").text();
    }
    else{
      values[this.name] = $(this).val();
    }
  });

  var registerData = {
    genre:values["Genre"],
    author:values["Author"],
    rating:values["Rating"]
  };




  //send to server
  sendData(registerData);

  e.preventDefault();
});

async function sendData(registerData){
  await $.ajax({
    url: apiSearchURL,
    type: "POST",
    data: JSON.stringify(registerData),
    dataType: "json",
    contentType: "application/json",
    success: function(data) {
      //window.open('verify.html', '_self', 'resizable=yes')
      console.log(data);
      $("#my-content").html("");
      for(let i=0;i<data.length ; i++){
        if(data !== null){
         $("#my-content").append(  '<div class="card content" style="width: 18rem;margin-top:40px">' +
             '<div class="card-body">' +

             "<h2>Top " +(i+1)+ " </br> " + data[i].title + "</h2>" +
             "<h7>Author: " + data[i].author.name + "</h7></br>" +
             "<h7>Genre: " + data[i].genre.type + "</h7>" +
             "<hr><p class='card-text'>" +  data[i].description.substring(0, 40) + '...' + '</p><hr>' +
             '<h5>Date added: '+ data[i].dateAdded +  '</h5>' +
             '<p>Rating: '+ data[i].rating +  '</p>' +
             '<p>Number of Ratings: '+ data[i].nrRating +  '</p>' +
             '<p id="rating-date-' + data[i].dateAdded + '"></p>' +
             '<a href="./index.html?date=' +data[i].dateAdded +  '"id="tale-date-' + data[i].dateAdded +'" class="li-modal btn btn-info">See Full description</a>' +
           '</div></div>');
          //append rating when available

       }
     }
    },
    error: function(err) {
        try{
        addErrorMessage(err.responseJSON.message);
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
