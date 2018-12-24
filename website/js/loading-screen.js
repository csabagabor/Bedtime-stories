var available = false;

$(document).ready(function() {
  if(!available){
    showLoadingScreenMessage("Loading...","Please wait for the page to load......");
    available = true;
  }
});

function showLoadingScreenMessage(title, body){
  var str =
  `<div class="modal" id="loading-modal">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">

      <div class="modal-header">
        <h3 class="modal-title">`;
      str += title;
      str += `</h4></div><div class="modal-body">`;
      str += body;

      str += `</div>
              </div>
              </div>
              </div>`;

  $("#loading-screen").html(str);
  $('#loading-modal').modal({
    backdrop: 'static',
    keyboard: false
  });
  $("#loading-modal").modal("show");
  available = true;
}


function hideLoadingScreen(){
  var a = $("#loading-modal");
  if(available) //modal is added dynamically to DOM, so it can happen that it isn't appended yet
    a.modal("hide");
  else
    setTimeout(a.modal("hide"), 3000);
}
