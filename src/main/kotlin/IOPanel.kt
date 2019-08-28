import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*

class IOPanel : View() {

    private var outputLine = 1
    val navigation: NavigationView by inject()
    val teams: TeamView by inject()
    val prompt = label("What is ${outputLine.english()} line of output?") {
        font = Font(16.0)
        alignment = Pos.CENTER
        padding = Insets(0.0, 0.0, 8.0, 0.0)
    }

    override val root = vbox {
        paddingAll = 16
    }

    init {
        root += prompt
        root += teams.root
        root += navigation.root
    }

    fun nextOutputLine(){
        outputLine += 1
        prompt.text = "What is ${outputLine.english()} line of output?"
    }

    fun prevOutputLine(){
        outputLine -= 1
        prompt.text = "What is ${outputLine.english()} line of output?"
    }

    companion object {
        private fun Int.english() = listOf("first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth")[this - 1]
    }
}
