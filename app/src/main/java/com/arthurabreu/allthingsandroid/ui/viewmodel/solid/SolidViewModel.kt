import androidx.lifecycle.ViewModel
import com.arthurabreu.allthingsandroid.data.solid.SolidPrincipleState

class SolidViewModel : ViewModel() {
    val principles = listOf(
        SolidPrincipleState(
            name = "S - Single Responsibility Principle",
            description = "Uma classe deve ter apenas uma razão para mudar.",
            rightExample = """
class UserRepository {
    fun save(user: User) { /* ... */ }
}
class UserValidator {
    fun validate(user: User) { /* ... */ }
}
""".trimIndent(),
            wrongExample = """
class UserManager {
    fun save(user: User) { /* ... */ }
    fun validate(user: User) { /* ... */ }
}
""".trimIndent()
        ),
        SolidPrincipleState(
            name = "O - Open/Closed Principle",
            description = "Classes devem estar abertas para extensão, mas fechadas para modificação.",
            rightExample = """
interface Shape {
    fun area(): Double
}
class Circle(val radius: Double) : Shape {
    override fun area() = Math.PI * radius * radius
}
""".trimIndent(),
            wrongExample = """
class Shape {
    var type: String = ""
    fun area(): Double {
        if (type == "circle") { /* ... */ }
        // precisa modificar para cada novo tipo
    }
}
""".trimIndent()
        ),
        SolidPrincipleState(
            name = "L - Liskov Substitution Principle",
            description = "Subtipos devem poder substituir seus tipos base sem alterar o funcionamento do programa.",
            rightExample = """
open class Bird {
    open fun fly() { /* ... */ }
}
class Sparrow : Bird() {
    override fun fly() { /* ... */ }
}
""".trimIndent(),
            wrongExample = """
open class Bird {
    open fun fly() { /* ... */ }
}
class Ostrich : Bird() {
    override fun fly() { throw Exception("Não pode voar") }
}
""".trimIndent()
        ),
        SolidPrincipleState(
            name = "I - Interface Segregation Principle",
            description = "Não obrigue uma classe a implementar interfaces que ela não usa.",
            rightExample = """
interface Printer {
    fun print()
}
interface Scanner {
    fun scan()
}
class MultiFunctionPrinter : Printer, Scanner {
    override fun print() { /* ... */ }
    override fun scan() { /* ... */ }
}
""".trimIndent(),
            wrongExample = """
interface Machine {
    fun print()
    fun scan()
}
class OldPrinter : Machine {
    override fun print() { /* ... */ }
    override fun scan() { /* não implementa */ }
}
""".trimIndent()
        ),
        SolidPrincipleState(
            name = "D - Dependency Inversion Principle",
            description = "Dependa de abstrações, não de implementações concretas.",
            rightExample = """
interface Database {
    fun save(data: String)
}
class SqlDatabase : Database {
    override fun save(data: String) { /* ... */ }
}
class DataManager(private val db: Database) {
    fun saveData(data: String) = db.save(data)
}
""".trimIndent(),
            wrongExample = """
class DataManager {
    private val db = SqlDatabase()
    fun saveData(data: String) = db.save(data)
}
""".trimIndent()
        )
    )
}