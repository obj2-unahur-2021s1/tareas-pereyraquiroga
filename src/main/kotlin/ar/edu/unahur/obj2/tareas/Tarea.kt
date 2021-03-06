package ar.edu.unahur.obj2.tareas

abstract class Tarea(){


    // CADA TAREA TIENE UNA LISTA DE EMPLEADOS
    open val listaEmpleados= mutableListOf<Empleado>()
     abstract fun horasParaTerminarUnaTarea(lista: List<Tarea>,listaE:List<Empleado>) : Int
    abstract  fun costoDeUnaTarea(): Int
    abstract fun nominaEmpleados(lista : List<Empleado>):Int
}

class TareaSimples(val horasEstimadas: Int, val responsable: Responsable, val costoInfrastructura : Int, override val listaEmpleados: MutableList<Empleado>) : Tarea(){
// LE PASAMS POR PARAMETROS LA LISTA DE EMPLEADOS , CADA TAREA QUE CREAMOS VA A TENER SU PROPIA LISTA

    override fun horasParaTerminarUnaTarea(lista: List<Tarea>,listaE:List<Empleado>)= horasEstimadas / listaE.size // PORQUE DOS EMPLEADOS LAS HACEN EN MENS TIEMPO
    override fun costoDeUnaTarea() = (salariosEmpleados() * horasTrabajoCadaEmpleado()) + (horasEstimadas * responsable.sueldoResponsable(this)) + costoInfrastructura

    // METODO ALTERNATIVO PARA SACAR EL SALARIO DE TODOS LOS EMPLEADOS
    fun salariosEmpleados()= listaEmpleados.sumBy{e -> e.sueldoPorHoraDeCadaEmpleado(this)}
    // METODO ALTERNATIVO PARA SACAR LAS HORAS DE TODOS LOS EMPLEADOS
    fun horasTrabajoCadaEmpleado()= listaEmpleados.sumBy{e-> e.horasDeTrabajo(this)}
    // METODO QUE USO EN EMPLEADO PARA SACAR LAS HORAS DE TRABAJO QUE LE CORRESPONDEN A CADA EMPLEADO
    fun cantidadEmpleadosDeTarea() = listaEmpleados.size
    override fun nominaEmpleados(lista : List<Empleado>) = (listaEmpleados.size) +1
}



  class TareasIntegracion(responsable: Empleado,  override val listaEmpleados: MutableList<Empleado>) : Tarea(){

    val subTareas = mutableListOf<Tarea>() // TAREAS QUE PUEDEN SER INTEGRACION O SIMPLES

      fun agregarTareaALista(tarea:Tarea){ // METODO ALTERNATIVO PARA PODER USARLO EN EL TEST
        subTareas.add(tarea)
    }
      // METODO PARA SACAR EL BONUS DEL RESPONSABLE
      fun bonusResponsable()= this.sumaCostoTareas() * 3 / 100
    //SUMA DE LAS HORAS ,DE TODAS LAS SUB TAREAS ,QUE SE NECESITA PARA TERMINAR UNA TAREA
      override fun horasParaTerminarUnaTarea(lista: List<Tarea>,listaE:List<Empleado>)= subTareas.sumBy { t ->t.horasParaTerminarUnaTarea(lista,listaE) } + 1 * 8

      // COSTO DE TODAS LAS TAREAS MAS EL BONUS DEL RESPONSABLE
      override fun costoDeUnaTarea() = sumaCostoTareas() + bonusResponsable()
      //SUMA DE TODOS LOS COSTOS DE TODAS LAS SUBTAREAS
      fun sumaCostoTareas()= subTareas.sumBy { t-> t.costoDeUnaTarea()  }
    // SUMA DE EL RESPONSABLE MAS LA CANTIDAD DE EMPLEADOS QUE TIENE SU PROPIA LISTA
    override fun nominaEmpleados(lista : List<Empleado>)= (lista.size) +1 // el 1 que sumamos es el responsable de tareas

}
 open class Empleado  {
     //SUELDO QUE LE TOCA POR LAS HORAS QUE TRABAJAN
    fun sueldoPorHoraDeCadaEmpleado(tarea:TareaSimples) = horasDeTrabajo(tarea) * tarea.costoInfrastructura
     // HORAS QUE LE TOCAN POR LA CANTIDAD DE EMPLEADOS QUE HAY, LAS HORAS SE DIVIDEN EN EMPLEADOS
     fun horasDeTrabajo(tarea:TareaSimples)= tarea.horasEstimadas / tarea.cantidadEmpleadosDeTarea()

}



class Responsable: Empleado() { // EL RESPONSABLE HACE TODA LA TAREA SOLO POR LO TANTO COBRA EL DOBLE DE CADA EMPLEADO
      fun sueldoResponsable(tarea:TareaSimples)= tarea.horasEstimadas * tarea.costoInfrastructura

}


