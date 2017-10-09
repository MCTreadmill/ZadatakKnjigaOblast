package zadaci;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import model.Oblast;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Zadatak3IzmenaVrednosti {
    static Dao<Oblast,Integer> oblastDao;

    public static void main(String[] args) {
        ConnectionSource connectionSource = null;

        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:knjigaOblast.db");

            oblastDao = DaoManager.createDao(connectionSource, Oblast.class);

            List<Oblast> oblasti = oblastDao.queryForAll();
            for (Oblast o : oblasti)
                System.out.println(o);

            List<Oblast> oblastZaIzmenu = oblastDao.queryForEq(Oblast.POLJE_NAZIV, "Activity klasa");
            Oblast oblast1 = oblastZaIzmenu.get(0);
            oblast1.setPocetnaStrana(35);
            oblastDao.update(oblast1);

            List<Oblast> oblasti2 = oblastDao.queryForAll();
            for (Oblast o : oblasti2)
                System.out.println(o);


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connectionSource != null) {
                try {
                    connectionSource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
