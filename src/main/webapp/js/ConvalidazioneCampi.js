function checkEmail() {
    var input = document.getElementsByName("email")[0];
    var button = document.getElementById("Registrati");
    var check = /^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    var esito;
    if (!input.value.match(check)) {
        $('#email').css("border-bottom", "1px solid #BB0000");
        $('#rsEmail').css("display", "inline");
        $('#rsEmail').css("color", "#BB0000").html('Email non valida');
        $('#email').css("color", "#BB0000");
        button.setAttribute("type", "button");
        esito = false;
    } else if (input.value.match(check)) {
        $('#email').css("border-bottom", "2px solid green");
        $('#rsEmail').css("display", "none");
        $('#email').css("color", "#1E1E24");
        esito = true;
    }
    return esito;
}

function checkPassword() {
    var input = document.getElementsByName("pwd")[0];
    var button = document.getElementById("Registrati");
    var check = /^[A-Za-z0-9.]{3,16}$/;
    var esito;
    if (!input.value.match(check)) {
        $('#pwd').css("border-bottom", "2px solid #BB0000");
        $('#rsPassword').css("display", "inline");
        $('#rsPassword').css("color", "#BB0000").html('Password non valida');
        $('#pwd').css("color", "#BB0000");
        button.setAttribute("type", "button");
        esito = false;
    } else if (input.value.match(check)) {
        $('#pwd').css("border-bottom", "2px solid green");
        $('#rsPassword').css("display", "none");
        $('#pwd').css("color", "#1E1E24");
        esito = true;
    }
    return esito;
}

function checkNome() {
    var input = document.getElementsByName("nome")[0];
    var button = document.getElementById("Registrati");
    var check = /^[A-Za-z]{2,32}$/;
    var esito;
    if (!input.value.match(check)) {
        $('#name').css("border-bottom", "2px solid #BB0000");
        $('#rsNome').css("display", "inline");
        $('#rsNome').css("color", "#BB0000").html('Nome non valido');
        $('#name').css("color", "#BB0000");
        button.setAttribute("type", "button");
        esito = false;
    } else if (input.value.match(check)) {
        $('#name').css("border-bottom", "2px solid green");
        $('#rsNome').css("display", "none");
        $('#name').css("color", "#1E1E24");
        esito = true;
    }
    return esito;
}

function checkCognome() {
    var input = document.getElementsByName("cognome")[0];
    var button = document.getElementById("Registrati");
    var check = /^[A-Za-z]{2,32}$/;
    var esito;
    if (!input.value.match(check)) {
        $('#surname').css("border-bottom", "2px solid #BB0000");
        $('#rsCognome').css("display", "inline");
        $('#rsCognome').css("color", "#BB0000").html('Cognome non valido');
        $('#surname').css("color", "#BB0000");
        button.setAttribute("type", "button");
        esito = false;
    } else if (input.value.match(check)) {
        $('#surname').css("border-bottom", "2px solid green");
        $('#rsCognome').css("display", "none");
        $('#surname').css("color", "#1E1E24");
        esito = true;
    }
    return esito;
}

function checkRegistrationSubmit() {
    var button = document.getElementById("Registrati");
    if (checkEmail() && checkPassword() && checkNome() && checkCognome()) {
        button.setAttribute("type", "submit");
    }
}

function checkTeam() {
    var input = document.getElementsByName("fname")[0];
    var button = document.getElementsByName("crea")[0];
    var check = /^[\w\sA-Za-z]{1,32}$/;
    var esito;
    if (!input.value.match(check)) {
        $('#fname').css("border-bottom", "2px solid #BB0000");
        $('#rsTeam').css("display", "inline");
        $('#rsTeam').css("color", "#BB0000").html('Nome team non valido');
        $('#fname').css("color", "#BB0000");
        button.setAttribute("type", "button");
        esito = false;
    } else if (input.value.match(check)) {
        $('#fname').css("border-bottom", "2px solid green");
        $('#rsTeam').css("display", "none");
        $('#fname').css("color", "#1E1E24");
        esito = true;
    }
    return esito;
}

function checkProject() {
    var input = document.getElementsByName("lname")[0];
    var button = document.getElementsByName("crea")[0];
    var check = /^[\w\sA-Za-z]{1,32}$/;
    var esito;
    if (!input.value.match(check)) {
        $('#lname').css("border-bottom", "2px solid #BB0000");
        $('#rsProject').css("display", "inline");
        $('#rsProject').css("color", "#BB0000").html('Nome progetto non valido');
        $('#lname').css("color", "#BB0000");
        button.setAttribute("type", "button");
        esito = false;
    } else if (input.value.match(check)) {
        $('#lname').css("border-bottom", "2px solid green");
        $('#rsProject').css("display", "none");
        $('#lname').css("color", "#1E1E24");
        esito = true;
    }
    return esito;
}

function checkMaxDip() {
    var input = document.getElementsByName("quantity")[0];
    var button = document.getElementsByName("crea")[0];
    var check = /^([1-9]|[1][0-9]|[2][0])$/;
    var esito;
    if (!input.value.match(check)) {
        $('#quantity').css("border-bottom", "2px solid #BB0000");
        $('#rsMaxDip').css("display", "inline");
        $('#rsMaxDip').css("color", "#BB0000").html('Numero dipendenti massimo non valido');
        $('#quantity').css("color", "#BB0000");
        button.setAttribute("type", "button");
        esito = false;
    } else if (input.value.match(check)) {
        $('#quantity').css("border-bottom", "2px solid green");
        $('#rsMaxDip').css("display", "none");
        $('#quantity').css("color", "#1E1E24");
        esito = true;
    }
    return esito;
}

function checkTeamDescr() {
    var input = document.getElementsByName("teamDescr")[0];
    var button = document.getElementsByName("crea")[0];
    var check = /^[\w\s,.;:+#/-]{1,128}$/;
    var esito;
    if (!input.value.match(check)) {
        $('#teamDescr').css("border", "2px solid #BB0000");
        $('#rsDescr').css("display", "inline");
        $('#rsDescr').css("color", "#BB0000").html('Descrizione team non valida');
        $('#teamDescr').css("color", "#BB0000");
        button.setAttribute("type", "button");
        esito = false;
    } else if (input.value.match(check)) {
        $('#teamDescr').css("border", "2px solid green");
        $('#rsDescr').css("display", "none");
        $('#teamDescr').css("color", "#1E1E24");
        esito = true;
    }
    return esito;
}

function checkTeamSubmit() {
    var button = document.getElementsByName("crea")[0];
    if (checkTeam() && checkProject() && checkTeamDescr() && checkMaxDip()) {
        button.setAttribute("type", "submit");
    }
}