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
    //throws Exception
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

        //Eliminó Elementos Repetidos
        determinante = new ArrayList<String>(new HashSet<String>(determinante));
        determinado = new ArrayList<String>(new HashSet<String>(determinado));

        //Obtengo atributos que no esten en determinantes y determinados;
        lprefijoClave.removeAll(determinado);
        lprefijoClave.removeAll(determinante);

        //Libero Espacio de los determinados
        determinado.clear();

        //Cargo la lista de los posibles componentes de las claves
        lposibles = determinante;

        calcularClavesRecursivo(lprefijoClave,lposibles);

        //ELIMINÓ CLAVES NO CANDIDATAS

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
            ArrayList<String> lAuxPrefijos = new ArrayList<String>(lPrefijos);
            ArrayList<String> lAuxAtributosPosibles = new ArrayList<String>(lAtributosPosibles);

            for (String atributoPosible : lAtributosPosibles) {

                lAuxAtributosPosibles.remove(atributoPosible);
                lAuxPrefijos.add(atributoPosible);

                if (calcularUniverso(lAuxPrefijos)) {
                    ArrayList<String> aux = new ArrayList<String>();
                    aux.addAll(lAuxPrefijos);
                    claves.add(aux);
                } else
                    calcularClavesRecursivo(lAuxPrefijos, lAuxAtributosPosibles);

                lAuxPrefijos.remove(atributoPosible);
            }
        }
    }

    public boolean calcularUniverso(ArrayList<String> clave){
        if(!clave.isEmpty()&&!lDependenciasFuncionales.isEmpty()) {
            ArrayList<String> auxClaves = clave;
            ArrayList<String> auxDeterminante;
            ArrayList<String> auxDeterminado;
            auxClaves.addAll(clave);
            boolean cambios = true;
            while (cambios) {
                cambios = false;
                for (DependenciaFuncional df : lDependenciasFuncionales) {
                    auxDeterminante = df.getDeterminante();
                    auxDeterminado = df.getDeterminante();
                    if (auxClaves.containsAll(auxDeterminante) && auxClaves.containsAll(auxDeterminado)) {
                        auxClaves.addAll(df.getDeterminado());
                        cambios = true;
                    }
                }
            }
            return auxClaves.containsAll(lAtributos);
        }
        return false;
        }

    public ArrayList<String> calcularClausura(ArrayList<String> AtributoACalcular ){

        if (lDependenciasFuncionales.isEmpty() || AtributoACalcular.isEmpty()) {
            return new ArrayList<String>();
        }

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

    //FORMA NORMAL

    //TODO
    public FormaNormal calcularFormaNormal() {

        if (lDependenciasFuncionales.isEmpty() || fmin.isEmpty() || lAtributos.size() <= 2) {
            formaNormal = new FNBC();
            return formaNormal;
        }

        ArrayList<String> clave = claves.get(0);

        for (DependenciaFuncional df : fmin) {

        }
        int tam = lDependenciasFuncionales.size();
        int i = 0;
        FormaNormal fnAux = null;

        while (i < tam && (formaNormal == null || !formaNormal.soyPrimeraFN())) {
            fnAux = determinarFormaNormalDF(lDependenciasFuncionales.get(i), clave);
            //PERDÓN MUNDO POR LA SENTENCIA TAN FEA !!!!
            if (fnAux != null && (formaNormal == null ||
                    fnAux.soyPrimeraFN() || //SI fnAUX  esta en 1FN  No calculo mas y asigo a FN
                    (fnAux.soySegundaFN() && (formaNormal.soyFNBC() || formaNormal.soyTerceraFN())) || // Si FnAux es 2FN solo REEMPLAZO SI FN ESTA EN FNBC o 3FN
                    (fnAux.soyTerceraFN() && formaNormal.soyFNBC()))) //Si FNAux esta en 3FN SOLO LO REEMPLAZO SI FN ESTA EN FNBC
                formaNormal = fnAux;
            i++;
        }

        return formaNormal;

    }

    private FormaNormal determinarFormaNormalDF(DependenciaFuncional df, ArrayList<String> clave) {
        if (df == null || clave == null)
            return null;

        ArrayList<String> determinante = df.getDeterminante();
        ArrayList<String> determinado = df.getDeterminado();

        if (determinante.containsAll(clave))
            return new FNBC();
        else {
            int tam = determinado.size();
            int i = 0;
            while (i < tam && !clave.contains(determinado.get(i))) {
                i++;
            }
            //SI EL DETERMINADO CONTIENE UNA PARTE DE LA CLAVE ESTA EN 3FN
            if (i < tam)
                return new TerceraFormaNormal(df);

            tam = determinante.size();
            i = 0;
            while (i < tam && !clave.contains(determinado.get(i))) {
                i++;
            }
            //SI EL DETERMINANTE NO CONTIENE PARTE DE LA CLAVE CAND ESTA EN 2FN
            if (i >= tam)
                return new SegundaFormaNormal(df);
            //SI NO ESTA EN NINGUNA DE LAS FORMAS ANTERIORES SI O SI DEBE ESTAR EN 1FN
            return new PrimeraFormaNormal(df);
        }

    }

    public boolean tieneDescomposicion3FN() {
        return !formaNormal.soyTerceraFN();
    }

    public boolean tieneDescomposicionFNBC() {
        return !formaNormal.soyFNBC();
    }

    public ArrayList<ArrayList<DependenciaFuncional>> calcularDescomposicion3FN() {
        if (fmin == null || claves == null || claves.isEmpty())
            return null;

        ArrayList<ArrayList<DependenciaFuncional>> descomposicion = new ArrayList<ArrayList<DependenciaFuncional>>();
        ArrayList<String> clave = claves.get(0);

        int tam = fmin.size();
        int i = 0;
        int tamDescomposicion = descomposicion.size();
        int x = 0;
        while (i < tam && !fmin.get(i).getDeterminante().containsAll(clave)) {
            x = 0;
            while (x < tamDescomposicion && !(descomposicion.get(x)).get(0).getDeterminante().containsAll(fmin.get(i).getDeterminante())) {
                x++;
            }
            if (x < tamDescomposicion) {
                descomposicion.get(x).add(fmin.get(i));
            } else {
                ArrayList<DependenciaFuncional> lDFAux = new ArrayList<DependenciaFuncional>();
                lDFAux.add(fmin.get(i));
                descomposicion.add(lDFAux);
            }
            i++;
        }

        if (!(i < tam)) {
            ArrayList<DependenciaFuncional> lDFAux = new ArrayList<DependenciaFuncional>();
            DependenciaFuncional DFAux;
            if (clave.size() > 1)
                DFAux = new DFDeterminanteComplejo(clave, "");
            else
                DFAux = new DFSimple(clave.get(0), "");

            lDFAux.add(DFAux);
            descomposicion.add(lDFAux);
        } else {
            x = 0;
            while (x < tamDescomposicion && !(descomposicion.get(x)).get(0).getDeterminante().containsAll(fmin.get(i).getDeterminante())) {
                x++;
            }
            if (x < tamDescomposicion) {
                descomposicion.get(x).add(fmin.get(i));
            } else {
                ArrayList<DependenciaFuncional> lDFAux = new ArrayList<DependenciaFuncional>();
                lDFAux.add(fmin.get(i));
                descomposicion.add(lDFAux);
            }
            i++;
        }

        return descomposicion;
    }

    //TODO
    public ArrayList<ArrayList<String>> calcularDescomposicionFNBC() {
        return null;
    }

    //FMIN
    public ArrayList<DependenciaFuncional> calcularFmin(){

        ArrayList<DependenciaFuncional> lAuxDependenciaFuncional = new ArrayList<DependenciaFuncional>();

        ArrayList<String> A;
        ArrayList<String> B;

        //GUARDO LAS DEPENDENCIAS FUNCIONALES
        ArrayList<DependenciaFuncional> aux2 = new ArrayList<DependenciaFuncional>();
        aux2.addAll(lDependenciasFuncionales);

        //Cargo la lista Auxiliar de DF con las DF para Fmin (Quedan o DF simples o DF con determinante complejo)
        for (DependenciaFuncional DependenciaFuncional : lDependenciasFuncionales) {
            lAuxDependenciaFuncional.addAll(DependenciaFuncional.convertirAFmin());
        }
        //Elimino Repetidos
        lAuxDependenciaFuncional= new ArrayList<DependenciaFuncional>(new HashSet<DependenciaFuncional>(lAuxDependenciaFuncional));

        lDependenciasFuncionales.clear();

        //Dividó Dependencias funcionales en simples o con Dependencia Funcionales y Analizó Dependencias Funcionales con Determinante Complejo
        for (DependenciaFuncional df : lAuxDependenciaFuncional) {
                if(df.soyDeterminanteComplejo())
                {
                    ArrayList<String> aux = calcularRedundanciaDeterminante(null, df.getDeterminante(), df.getDeterminado().get(0));
                    if (!aux.containsAll(df.getDeterminante())) {
                        DependenciaFuncional dfAux;

                        if (aux.size() == 1) {
                            //ES UN DETERMINANTE SIMPLE
                            dfAux = new DFSimple(aux.get(0), df.getDeterminado().get(0));
                        } else {
                            //ES UN DETERMINANTE COMPLEJO
                            dfAux = new DFDeterminanteComplejo(aux, df.getDeterminado().get(0));
                        }

                        if (!lDependenciasFuncionales.contains(dfAux)) {
                            lDependenciasFuncionales.add(dfAux);
                        }
                    } else
                        lDependenciasFuncionales.add(df);
                }
                else
                    lDependenciasFuncionales.add(df);
        }

        //ELIMINO REDUNDANCIAS

        ArrayList<DependenciaFuncional> aux = new ArrayList<DependenciaFuncional>();
        aux.addAll(lDependenciasFuncionales);

        for (DependenciaFuncional df : aux) {

            A = calcularClausura(df.getDeterminante());
            lDependenciasFuncionales.remove(df);
            B = calcularClausura(df.getDeterminante());
            if ((A.size() != B.size()))          //Si Obtengo El mismo resultado calculando la clausura  , quitando la df (se calcula la clausura con Determinante)
                lDependenciasFuncionales.add(df);
        }

        fmin = new ArrayList<DependenciaFuncional>();
        fmin.addAll(lDependenciasFuncionales);

        lDependenciasFuncionales = aux2;

        return fmin;
    }


    private ArrayList<String> calcularRedundanciaDeterminante(ArrayList<String> Prefijo, ArrayList<String> Determinante, String Determinado) {
        //Calcula todas las combinaciones posibles del Determinante y evalua si son redundantes
        if (Prefijo == null) {
            Prefijo = new ArrayList<String>();
        }
        if (Determinante == null || Determinante.isEmpty()) {
            if (Determinante == null)
                Determinante = new ArrayList<String>();
            return Determinante;
        }

        ArrayList<String> lretorno = new ArrayList<String>();
        ArrayList<String> lAux;
        ArrayList<String> lAuxII = new ArrayList<String>(Prefijo);
        ArrayList<String> lAuxDeterminantes = new ArrayList<String>(Determinante);

        for (String s : Determinante) {
            lAuxII.add(s);
            lAuxDeterminantes.remove(s);
            lAux = calcularRedundanciaDeterminante(lAuxII, lAuxDeterminantes, Determinado);
            if (!lAux.isEmpty() && (lretorno.isEmpty() || (!lretorno.isEmpty() && lAux.size() < lretorno.size()))) {
                //si lAux != null ,etonces es Determinante sin redundancia
                lretorno.clear();
                lretorno.addAll(lAux);
            }
            if (calcularClausura(lAuxII).contains(Determinado) && (lretorno.isEmpty() || (!lretorno.isEmpty() && lAuxII.size() < lretorno.size()))) {
                lretorno.clear();
                lretorno.addAll(lAuxII);
            }
            lAuxII.remove(s);
        }
        return lretorno;
    }

    //TABLEAUX TODO



}
