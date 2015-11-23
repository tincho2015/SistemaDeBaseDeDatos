import java.util.ArrayList;
import java.util.HashSet;

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

    //ATRIBUTOS
    public void agregarAtributos(String atributo) {
        if (!lAtributos.contains(atributo)) {
            lAtributos.add(atributo);
        }
    }

    public void modificarAtributo(String atributoViejo, String atributoNuevo) {
        if (lAtributos.contains(atributoViejo) && !lAtributos.contains((atributoNuevo))) {
            int pos = lAtributos.indexOf(atributoViejo);
            lAtributos.remove(atributoViejo);
            lAtributos.add(pos, atributoNuevo);
        }
        if (lAtributos.contains(atributoViejo)) {
            lAtributos.remove(atributoViejo);
        }
    }

    public void eliminarAtributo(String atributo) {
        if (lAtributos.contains(atributo)) {
            lAtributos.remove(atributo);
        }
    }

    public ArrayList<String> darListadoAtributos() {
        return lAtributos;
    }



    //DEPENDENCIAS FUNCIONAL
    public void agregarDependenciaFuncional(DependenciaFuncional dependenciaFuncional){
        if( dependenciaFuncional!=null && !lDependenciasFuncionales.contains(dependenciaFuncional))
        {
            lDependenciasFuncionales.add(dependenciaFuncional);
        }
    }

    public void modificarDependenciaFuncional(DependenciaFuncional dependenciaFuncionalAntigua,DependenciaFuncional dependenciaFuncionalNueva){
        if(lDependenciasFuncionales.contains(dependenciaFuncionalAntigua)&&!lDependenciasFuncionales.contains(dependenciaFuncionalNueva))
        {
            int pos=lDependenciasFuncionales.indexOf(dependenciaFuncionalAntigua);
            lDependenciasFuncionales.remove(dependenciaFuncionalAntigua);
            lDependenciasFuncionales.add(pos, dependenciaFuncionalNueva);
        }
    }

    public void eliminarDependenciaFuncional(DependenciaFuncional dependenciaFuncional){
        if(lDependenciasFuncionales.contains(dependenciaFuncional))
        {
            lDependenciasFuncionales.remove(dependenciaFuncional);
        }
    }

    public ArrayList<DependenciaFuncional> darListadoDependenciasFuncional(){
        return lDependenciasFuncionales;
    }

    //throws Exception
    //CLAVE CANDIDATAS
    public ArrayList<ArrayList<String>> calcularClavesCandidatas() {

        ArrayList<String> lposibles;
        ArrayList<String> lprefijoClave = new ArrayList<String>(lAtributos);
        ArrayList<String> determinante=new ArrayList<String>();
        ArrayList<String> determinado = new ArrayList<String>();

       //Obtengo todos los Determinantes y todos los Determinados
        for (DependenciaFuncional df : lDependenciasFuncionales) {
            determinante.addAll(df.getDeterminante());
            determinado.addAll(df.getDeterminado());
        }

        //Elimino Elementos Repetidos
        determinante= new ArrayList<String>(new HashSet<String>(determinante));
        determinado = new ArrayList<String>(new HashSet<String>(determinado));

        //Obtengo atributos que no esten en determinantes y determinados;
        lprefijoClave.removeAll(determinado);
        lprefijoClave.removeAll(determinante);
        //Libero Espacio de los determinados
        determinado.clear();

        //Cargo la lista de los posibles componentes de las claves
        lposibles = determinante;

        calcularClavesRecursivo(lprefijoClave,lposibles);

        //ELIMINO CLAVESNOCANDIDATAS
        int index = 0;

        ArrayList<ArrayList<String>> lAuxClave = new ArrayList<ArrayList<String>>(claves);

        for(ArrayList<String> clave : claves) {
            for (ArrayList<String> string : lAuxClave) {
                if( string.contains(clave) &&  string.size() < clave.size() )
                {
                    claves.remove(string);
                }
            }
        }

        return claves;
    }

    private void calcularClavesRecursivo(ArrayList<String> lPrefijos,ArrayList<String> lAtributosPosibles){
        if( lAtributosPosibles!= null &&!lAtributosPosibles.isEmpty() )
        {
            //Verificó que la lista de Atributos exista y tenga elementos.
            ArrayList<String> lAuxPrefijos = new ArrayList<String>(lPrefijos);
            ArrayList<String> lAuxAtributosPosibles = new ArrayList<String>(lAtributosPosibles);

            for (String atributosPosible : lAtributosPosibles) {

                lAtributosPosibles.remove(atributosPosible);
                lAuxPrefijos.add(atributosPosible);

                    if(calcularUniverso(lAuxPrefijos))
                        claves.add(lAuxPrefijos);
                    else
                        calcularClavesRecursivo(lAuxPrefijos, lAuxAtributosPosibles);

                lAuxPrefijos.remove(atributosPosible);
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
        clausura = new ArrayList<String>(new HashSet<String>(clausura));

        return clausura;
    }

    //Tableaux

    //FORMA NORMAL

    //FMIN
    public ArrayList<DependenciaFuncional> calcularFmin(){
        ArrayList<DependenciaFuncional> lAuxDependenciaFuncional = new ArrayList<DependenciaFuncional>();
        DependenciaFuncional auxDependencia;
        for (DependenciaFuncional DependenciaFuncional : lDependenciasFuncionales) {
            lAuxDependenciaFuncional.addAll(DependenciaFuncional.convertirAFmin());
        }
        //Elimino Repetidos
        lAuxDependenciaFuncional= new ArrayList<DependenciaFuncional>(new HashSet<DependenciaFuncional>(lAuxDependenciaFuncional));

        //Dividó Dependencias funcionales en simples o con Dependencia Funcionales y Analizó Dependencias Funcionales con Determinante Complejo
        for (DependenciaFuncional df : lAuxDependenciaFuncional) {
                if(df.soyDeterminanteComplejo())
                {

                }
                else
                lDependenciasFuncionales.add(df);
        }

        return  lDependenciasFuncionales;
    }





}
