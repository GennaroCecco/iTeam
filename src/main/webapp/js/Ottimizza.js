function ottimizza() {
    $("#ottimizza").css("display", "none");
    $('#logo-loader').css("display", "block");
    $('#logo-loader').html("<img src=\"img/dots.gif\">");
    $("#header").hide();
    $("button").attr("disabled", "disable");
}

function loading() {
    $("#ottimizza").css("display", "none");
    $('#logo-loader').css("display", "block");
    $('#logo-loader').html("<img src=\"img/dots.gif\">");
    $("#header").hide();
    $("button").attr("disabled", "disable");
}