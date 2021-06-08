package ar.edu.unahur.obj2.tareas

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class TareaTest : DescribeSpec({
  val empleadoResponsable=Responsable()
  val empleado1= Empleado()
  val empleado2=Empleado()

  val tareaSimple=TareaSimples(8,empleadoResponsable,400)

  val tareaIntegracion=TareasIntegracion(empleadoResponsable)
  val listaEmpleados= mutableListOf<Empleado>()

  val listaSubTareas= mutableListOf<Tarea>()

  describe("Una tarea Integracion ") {
    listaEmpleados.add(empleado1)
    listaEmpleados.add(empleado2)
    listaEmpleados.size.shouldBe(2)
    tareaIntegracion.nominaEmpleados(tareaIntegracion,listaEmpleados).shouldBe(3)
  }
})
