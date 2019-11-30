package restaurant.service;
import java.util.*;
import restaurant.domain.*;

public class TableService {
    
    private List<Table> tables = new ArrayList<Table>();

    public List<Table> getTables() {
        return tables;
    }

    public Table getAvailableTable() {
        for (Table table : tables) {
            if (table.getStatus() == TableStatus.Available) return table;
        }
        return null;
    }


    public boolean isTableAvailable() {
          for (Table table : tables) {
            if (table.getStatus() == TableStatus.Available) return true;
        }
        return false;      
    }    

    public Table getTableById(int id) {
        for (Table table : tables) {
            if (table.getId() == id) return table;
        }
        return null;
    }

    public void assignTable(Table table, Eater eater) {
        table.setStatus(TableStatus.UnAvailable);
        table.setEater(eater);
    }

    public void releaseTable(Table table) {
        table.setStatus(TableStatus.Available);
        table.setEater(null);
    }

    public void addTable(Table table) {
        tables.add(table);
    }
}