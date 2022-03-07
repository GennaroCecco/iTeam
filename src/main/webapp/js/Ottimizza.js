function ottimizza() {
    $("#footer").hide();
    $("#header").hide();
    $('#logo-loader').css("display", "block");
    $('#body').hide();
    $('#video').html("<video controls='false' autoplay playsinline style='pointer-events: none;' loop><source src=\"img/Loading.mp4\" type=\"video/mp4\"></video>");
    $('#binary').html("<audio id=\"binary\" controls autoplay loop hidden><source src=\"img/Loading.mp3\" type=\"audio/mpeg\"></audio>");
}

function loading() {
    $("#footer").hide();
    $("#header").hide();
    $('#logo-loader').css("display", "block");
    $('#body').hide();
    $('#video').html("<video controls='false' autoplay playsinline style='pointer-events: none;' loop><source src=\"img/Loading.mp4\" type=\"video/mp4\"></video>");
}