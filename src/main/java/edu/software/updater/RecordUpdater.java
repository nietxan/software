package edu.software.updater;

import java.util.HashMap;
import java.util.Map;

public class RecordUpdater {

    private final Map<Update, Object[]> updateList = new HashMap<>();

    // First param is Update receiver object,
    // second 'Record' that will be updated,
    // rest params are 'Record' properties that will be updated
    public void addUpdate(Update update, Object... args) {
        updateList.put(update, args);
    }

    public void updateAll() {
        for (Update update : updateList.keySet()) {
            update.update(updateList.get(update));
        }
    }
}
