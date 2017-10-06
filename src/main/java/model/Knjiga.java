package model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "knjiga")
public class Knjiga {
    public static final String POLJE_NASLOV="naslov";
    public static final String POLJE_BROJ_STRANA="broj_strana";
    public static final String POLJE_DATUM_IZDAVANJA= "datum_izdavanja";

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = POLJE_NASLOV)
    private String naslov;
    @DatabaseField(columnName = POLJE_BROJ_STRANA)
    private int brojStrana;
    @DatabaseField(columnName = POLJE_DATUM_IZDAVANJA)
    private Date datumIzdanja;

    private boolean prisutna;

    @ForeignCollectionField(foreignFieldName = "knjiga",eager=false,maxEagerLevel = 1)
    private ForeignCollection<Oblast> oblasti;

    public Knjiga(){

    }

    public Knjiga(String naslov, int brojStrana, Date datumIzdanja) {
        this.naslov = naslov;
        this.brojStrana = brojStrana;
        this.datumIzdanja = datumIzdanja;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public int getBrojStrana() {
        return brojStrana;
    }

    public void setBrojStrana(int brojStrana) {
        this.brojStrana = brojStrana;
    }

    public Date getDatumIzdanja() {
        return datumIzdanja;
    }

    public void setDatumIzdanja(Date datumIzdanja) {
        this.datumIzdanja = datumIzdanja;
    }

    @Override
    public String toString() {
        return "Knjiga{" +
                "id=" + id +
                ", naslov=" + naslov +
                ", broj_strana=" + brojStrana +
                "datum_izdavanja=" + datumIzdanja + '\'' +
                '}';
    }
}
