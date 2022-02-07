function view() {
    var x = document.getElementById("drop");
    var setting = x.style.display;
    if (setting == "none") {
        x.style.display = "flex";
    } else {
        x.style.display = "none";
    }
}

function viewLink() {
    $.ajax({
        type: 'GET',
        url: 'ViewMaterialeControl',
        success: function (data) {
            if (data == "2") {
                var x = document.getElementById("hrefDocumenti");
                x.style.display = "block";
            } else {
                document.getElementById('materiale').removeAttribute("onclick");
                $('#noMateriale').css("display", "inline");
                $('#noMateriale').css("color", "red");
                $('#noMateriale').css("font-size", "14px").html("<br>Al momento non &egrave; presente materiale");
            }
        }
    })
}

function deleteSpanMateriale() {
    $('#noMateriale').css("display", "none");
}

function checkFileMateriale(index) {
    var fileUpload = document.getElementsByName("materiale")[index];
    var index = index;
    var filePath = fileUpload.value;
    var estensione = /(.pdf)$/i;
    var button = document.getElementsByName("uploadMateriale")[index];
    var buttonName = document.getElementsByName("materialeNotUpload")[index];
    if (!estensione.exec(filePath)) {
        $(buttonName).css("display", "inline");
        $(buttonName).css("color", "red");
        $(buttonName).css("font-size", "14px").html("<br>Formato non valido, seleziona un pdf");
    } else if (fileUpload.files.item(0).size == 0) {
        $(buttonName).css("display", "inline");
        $(buttonName).css("color", "red");
        $(buttonName).css("font-size", "14px").html("<br>Seleziona un file");
    } else if (fileUpload.files.item(0).size > 10485760) {
        $(buttonName).css("display", "inline");
        $(buttonName).css("color", "red");
        $(buttonName).css("font-size", "14px").html("<br>File troppo grande");
    } else {
        button.setAttribute('type', "submit")
    }
}

function viewAddSkill() {
    var x = document.getElementById("addSkill");
    var setting = x.style.display;
    if (setting == "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}

function viewSpecifySkills(i) {
    var indexSkill = i;
    var x = document.getElementsByName("drop")[indexSkill];
    var setting = x.style.display;
    if (setting == "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}

function viewCompetenze(i) {
    var index = i;
    var x = document.getElementsByName("drop")[index];
    var y = document.getElementsByName("drop-button")[index];
    var setting = x.style.display;
    var setButton = y.style.display;
    if (setting == "none" && setButton == "inline") {
        x.style.display = "inline";
        y.style.display = "none";
    }
}

function viewCaricamento(i) {
    var index = i;
    var x = document.getElementsByName("drop-carica")[index];
    var y = document.getElementsByName("dropButton")[index];
    var setting = x.style.display;
    var setButton = y.style.display;
    if (setting == "none" && setButton != "none") {
        x.style.display = "inline";
        y.style.display = "none";
    }
}

function viewSkill(i) {
    var index = i;
    var x = document.getElementsByName("drop")[index];
    var setting = x.style.display;
    if (setting == "none") {
        x.style.display = "inline";
    } else
        x.style.display = "none";
}

function scioglimentoTeam(i) {
    var index = i;
    var x = document.getElementsByName("conferma-scioglimento")[index];
    var y = document.getElementsByName("flexButton")[index];
    var setSciogli = x.style.display;
    var setButton = y.style.display;
    if (setSciogli == "none" && setButton == "inline-flex") {
        x.style.display = "inline";
        y.style.display = "none";
    }
    if (setSciogli == "inline" && setButton == "none") {
        x.style.display = "none";
        y.style.display = "inline-flex";
    }

}

function checkAggiungiSkill() {
    var name = document.getElementsByName("skillName")[0];
    var description = document.getElementsByName("skillDescription")[0];
    var level = document.getElementsByName("quantity")[0];
    var button = document.getElementById("Aggiungi");
    var checkName = /^[\w\s+#/-]{1,64}$/;
    var checkDescription = /^[\w\s,.;:+#/-]{1,512}$/;
    var checkLevel = /^[1-5]$/;
    if (!name.value.match(checkName)) {
        $('#nameSkill').css("display", "inline");
        $('#nameSkill').css("color", "red");
        $('#nameSkill').css("font-size", "14px").html("Formato non valido o lunghezza errata");
    } else {
        $('#nameSkill').css("display", "none");
    }
    if (!description.value.match(checkDescription)) {
        $('#descriptionSkill').css("display", "inline");
        $('#descriptionSkill').css("color", "red");
        $('#descriptionSkill').css("font-size", "14px").html("Formato non valido o lunghezza errata");
    } else {
        $('#descriptionSkill').css("display", "none");
    }
    if (!level.value.match(checkLevel)) {
        $('#levelSkill').css("display", "inline");
        $('#levelSkill').css("color", "red");
        $('#levelSkill').css("font-size", "14px").html("Livello non valido");
    } else {
        $('#levelSkill').css("display", "none");
    }
    if (name.value.match(checkName) && description.value.match(checkDescription) && level.value.match(checkLevel)) {
        $('#nameSkill').css("display", "none");
        $('#descriptionSkill').css("display", "none");
        $('#levelSkill').css("display", "none");
        button.setAttribute('type', "submit");
    }
}

function checkSpecify(i) {
    var index = i;
    var specify = document.getElementsByName("specCompetenze")[index];
    var button = document.getElementsByName("specifica")[index];
    var check = /^[\w\s,.;:+#/-]{1,512}$/;
    if (!specify.value.match(check)) {
        $('#specifyCompetence').css("display", "inline");
        $('#specifyCompetence').css("color", "red");
        $('#specifyCompetence').css("font-size", "14px").html("Formato non valido o lunghezza errata");
    } else {
        button.setAttribute('type', "submit");
    }
}