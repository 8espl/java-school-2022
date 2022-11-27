package ru.croc.task13;

import java.nio.file.attribute.UserPrincipal;

public class User {
    private String history;

    User(String history) {
        this.history = history;
    }

    public String getHistory() {
        return history;
    }

    @Override
    public String toString() {
        return getHistory();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof User))
            return false;
        User user = (User) obj;
        return history.equals(user.history);
    }

}
