


function getTaleRatingByDate(date){
  $.ajax({
      url: apiRatingURL+date
  }).then(function(data) {
     $("#rating-date-"+date).text("Avg. Rating: "+ data.rating.toFixed(2) + "("+ data.nr_rating +" ratings)");
  });
}


async function getTopTales(limit){
  await $.ajax({
      url: apiTopTalesURL + limit
  }).then(function(data) {
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
       //hideLoadingScreen();
  });
}

async function main_top(){
  await getTopTales(25);
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
