package com.arthurabreu.allthingsandroid.ui.viewmodel.designprinciple

import androidx.lifecycle.ViewModel
import com.arthurabreu.allthingsandroid.data.designprinciple.DesignPrincipleState

class DesignPrincipleViewModel : ViewModel() {
    val principles = listOf(
        DesignPrincipleState(
            name = "Separation of Concerns",
            description = "Separe responsabilidades em diferentes camadas ou classes.",
            rightExample = """
class UserRepository { /* acesso a dados */ }
class UserViewModel(val repo: UserRepository) { /* lógica de apresentação */ }
""".trimIndent(),
            wrongExample = """
class UserViewModel {
    fun saveUser(user: User) { /* lógica de dados e apresentação misturadas */ }
}
""".trimIndent()
        ),
        DesignPrincipleState(
            name = "DRY (Don't Repeat Yourself)",
            description = "Evite duplicação de código.",
            rightExample = """
fun showToast(context: Context, msg: String) { /* ... */ }
// Reutilize showToast em vários lugares
""".trimIndent(),
            wrongExample = """
fun showToast1(context: Context) { /* ... */ }
fun showToast2(context: Context) { /* ... */ }
// Código duplicado
""".trimIndent()
        ),
        DesignPrincipleState(
            name = "KISS (Keep It Simple, Stupid)",
            description = "Prefira soluções simples e diretas.",
            rightExample = """
fun sum(a: Int, b: Int) = a + b
""".trimIndent(),
            wrongExample = """
fun sum(a: Int, b: Int): Int {
    var result = 0
    for (i in 1..a) { result++ }
    for (i in 1..b) { result++ }
    return result
}
""".trimIndent()
        ),
        DesignPrincipleState(
            name = "YAGNI (You Aren't Gonna Need It)",
            description = "Não implemente funcionalidades desnecessárias.",
            rightExample = """
// Implemente apenas o necessário para o requisito atual
""".trimIndent(),
            wrongExample = """
fun futureFeature() { /* código para algo que não foi solicitado */ }
""".trimIndent()
        ),
        DesignPrincipleState(
            name = "Fator Composição sobre Herança",
            description = "Prefira compor objetos ao invés de herdar.",
            rightExample = """
class Engine
class Car(val engine: Engine)
""".trimIndent(),
            wrongExample = """
open class Engine
class Car : Engine()
""".trimIndent()
        ),
        DesignPrincipleState(
            name = "Singleton",
            description = "Garante que uma classe tenha apenas uma instância e fornece um ponto global de acesso.",
            rightExample = """
object DatabaseManager {
    fun connect() { /* ... */ }
}
""".trimIndent(),
            wrongExample = """
class DatabaseManager {
    fun connect() { /* ... */ }
}
// Pode criar várias instâncias, não é singleton
""".trimIndent()
        ),
        DesignPrincipleState(
            name = "Factory",
            description = "Cria objetos sem expor a lógica de criação ao cliente.",
            rightExample = """
interface Animal { fun speak(): String }
class Dog : Animal { override fun speak() = "Woof" }
class Cat : Animal { override fun speak() = "Meow" }
object AnimalFactory {
    fun create(type: String): Animal =
        if (type == "dog") Dog() else Cat()
}
""".trimIndent(),
            wrongExample = """
if (type == "dog") {
    val animal = Dog()
} else {
    val animal = Cat()
}
// Lógica de criação espalhada pelo código
""".trimIndent()
        ),
        DesignPrincipleState(
            name = "Observer",
            description = "Permite que objetos sejam notificados sobre mudanças em outros objetos.",
            rightExample = """
interface Observer { fun update() }
class Data : Observable() { /* ... */ }
class Screen : Observer { override fun update() { /* ... */ } }
""".trimIndent(),
            wrongExample = """
class Data {
    var screen: Screen? = null
    fun change() { screen?.refresh() }
}
// Acoplamento direto, sem padrão Observer
""".trimIndent()
        ),
        DesignPrincipleState(
            name = "Facade",
            description = "Fornece uma interface simplificada para um conjunto de interfaces de um subsistema.",
            rightExample = """
class MediaFacade {
    fun playAudio() { /* ... */ }
    fun playVideo() { /* ... */ }
}
""".trimIndent(),
            wrongExample = """
class AudioPlayer { fun play() { /* ... */ } }
class VideoPlayer { fun play() { /* ... */ } }
// Cliente precisa lidar com múltiplas interfaces
""".trimIndent()
        ),
        DesignPrincipleState(
            name = "Adapter",
            description = "Permite que interfaces incompatíveis trabalhem juntas.",
            rightExample = """
interface OldLogger { fun log(msg: String) }
class NewLogger { fun write(msg: String) }
class LoggerAdapter(val logger: NewLogger) : OldLogger {
    override fun log(msg: String) = logger.write(msg)
}
""".trimIndent(),
            wrongExample = """
val logger = NewLogger()
logger.log("mensagem")
// Não funciona, pois a interface é diferente
""".trimIndent()
        ),
        DesignPrincipleState(
            name = "Dependency Injection",
            description = "Permite injetar dependências ao invés de criá-las diretamente.",
            rightExample = """
class Repository(val api: ApiService)
class ApiService
val repo = Repository(ApiService())
""".trimIndent(),
            wrongExample = """
class Repository {
    val api = ApiService()
}
// Repository cria sua própria dependência
""".trimIndent()
        )
    )
}