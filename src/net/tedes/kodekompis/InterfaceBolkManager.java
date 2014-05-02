package net.tedes.kodekompis;

import java.util.ArrayList;

public interface InterfaceBolkManager {
    public void addDataBolk(DataBolk bolken);
    public void deleteDataBolk(DataBolk bolken);
    public ArrayList<String> getPasswords();
    public ArrayList<String> getUsernames();
    public void sortDataBolkList(DataBolk.SortMethod sortMethod);
    public void updateDataBolk(DataBolk bolken);
}
