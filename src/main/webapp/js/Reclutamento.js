function controlCandidates() {
    $.ajax({
        type: 'GET',
        url: 'ListaCandidati',
        success: function (data) {
            if (data == "2") {
                $('#noCandidati').css("display", "inline");
                $('#noCandidati').css("color", "red");
                $('#noCandidati').css("font-size", "14px").html("Non ci sono candidati");
                var link = document.getElementById("viewCandidates");
                link.setAttribute('href', "#");
                return false;
            } else {
                $('#noCandidati').css("display", "none");
            }
        }
    })
}

function deleteSpan() {
    $('#noCandidati').css("display", "none");
}


function checkFileCurriculum() {
    var fileUpload = document.getElementById("fileCurriculum");
    var button = document.getElementById("uploadCurriculum");
    var filePath = fileUpload.value;
    var estensione = /(.pdf)$/i;
    if (!estensione.exec(filePath)) {
        $('#curriculumNotUpload').css("display", "inline");
        $('#curriculumNotUpload').css("color", "red");
        $('#curriculumNotUpload').css("font-size", "14px").html("<br>Formato non valido, seleziona un pdf");
    } else {
        if (fileUpload.files.item(0).size == 0) {
            $('#curriculumNotUpload').css("display", "inline");
            $('#curriculumNotUpload').css("color", "red");
            $('#curriculumNotUpload').css("font-size", "14px").html("<br>Seleziona un file");
        } else if (fileUpload.files.item(0).size > 10485760) {
            $('#curriculumNotUpload').css("display", "inline");
            $('#curriculumNotUpload').css("color", "red");
            $('#curriculumNotUpload').css("font-size", "14px").html("<br>File troppo grande");
        } else {
            button.setAttribute('type', "submit")
        }
    }
}

function checkFileDocumenti() {
    var fileUpload = document.getElementById("fileDocumenti");
    var button = document.getElementById("uploadDocumenti");
    var filePath = fileUpload.value;
    var estensione = /(.pdf)$/i;
    if (!estensione.exec(filePath)) {
        $('#documentNotUpload').css("display", "inline");
        $('#documentNotUpload').css("color", "red");
        $('#documentNotUpload').css("font-size", "14px").html("<br>Formato non valido, seleziona un pdf");
    } else {

        if (fileUpload.files.item(0).size == 0) {
            $('#documentNotUpload').css("display", "inline");
            $('#documentNotUpload').css("color", "red");
            $('#documentNotUpload').css("font-size", "14px").html("<br>Seleziona un file");
        } else if (fileUpload.files.item(0).size > 10485760) {
            $('#documentNotUpload').css("display", "inline");
            $('#documentNotUpload').css("color", "red");
            $('#documentNotUpload').css("font-size", "14px").html("<br>File troppo grande");
        } else {
            button.setAttribute('type', "submit")
        }
    }
}