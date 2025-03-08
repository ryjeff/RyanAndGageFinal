/*
 * This class file contains a re-usable function to add information to a battle log defined in main
 */

package com.example;

import javafx.collections.ObservableList;

public class battleLogAdds {
    private ObservableList<String> items;

    public battleLogAdds(ObservableList<String> items) {
        this.items = items;
    }
    // Method to add a log entry
    public void addLog(String text) {
        items.add(text);
        
    }
}
