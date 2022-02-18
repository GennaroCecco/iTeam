package it.unisa.agency_formation.FIA;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

public class DataFromDataset {
    public static ArrayList<DipendenteRefactor> fromDataSet() throws IOException {
        String[] HEADERS = {"id", "name", "surname", "email",
                "skill1", "skill2", "skill3", "level1", "level2", "level3"};
        Reader in = new FileReader(System.getProperty("user.home")+"\\IdeaProjects\\iTeam\\Dataset\\dataset.csv");
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withHeader(HEADERS)
                .withFirstRecordAsHeader()
                .parse(in);
        ArrayList<DipendenteRefactor> data = new ArrayList<>();
        for (CSVRecord record : records) {
            DipendenteRefactor temp = new DipendenteRefactor();
            HashMap<String, Integer> mapTemp = new HashMap<>();
            boolean flag = false;
            String id = record.get("id");
            String name = record.get("name");
            String surname = record.get("surname");
            String email = record.get("email");
            String skill1 = record.get("skill1");
            String skill2 = record.get("skill2");
            String skill3 = record.get("skill3");
            String livello1 = record.get("level1");
            String livello2 = record.get("level2");
            String livello3 = record.get("level3");
            /*if (livello1.equalsIgnoreCase("0") || livello2.equalsIgnoreCase("0") || livello3.equalsIgnoreCase("0")) {
                flag = true;
            }*/
            //if (!flag) {
                temp.setId(Integer.parseInt(id));
                temp.setNome(name);
                temp.setCognome(surname);
                temp.setEmail(email);
                mapTemp.put(skill1, Integer.parseInt(livello1));
                mapTemp.put(skill2, Integer.parseInt(livello2));
                mapTemp.put(skill3, Integer.parseInt(livello3));
                temp.setSkills(mapTemp);
                data.add(temp);
            //}
        }
        return data.size() > 0 ? data : null;
    }
}
