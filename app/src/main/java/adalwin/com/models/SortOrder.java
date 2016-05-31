package adalwin.com.models;

/**
 * Created by aramar1 on 5/30/16.
 */
public enum SortOrder {

        OLDEST("OLDEST","oldest"),
        NEWEST("NEWEST","newest");

        private String label;



    private String description;

        SortOrder(String label, String description) {
            this.description = description;
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }

    public String getDescription() {
        return description;
    }


    }

