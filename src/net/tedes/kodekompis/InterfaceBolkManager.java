package net.tedes.kodekompis;

public interface InterfaceBolkManager {
    public void addDataBolk(DataBolk bolken);
    public void deleteDataBolk(DataBolk bolken);
    public void sortDataBolkList(DataBolk.SortMethod sortMethod);
    public void updateDataBolk(DataBolk bolken);
}
