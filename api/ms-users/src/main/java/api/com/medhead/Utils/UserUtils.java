package api.com.medhead.Utils;

import api.com.medhead.model.User;
import au.com.bytecode.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserUtils {

    private final static String USERS_FILE_NAME = "/users.csv";
    private final static char SEPARATOR = ';';
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> findUsers() throws IOException {
        List<String[]> data = new ArrayList<String[]>();
        List<User> userList = new ArrayList<User>();

        try (InputStream inputStream = getClass().getResourceAsStream(USERS_FILE_NAME);
             CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream), SEPARATOR);
        ) {
            String[] nextLine = null;
            while ((nextLine = csvReader.readNext()) != null) {
                int size = nextLine.length;

                // ligne vide
                if (size == 0) {
                    continue;
                }
                String debut = nextLine[0].trim();
                if (debut.length() == 0 && size == 1) {
                    continue;
                }

                // ligne de commentaire
                if (debut.startsWith("#")) {
                    continue;
                }
                data.add(nextLine);
            }
            int count = 1;
            for (String[] oneData : data) {
                int id = count;
                String firstName = oneData[0];
                String lastName = oneData[1];
                String address = (oneData[2].trim());
                String city = oneData[3];
                String postCode = oneData[4];
                String lon = oneData[6];
                String lat = oneData[5];
                Double latitude = Double.valueOf(lat);
                Double longitude = Double.valueOf(lon);
                String phone = oneData[7];
                String email = oneData[8];
                String birthdateText = oneData[9];
                LocalDate birthDate = LocalDate.parse(birthdateText);
                String password = passwordEncoder.encode(oneData[10]);
                String socialNumber = oneData[11];

                User u = new User(count, firstName, lastName, password, address, city, postCode, longitude, latitude, phone, email, birthDate, socialNumber);
                userList.add(u);
                count++;
            }
        }
        return userList;
    }
}
