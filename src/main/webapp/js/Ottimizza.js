function ottimizza(index) {
    console.log(index);
    var logoLoader = document.getElementsByName("logo-loader")[index];
    var noButtons = document.getElementsByName("ottimizza")[index];
    $(noButtons).css("display", "none");
    $(logoLoader).css("display", "block");
    $(logoLoader).html("<img src=\"img/dots.gif\">");
    $("button").attr("disabled", "disable");
}

function loading() {
    $("#ottimizza").css("display", "none");
    $('#logo-loader').css("display", "block");
    $('#logo-loader').html("<img src=\"img/dots.gif\">");
    $("button").attr("disabled", "disable");
}