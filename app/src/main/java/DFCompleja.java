import java.util.ArrayList;

/**
 * Created by federicolizondo on 02/10/15.
 */
public class DFCompleja extends DependenciaFuncional {

    private ArrayList<String> determinante;
    private ArrayList<String> determinado;

    public DFCompleja(ArrayList<String> determinante,ArrayList<String>determinado) {
        super();
        this.determinante=determinante;
        this.determinado=determinado;
    }

    @Override
    public boolean tengoDF(String determinante, String determinado) {
        return this.determinante.contains(determinante) && this.determinado.contains(determinado);
    }

    @Override
    public ArrayList<DependenciaFuncional> convertirAFmin() {

        ArrayList<DependenciaFuncional> df = new ArrayList<DependenciaFuncional>();
        for (String s : this.determinado) {
            df.add(new DFDeterminanteComplejo(this.determinante,s));
        }
        return df;
    }

    @Override
    public ArrayList<String> getDeterminante() {
        return this.determinante;
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
        return true;
    }

    @Override
    public String toString() {
        /*
        String s="";
        for (String d : determinante) {
            s+=d+",";
        }
        if(s.endsWith(",")){s=s.substring(0,s.length()-2);}
        s+=" -> ";
        for (String d : determinado) {
            s+=d+",";
        }
        if(s.endsWith(",")){s=s.substring(0,s.length()-2);}
        return s;*/
        return determinante+" - > "+determinado;
    }

    @Override
    public int hashCode() {
        return ( determinante.toString() + determinado.toString()).hashCode() ;
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
            int ii = 0;
            int tamd = this.determinante.size();
            while ( i < tam && lDeterminado.contains(this.determinado.get(tam))){i++;}
            while ( ii < tam && lDeterminantes.contains(this.determinante.get(tam))){ii++;}
            return  ( i == tam ) && (ii == tamd);
        }
        return false;
    }

}
