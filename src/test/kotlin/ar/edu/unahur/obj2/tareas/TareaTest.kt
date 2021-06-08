package ar.edu.unahur.obj2.tareas

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class TareaTest : DescribeSpec({
  val empleadoResponsable=Responsable()
  val empleado1= Empleado()
  val empleado2=Empleado()

  val listaEmpleadosSimple= mutableListOf<Empleado>()
  val tareaSimple=TareaSimples(8,empleadoResponsable,400,listaEmpleadosSimple)

  val listaEmpleadosInt= mutableListOf<Empleado>()
  val tareaIntegracion=TareasIntegracion(empleadoResponsable,listaEmpleadosInt)


  val listaSubTareas= mutableListOf<Tarea>()

  describe(" Nomina  tarea Integracion ") {
    listaEmpleadosInt.add(empleado1)
    listaEmpleadosInt.add(empleado2)
    listaEmpleadosInt.size.shouldBe(2)
    tareaIntegracion.nominaEmpleados(tareaIntegracion,listaEmpleadosInt).shouldBe(3)
  }

  describe("Nomina tarea Simple"){
    listaEmpleadosSimple.add(empleado1)
    listaEmpleadosSimple.add(empleado2)
    listaEmpleadosSimple.add(empleado1)

    tareaSimple.nominaEmpleados(tareaSimple,listaEmpleadosSimple).shouldBe(4)
  }

  describe("Horas necesarias para terminar una tarea"){ // FALTARIA HACER PROBANDO CON AGREGAR COMO SUBTAREA A TAREA INTEGRACION
    listaSubTareas.add(tareaSimple)
    listaEmpleadosSimple.add(empleado1)
    listaEmpleadosSimple.add(empleado2)

    tareaSimple.horasParaTerminarUnaTarea(listaSubTareas,listaEmpleadosSimple).shouldBe(4)

  }

  describe("Costo de una tarea"){
    val tareaSimple2 = TareaSimples(4,empleadoResponsable,200,listaEmpleadosSimple)
    listaSubTareas.add(tareaSimple)
    listaSubTareas.add(tareaSimple2)
    tareaIntegracion.costoDeUnaTarea().shouldBe()
  }

})
