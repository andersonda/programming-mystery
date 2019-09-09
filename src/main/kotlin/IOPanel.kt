import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import tornadofx.*

class IOPanel : View() {

    var outputLine = 0
        private set
    val navigation: NavigationView by inject()
    val question: QuestionView by inject()
    val teams: TeamView by inject()
    val scores: ScoresView by inject()

    val prompt = textflow{
        textAlignment = TextAlignment.CENTER
        padding = Insets(0.0, 0.0, 8.0, 0.0)
        text("What is the "){
            font = Font(18.0)
        }
        text(outputLine.english()){
            font = Font(18.0)
            fill = Color.BLUE
        }
        text(" line of output?"){
           font = Font(18.0)
        }
    }

    override val root = scrollpane()

    init {
        val vb = vbox {
            paddingAll = 16
            alignment = Pos.TOP_CENTER
        }
        vb += imageview("incognito-512.png"){
            fitWidth = 256.0
            fitHeight = 256.0
        }
        vb += label("Programming Mystery"){
            font = Font(20.0)
        }
        vb += label("Version 0.1 ALPHA"){
            font = Font(10.0)
        }
        vb += separator(Orientation.HORIZONTAL){
            paddingAll = 16
        }
        vb += prompt
        vb += teams.root
        vb += separator(Orientation.HORIZONTAL){
            paddingAll = 16
        }
        vb += navigation.root
        vb += separator(Orientation.HORIZONTAL){
            paddingAll = 16
        }
        vb += label("Scores"){
            font = Font(20.0)
            paddingAll = 16.0
        }
        vb += scores.root

        root.content = vb
    }

    fun nextOutputLine(){
        val size = question.answers!!.size
        outputLine = (outputLine + 1) % size
        if(outputLine < 0) outputLine += size
        (prompt.children[1] as Text).text = outputLine.english()
    }

    fun prevOutputLine(){
        val size = question.answers!!.size
        outputLine = (outputLine - 1) % size
        if(outputLine < 0) outputLine += size
        (prompt.children[1] as Text).text = outputLine.english()
    }
}
