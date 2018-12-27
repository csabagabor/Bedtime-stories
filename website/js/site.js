
var currentTaleDate;

var getDates = function(startDate, endDate) {
  var dates = [],
      currentDate = startDate,
      addDays = function(days) {
        var date = new Date(this.valueOf());
        date.setDate(date.getDate() + days);
        return date;
      };
  while (currentDate <= endDate) {
    dates.push(currentDate);
    currentDate = addDays.call(currentDate, 1);
  }
  return dates;
};

function showTaleByDate(date){
  var baseURL = window.location.href.split('?')[0];//url without query params
  window.location = baseURL +"?date="+date;
}

function getTaleByURL(url){
  let searchParams = new URLSearchParams(url.search);
  let paramDate = searchParams.get('date');
  if(paramDate)
    getTaleByDate(paramDate);
  else getTaleByDate(formatDateToString(new Date()));//show today's tale
}

async function getTaleByDate(date){
  currentTaleDate = date;
  let getURL = apiTaleURL;
  getURL+=date;
  $.ajax({
      url: getURL
  }).then(function(data) {
     if(data ==""){
       addErrorMessage("Tale not available! Try another one!");
     }
     else{
       if(formatDateToString(new Date()) !== date){
         $('#story-big-title').text("Tale: "+date);
       }
       $('#tale-genre').text("Genre: "+data.genre.type);
       $('#tale-author').text("Author: "+data.author.name);
       $('#tale-title').text(data.title);
       $('#tale-description').text(data.description);
     }
  });
  //show user's Rating
  var rating = await getOwnRatingByDate(date);
  appendRating(rating);
  showTaleRatingByDate(date);
  appendFavorite(date);
}



function showTaleRatingByDate(date){
  $.ajax({
      url: apiRatingURL+date
  }).then(function(data) {
     $('#average-rating').text("Avg. Rating: "+ data.rating.toFixed(2) + "("+ data.nr_rating +" ratings)");
  });
}

async function getOwnRatingByDate(date){
  var rating = 0;
  var success = false;
  await $.ajax({
      url: apiOwnRatingURL+date
  }).then(function(data) {
     if(data.rating){
       rating = data.rating;
       success = true;
    }
  });
  if(success)
    return Number(rating);
  else return 0;
}

async function setOwnRatingByDate(date, rating){
  //send data to api
  var ratingData;
  var httpMethod = 'POST';
  var oldRating = await getOwnRatingByDate(date);
  //check if it has been rated before or not
  if(oldRating === 0){//not rated
    ratingData = {
      "rating" : rating
    }
    httpMethod = 'POST';
  }
  else{//if rated before, update the previous rating
    ratingData = {
      "rating" : rating,
      "oldRating" : oldRating
    }
    httpMethod = 'PUT';
  }

  $.ajax({
   url: apiRatingURL+date,
   type: httpMethod,
   data : JSON.stringify(ratingData),
   dataType: "json",
   contentType: "application/json",
   success: function(data){
        //success
          //change average rating shown
      showTaleRatingByDate(date);
    },
   error: function(err) {
      console.log(err);
    }
  });
}

function appendRating(rating){
  $("#rating").emojiRating({
     initRating : rating,
     fontSize: 32,
     onUpdate: function(count) {
       setOwnRatingByDate(currentTaleDate,count);
     }
   });
}

function formatDateToString(date){
    year = date.getFullYear();
    month = date.getMonth()+1;
    dt = date.getDate();

    if (dt < 10) {
      dt = '0' + dt;
    }
    if (month < 10) {
      month = '0' + month;
    }
    return (year+'-' + month + '-'+dt);
}

function appendItemsToArchiveList(){
  var archiveListElem = document.getElementById("archive-list");
  $.get(fullDatesUrl, function(data, status){
    data.forEach(function(prettyDate){
      var listItem = document.createElement("a");
      listItem.appendChild(document.createTextNode(prettyDate));
      listItem.href = "javascript:showTaleByDate('"+prettyDate+"');";
      listItem.className = "dropdown-item";
      archiveListElem.appendChild(listItem);
    });
  });
}

function appendFavorite(date){
  $.get(allFavorites, function(data, status){
    if(data.includes(date)){
      $("#favorite").attr("src","http://icons.iconarchive.com/icons/custom-icon-design/flatastic-3/32/favorites-add-icon.png");
    }
  });
}

function addToFavorite(){
  if($("#favorite").attr("src") != "http://icons.iconarchive.com/icons/custom-icon-design/flatastic-3/32/favorites-add-icon.png"){
    //add as favorite
    $.ajax({
     url: favoriteURL+currentTaleDate,
     type: 'POST',
     data : "",
     dataType: "json",
     contentType: "application/json",
     success: function(data){
          //success
          //change average rating shown
            $("#favorite").attr("src","http://icons.iconarchive.com/icons/custom-icon-design/flatastic-3/32/favorites-add-icon.png");
      },
     error: function(err) {
        console.log(err);
      }
    });
  }
  else{
    //remove from favorite
    $.ajax({
     url: favoriteURL+currentTaleDate,
     type: 'DELETE',
     data : "",
     dataType: "json",
     contentType: "application/json",
     success: function(data){
          //success
          //change average rating shown
            $("#favorite").attr("src","http://icons.iconarchive.com/icons/custom-icon-design/flatastic-3/32/favorites-remove-icon.png");
      },
     error: function(err) {
        console.log(err);
      }
    });
  }
}

function editTale(){
  window.open('edit.html?date='+currentTaleDate, '_self', 'resizable=yes');
}

function deleteTale(){
  $.ajax({
   url: deleteTaleURL+currentTaleDate,
   type: 'DELETE',
   data : "",
   dataType: "json",
   contentType: "application/json",
   success: function(data){
        //success
        addSuccessMessage("Tale successfully deleted!");
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
    if(window.location.href.indexOf("top") > -1){
      //top.html
    }
    else{
      //index.html
      getTaleByURL(window.location);
      appendItemsToArchiveList();
      hideLoadingScreen();
      //check if user is admin
      if(isAdmin(user)){
        $("#edit-delete").show();
        $("#reviewBtn").show();
      }
      else {
        $("#edit-delete").hide();//not neccessary
        $("#reviewBtn").hide();//not neccessary
      }
    }
  }
}

$(document).ready(function() {
    main();
});
