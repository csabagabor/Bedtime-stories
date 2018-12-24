var serverUrl = "http://localhost:8080";
var apiTaleURL = serverUrl+"/api/tale/";
var apiRatingURL = serverUrl+"/api/tale/rating/";
var checkLoginURL = serverUrl + "/token/check-login";
var apiTaleURL = serverUrl+"/api/tale/";
var apiRatingURL = serverUrl+"/api/tale/rating/";
var apiTopTalesURL = serverUrl+"/api/tale/top/";


function logout(){
  sessionStorage.removeItem('token');
  localStorage.removeItem('token');
  window.open('login.html', '_self', 'resizable=yes');
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
