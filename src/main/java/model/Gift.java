package model;

import java.util.ArrayList;
import java.util.List;

public class Gift {
    private final int id;
    private final String title;
    private List<Integer> sweetIds = new ArrayList<>();
    private final boolean deleted;

    private Gift(Builder b) {
        this.id = b.id;
        this.title = b.title;
        this.sweetIds = List.copyOf(b.sweetIds);
        this.deleted = b.deleted;
    }
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public List<Integer> getSweetIds() {
        return sweetIds;
    } public boolean isDeleted() {
        return deleted;
    }
    public Builder toBuilder() {
        return new Builder()
                .id(id)
                .title(title)
                .addAllSweetIds(sweetIds)
                .deleted(deleted);
    }
    public static class Builder {
        private int id;
        private String title;
        private List<Integer> sweetIds = new ArrayList<>();
        private boolean deleted=false;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder addSweetId(int sweetId) {
            this.sweetIds.add(sweetId);
            return this;
        }

        public Builder removeSweetId(int sweetId) {
            this.sweetIds.remove(Integer.valueOf(sweetId));
            return this;
        }
        public Builder addAllSweetIds(List<Integer> ids) {
            this.sweetIds.addAll(ids);
            return this;
        }

        public Builder deleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }


        public Gift build() {
            if (title == null || title.isBlank())
                throw new IllegalArgumentException("Назва подарунка не може бути порожньою");
            return new Gift(this);
        }
    }
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "Подарунок: id=" + id + ", Назва='" + title + "', id солодощів=" + sweetIds + "}";
    }
}
