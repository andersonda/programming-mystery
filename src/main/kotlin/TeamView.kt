import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.text.Font
import javafx.scene.text.Text
import tornadofx.*

class TeamView : View() {

    val question: QuestionView by inject()
    val io: IOPanel by inject()

    override val root = gridpane{
        hgap = 8.0
    }

    init {
        populateTeams(listOf("Team 1", "Team 2", "Team 3" ,"Team 4"))
    }


    fun populateTeams(teams:List<String>){
        root.removeAllRows()
        teams.forEachIndexed { index, team ->
            val text = Text(team)
            text.font = Font(16.0)
            val image = ImageView("question-mark.png")
            image.fitWidth = 16.0
            image.fitHeight = 16.0

            root.add(text, 0, index)
            root.add(TextField(), 1, index)
            root.add(image, 2, index)
        }
    }

    fun checkAnswers() = root.children.forEachIndexed { index, node ->
        if(node is TextField){
            (root.children[index + 1] as ImageView).image = when(node.text){
                question.answers!![io.outputLine] -> Image("checkmark.png")
                else -> Image("x-mark.png")
            }
            // TODO: handle scoring
        }
    }

    fun resetResponses() = root.children.forEach {
        if(it is ImageView){
            it.image = Image("question-mark.png")
        }
        else if(it is TextField){
            it.text = ""
        }
    }
}
