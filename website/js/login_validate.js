$(function () {


    $('[data-toggle="tooltip"]').tooltip();


    var input_username = "#UserName";
    var input_password = "#password";

    var error_username = true;
    var error_password = true;

    var tooltip_username_shown = 0;
    var color_correct = "greenyellow";
    var color_incorrect = "red";
	  var color_original = "SkyBlue";
    var button = "#btn_LogIn";


    $(input_password)
        .attr('data-original-title', "")
        .css("border-color", color_original)
        .tooltip('_fixTitle').tooltip('hide');


    $(input_username)
        .attr('data-original-title', "")
        .css("border-color", color_original)
        .tooltip('_fixTitle').tooltip('hide');


    //when starting the page, if the inputs are correct, do not disable the button(when refreshing the page)
    CheckUsername(1);
    CheckPassword(1);


    $(input_password).keyup(function () {
        CheckUsername(1);
        CheckPassword(0);
    });
    $(input_username).keyup(function () {
        CheckPassword(1);
        CheckUsername(0);
    });


    function SetTextTooltipusername(text) {
        $(input_username)
            .attr('data-original-title', text)
            .tooltip('_fixTitle');
        $(input_username).css("border-color", color_incorrect);
    }


    function SetTextTooltipPassword(text) {
        $(input_password)
            .attr('data-original-title', text)
            .tooltip('_fixTitle');
        $(input_password).css("border-color", color_incorrect);
    }

    function DisableButton() {
        $(button).css('opacity', '0.5');
        $(button).attr("onClick", "return false");
    }

    function EnableButton() {
        $(button).css('opacity', '1');
        $(button).attr("onClick", "return true");
    }



    function HideTooltipusername() {
        $(input_username)
            .attr('data-original-title', "")
            .tooltip('_fixTitle');
        $(input_username).css("border-color", color_correct);
        $(input_username).tooltip('hide');
    }

    function HideTooltipPassword() {
        $(input_password)
            .attr('data-original-title', "")
            .tooltip('_fixTitle');
        $(input_password).css("border-color", color_correct);
        $(input_password).tooltip('hide');
    }

    $(button).click(function () {
        CheckPassword(0);
        CheckUsername(0);

    });



    function CheckUsername(justCheck) {
        var temp_tooltip_username_shown = 0;
        var outputString = "";
        var username = $(input_username).val();

        if (username === "") {
            temp_tooltip_username_shown = 1;
            outputString="Username field is required!";
        }

        if (justCheck===0 && temp_tooltip_username_shown !== tooltip_username_shown) {
            SetTextTooltipusername(outputString);
            $(input_username).tooltip('show');
            tooltip_username_shown = temp_tooltip_username_shown;
        }

        if (outputString.length > 0) {
            error_username = true;
            DisableButton();
        }
        else {
            tooltip_username_shown = 0;
            HideTooltipusername();
            error_username = false;
            if (error_username === false && error_password === false) EnableButton();
        }
    }


    function CheckPassword(justCheck) {

        if ($(input_password).val() === "") {

            if (justCheck === 0) {
                SetTextTooltipPassword("Password field is required!");
                 $(input_password).tooltip('show');
            }
            DisableButton();
            error_password = true;
        }
        else {
            error_password = false;
            HideTooltipPassword();
            if (error_username === false && error_password === false) EnableButton();
        }
    }


});
