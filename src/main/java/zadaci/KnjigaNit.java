package zadaci;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import model.Knjiga;
import model.Oblast;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KnjigaNit extends Thread {

    private String imeClana;
    private Knjiga knjiga;

    public KnjigaNit(){

    }

    public KnjigaNit(String imeClana, Knjiga knjiga){
        this.imeClana = imeClana;
        this.knjiga = knjiga;
    }

    public String getImeClana() {
        return imeClana;
    }

    public void setImeClana(String imeClana) {
        this.imeClana = imeClana;
    }

    public Knjiga getKnjiga() {
        return knjiga;
    }

    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    @Override
    public void run(){
      Random random = new Random();
      System.out.println("Clan " + imeClana + " trazi knjigu " + knjiga.getNaslov());
        do{
            try {
                // Svake sekudne proveravaj dali je knjiga slobodna
                this.sleep(1000);

            } catch (InterruptedException e){
                e.printStackTrace();
            }
            // Citamo u synchronized bloku
            synchronized (Knjiga.prisutna) {
                System.out.println("Clan " + imeClana + " ceka da se vrati knjiga " + knjiga.getNaslov());
                if (Knjiga.prisutna) {
                    System.out.println("Clan " + imeClana + " je pozajmio knjigu " + knjiga.getNaslov());
                    Knjiga.prisutna = false;
                    // Clan pozajmljuje knjigu na period od 0 do 5 sekundi
                    try {
                        sleep(random.nextInt(5000));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                         // Pisemo u synchronized bloku
                         synchronized (Knjiga.prisutna) {
                             System.out.println("Clan " + imeClana + " je vratio knjigu " + knjiga.getNaslov());
                             Knjiga.prisutna = true;
                         }
                    }
                }
        } while(!Knjiga.prisutna);
    }

    static Dao<Knjiga,Integer> knjigaDao;

    public static void main(String[] args) {
        ConnectionSource connectionSource = null;

        try {
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:knjigaOblast.db");

            knjigaDao = DaoManager.createDao(connectionSource, Knjiga.class);


            List<KnjigaNit> listaKnjiga = new ArrayList<KnjigaNit>();

            listaKnjiga.add(new KnjigaNit("Zare",knjigaDao.queryForId(1)));
            listaKnjiga.add(new KnjigaNit("Dusan",knjigaDao.queryForId(1)));
            listaKnjiga.add(new KnjigaNit("Trile",knjigaDao.queryForId(1)));
            listaKnjiga.add(new KnjigaNit("Boki",knjigaDao.queryForId(2)));
            listaKnjiga.add(new KnjigaNit("Stefan",knjigaDao.queryForId(2)));
            listaKnjiga.add(new KnjigaNit("Laza",knjigaDao.queryForId(2)));

            ArrayList<Thread> listaNiti = new ArrayList<>();

            for (KnjigaNit knjigaNit: listaKnjiga){
                Thread t = new Thread(knjigaNit);
                listaNiti.add(t);
                t.start();
            }

           for (Thread t : listaNiti){
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Biblioteka se zatvara");

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
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
