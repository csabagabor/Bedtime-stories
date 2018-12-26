var serverUrl = "http://localhost:8080";
var apiTaleURL = serverUrl+"/api/tale/";
var apiRatingURL = serverUrl+"/api/tale/rating/";
var checkLoginURL = serverUrl + "/token/check-login";
var apiTopTalesURL = serverUrl+"/api/tale/top/";
var registerURL = serverUrl + "/api/signup";
var loginURL = serverUrl + "/token/generate-token";
var changePassURL = serverUrl + "/token/changePassword";
var changeUserURL = serverUrl + "/token/changeUser";
var postTale = serverUrl + "/api/tale/tales";
var getGenres = serverUrl + "/api/genre/";
var getAuthors = serverUrl + "/api/author/";
var favoriteURL = serverUrl + "/api/favorite/";
var allFavorites = serverUrl + "/api/favorite";
var deleteTaleURL = serverUrl + "/api/tale/tales/";
var editTaleURL = serverUrl + "/api/tale/tales/";
var postedTalesURL = serverUrl + "/api/tale/posted";
var deleteTaleByIdURL = serverUrl + "/api/tale/tales/id/";
var updateTaleByIdURL = serverUrl + "/api/tale/tales/id/";
var fullDatesUrl = serverUrl + "/api/tale/dates/full";
var availableDatesUrl = serverUrl + "/api/tale/dates";


function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

$.ajaxSetup({
    beforeSend: function(xhr) {
        if(localStorage.getItem("token"))
          xhr.setRequestHeader('Authorization', 'Bearer '+localStorage.getItem("token"));
        else if(sessionStorage.getItem("token"))
          xhr.setRequestHeader('Authorization', 'Bearer '+sessionStorage.getItem("token"));
    }
});


function logout(){
  sessionStorage.removeItem('token');
  localStorage.removeItem('token');
  window.open('login.html', '_self', 'resizable=yes');
}

function isAdmin(user){
  var res = false;
  user.roles.forEach(function(item) {
    if(item.name=="ADMIN")
      res = true;
    });
  return res;
}

async function isLoggedIn(){
  var tokenData;
  if(localStorage.getItem('token'))
     tokenData = {
        token: localStorage.getItem('token')
      };
  else if(sessionStorage.getItem('token'))
     tokenData = {
        token: sessionStorage.getItem('token')
      };
  else
      return null;

  try {
    var resdata = null;
    await $.ajax({
      url: checkLoginURL,
      type: "POST",
      data: JSON.stringify(tokenData),
      dataType: "json",
      contentType: "application/json",
      success: function(data) {
        console.log(data);
        resdata = data;
      },
      error: function(err) {
        console.log(err);
        return null;
      }
    });
  } catch (err) {
    console.log(err.message);
    return null;
  }

  return resdata;
}
