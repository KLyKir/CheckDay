package buisnes_logic;

import dao.DaoException;
import dao.jdbc.JdbcPresent;
import dao.jdbc.JdbcWorker;
import data.Present;
import data.Worker;

import java.util.List;

public class PresentDataValidator {

    private static boolean validatePresentData(Present present) throws DaoException {
        List<Worker> workerList = new JdbcWorker().findAll().get();

        for(Worker packageInfo : workerList){
            if(packageInfo.getId() == present.getWorkerId()){
                return true;
            }
        }
        return false;
    }

    public static void createPresent(Present present) throws DaoException {
        if(validatePresentData(present)){
            new JdbcPresent().create(present);
        } else {
            throw new DaoException("Not valid data");
        }
    }
}
