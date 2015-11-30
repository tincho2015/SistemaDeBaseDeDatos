/**
 * Created by federicolizondo on 03/10/15.
 */
public class PrimeraFormaNormal extends FormaNormal {

    private DependenciaFuncional dependenciaFuncional;

    public PrimeraFormaNormal(DependenciaFuncional DF)
    {
        super();
        this.dependenciaFuncional = DF;
    }

    @Override
    public String JustificaMiFN() {

        return "Esta en 1ra Forma Normal :\nPor la dependencia Funcional "+dependenciaFuncional.toString()+". ";
    }

    @Override
    public String toString() {
        return "PRIMERA FORMA NORMAL";
    }

    @Override
    public boolean soyFNBC() {
        return false;
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
        return true;
    }
}
