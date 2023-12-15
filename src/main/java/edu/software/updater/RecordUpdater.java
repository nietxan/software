package edu.software.updater;

import java.util.HashMap;
import java.util.Map;

public class RecordUpdater {

    private final Map<Update, Object[]> updateList = new HashMap<>();

    // Big f issue
    public void addUpdate(Update update, Object... args) {
        updateList.put(update, args);
    }

    public void updateAll() {
        for (Update update : updateList.keySet()) {
            update.update(updateList.get(update));
        }
    }
}
