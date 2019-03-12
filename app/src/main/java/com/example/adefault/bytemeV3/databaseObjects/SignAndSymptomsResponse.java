package com.example.adefault.bytemeV3.databaseObjects;

public class SignAndSymptomsResponse {
    private String Name;
    private boolean selected;
    private String ID;

    public SignAndSymptomsResponse(){

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
