/**
 * Created by federicolizondo on 03/10/15.
 */
public class SegundaFormaNormal extends FormaNormal {

    private DependenciaFuncional dependenciaFuncional;

    public SegundaFormaNormal(DependenciaFuncional DF)
    {
        super();
        this.dependenciaFuncional = DF;
    }

    @Override
    public String JustificaMiFN() {

        return "Esta en 2ra Forma Normal :\nPor la dependencia Funcional "+dependenciaFuncional.toString()+". ";
    }

    @Override
    public String toString() {
        return "SEGUNDA FORMA NORMAL";
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
        return true;
    }

    @Override
    public boolean soyPrimeraFN() {
        return false;
    }
}
