import java.util.ArrayList;

/**
 * Created by federicolizondo on 02/10/15.
 */
public class DFDeterminadoComplejo extends DependenciaFuncional {

    private String determinante;
    private ArrayList<String> determinado ;

    public DFDeterminadoComplejo(String Determinante,ArrayList<String> Determinado) {
        super();
        this.determinante = Determinante;
        this.determinado = Determinado;
    }

    @Override
    public boolean tengoDF(String determinante, String determinado) {

        return this.determinante==determinante && this.determinado.contains(determinado);
    }

    @Override
    public ArrayList<DependenciaFuncional> convertirAFmin() {

        ArrayList<DependenciaFuncional> DF = new ArrayList<DependenciaFuncional>();
        for (String s : determinado) {
          DF.add(new DFSimple(this.determinante,s));
        }
        return DF;
    }

    @Override
    public ArrayList<String> getDeterminante() {
        ArrayList<String> ld = new ArrayList<String>();
        ld.add(this.determinante);
        return ld;
    }

    @Override
    public ArrayList<String> getDeterminado() {
        return this.determinado;
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
       /*
           String s=this.determinante+" -> ";
            for (String d : determinado) {
                s+=d+",";
            }
            if(s.endsWith(",")){s=s.substring(0,s.length()-2);}
            return  s;
        */

        return determinante+" - > "+determinado;
    }

    @Override
    public int hashCode() {
        return (this.determinante+this.determinado.toString()).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this){ return true; }
        if(o != null || !(o instanceof DependenciaFuncional))
        {
            DependenciaFuncional DF = (DependenciaFuncional) o;
            ArrayList<String> lDeterminantes = DF.getDeterminante();
            ArrayList<String> lDeterminado = DF.getDeterminado();
            int i = 0;
            int tam = this.determinado.size();
            while ( i < tam && lDeterminado.contains(this.determinado.get(tam)))
            {
                i++;
            }
            return  ( i == tam ) && lDeterminantes.contains(this.determinante);
        }
        return false;
    }

}
