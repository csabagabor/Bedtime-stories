var user = null;



function appendFavorites(){
  $.get(allFavorites, function(data, status){
    data.forEach(function(item){
        $("#favorite-list").append("<a href='./index.html?date=" +item+ "'>" +item+  "<a></br>");
    });
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
    appendFavorites();
    $("#profileNav").html("Welcome "+user.username+" ");
    $(".fadeMe").fadeOut();
    hideLoadingScreen();
  }
}

$(document).ready(function() {
      main();
});
