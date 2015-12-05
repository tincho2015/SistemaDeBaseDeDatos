import java.util.ArrayList;

/**
 * Created by federicolizondo on 02/10/15.
 */
public abstract class  DependenciaFuncional {

    private  static  int id;
    private int cod;
    public static void setID(int nro){ id = nro;}
    public static int getID(){return  id;}

    public DependenciaFuncional()
    {
        this.cod = id;
        id++;
    }
    public int getCod(){return this.cod;}



    public abstract boolean tengoDF(String determinante,String determinado);
    public abstract ArrayList<DependenciaFuncional>convertirAFmin();
    public abstract ArrayList<String> getDeterminante();
    public abstract ArrayList<String> getDeterminado();
    public abstract boolean soyDeterminanteComplejo();
    public abstract boolean soyCompleja();

    public abstract ArrayList<String> dameAtributos();

    @Override
    public abstract String toString();
    @Override
    public abstract int hashCode();
    @Override
    public abstract boolean equals(Object o);
}
