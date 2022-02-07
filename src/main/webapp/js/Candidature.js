function viewFile(id, i) {
    var id = id;
    var index = i;
    $.ajax({
        type: 'GET',
        data: {"idCandidato": id},
        url: 'ViewCandidaturaControl',
        success: function (data1) {
            var cv = data1.substr(0, data1.indexOf('.'));
            var doc = data1.substr(data1.indexOf('.') + 1, data1.length);
            if (doc.length > 2) {
                var x = document.getElementsByName("hrefDocumenti")[index];
                x.style.display = "block";
            } else {
                var x = document.getElementsByName("hrefDocumenti")[index];
                x.style.display = "none";
            }
            if (cv.length > 0) {
                var x = document.getElementsByName("hrefCurriculum")[index];
                x.style.display = "block";
            } else {
                var x = document.getElementsByName("hrefCurriculum")[index];
                x.style.display = "none";
            }

        }
    })
}

function checkFile(i) {
    var index = i;
    var x = document.getElementsByName("drop")[index];
    var setting = x.style.display;
    if (setting == "none") {
        x.style.display = "flex";
    } else {
        x.style.display = "none";
    }
}

function acceptCandidature(id, index) {
    var id = id;
    var index = index;
    var date = document.getElementsByName("data1")[index];
    var time = document.getElementsByName("time")[index];
    var error = document.getElementsByName("errore")[index];
    $.ajax({
        type: 'GET',
        data: {"idCandidato": id, "data1": date.value, "time": time.value},
        url: 'AcceptCandidatureControl',
        success: function (data) {
            if (data == "1") {
                window.location.reload();
            } else {
                //window.location.reload();
                $(error).css("display", "inline");
                $(error).css("color", "red");
                $(error).css("font-size", "14px").html("Errore. Controlla la data");
            }
        }
    })
}

function assumi(id) {
    var id = id;
    $.ajax({
        type: 'GET',
        data: {"idCandidato": id},
        url: 'AssunzioneCandidatoControl',
        success: function (data) {
            if (data == "1") {
                window.location.reload();
            } else {
                window.location.reload();
            }
        }
    })
}

function rifiuta(id) {
    var id = id;

    $.ajax({
        type: 'GET',
        data: {"idCandidato": id},
        url: 'RifiutaColloquioControl',
        success: function (data) {
            if (data == "1") {
                window.location.reload();
            } else {
                window.location.reload();
            }
        }
    })
}

function rejectCandidature(id) {
    var id = id;
    $.ajax({
        type: 'GET',
        data: {"idCandidato": id},
        url: 'RejectCandidatureControl',
        success: function (data) {
            if (data == "1") {
                window.location.reload();
            } else {
                window.location.reload();
            }
        }
    })
}


function openColloquio(index) {
    var index = index;
    var x = document.getElementsByName("colloquio")[index];
    var setting = x.style.display;
    if (setting == "none") {
        x.style.display = "block";
    }
}

function closeColloquio(index) {
    var index = index;
    var x = document.getElementsByName("colloquio")[index];
    var setting = x.style.display;
    if (setting == "block") {
        x.style.display = "none";
    }
}