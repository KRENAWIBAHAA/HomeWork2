import java.util.List;

public class UserResponse {
    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public static class Result {
        private Name name;
        private Dob dob;
        private String email;
        private Location location;
        private Picture picture;
        private Id id;

        public User toUser() {
            return new User(id.getValue(), name.getFirst(), name.getLast(), dob.getAge(), email, location.getCity(), location.getCountry(), picture.getLarge());
        }

        // Getters...

        public static class Name {
            private String first;
            private String last;

            // Getters...
        }

        public static class Dob {
            private int age;

            // Getters...
        }

        public static class Location {
            private String city;
            private String country;

            // Getters...
        }

        public static class Picture {
            private String large;

            // Getters...
        }

        public static class Id {
            private String value;

            // Getters...
        }
    }
}
