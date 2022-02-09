package it.unisa.agency_formation.utils;

public class Check {

    /**
     * Questo metodo controlla se il parametro email rispetta il giusto formato
     *
     * @param email da controllare
     * @return boolean (true = rispettato , false = altrimenti)
     */

    public static boolean checkEmail(String email) {
        if (email == null) {
            return false;
        }
        String regular = "^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
        return email.matches(regular);
    }

    /**
     * Questo metodo controlla se il parametro password rispetta il giusto formato
     *
     * @param pwd password
     * @return boolean (true = rispettato , false = altrimenti)
     */

    public static boolean checkPwd(String pwd) {
        if (pwd == null) {
            return false;
        }
        //String regular = "^.*(?=.*[a-z A-Z])(?=.*\\d)(?=.*[!#$%&? \"]).*$";
        String regularSimple = "^[A-Za-z0-9]{3,16}$";
        return pwd.matches(regularSimple);
    }

    /**
     * Questo metodo controlla se il parametro nome rispetta il giusto formato
     *
     * @param name nome di un utente
     * @return boolean (true = rispettato , false = altrimenti)*/

    public static boolean checkName(String name) {
        if (name == null) {
            return false;
        }
        String regular = "^[A-Za-z]{2,32}$";
        return name.matches(regular);
    }

    /**
     * Questo metodo controlla se il parametro cognome rispetta il giusto formato
     *
     * @param surname , cognome dell'utente
     * @return boolean (true = rispettato , false = altrimenti)*/

    public static boolean checkSurname(String surname) {
        if (surname == null) {
            return false;
        }
        String regular = "^[A-Za-z]{2,32}$";
        return surname.matches(regular);
    }
    /*----------check per la creazione di un team----------*/

    /**
     * Questo metodo controlla se il parametro nome del progetto rispetta il giusto formato
     *
     * @param projectName nome del progetto
     * @return boolean (true = rispettato , false = altrimenti)*/

    public static boolean checkProjectName(String projectName) {
        String regular = "^[\\w\\sA-Za-z]{1,32}$";
        return projectName.matches(regular);
    }

    /**
     * Questo metodo controlla se il parametro descrizione del team rispetta il giusto formato
     *
     * @param desc descrizione del team e di cosa farà
     * @return boolean (true = rispettato , false = altrimenti)*/

    public static boolean checkDescription(String desc) {
        String regular = "^[\\w\\s,.;:+#/-]{1,128}$";
        return desc.matches(regular);
    }

    /**
     * Questo metodo controlla se il parametro nome del team rispetta il giusto formato
     *
     * @param teamName , nome del team
     * @return boolean (true = rispettato , false = altrimenti)*/

    public static boolean checkTeamName(String teamName) {
        String regular = "^[\\w\\sA-Za-z]{1,32}$";
        return teamName.matches(regular);
    }

    /**
     * Questo metodo controlla se il parametro competenze rispetta il giusto formato
     *
     * @param competence , competenze necessarie per svolgere il progetto del team
     * @return boolean (true = rispettato , false = altrimenti)*/

    public static boolean checkCompetence(String competence) {
        String regular = "^[\\w\\s,.;:+#/-]{1,512}$";
        return competence.matches(regular);
    }
}
