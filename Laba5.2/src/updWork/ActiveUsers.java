package updWork;

import java.io.Serializable;
import java.util.ArrayList;

public class ActiveUsers implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<User> users;

    public ActiveUsers() {
        users = new ArrayList<>();
    }

    public void add(User user) {
        users.add(user);
    }

    public boolean isEmpty() {
        return users.isEmpty();
    }

    public int size() {
        return users.size();
    }

    public boolean contains(User user) {
        return users.contains(user);
    }

    public User get(int index) {
        return users.get(index);
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        for (User u : users) {
            buf.append(u).append("\n");
        }
        return buf.toString();
    }
}
