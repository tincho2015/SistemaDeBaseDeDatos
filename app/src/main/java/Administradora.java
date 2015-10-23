import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by federicolizondo on 03/10/15.
 */
public class Administradora {
    private static Administradora ourInstance = new Administradora();
    public static Administradora getInstance() {
        return ourInstance;
    }

    private ArrayList<String> lAtributos;
    private ArrayList<DependenciaFuncional> lDependenciasFuncionales;
    private ArrayList<ArrayList<String>> claves;
    private ArrayList<DependenciaFuncional> fmin;
    private FormaNormal formaNormal;

    private Administradora() {

        Runtime garbage = Runtime.getRuntime();
        garbage.gc();

        lAtributos = new ArrayList<String>();
        lDependenciasFuncionales = new ArrayList<DependenciaFuncional>();
        claves = new ArrayList<ArrayList<String>>();
        fmin = new ArrayList<DependenciaFuncional>();
        formaNormal = null;
    }

    public void AgregarAtributos(String atributo) {
        if (!lAtributos.contains(atributo)){lAtributos.add(atributo);}

    }

    public void modificarAtributo(String atributoViejo,String atributoNuevo) {
        if(lAtributos.contains(atributoViejo)&& !lAtributos.contains((atributoNuevo)))
        {
            int pos = lAtributos.indexOf(atributoViejo);
            lAtributos.remove(atributoViejo);
            lAtributos.add(pos,atributoNuevo);
        }
        if(lAtributos.contains(atributoViejo))
        {
            lAtributos.remove(atributoViejo);
        }
    }

    public void eliminarAtributo(String atributo){
        if(lAtributos.contains(atributo))
        {
            lAtributos.remove(atributo);
        }
    }

    public void AgregarDependenciaFuncional(DependenciaFuncional dependenciaFuncional){
        if( dependenciaFuncional!=null && !lDependenciasFuncionales.contains(dependenciaFuncional))
        {
            lDependenciasFuncionales.add(dependenciaFuncional);
        }
    }

    public void ModificarDependenciaFuncional(DependenciaFuncional dependenciaFuncionalAntigua,DependenciaFuncional dependenciaFuncionalNueva){
        if(lDependenciasFuncionales.contains(dependenciaFuncionalAntigua)&&!lDependenciasFuncionales.contains(dependenciaFuncionalNueva))
        {
            int pos=lDependenciasFuncionales.indexOf(dependenciaFuncionalAntigua);
            lDependenciasFuncionales.remove(dependenciaFuncionalAntigua);
            lDependenciasFuncionales.add(pos, dependenciaFuncionalNueva);
        }
    }

    public void EliminarDependenciaFuncional(DependenciaFuncional dependenciaFuncional){
        if(lDependenciasFuncionales.contains(dependenciaFuncional))
        {
            lDependenciasFuncionales.remove(dependenciaFuncional);
        }
    }

    public void calcularClavesCandidatas(){

        ArrayList<String> lposibles = new ArrayList<String>(lAtributos);
        ArrayList<String> lclaves = new ArrayList<String>(lAtributos);
        ArrayList<String> determinante=new ArrayList<>();
        ArrayList<String> determinado = new ArrayList<String>();

        //ELIMINO TODOS LOS ATRIBUTOS REDUNDANTES

        for (DependenciaFuncional df : lDependenciasFuncionales) {
            determinante.addAll(df.getDeterminante());
            determinado.addAll(df.getDeterminado());
        }
        //Elimino Elementos Repetidos
        determinante= new ArrayList<String>(new HashSet<String>(determinante));
        determinado = new ArrayList<String>(new HashSet<String>(determinado));
        determinado.removeAll(determinante);
        lposibles.removeAll(determinado);

        //Si hay atributos de una sola clave
        for (String la : lposibles) {
            lclaves = new ArrayList<String>();
            lclaves.add(la);
            if(calcularUniverso(lclaves))
            {
                claves.add(lclaves);
               lposibles.remove(la);
            }
        }


    }




    public boolean calcularUniverso(ArrayList<String> clave){
        if(!clave.isEmpty()&&!lDependenciasFuncionales.isEmpty()) {
            ArrayList<String> claves = clave;
            ArrayList<String> auxDeterminante;
            ArrayList<String> auxDeterminado;
            claves.addAll(clave);
            boolean cambios = true;
            while (cambios) {
                cambios = false;
                for (DependenciaFuncional df : lDependenciasFuncionales) {
                    auxDeterminante = df.getDeterminante();
                    auxDeterminado = df.getDeterminante();
                    if (claves.containsAll(auxDeterminante) && claves.containsAll(auxDeterminado)) {
                        claves.addAll(df.getDeterminado());
                        cambios = true;
                    }
                }
            }
            return claves.containsAll(lAtributos);
        }
        return false;
        }

    public ArrayList<String> calcularClausura(ArrayList<String> AtributoACalcular ){
        if(lDependenciasFuncionales.isEmpty()||AtributoACalcular.isEmpty()){return null;}

        ArrayList<String> clausura = new ArrayList<String>();
        ArrayList<String> ldeterminante;
        ArrayList<String> ldeterminado;

        boolean tengoCambio= true;
        while(tengoCambio) {
            tengoCambio = false;
            for (DependenciaFuncional df : lDependenciasFuncionales) {
                ldeterminante = df.getDeterminante();
                ldeterminado = df.getDeterminado();
                if (!clausura.containsAll(ldeterminante) && !clausura.containsAll(ldeterminado)) {
                    clausura.addAll(ldeterminado);
                    tengoCambio=true;
                }
            }
        }

        clausura= new ArrayList<String>(new HashSet<String>(clausura));

        return clausura;
    }

}
