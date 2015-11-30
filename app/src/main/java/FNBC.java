/**
 * Created by federicolizondo on 03/10/15.
 */
public class FNBC extends  FormaNormal {

    public FNBC()
    {
        super();
    }

    @Override
    public String JustificaMiFN() {

        return "Esta en Forma Normal de Boyce-Codd.";
    }

    @Override
    public String toString() {
        return "FORMA NORMAL DE BOYCE-CODD";
    }

    @Override
    public boolean soyFNBC() {
        return true;
    }

    @Override
    public boolean soyTerceraFN() {
        return false;
    }

    @Override
    public boolean soySegundaFN() {
        return false;
    }

    @Override
    public boolean soyPrimeraFN() {
        return false;
    }
}
