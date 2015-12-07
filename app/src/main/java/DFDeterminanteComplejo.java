import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by federicolizondo on 02/10/15.
 **/
public class DFDeterminanteComplejo extends DependenciaFuncional {

    private ArrayList<String> determinante;
    private String determinado;

    public DFDeterminanteComplejo(ArrayList<String> Determinantes,String Determinado)
    {
        super();
        this.determinante = Determinantes;
        this.determinado= Determinado;
    }

    @Override
    public boolean tengoDF(String determinante, String determinado) {
        return this.determinante.contains(determinante) && this.determinado.equals(determinado);
    }

    @Override
    public ArrayList<DependenciaFuncional> convertirAFmin() {
        ArrayList<DependenciaFuncional> DF = new ArrayList<DependenciaFuncional>();
        DF.add(this);
        return DF;
    }

    @Override
    public ArrayList<String> getDeterminante() {
        return this.determinante;
    }

    @Override
    public ArrayList<String> getDeterminado() {
        ArrayList<String> lDeterminado = new ArrayList<String>();
        lDeterminado.add(this.determinado);
        return lDeterminado;
    }

    @Override
    public boolean soyDeterminanteComplejo() {
        return true;
    }

    @Override
    public boolean soyCompleja() {
        return false;
    }

    @Override
    public String toString() {
        return determinante + ((!determinado.isEmpty()) ? " - > " : "") + determinado;
    }

    @Override
    public int hashCode() {
        return (determinante.toString()+determinado).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this){ return true; }
        if (o != null && (o instanceof DependenciaFuncional))
        {
            DependenciaFuncional DF = (DependenciaFuncional) o;
            ArrayList<String> lDeterminantes = DF.getDeterminante();
            ArrayList<String> lDeterminado = DF.getDeterminado();
            int i = 0;
            int tam = this.determinante.size();
            while ( i < tam && lDeterminantes.contains(this.determinante.get(tam)))
            {
                i++;
            }
            return  ( i == tam ) && lDeterminado.contains(this.determinado);
        }
        return false;
    }

    @Override
    public ArrayList<String> dameAtributos() {
        ArrayList<String> aux = new ArrayList<String>();
        aux.addAll(determinante);
        aux.add(determinado);
        aux = new ArrayList<String>(new HashSet<String>(aux));
        return aux;
    }
}
