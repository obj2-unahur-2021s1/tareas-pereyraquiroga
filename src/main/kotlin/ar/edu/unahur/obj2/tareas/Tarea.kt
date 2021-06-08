package ar.edu.unahur.obj2.tareas

abstract class Tarea(){


    // CADA TAREA TIENE UNA LISTA DE EMPLEADOS
    open val listaEmpleados= mutableListOf<Empleado>()
     abstract fun horasParaTerminarUnaTarea() : Int
    abstract  fun costoDeUnaTarea(): Int

}

class TareaSimples(val horasEstimadas: Int , val responsable: Responsable, val costoInfrastructura : Int) : Tarea(){
    override val listaEmpleados= mutableListOf<Empleado>()

    override fun horasParaTerminarUnaTarea()= horasEstimadas / listaEmpleados.size  // PORQUE DOS EMPLEADOS LAS HACEN EN MENS TIEMPO
    override fun costoDeUnaTarea() = (salariosEmpleados() * horasTrabajoCadaEmpleado()) + (horasEstimadas * responsable.sueldoResponsable(this)) + costoInfrastructura

    // METODO ALTERNATIVO PARA SACAR EL SALARIO DE TODOS LOS EMPLEADOS
    fun salariosEmpleados()= listaEmpleados.sumBy{e -> e.sueldoPorHoraDeCadaEmpleado(this)}
    // METODO ALTERNATIVO PARA SACAR LAS HORAS DE TODOS LOS EMPLEADOS
    fun horasTrabajoCadaEmpleado()= listaEmpleados.sumBy{e-> e.horasDeTrabajo(this)}
    // METODO QUE USO EN EMPLEADO PARA SACAR LAS HORAS DE TRABAJO QUE LE CORRESPONDEN A CADA EMPLEADO
    fun cantidadEmpleadosDeTarea() = listaEmpleados.size

}



  class TareasIntegracion(responsable: Empleado) : Tarea(){
      override val listaEmpleados = mutableListOf<Empleado>()
    val subTareas = mutableListOf<Tarea>() // TAREAS QUE PUEDEN SER INTEGRACION O SIMPLES
    // METODO PARA SACAR EL BONUS DEL RESPONSABLE
      fun bonusResponsable()= this.sumaCostoTareas() * 3 / 100
    //SUMA DE LAS HORAS ,DE TODAS LAS SUB TAREAS ,QUE SE NECESITA PARA TERMINAR UNA TAREA
      override fun horasParaTerminarUnaTarea()= ( subTareas.sumBy { t ->t.horasParaTerminarUnaTarea() }) + 1 * 8
      // COSTO DE TODAS LAS TAREAS MAS EL BONUS DEL RESPONSABLE
      override fun costoDeUnaTarea() = sumaCostoTareas() + bonusResponsable()
      //SUMA DE TODOS LOS COSTOS DE TODAS LAS SUBTAREAS
      fun sumaCostoTareas()= subTareas.sumBy { t-> t.costoDeUnaTarea()  }
    // SUMA DE EL RESPONSABLE MAS LA CANTIDAD DE EMPLEADOS
    fun nominaEmpleados(tarea:Tarea,lista : List<Empleado>)= (lista.size) +1 // el 1 que sumamos es el responsable de tareas

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


