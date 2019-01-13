package com.jheank16oz.questionform

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Question{
    // el id de la pregunta es muy importante para la creacion del componente
    // se debe tener en cuenta que el id de pregunta padre e hija se validan como dos
    // preguntas diferentes y no debe existir una pregunta con el id de la pregunta padre
    // igual.
    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("title")
    @Expose
    var tittle: String? = null
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("obligatory")
    @Expose
    var obligatory: Boolean = false
    @SerializedName("properties")
    @Expose
    var properties: Properties? = null





    /** validaciones iniciales*/

    /*
     * Para las validaciones de error
     * Se tiene como prioridad validar si el campo es obligatorio,
     * en este caso el mensaje de error devuelto posible es "Esta pregunta es obligatoria"
     * y posterior si el campo requiere mostrar el mensaje de error , ejemplo:
     *
     * input = el campo de entrada es de tipo number y se sobrepaso max, se mostrara
     * el mensaje $mensajeError por defecto se mostrara "Formato invalido"
     *
     */


    /** @TYPE input*/
    /* Se representa como un campo de entrada el cual puede ser de tipo:
     * text : caracteres normales UTF-8
     *        validaciones{
     *            * se valida el numero maximo de caracteres
     *        }
     * number : caracteres solo numericos -0123456789
     *        validaciones {
     *            min = numero minimo posible
     *            max = numero maximo posible
     *            * se valida el numero maximo de caracteres
     *            * se valida si max fue sobrepasado
     *            * se valida si el min fue sobrepasado
     *            * se valida que el caracter ( - ) solo se ingrese al inicio
     *        }
     * email : campo de entrada email
     *        validaciones{
     *            * se valida el numero maximo de caracteres
     *            * que el correo tenga un formato correcto, se valida
     *            con el patron android.util.Patterns.EMAIL_ADDRESS
     *        }
     *
     * multiline : campo de entrada mayor a una linea
     *        validaciones{
     *            * se valida el numero maximo de caracteres
     *        }
     *
     */


    /** @TYPE select*/
    /*
     * Se representa como una lista desplegable de posibles opciones
     * con posibilidad de desplegar campos dependientes.
     *
     * Para el campo de tipo select se valida si contiene preguntas
     * dependientes, para proceder a desplegarlas en el caso de su selección.
     * tener en cuenta que las preguntas hijas contienen las mismas propiedades posibles
     *
     * Por defecto el primer item sera Elige una opcion, cuando se envia el formulario y
     * @IMPORTANT si la pregunta no es obligatoria y el primer item es el seleccionado,
     * se manda el id de respuesta seleccionado en -1
     *
     */


    /** @TYPE date*/
    /* Se representa como un campo de tipo fecha
     * Para el campo de tipo date se valida que los formatos
     * enviados para minDate y maxDate sean correctos y posteriorment
     * se asigna al calendario el minimo fecha y el maximo fecha
     *
     */

    /** @TYPE time*/
    /* Se representa como un campo de tipo hora
     * Para el campo de tipo time se valida que los formatos
     * enviados para minTime y maxTime sean correctos y posteriormente
     * se asigna al tiempo el minimo de tiempo y el maximo de tiempo
     *
     */

    /** @TYPE datetime*/
    /* Se representa como un campo de tipo fecha-hora
     * Para el campo de tipo fecha-hora se valida que los formatos
     * enviados para minDatetime y maxDatetime sean correctos y posteriormente
     * se asigna al datetime el minimo de tiempo y el maximo de tiempo
     *
     */


    // @TYPE capture
    /** @TYPE capture*/
    /*
     * se representa como un campo de tipo captura,
     * se valida que el peso de cada archivo no soprepase $maxSizeMB
     * y la cantidad a agregar no sea mayor a $count.
     * si count es < 1 se deja ingresar solo un archivo
     * el maximo de peso por defecto de un archivo es de 10MB
     *
     */

    /** @TYPE form*/
    /*
     * Se representa como un formulario dinámico que se abre en otra vista
     * recibe una cantidad de posibles ingresos de Formulario
     */

    /** @TYPE location*/
    /*
     * Se representa como una posible ubicación que abre otra vista
     * para buscar la dirección
     */









}

