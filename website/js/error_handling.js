function addErrorMessage(msg) {
  var errMsg = `
  <div class="alert alert-danger">
  <strong>`;
  errMsg += msg;
  errMsg += `</strong></div>`;
  $("#serverMessage").html(errMsg);
}
