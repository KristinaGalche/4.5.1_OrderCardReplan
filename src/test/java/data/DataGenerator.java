package data;

import lombok.experimental.UtilityClass;
import java.time.LocalDate;
import java.util.Locale;
import java.time.format.DateTimeFormatter;
import com.github.javafaker.Faker;

//public class DataGenerator {

    public class DataGenerator {
        private DataGenerator() { }
//        public static RegistrationInfo generateInfo(String locale) {
//            Faker faker = new Faker(new Locale(locale));
//
//            return new RegistrationInfo(faker.address().city(),
//                    faker.name().lastName(),
//                    faker.name().firstName(),
//                    faker.phoneNumber().phoneNumber());
//        }
        public static Faker faker = new Faker(new Locale("ru"));
        public static String generateCity(String locale) {
            return faker.address().city();
        }
        public static String generateDate(int days) {
            return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }
        public static String generateName(String locale) {
            return faker.name().lastName() + " " + faker.name().firstName();
        }
        public static String generatePhone(String locale) {
            return faker.phoneNumber().phoneNumber();
        }
        public static class Registration {
            private Registration() { }
            public static RegistrationInfo generateUser(String locale) {
                return new RegistrationInfo(generateCity(locale), generateName(locale), generatePhone(locale));
            }
    }
}
