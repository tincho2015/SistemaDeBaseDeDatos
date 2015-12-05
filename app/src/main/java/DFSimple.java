import java.util.ArrayList;


/**
 * Created by federicolizondo on 02/10/15.
 */
public class DFSimple extends DependenciaFuncional{

    private String determinante;
    private String determinado;

    public DFSimple(String Determinado,String Determinante)
    {
        super();
        this.determinante = Determinante;
        this.determinado = Determinado;
    }


    @Override
    public boolean tengoDF(String determinante, String determinado) {
        return this.determinado.equals(determinado) && this.determinante.equals(determinado);
    }

    @Override
    public ArrayList<DependenciaFuncional> convertirAFmin() {
        ArrayList<DependenciaFuncional> a = new ArrayList<DependenciaFuncional>();
        a.add(this);
        return a;
    }

    @Override
    public boolean soyDeterminanteComplejo() {
        return false;
    }

    @Override
    public boolean soyCompleja() {
        return false;
    }

    @Override
    public String toString() {
        return this.determinante+" -> "+this.determinado;
    }

    @Override
    public int hashCode() {
        return (this.determinado+this.determinante).hashCode();
    }
    @Override
    public boolean equals(Object o) {
        if(o == this){ return true; }
        if (o != null && o instanceof DependenciaFuncional)
        {
                DependenciaFuncional DF = (DependenciaFuncional) o;
                ArrayList<String> lDeterminantes = DF.getDeterminante();
                ArrayList<String> lDeterminado = DF.getDeterminado();
                return lDeterminantes.contains(this.determinante) && lDeterminado.contains(this.determinado);
        }
        return false;
    }

    @Override
    public ArrayList<String> getDeterminante() {
        ArrayList<String> a = new ArrayList<String>();
        a.add(this.determinante);
        return a;
    }

    @Override
    public ArrayList<String> getDeterminado() {
        ArrayList<String> a = new ArrayList<String>();
        a.add(this.determinado);
        return a;
    }

    @Override
    public ArrayList<String> dameAtributos() {
        ArrayList<String> aux = new ArrayList<String>();
        aux.add(determinante);
        aux.add(determinado);
        return aux;
    }
}
