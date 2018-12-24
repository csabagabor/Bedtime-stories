function addErrorMessage(msg) {
  var errMsg = `
  <div class="alert alert-danger">
  <strong>`;
  errMsg += msg;
  errMsg += `</strong></div>`;
  $("#serverMessage").html(errMsg);
}


function addSuccessMessage(msg) {
  var errMsg = `
  <div class="alert alert-success">
  <strong>`;
  errMsg += msg;
  errMsg += `</strong></div>`;
  $("#serverMessage").html(errMsg);
}
