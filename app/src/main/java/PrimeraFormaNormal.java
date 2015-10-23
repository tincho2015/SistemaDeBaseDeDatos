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
}
